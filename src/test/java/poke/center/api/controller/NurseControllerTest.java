package poke.center.api.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import poke.center.api.domain.user.UserRegisterData;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

class NurseControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JacksonTester<UserRegisterData> nurseRegisterDataJson;

    @Test
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

}