package devruibin.github.azdev.controller.dto;

import devruibin.github.azdev.data.Approach;

public record ApproachPayloadDTO(
        UserErrorDTO errors,
        Approach approach
) {

}
