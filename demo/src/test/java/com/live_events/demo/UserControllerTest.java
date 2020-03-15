package com.live_events.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.containsString;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired MockMvc mockMvc;

    @Test
    void homeTest() throws Exception {
        this.mockMvc.perform(get("/")).andExpect(status().is2xxSuccessful());
    }

    @Test
    void listUsersTest() throws Exception {
        this.mockMvc.perform(get("/listUsers")).andExpect(status().is2xxSuccessful());
    }

    @Test
    void createUserTest() throws Exception {
        this.mockMvc.perform(post("/createUser?id=0000")).andExpect(status().is2xxSuccessful());
        this.mockMvc.perform(get("/fetchUser?id=0000")).andExpect(status().is2xxSuccessful());
        this.mockMvc.perform(delete("/removeUser?id=0000")).andExpect(status().is2xxSuccessful());
    }

    @Test
    void updateUserTest() throws Exception {
        this.mockMvc.perform(post("/createUser?id=0000")).andExpect(status().is2xxSuccessful());
        this.mockMvc.perform(put("/updateUser?id=0000&name=NewAdmin")).andExpect(status().is2xxSuccessful());
        this.mockMvc.perform(get("/fetchUser?id=0000")).andExpect(content().string(containsString("NewAdmin")));
        this.mockMvc.perform(delete("/removeUser?id=0000")).andExpect(status().is2xxSuccessful());
    }
}
