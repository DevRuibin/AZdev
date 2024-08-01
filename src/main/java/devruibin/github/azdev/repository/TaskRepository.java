package devruibin.github.azdev.repository;

import devruibin.github.azdev.data.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
    Optional<List<Task>> findTop100ByIsPrivateOrderByCreatedAtDesc(boolean isPrivate);
}
