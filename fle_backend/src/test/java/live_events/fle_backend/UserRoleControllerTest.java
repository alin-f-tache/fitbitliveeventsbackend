package live_events.fle_backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("testenv")
@AutoConfigureMockMvc
public class UserRoleControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    void getUserRolesTest() throws Exception {
        this.mockMvc.perform(post("/events?id=10")).andExpect(status().is2xxSuccessful());
        this.mockMvc.perform(post("/userRoles?eventid=10&username=user12")).andExpect(status().is2xxSuccessful());
        this.mockMvc.perform(post("/userRoles?eventid=11&username=user12")).andExpect(status().is2xxSuccessful());
        this.mockMvc.perform(get("/userRoles?id=10&role=organizer")).andExpect(jsonPath("$", hasSize(2)));
        this.mockMvc.perform(delete("/userRoles?eventid=10&username=user12")).andExpect(status().is2xxSuccessful());
        this.mockMvc.perform(delete("/userRoles?eventid=11&username=user12")).andExpect(status().is2xxSuccessful());
        this.mockMvc.perform(delete("/events?id=10")).andExpect(status().is2xxSuccessful());
    }
}