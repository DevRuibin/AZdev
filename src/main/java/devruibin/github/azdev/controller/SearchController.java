package devruibin.github.azdev.controller;

import devruibin.github.azdev.controller.dto.SearchResultItemDTO;
import devruibin.github.azdev.data.User;
import devruibin.github.azdev.service.SearchService;
import devruibin.github.azdev.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.ContextValue;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class SearchController {
    private final SearchService searchService;
    private final HttpServletRequest request;
    private final UserService userService;
    @QueryMapping
    public List<SearchResultItemDTO> search(@Argument String term){
        String authorization = request.getHeader("Authorization");
        Optional<User> user = userService.getUserByToken(authorization);
        log.info("Authorization: {}", authorization);
        return searchService.search(term, user);
    }

}
