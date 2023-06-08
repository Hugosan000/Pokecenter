package poke.center.api.domain.user.validation;

import poke.center.api.domain.user.UserRegisterData;

public interface UserValidator {

    void validate(UserRegisterData data);
}
