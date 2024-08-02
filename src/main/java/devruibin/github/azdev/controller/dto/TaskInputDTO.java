package devruibin.github.azdev.controller.dto;

import java.util.List;

public record TaskInputDTO(String content,
                           List<String> tags,
                           Boolean isPrivate) {
}
