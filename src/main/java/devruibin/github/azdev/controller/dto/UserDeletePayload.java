package devruibin.github.azdev.controller.dto;

import java.util.List;

public record UserDeletePayload(List<UserErrorDTO> errors, Long deletedUserId) {
}
