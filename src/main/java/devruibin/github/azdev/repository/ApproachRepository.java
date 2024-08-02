package devruibin.github.azdev.repository;

import devruibin.github.azdev.data.Approach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ApproachRepository extends JpaRepository<Approach, Long> {
    Iterable<Approach> findAllByTaskIdOrderByVoteCountDescCreatedAtDesc(Long taskId);

    @Query(value = """
    SELECT a FROM approaches a, tasks t
    WHERE t.id = a.taskId and  (t.UserId = :userId or t.isPrivate = false)
       and a.content LIKE %:term%
    """)
    Optional<List<Approach>> findByTerm(@Param("term") String term, @Param("userId") Long userId);
}
