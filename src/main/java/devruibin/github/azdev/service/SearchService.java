package devruibin.github.azdev.service;

import devruibin.github.azdev.controller.dto.SearchResultItemDTO;
import devruibin.github.azdev.data.Approach;
import devruibin.github.azdev.data.Task;
import devruibin.github.azdev.data.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final ApproachService approachService;
    private final TaskService taskService;
    public List<SearchResultItemDTO> search(String term, Optional<User> user) {
        List<Approach> approaches = approachService.findByTerm(term, user.orElseGet(User::new).getId());
        List<Task> tasks = taskService.findAllByTerm(term, user.orElseGet(User::new).getId());
        ArrayList<SearchResultItemDTO> searchResultItemDTOS = new ArrayList<>();
        searchResultItemDTOS.addAll(approaches);
        searchResultItemDTOS.addAll(tasks);
        return searchResultItemDTOS;
    }
}
