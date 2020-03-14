package com.live_events.demo;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.sql.DataSource;

@RestController
public class UserController {
    private final JdbcTemplate jdbcTemplate;

    public UserController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @RequestMapping(value="/createUser", method = RequestMethod.GET)
    public void createUser(@RequestParam(value = "id", defaultValue = "1234") int id,
                           @RequestParam(value = "name", defaultValue = "Admin") int name,
                           @RequestParam(value = "address", defaultValue = "Iuliu Maniu") int address,
                           @RequestParam(value = "mobile", defaultValue = "0") int mobile,
                           @RequestParam(value = "email", defaultValue = "admin@gmail.com") int email) {
        this.jdbcTemplate.update("INSERT into Users values(?, ?, ?, ?, ?)", id, name, address, mobile, email);
    }

    @RequestMapping(value="/updateUser", method = RequestMethod.POST)
    public void updateUser() {

    }

    @GetMapping("/fetchUser")
    public void fetchUser() {

    }

    @RequestMapping(value="/listUsers", method = RequestMethod.GET)
    public List<Map<String, Object>> listUsers() {
        return this.jdbcTemplate.queryForList("SELECT * FROM Users;");
    }
}
