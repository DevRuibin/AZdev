package devruibin.github.azdev.controller;

import devruibin.github.azdev.controller.dto.UserDeletePayloadDTO;
import devruibin.github.azdev.controller.dto.UserErrorDTO;
import devruibin.github.azdev.controller.dto.UserInputDTO;
import devruibin.github.azdev.controller.dto.UserPayloadDTO;
import devruibin.github.azdev.data.Task;
import devruibin.github.azdev.data.User;
import devruibin.github.azdev.repository.UserRepository;
import devruibin.github.azdev.service.TaskService;
import devruibin.github.azdev.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private  final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final HttpServletRequest request;
    private final TaskService taskService;

    @SchemaMapping(typeName = "User", field = "name")
    public String getUserName(User user) {
        return user == null ? "" : user.getFirstname() + " " + user.getLastname();
    }

    @MutationMapping("userCreate")
    public UserPayloadDTO userCreate(@Argument UserInputDTO input) {
        User user = User.builder()
                .username(input.username())
                .password(passwordEncoder.encode(input.password()))
                .firstname(input.firstName())
                .lastname(input.lastName())
                .build();
        userService.GenerateToken(user);
        if(userRepository.getUserByUsername(user.getUsername()).isPresent()) {
            return new UserPayloadDTO(List.of(new UserErrorDTO("Username already exists")), null, null);
        }
        return new UserPayloadDTO(null, userRepository.save(user), user.getAuthToken());
    }

    @MutationMapping("userLogin")
    public UserPayloadDTO userLogin(@Argument UserInputDTO input) {
        User user = userRepository.getUserByUsername(input.username()).orElse(null);
        if(user == null || !passwordEncoder.matches(input.password(), user.getPassword())) {
            return new UserPayloadDTO(List.of(new UserErrorDTO("Invalid username or password")), null, null);
        }
        user = userService.updateToken(user);
        return new UserPayloadDTO(null, user, user.getAuthToken());
    }

    @QueryMapping
    public User me() {
        String token = request.getHeader("Authorization");
        return userService.getUserByToken(token).orElse(null);
    }

    @SchemaMapping(typeName = "User", field = "taskList")
    public List<Task> getTaskList(User user) {
        return taskService.findAllByUserId(user.getId());
    }

    @MutationMapping("userDelete")
    public UserDeletePayloadDTO userDelete() {
        String token = request.getHeader("Authorization");
        User user = userService.getUserByToken(token).orElse(null);
        if(user == null) {
            return new UserDeletePayloadDTO(List.of(new UserErrorDTO("User not found")), null);
        }
        log.info("Deleting user: {}", user);
        userService.deleteUser(user.getId());
        log.info("User deleted: {}", user);
        return new UserDeletePayloadDTO(null, user.getId());
    }
}
