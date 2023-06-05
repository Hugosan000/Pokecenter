package poke.center.api.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import poke.center.api.domain.user.UserRegisterData;

@Controller
@RequestMapping("nurse")
public class NurseController {


    @PostMapping("/register")
    public ResponseEntity nurseRegister(@RequestBody @Valid UserRegisterData user) {

    }
}
