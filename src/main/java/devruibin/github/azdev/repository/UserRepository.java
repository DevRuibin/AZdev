package devruibin.github.azdev.repository;

import devruibin.github.azdev.data.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getUserById(Long id);

    Optional<User> getUserByUsername(String username);


}
