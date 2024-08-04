package devruibin.github.azdev.controller;

import devruibin.github.azdev.controller.dto.ApproachInputDTO;
import devruibin.github.azdev.controller.dto.ApproachPayloadDTO;
import devruibin.github.azdev.controller.dto.ApproachVoteInputDTO;
import devruibin.github.azdev.controller.dto.UserErrorDTO;
import devruibin.github.azdev.data.Approach;
import devruibin.github.azdev.data.Detail;
import devruibin.github.azdev.data.Task;
import devruibin.github.azdev.data.User;
import devruibin.github.azdev.service.ApproachService;
import devruibin.github.azdev.service.DetailService;
import devruibin.github.azdev.service.TaskService;
import devruibin.github.azdev.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ApproachController {
    private final DetailService detailService;
    private final UserService userService;
    private final TaskService taskService;
    private final ApproachService approachService;
    private final HttpServletRequest httpServletRequest;

    @SchemaMapping(typeName = "Approach", field = "detailList")
    public Iterable<Detail> getDetailList(Approach approach) {
        return detailService.getDetails();
    }

    @SchemaMapping(typeName = "Approach", field = "author")
    public User getAuthor(Approach approach) {
        return userService.getUser(approach.getUserId());
    }

    @SchemaMapping(typeName = "Approach", field = "task")
    public Task getTask(Approach approach) {
        return taskService.getTask(approach.getTaskId());
    }

    @MutationMapping("approachCreate")
    public ApproachPayloadDTO approachCreate(@Argument("taskId") Long taskID, @Argument("input") ApproachInputDTO input){
        String token = httpServletRequest.getHeader("Authorization");
        Long userId = userService.getUserByToken(token).map(User::getId).orElse(null);
        if(userId == null){
            return new ApproachPayloadDTO(List.of(new UserErrorDTO("User not found")), null);
        }
        return approachService.approachCreate(taskID, input, userId);
    }

    @MutationMapping
    public ApproachPayloadDTO approachVote(@Argument Long approachId, @Argument ApproachVoteInputDTO input ){
        String token = httpServletRequest.getHeader("Authorization");
        Long userId = userService.getUserByToken(token).map(User::getId).orElse(null);
        if(userId == null){
            return new ApproachPayloadDTO(List.of(new UserErrorDTO("User not found")), null);
        }
        return approachService.approachVote(approachId, input, userId);

    }

    @SubscriptionMapping("voteChanged")
    public Flux<Approach> voteChanged(@Argument Long taskId){
        log.info("voteChanged taskId: {}", taskId);
        return approachService.getVoteChanges(taskId);
    }
}
