package devruibin.github.azdev.service;

import devruibin.github.azdev.data.User;
import devruibin.github.azdev.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
