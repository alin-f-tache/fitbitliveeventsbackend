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

    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    public void createUser(@RequestParam(value = "id", defaultValue = "1234") String id,
                           @RequestParam(value = "name", defaultValue = "Admin") String name,
                           @RequestParam(value = "address", defaultValue = "Iuliu Maniu") String address,
                           @RequestParam(value = "mobile", defaultValue = "0") String mobile,
                           @RequestParam(value = "email", defaultValue = "admin@gmail.com") String email) {
        this.jdbcTemplate.update("INSERT into Users values(?, ?, ?, ?, ?)", id, name, address, mobile, email);
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.PUT)
    public void updateUser(@RequestParam(value = "id", defaultValue = "1234") String id,
                           @RequestParam(value = "name", defaultValue = "Admin") String name,
                           @RequestParam(value = "address", defaultValue = "Iuliu Maniu") String address,
                           @RequestParam(value = "mobile", defaultValue = "0") String mobile,
                           @RequestParam(value = "email", defaultValue = "admin@gmail.com") String email) {
        this.jdbcTemplate.update("UPDATE Users Set Name = ?, Address = ?,  MobileNo = ?,  Email = ? WHERE Id = ?", name, address, mobile, email, id);
    }

    @RequestMapping(value = "/removeUser", method = RequestMethod.DELETE)
    public void removeUser(@RequestParam(value = "id", defaultValue = "1234") String id) {
        this.jdbcTemplate.update("DELETE FROM Users WHERE Id = ?", id);
    }

    @RequestMapping(value = "/fetchUser", method = RequestMethod.GET)
    public List<Map<String, Object>> fetchUser(@RequestParam(value = "id", defaultValue = "1234") String id) {
            return this.jdbcTemplate.queryForList("SELECT * FROM Users WHERE Id = ?", id);
    }

    @RequestMapping(value = "/listUsers", method = RequestMethod.GET)
    public List<Map<String, Object>> listUsers() {
        return this.jdbcTemplate.queryForList("SELECT * FROM Users;");
    }
}
