package com.live_events.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserRoleControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private UserController userServiceMock;

    @Test
    void addUserRoleTest() throws Exception {
        this.mockMvc.perform(post("/addUserRole?eventid=0000&participantid=0000")).andExpect(status().is2xxSuccessful());
        this.mockMvc.perform(delete("/removeUserRole?participantid=0000")).andExpect(status().is2xxSuccessful());
    }

    @Test
    void listAllEventsTest() throws Exception {
        this.mockMvc.perform(post("/addUserRole?eventid=0000&participantid=0000")).andExpect(status().is2xxSuccessful());
        this.mockMvc.perform(get("/listAllEvents?participantid=0000")).andExpect(content().string(containsString("organizer")));;
        this.mockMvc.perform(delete("/removeUserRole?participantid=0000")).andExpect(status().is2xxSuccessful());
    }
}
