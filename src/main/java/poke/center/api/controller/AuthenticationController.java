package poke.center.api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import poke.center.api.domain.user.UserAuthenticationData;
import poke.center.api.domain.user.User;
import poke.center.api.infra.secutiry.JwtTokenData;
import poke.center.api.infra.secutiry.TokenService;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("login")
    @Transactional
    public ResponseEntity trainerLogin(@RequestBody @Valid UserAuthenticationData data) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var authentication = manager.authenticate(authenticationToken);

        var jwtToken = tokenService.createToken((User) authentication.getPrincipal());

        return ResponseEntity.ok(new JwtTokenData(jwtToken));
    }


}
