package poke.center.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Login {

    @PostMapping("/login")
    public String login() {
        return "success";
    }

}
