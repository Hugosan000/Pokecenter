package poke.center.api.domain.trainer;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;

public record TrainerPokemonTeamData(
        @NotNull
        @Min(value = 1L)
        Long trainerId,

        @Size(min = 6, max = 6, message = "Pokemon array must contain the min/max of 6")
        @NotNull
        @UniqueElements
        List<@Pattern(regexp = "^[0-9]*$", message = "Only Integer values are acceptable") String> pokemons
) {
}
