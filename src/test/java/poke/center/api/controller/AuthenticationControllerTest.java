package poke.center.api.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import poke.center.api.domain.user.User;
import poke.center.api.domain.user.UserAuthenticationData;
import poke.center.api.domain.user.UserRegisterData;
import poke.center.api.domain.user.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JacksonTester<UserAuthenticationData> userAuthenticationDataJson;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("It should return a jwt token on successfully login")
    void loginCenario1() throws Exception {
        var user = createUser();
        userRepository.save(user);

        var response = mockMvc.perform(
                post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userAuthenticationDataJson.write(
                                new UserAuthenticationData(user.getLogin(), user.getPassword())
                        ).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }


    private User createUser() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return new User(userData("test", "test", encoder.encode("potatopotato")));
    }

    private UserRegisterData userData(String name, String login, String password) {
        return new UserRegisterData(
                name,
                login,
                password
        );
    }
}