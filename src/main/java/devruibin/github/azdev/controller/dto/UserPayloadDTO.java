package devruibin.github.azdev.controller.dto;

import devruibin.github.azdev.data.User;

import java.util.List;

public record UserPayloadDTO(List<UserErrorDTO> errors,
                             User user,
                             String authToken) {
}
