package poke.center.api.domain.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegisterData(
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
