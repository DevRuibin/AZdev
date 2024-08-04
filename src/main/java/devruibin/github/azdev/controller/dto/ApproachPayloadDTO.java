package devruibin.github.azdev.controller.dto;

import devruibin.github.azdev.data.Approach;

import java.util.List;

public record ApproachPayloadDTO(
        List<UserErrorDTO> errors,
        Approach approach
) {

}
