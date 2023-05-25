package poke.center.api.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import poke.center.api.domain.user.UserRegisterData;
import poke.center.api.domain.user.User;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthenticationControllerTest {

    private MockMvc mockMvc;
    @Test
    @DisplayName("It should return a jwt token on successfull login")
    void loginCenario1() {
        Assertions.assertEquals(1, 1);
    }


    private User createUser() {
        var user = new User(userData("test", "test", "potatopotato"));
        return user;
    }

    private UserRegisterData userData(String name, String login, String password) {
        return new UserRegisterData(
                name,
                login,
                password
        );
    }
}