package devruibin.github.azdev.repository;

import devruibin.github.azdev.data.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<List<Task>> findTop100ByIsPrivateOrderByCreatedAtDesc(boolean isPrivate);

    @Query("SELECT t FROM tasks t" +
            " WHERE (t.isPrivate = false or t.UserId = :userId) and (t.content LIKE %:term% OR t.tags LIKE %:term%)")
    Optional<List<Task>> findAllByTerm(@Param("term") String term, @Param("userId") Long userId);
}
