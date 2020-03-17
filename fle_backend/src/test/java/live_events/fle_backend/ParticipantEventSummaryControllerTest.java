package live_events.fle_backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ParticipantEventSummaryControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    void addAndFetchSummaryTest() throws Exception {
        this.mockMvc.perform(post("/addSummary?eventid=10&participantid=12")).andExpect(status().is2xxSuccessful());
        this.mockMvc.perform(get("/fetchSummary?eventid=10&participantid=12")).andExpect(status().is2xxSuccessful());
        this.mockMvc.perform(delete("/removeSummary?eventid=10&participantid=12")).andExpect(status().is2xxSuccessful());
    }

    @Test
    void addAndFetchEventSummaryTest() throws Exception {
        this.mockMvc.perform(post("/addSummary?eventid=10&participantid=12")).andExpect(status().is2xxSuccessful());
        this.mockMvc.perform(post("/addSummary?eventid=10&participantid=13")).andExpect(status().is2xxSuccessful());
        this.mockMvc.perform(post("/addSummary?eventid=10&participantid=14")).andExpect(status().is2xxSuccessful());
        this.mockMvc.perform(get("/fetchEventSummary?eventid=10")).andExpect(jsonPath("$", hasSize(3)));
        this.mockMvc.perform(delete("/removeEventSummary?eventid=10")).andExpect(status().is2xxSuccessful());
        this.mockMvc.perform(get("/fetchEventSummary?eventid=10")).andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void addAndFetchParticipantSummaryTest() throws Exception {
        this.mockMvc.perform(post("/addSummary?eventid=10&participantid=12")).andExpect(status().is2xxSuccessful());
        this.mockMvc.perform(post("/addSummary?eventid=20&participantid=12")).andExpect(status().is2xxSuccessful());
        this.mockMvc.perform(post("/addSummary?eventid=30&participantid=12")).andExpect(status().is2xxSuccessful());
        this.mockMvc.perform(post("/addSummary?eventid=40&participantid=12")).andExpect(status().is2xxSuccessful());
        this.mockMvc.perform(get("/fetchParticipantSummary?participantid=12")).andExpect(jsonPath("$", hasSize(4)));
        this.mockMvc.perform(delete("/removeParticipantSummary?participantid=12")).andExpect(status().is2xxSuccessful());
        this.mockMvc.perform(get("/fetchParticipantSummary?participantid=10")).andExpect(jsonPath("$", hasSize(0)));
    }
}