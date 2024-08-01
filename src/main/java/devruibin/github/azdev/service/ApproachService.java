package devruibin.github.azdev.service;

import devruibin.github.azdev.data.Approach;
import devruibin.github.azdev.repository.ApproachRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApproachService {
    private final ApproachRepository approachRepository;

    public Iterable<Approach> findAllByTaskId(Long taskId) {
        return approachRepository.findAllByTaskIdOrderByVoteCountDescCreatedAtDesc(taskId);
    }
}
