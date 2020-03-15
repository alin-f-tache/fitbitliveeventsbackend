package com.live_events.demo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

@RestController
public class UserRoleController {
    private final JdbcTemplate jdbcTemplate;

    public UserRoleController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @RequestMapping(value = "/addUserRole", method = RequestMethod.POST)
    public void addUserRole(@RequestParam(value = "eventid", defaultValue = "0000") String eventId,
                            @RequestParam(value = "participantid", defaultValue = "1234") String participantId,
                            @RequestParam(value = "role", defaultValue = "organizer") String role) {
        this.jdbcTemplate.update("INSERT into UsersRole values(?, ?, ?)", eventId, participantId, role);

    }

    @RequestMapping(value = "/removeUserRole", method = RequestMethod.DELETE)
    public void removeUserRole(@RequestParam(value = "eventid", defaultValue = "0000") String eventId,
                               @RequestParam(value = "participantid", defaultValue = "1234") String participantId,
                               @RequestParam(value = "role", defaultValue = "organizer") String role) {
        this.jdbcTemplate.update("DELETE FROM UsersRole WHERE EventId = ? and ParticipantId = ? and Role = ?", eventId, participantId, role);
    }

    @RequestMapping(value = "/listAllEvents", method = RequestMethod.GET)
    public List<Map<String, Object>> listAllEvents(@RequestParam(value = "participantid", defaultValue = "1234") String participantId,
                                                   @RequestParam(value = "role", defaultValue = "all") String role) {
        if(role.equals("all")) {
            return this.jdbcTemplate.queryForList("SELECT * FROM UsersRole WHERE ParticipantId = ?", participantId);
        }
        return this.jdbcTemplate.queryForList("SELECT * FROM UsersRole WHERE ParticipantId = ? and Role = ?", participantId, role);
    }
}
