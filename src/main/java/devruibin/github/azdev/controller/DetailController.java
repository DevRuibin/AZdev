package devruibin.github.azdev.controller;

import devruibin.github.azdev.data.Detail;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import devruibin.github.azdev.controller.dto.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class DetailController {
    @SchemaMapping(typeName = "ApproachDetail", field = "content")
    public String getContent(Detail detail) {
        List<String> explanations = detail.getExplanations();
        List<String> notes = detail.getNotes();
        List<String> warnings = detail.getWarnings();

        List<List<String>> allContent = new ArrayList<>();
        allContent.add(Objects.requireNonNullElseGet(explanations, ArrayList::new));
        allContent.add(Objects.requireNonNullElseGet(notes, ArrayList::new));
        allContent.add(Objects.requireNonNullElseGet(warnings, ArrayList::new));
        return allContent.toString();



    }

    @SchemaMapping(typeName = "ApproachDetail", field = "category")
    public String getCategory(Detail detail) {
        if(detail.getWarnings() != null) {
            return Category.WARNING.name();
        }
        if(detail.getExplanations() != null) {
            return Category.EXPLANATION.name();
        }
        if(detail.getNotes() != null) {
            return Category.NOTE.name();
        }
        return Category.UNKNOWN.name();
    }


}
