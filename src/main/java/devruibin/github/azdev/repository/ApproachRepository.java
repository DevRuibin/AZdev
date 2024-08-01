package devruibin.github.azdev.repository;

import devruibin.github.azdev.data.Approach;
import org.springframework.data.repository.CrudRepository;

public interface ApproachRepository extends CrudRepository<Approach, Long> {
    Iterable<Approach> findAllByTaskIdOrderByVoteCountDescCreatedAtDesc(Long taskId);
}
