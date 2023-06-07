package poke.center.api.domain.role.validation;

import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import poke.center.api.domain.role.Role;
import poke.center.api.domain.role.RoleRepository;

@Component
public class RoleExistValidator implements RoleValidator{

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void validate(Role role) {
        if (role == null) {
            throw new ValidationException("Invalid Role");
        }
    }
}
