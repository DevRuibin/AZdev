package devruibin.github.azdev.controller;

import devruibin.github.azdev.controller.dto.ApproachInputDTO;
import devruibin.github.azdev.controller.dto.ApproachPayloadDTO;
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
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
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
            return new ApproachPayloadDTO(new UserErrorDTO("User not found"), null);
        }
        return approachService.approachCreate(taskID, input, userId);
    }
}
