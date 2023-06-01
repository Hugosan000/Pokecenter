package poke.center.api.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.jdbc.core.JdbcTemplate;
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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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
    TokenService tokenService;

    @BeforeEach
    void clearDatabase(@Autowired JdbcTemplate jdbcTemplate) {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "userRole");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "user");
    }

    @Test
    @DisplayName("Should")
    void tokenScenario1() throws Exception {
        var user = createUser();
        Set<Role> userRole = new HashSet<>();
        userRole.add(roleRepository.findByName("trainer"));
        user.setRoles(userRole);
        userRepository.save(user);

        var token = tokenService.createToken(user);
        assertNotNull(token);
        var response = mockMvc.perform(
                get("/trainer/test")
                        .header("Authorization", token)
        ).andExpect(status().isOk());

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