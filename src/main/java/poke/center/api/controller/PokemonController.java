package poke.center.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import poke.center.api.domain.pokemon.Pokemon;
import poke.center.api.domain.pokemon.PokemonRepository;


@RestController
@RequestMapping("pokemon")
public class PokemonController {

    @Autowired
    private PokemonRepository pokemonRepository;

    @GetMapping
    public ResponseEntity<Page<Pokemon>> listPokemons(@PageableDefault(size=150, sort = {"id"}) Pageable pagination) {

        return ResponseEntity.ok(pokemonRepository.findAll(pagination));
    }
}
