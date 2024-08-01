package devruibin.github.azdev.controller;

import devruibin.github.azdev.data.Detail;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
public class DetailController {
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

    enum Category {
        WARNING,
        EXPLANATION,
        NOTE,
        UNKNOWN
    }
}
