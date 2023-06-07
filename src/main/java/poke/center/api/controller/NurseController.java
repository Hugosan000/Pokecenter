package poke.center.api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import poke.center.api.domain.role.Role;
import poke.center.api.domain.role.RoleRepository;
import poke.center.api.domain.role.validation.RoleValidator;
import poke.center.api.domain.user.User;
import poke.center.api.domain.user.UserRegisterData;
import poke.center.api.domain.user.UserRepository;
import poke.center.api.domain.user.validation.UserValidator;

import java.util.List;

@Controller
@RequestMapping("nurse")
public class NurseController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private List<UserValidator> userValidators;

    @Autowired
    private List<RoleValidator> roleValidators;

    @PostMapping("/register")
    public ResponseEntity nurseRegister(@RequestBody @Valid UserRegisterData data) {

        Role role = roleRepository.findByName("trainer");

        userValidators.forEach(validator -> validator.validate(data));
        roleValidators.forEach(validator -> validator.validate(role));

        var newUser = new User(data);
        newUser.create(role);
        userRepository.save(newUser);

        return ResponseEntity.noContent().build();
    }
}
