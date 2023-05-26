package poke.center.api.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import poke.center.api.domain.role.Role;
import poke.center.api.domain.role.RoleRepository;
import poke.center.api.domain.user.User;
import poke.center.api.domain.user.UserAuthenticationData;
import poke.center.api.domain.user.UserRegisterData;
import poke.center.api.domain.user.UserRepository;
import poke.center.api.infra.secutiry.TokenService;

import java.util.HashSet;
import java.util.Set;

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

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager manager;

    @BeforeEach
    void clearDatabase(@Autowired JdbcTemplate jdbcTemplate) {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "userRole");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "user");
    }

    @Test
    @DisplayName("It should return a jwt token on successfully login")
    void loginScenario1() throws Exception {
        var user = createUser();
        Set<Role> userRole = new HashSet<>();
        userRole.add(roleRepository.findByName("trainer"));
        user.setRoles(userRole);
        userRepository.save(user);

        AuthenticationManager authenticationManager = Mockito.mock(AuthenticationManager.class);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getLogin(), user.getPassword());
        Mockito.when(authenticationManager.authenticate(Mockito.any(Authentication.class)))
                .thenReturn(authentication);

        TokenService tokenService = Mockito.mock(TokenService.class);
        Mockito.when(tokenService.createToken(Mockito.any(User.class)))
                .thenReturn("sample-jwt-token");

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