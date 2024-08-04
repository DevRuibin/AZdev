package devruibin.github.azdev.service;

import devruibin.github.azdev.controller.dto.*;
import devruibin.github.azdev.data.Approach;
import devruibin.github.azdev.data.User;
import devruibin.github.azdev.repository.ApproachRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApproachService {
    private final ApproachRepository approachRepository;
    private final DetailService detailService;
    private final Sinks.Many<Approach> voteSink = Sinks.many().multicast().onBackpressureBuffer();


    public  Flux<Approach> getVoteChanges(Long taskId) {
        return voteSink.asFlux().filter(approach -> approach.getTaskId().equals(taskId));
    }

    private void publishVoteChange(Approach approach) {
        voteSink.tryEmitNext(approach);
    }

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

    public ApproachPayloadDTO approachVote(Long approachId, ApproachVoteInputDTO input, Long userId) {
        Approach approach = approachRepository.findById(approachId).orElse(null);
        if(approach == null){
            return new ApproachPayloadDTO(List.of(new UserErrorDTO("Approach not found")), null);
        }
        if(approach.getUserId().equals(userId)){
            return new ApproachPayloadDTO(List.of(new UserErrorDTO("You can't vote for your own approach")), null);
        }
        boolean up = input.up();
        int voteCount = approach.getVoteCount();
        if(up){
            voteCount++;
            publishVoteChange(approach);
        }else{
            // todo: enable down vote
            return new ApproachPayloadDTO(List.of(new UserErrorDTO("Down vote is not supported yet")), null);
        }
        approach.setVoteCount(voteCount);
        approachRepository.save(approach);
        return new ApproachPayloadDTO(null, approach);
    }
}
