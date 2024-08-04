package devruibin.github.azdev.controller.dto;

import java.util.List;

public record ApproachInputDTO(
        String content,
        List<ApproachDetailInputDTO> detailList
) {
}
