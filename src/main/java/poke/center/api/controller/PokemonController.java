package poke.center.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import poke.center.api.domain.pokemon.PokemonRepository;

@RestController
@RequestMapping("pokemon")
public class PokemonController {

    @Autowired
    private PokemonRepository pokemonRepository;

    @GetMapping
    public ResponseEntity listPokemons() {

        var pokemons = pokemonRepository.findAll();

        return ResponseEntity.ok(pokemons);
    }
}
