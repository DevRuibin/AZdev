package devruibin.github.azdev.controller;

import devruibin.github.azdev.controller.dto.UserErrorDTO;
import devruibin.github.azdev.controller.dto.UserInputDTO;
import devruibin.github.azdev.controller.dto.UserPayloadDTO;
import devruibin.github.azdev.data.User;
import devruibin.github.azdev.repository.UserRepository;
import devruibin.github.azdev.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private  final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final HttpServletRequest request;

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
}
