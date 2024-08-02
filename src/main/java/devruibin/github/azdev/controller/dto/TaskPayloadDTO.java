package devruibin.github.azdev.controller.dto;

import devruibin.github.azdev.data.Task;
import lombok.Builder;

import java.util.List;

@Builder
public record TaskPayloadDTO(List<UserErrorDTO> errors, Task task) {
}
