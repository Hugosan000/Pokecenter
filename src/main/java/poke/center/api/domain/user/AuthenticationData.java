package poke.center.api.domain.user;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationData(
        @NotBlank
        String login,
        @NotBlank
        String password
) {
}
