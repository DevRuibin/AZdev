package devruibin.github.azdev.controller.dto;

import java.util.List;

public record UserDeletePayloadDTO(List<UserErrorDTO> errors, Long deletedUserId) {
}
