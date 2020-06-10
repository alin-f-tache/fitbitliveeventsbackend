package live_events.fle_backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.containsString;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("testenv")
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired MockMvc mockMvc;

    @Test
    void homeTest() throws Exception {
        this.mockMvc.perform(get("/")).andExpect(status().is2xxSuccessful());
    }

    @Test
    void getUsersTest() throws Exception {
        this.mockMvc.perform(get("/users")).andExpect(status().is2xxSuccessful());
    }

    @Test
    void postUserTest() throws Exception {
        this.mockMvc.perform(post("/users?username=user_2")).andExpect(status().is2xxSuccessful());
        this.mockMvc.perform(get("/users?username=user_2")).andExpect(status().is2xxSuccessful());
        this.mockMvc.perform(delete("/users?username=user_2")).andExpect(status().is2xxSuccessful());
    }

    @Test
    void putUserTest() throws Exception {
        this.mockMvc.perform(post("/users")).andExpect(status().is2xxSuccessful());
        this.mockMvc.perform(put("/users?username=user_1&name=NewAdmin")).andExpect(status().is2xxSuccessful());
        this.mockMvc.perform(get("/users?username=user_1")).andExpect(content().string(containsString("NewAdmin")));
        this.mockMvc.perform(delete("/users?username=user_1")).andExpect(status().is2xxSuccessful());
    }
}
