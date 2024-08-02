package devruibin.github.azdev.service;

import devruibin.github.azdev.data.Task;
import devruibin.github.azdev.data.User;
import devruibin.github.azdev.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
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
}
