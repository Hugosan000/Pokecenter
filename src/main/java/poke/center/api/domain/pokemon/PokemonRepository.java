package poke.center.api.domain.pokemon;

import org.springframework.stereotype.Repository;

@Repository
public interface PokemonRepository {
    Pokemon findAll();
}
