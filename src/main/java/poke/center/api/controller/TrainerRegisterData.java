package poke.center.api.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TrainerRegisterData(
        @NotBlank
        @Size(min = 5)
        String name,
        @NotBlank
        @Size(min = 5)
        String login,
        @NotBlank
        @Size(min = 8)
        String password
) {
}
