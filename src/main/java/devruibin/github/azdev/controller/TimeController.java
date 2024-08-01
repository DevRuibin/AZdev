package devruibin.github.azdev.controller;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class TimeController {
    @QueryMapping
    public String currentTime(){
        return "2021-09-01 12:00:00";
    }
}


