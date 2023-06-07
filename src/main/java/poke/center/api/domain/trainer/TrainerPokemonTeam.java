package poke.center.api.domain.trainer;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import poke.center.api.domain.pokemon.Pokemon;

import java.util.List;

public record TrainerPokemonTeam(
        @NotBlank
        @Min(value = 1L)
        Long trainerId,

        @Size(min = 6, max = 6, message = "The information os pokemons must contain 6 of them")
        @NotBlank
        List<Pokemon> pokemons
) {
}
