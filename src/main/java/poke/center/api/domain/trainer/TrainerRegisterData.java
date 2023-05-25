package poke.center.api.domain.trainer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TrainerRegisterData(
        @NotBlank
        @Size(min = 4)
        @Size(max = 100)
        String name,
        @NotBlank
        @Size(min = 4)
        @Size(max = 50)
        String login,
        @NotBlank
        @Size(min = 8)
        String password
) {
}
