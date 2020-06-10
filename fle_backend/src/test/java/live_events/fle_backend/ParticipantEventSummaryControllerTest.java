package live_events.fle_backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("testenv")
@AutoConfigureMockMvc
public class ParticipantEventSummaryControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    void postAndGetSummaryTest() throws Exception {
        this.mockMvc.perform(post("/summary?eventid=10&username=user12")).andExpect(status().is2xxSuccessful());
        this.mockMvc.perform(get("/summary?eventid=10&username=user12")).andExpect(status().is2xxSuccessful());
        this.mockMvc.perform(delete("/summary?eventid=10&username=user12")).andExpect(status().is2xxSuccessful());
    }

    @Test
    void postAndGetEventSummaryTest() throws Exception {
        this.mockMvc.perform(post("/summary?eventid=10&username=user12")).andExpect(status().is2xxSuccessful());
        this.mockMvc.perform(post("/summary?eventid=10&username=user13")).andExpect(status().is2xxSuccessful());
        this.mockMvc.perform(post("/summary?eventid=10&username=user14")).andExpect(status().is2xxSuccessful());
        this.mockMvc.perform(get("/summary?eventid=10")).andExpect(jsonPath("$", hasSize(3)));
        this.mockMvc.perform(delete("/summary?eventid=10")).andExpect(status().is2xxSuccessful());
        this.mockMvc.perform(get("/summary?eventid=10")).andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void postAndGetParticipantSummaryTest() throws Exception {
        this.mockMvc.perform(post("/summary?eventid=10&username=user12")).andExpect(status().is2xxSuccessful());
        this.mockMvc.perform(post("/summary?eventid=20&username=user12")).andExpect(status().is2xxSuccessful());
        this.mockMvc.perform(post("/summary?eventid=30&username=user12")).andExpect(status().is2xxSuccessful());
        this.mockMvc.perform(post("/summary?eventid=40&username=user12")).andExpect(status().is2xxSuccessful());
        this.mockMvc.perform(get("/summary?username=user12")).andExpect(jsonPath("$", hasSize(4)));
        this.mockMvc.perform(delete("/summary?username=user12")).andExpect(status().is2xxSuccessful());
        this.mockMvc.perform(get("/summary?username=user12")).andExpect(jsonPath("$", hasSize(0)));
    }
}