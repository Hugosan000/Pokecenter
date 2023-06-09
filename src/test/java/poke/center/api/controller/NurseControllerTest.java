package poke.center.api.controller;

import org.junit.jupiter.api.BeforeAll;
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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import poke.center.api.domain.user.UserRegisterData;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class NurseControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JacksonTester<UserRegisterData> nurseRegisterDataJson;

    @BeforeAll
    static void clearDatabase(@Autowired JdbcTemplate jdbcTemplate) {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "userRole");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "user");
    }


    @Test
    @WithMockUser(authorities = {"nurse"})
    @DisplayName("It should return code 204 for successfully register")
    void nurseRegisterScenario1() throws Exception {

        var response = mockMvc.perform(
                post("/nurse/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(nurseRegisterDataJson.write(
                                new UserRegisterData("teste", "teste", "12345678")
                        ).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @WithMockUser(authorities = {"nurse"})
    @DisplayName("It should return 400 code because username already exists")
    void nurseRegisterScenario2() throws Exception {

        var response = mockMvc.perform(
                post("/nurse/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(nurseRegisterDataJson.write(
                                new UserRegisterData("teste", "teste", "12345678")
                        ).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @WithMockUser(authorities = {"trainer"})
    @DisplayName("It should return 403 because user has not nurse authority")
    void nurseRegisterScenario3() throws Exception {

        var response = mockMvc.perform(
                post("/nurse/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(nurseRegisterDataJson.write(
                                new UserRegisterData("teste", "teste", "12345678")
                        ).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

}