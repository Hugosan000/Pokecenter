package poke.center.api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import poke.center.api.domain.pokemon.Pokemon;
import poke.center.api.domain.pokemon.PokemonRepository;
import poke.center.api.domain.role.Role;
import poke.center.api.domain.role.RoleRepository;
import poke.center.api.domain.trainer.TrainerPokemonTeamData;
import poke.center.api.domain.user.User;
import poke.center.api.domain.user.UserRegisterData;
import poke.center.api.domain.user.UserRepository;
import poke.center.api.infra.secutiry.TokenService;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("trainer")
public class TrainerController {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PokemonRepository pokemonRepository;
    @PostMapping("/register")
    @Transactional
    public ResponseEntity trainerRegister(@RequestBody @Valid UserRegisterData data) {

        UserDetails user = userRepository.findByLogin(data.login());
        if (user != null) {
            return ResponseEntity.unprocessableEntity().body("Username not available");
        }

        Role role = roleRepository.findByName("trainer");
        if (role == null) {
            return ResponseEntity.badRequest().body("Invalid role");
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        var newUser = new User(data);
        newUser.setPassword(encoder.encode(data.password()));

        Set<Role> userRole = new HashSet<>();
        userRole.add(role);
        newUser.setRoles(userRole);

        userRepository.save(newUser);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/assemble-pokemon-team")
    @Transactional
    public ResponseEntity assemblePokemonTeam(@RequestBody @Valid TrainerPokemonTeamData data, @RequestHeader (name = "Authorization") String token){
        token = token.replace("Bearer ", "");
        var subjectId = tokenService.getSubjectId(token);
        var pokemonsId = data.pokemons();

        var trainer = userRepository.findById(Long.valueOf(subjectId)).orElse(null);

        if (trainer.getPokemons().size() != 0) {
            return ResponseEntity.badRequest().body("Trainer team already assembled");
        }

        Stream<Pokemon> pokemonValidation = pokemonsId.stream().map(p -> pokemonRepository.findById(Long.valueOf(p)).orElse(null));

        if (pokemonValidation.anyMatch(p -> p == null)) {
            return ResponseEntity.badRequest().body("Invalid pokemon id");
        }

        Stream<Pokemon> trainerPokemon = pokemonsId.stream().map(p -> pokemonRepository.findById(Long.valueOf(p)).orElse(null));

        trainer.setPokemons(trainerPokemon.collect(Collectors.toSet()));

        return ResponseEntity.ok(trainer.getPokemons());

    }

}
