package devruibin.github.azdev.service;

import devruibin.github.azdev.data.Approach;
import devruibin.github.azdev.repository.ApproachRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApproachService {
    private final ApproachRepository approachRepository;

    public Iterable<Approach> findAllByTaskId(Long taskId) {
        return approachRepository.findAllByTaskIdOrderByVoteCountDescCreatedAtDesc(taskId);
    }

    public List<Approach> findByTerm(String term, Long userId) {
        return approachRepository.findByTerm(term, userId).orElseGet(List::of);
    }
}
