package poke.center.api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import poke.center.api.domain.role.Role;
import poke.center.api.domain.role.RoleRepository;
import poke.center.api.domain.user.User;
import poke.center.api.domain.user.UserRepository;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("register")
public class RegisterController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    @PostMapping("/trainer")
    public ResponseEntity trainerRegister(@RequestBody @Valid TrainerRegisterData data) {

        var user = userRepository.findByLogin(data.login());

        if (user != null) {
            return ResponseEntity.badRequest().body("Username not available");
        }


        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        var newUser = new User(data);
        newUser.setPassword(encoder.encode(data.password()));

        var role = roleRepository.findByRoleName("trainer");
        if (role == null) {
            return ResponseEntity.badRequest().body("Error to complete register");
        }

        Set<Role> userRole = new HashSet<>();
        userRole.add(role);
        newUser.setRoles(userRole);

        userRepository.save(newUser);
        return ResponseEntity.noContent().build();
    }

}
