package devruibin.github.azdev.service;

import devruibin.github.azdev.data.User;
import devruibin.github.azdev.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username).orElse(null);
    }

    public void GenerateToken(User user) {
        String authToken =   passwordEncoder.encode(generateUUID().toString());
        user.setAuthToken(authToken);
    }

    @Transactional
    public User updateToken(User user) {
        GenerateToken(user);
        log.info("Updating token for user: {}", user);
        return userRepository.save(user);
    }

    private UUID generateUUID() {
        return UUID.randomUUID();
    }
}
