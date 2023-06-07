package poke.center.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pokemon")
public class PokemonController {


    @GetMapping
    public ResponseEntity listPokemons() {
        return ResponseEntity.ok().build();
    }
}
