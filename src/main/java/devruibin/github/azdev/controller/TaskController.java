package devruibin.github.azdev.controller;

import devruibin.github.azdev.data.Approach;
import devruibin.github.azdev.data.Task;
import devruibin.github.azdev.data.User;
import devruibin.github.azdev.repository.UserRepository;
import devruibin.github.azdev.service.ApproachService;
import devruibin.github.azdev.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class TaskController {
    private final TaskService taskService;
    private final UserRepository userRepository;
    private final ApproachService approachService;

    @QueryMapping
    public List<Task> taskMainList(){
        List<Task> tasks =  taskService.findTop100ByIsPrivateOrderByCreatedAtDesc();
        log.info("taskMainList: {}", tasks);
        return tasks;
    }

    @QueryMapping
    public Task taskInfo(@Argument("id") Long id){
        return taskService.getTask(id);
    }

    @SchemaMapping(typeName = "Task", field = "author")
    public User getUser(Task task) {
        return userRepository.getUserById(task.getUserId()).orElse(null);
    }

    @SchemaMapping(typeName = "Task", field = "approachList")
    public Iterable<Approach> getApproachList(Task task) {
        return approachService.findAllByTaskId(task.getId());
    }

    @SchemaMapping(typeName = "Task", field = "tags")
    public List<String> getTags(Task task) {
        return Arrays.stream(task.getTags().split(",")).toList();
    }
}
