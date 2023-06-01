package poke.center.api.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import poke.center.api.domain.user.User;
import poke.center.api.domain.user.UserAuthenticationData;
import poke.center.api.domain.user.UserRegisterData;
import poke.center.api.domain.user.UserRepository;
import poke.center.api.infra.secutiry.TokenService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JacksonTester<UserAuthenticationData> userAuthenticationDataJson;

    @MockBean
    private AuthenticationManager manager;

    @MockBean
    private TokenService tokenService;

    @Mock
    private UserRepository userRepository;

    private User user;
    private String jwtToken;


    @BeforeEach
    void clearDatabase(@Autowired JdbcTemplate jdbcTemplate) {
        jwtToken = "\"jwtToken\"";
        user = createUser();
        Authentication auth = mock(Authentication.class);
        when(userRepository.save(user)).thenReturn(user);
        auth.setAuthenticated(true);
        when(auth.isAuthenticated()).thenReturn(true);
        when(manager.authenticate(new UsernamePasswordAuthenticationToken(user.getLogin(), user.getPassword()))).thenReturn(auth);
        when(auth.getPrincipal()).thenReturn(user);
        when(tokenService.createToken((User) auth.getPrincipal())).thenReturn("jwtToken");
    }
    @Test
    @DisplayName("It should return a jwt token on successfull login")
    void successfullyLoginShouldReturnJwt() throws Exception {

        String expectedReponseBody = "{\"token\":"+ jwtToken +"}";

        var response = mockMvc.perform(
                post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userAuthenticationDataJson.write(
                                new UserAuthenticationData(user.getLogin(), user.getPassword())
                        ).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(expectedReponseBody);
    }

    private User createUser() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = new User(userData("test", "test", encoder.encode("potatopotato")));
        user.setId(1L);
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