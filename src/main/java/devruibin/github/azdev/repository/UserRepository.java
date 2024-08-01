package devruibin.github.azdev.repository;

import devruibin.github.azdev.data.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> getUserById(Long id);
}
