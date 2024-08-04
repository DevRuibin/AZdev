package devruibin.github.azdev.service;

import devruibin.github.azdev.controller.dto.TaskInputDTO;
import devruibin.github.azdev.controller.dto.TaskPayloadDTO;
import devruibin.github.azdev.data.Task;
import devruibin.github.azdev.data.User;
import devruibin.github.azdev.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {
    private final TaskRepository taskRepository;
    private final Sinks.Many<List<Task>> tasksSink = Sinks.many().multicast().onBackpressureBuffer();


    public List<Task> findTop100ByIsPrivateOrderByCreatedAtDesc() {
        return taskRepository.findTop100ByIsPrivateOrderByCreatedAtDesc(false).orElse(null);
    }

    public Task getTask(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    public Task getTaskWithUserId(Long id, Long userId) {
        return taskRepository.findByIdAndUserId(id, userId).orElse(null);
    }

    public List<Task> findAllByUserId(Long userId) {
        return taskRepository.findAllByUserId(userId).orElseGet(List::of);
    }

    public List<Task> findAllByTerm(String term, Long userId) {
        return taskRepository.findAllByTerm(term, userId).orElseGet(List::of);
    }

    public void saveTask(Task task) {
        taskRepository.save(task);
    }

    public TaskPayloadDTO taskCreate(TaskInputDTO input, User u) {
        log.info("User found: {}", u);
        Task task = Task.builder()
                .content(input.content())
                .tags( String.join(",", input.tags()))
                .userId(u.getId())
                .isPrivate(input.isPrivate())
                .build();
        log.info("Task created: {}", task);
        saveTask(task);
        publishMainTaskChange(findTop100ByIsPrivateOrderByCreatedAtDesc());
        return TaskPayloadDTO.builder()
                .errors(null)
                .task(task)
                .build();

    }


    public Flux<List<Task>> getTaskChange () {
        log.info("getTaskChange");
        Flux<List<Task>> flux = tasksSink.asFlux();
        log.info("getTaskChange: {}", flux);
        return flux;
    }

    private void publishMainTaskChange(List<Task> tasks) {
        log.info("publishMainTaskChange: {}", tasks);
        tasksSink.tryEmitNext(tasks);
    }
}
