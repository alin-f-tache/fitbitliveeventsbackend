package live_events.fle_backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    void createEventTest() throws Exception {
        this.mockMvc.perform(post("/createEvent?id=10")).andExpect(status().is2xxSuccessful());
        this.mockMvc.perform(get("/fetchEvent?id=10")).andExpect(status().is2xxSuccessful());
        this.mockMvc.perform(delete("/removeEvent?id=10")).andExpect(status().is2xxSuccessful());
    }

    @Test
    void listEventsTest() throws Exception {
        this.mockMvc.perform(post("/createEvent?id=10")).andExpect(status().is2xxSuccessful());
        this.mockMvc.perform(post("/createEvent?id=20")).andExpect(status().is2xxSuccessful());
        this.mockMvc.perform(get("/listEvents")).andExpect(jsonPath("$", hasSize(2)));
        this.mockMvc.perform(delete("/removeEvent?id=10")).andExpect(status().is2xxSuccessful());
        this.mockMvc.perform(delete("/removeEvent?id=20")).andExpect(status().is2xxSuccessful());
    }

    @Test
    void updateEventTest() throws Exception {
        this.mockMvc.perform(post("/createEvent?id=10")).andExpect(status().is2xxSuccessful());
        this.mockMvc.perform(put("/updateEvent?id=10&endpoint=Crangasi")).andExpect(status().is2xxSuccessful());
        this.mockMvc.perform(get("/fetchEvent?id=10")).andExpect(jsonPath("$[0].EndPoint").value("Crangasi"));
        this.mockMvc.perform(delete("/removeEvent?id=10")).andExpect(status().is2xxSuccessful());
    }
}