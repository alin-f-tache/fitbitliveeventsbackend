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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("testenv")
@AutoConfigureMockMvc
public class EventControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    void postEventTest() throws Exception {
        this.mockMvc.perform(post("/events?id=10")).andExpect(status().is2xxSuccessful());
        this.mockMvc.perform(get("/events?id=10")).andExpect(status().is2xxSuccessful());
        this.mockMvc.perform(delete("/events?id=10")).andExpect(status().is2xxSuccessful());
    }

    @Test
    void putEventTest() throws Exception {
        this.mockMvc.perform(post("/events?id=10")).andExpect(status().is2xxSuccessful());
        this.mockMvc.perform(put("/events?id=10&description=NewDesc")).andExpect(status().is2xxSuccessful());
        this.mockMvc.perform(get("/events?id=10")).andExpect(jsonPath("$[0].Description").value("NewDesc"));
        this.mockMvc.perform(delete("/events?id=10")).andExpect(status().is2xxSuccessful());
    }
}