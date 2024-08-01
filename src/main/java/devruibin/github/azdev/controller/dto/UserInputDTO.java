package devruibin.github.azdev.controller.dto;

public record UserInputDTO(
    String username,
    String password,
    String firstName,
    String lastName
) {
}