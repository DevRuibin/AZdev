package devruibin.github.azdev.service;

import devruibin.github.azdev.controller.dto.TaskInputDTO;
import devruibin.github.azdev.controller.dto.TaskPayloadDTO;
import devruibin.github.azdev.data.Task;
import devruibin.github.azdev.data.User;
import devruibin.github.azdev.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {
    private final TaskRepository taskRepository;

    public List<Task> findTop100ByIsPrivateOrderByCreatedAtDesc() {
        return taskRepository.findTop100ByIsPrivateOrderByCreatedAtDesc(false).orElse(null);
    }

    public Task getTask(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    public Task getTaskWithUserId(Long id, Long userId) {
        return taskRepository.findByIdAndUserId(id, userId).orElse(null);
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
                .UserId(u.getId())
                .isPrivate(input.isPrivate())
                .build();
        log.info("Task created: {}", task);
        saveTask(task);
        return TaskPayloadDTO.builder()
                .errors(null)
                .task(task)
                .build();

    }
}
