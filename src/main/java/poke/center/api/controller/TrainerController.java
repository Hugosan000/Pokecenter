package poke.center.api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import poke.center.api.domain.role.Role;
import poke.center.api.domain.role.RoleRepository;
import poke.center.api.domain.trainer.TrainerPokemonTeamData;
import poke.center.api.domain.user.UserRegisterData;
import poke.center.api.domain.user.User;
import poke.center.api.domain.user.UserRepository;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("trainer")
public class TrainerController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
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
    public ResponseEntity assemblePokemonTeam(@RequestBody @Valid TrainerPokemonTeamData data){

        var pokemonsId = data.pokemons();

        return ResponseEntity.ok(pokemonsId);

    }

}
