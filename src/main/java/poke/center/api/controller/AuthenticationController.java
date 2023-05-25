package poke.center.api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import poke.center.api.domain.user.AuthenticationData;
import poke.center.api.domain.user.User;
import poke.center.api.infra.secutiry.JwtTokenData;
import poke.center.api.infra.secutiry.TokenService;

@RestController
//@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationData data) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var authentication = manager.authenticate(authenticationToken);

        var jwtToken = tokenService.createToken((User) authentication.getPrincipal());

        return ResponseEntity.ok(new JwtTokenData(jwtToken));

    }

    @GetMapping("test")
    public String test() {
        return "Salve";
    }


}
