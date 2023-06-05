package poke.center.api.domain.user.validation;

import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import poke.center.api.domain.user.UserRegisterData;
import poke.center.api.domain.user.UserRepository;

@Component
public class UserLoginAlreadyInUseValidator implements UserValidator{

    @Autowired
    private UserRepository userRepository;

    public void validate(UserRegisterData data) {

        UserDetails user = userRepository.findByLogin(data.login());
        if (user != null) {
            throw new ValidationException("Username already in use");
        }
    }
}
