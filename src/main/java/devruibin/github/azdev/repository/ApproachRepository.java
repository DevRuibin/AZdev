package devruibin.github.azdev.repository;

import devruibin.github.azdev.data.Approach;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApproachRepository extends JpaRepository<Approach, Long> {
    Iterable<Approach> findAllByTaskIdOrderByVoteCountDescCreatedAtDesc(Long taskId);
}
