package devruibin.github.azdev.controller;

import devruibin.github.azdev.data.User;
import devruibin.github.azdev.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class UserController {
    private  final UserRepository userRepository;
    @SchemaMapping(typeName = "User", field = "name")
    public String getUserName(User user) {
        return user == null ? "" : user.getFirstname() + " " + user.getLastname();
    }
}
