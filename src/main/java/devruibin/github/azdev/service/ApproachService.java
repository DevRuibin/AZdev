package devruibin.github.azdev.service;

import devruibin.github.azdev.controller.dto.ApproachDetailInputDTO;
import devruibin.github.azdev.controller.dto.ApproachInputDTO;
import devruibin.github.azdev.controller.dto.ApproachPayloadDTO;
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
    private final DetailService detailService;

    public Iterable<Approach> findAllByTaskId(Long taskId) {
        return approachRepository.findAllByTaskIdOrderByVoteCountDescCreatedAtDesc(taskId);
    }

    public List<Approach> findByTerm(String term, Long userId) {
        return approachRepository.findByTerm(term, userId).orElseGet(List::of);
    }

    public ApproachPayloadDTO approachCreate(Long taskID, ApproachInputDTO input, Long userId) {
         String ApproachContent = input.content();
        List<ApproachDetailInputDTO> detailList = input.detailList();
        Approach approach = Approach.builder()
                .userId(userId)
                .taskId(taskID)
                .content(ApproachContent)
                .voteCount(0)
                .build();
        Approach savedApproach = approachRepository.save(approach);

        detailList.forEach(detail -> {
            detailService.createDetail(detail, savedApproach.getId());
        });

    return new ApproachPayloadDTO(null, savedApproach);
    }
}
