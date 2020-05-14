package live_events.fle_backend;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.sql.DataSource;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class UserController {
    private final JdbcTemplate jdbcTemplate;

    public UserController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    public String createUser(@RequestParam(value = "name", defaultValue = "Admin") String name,
                                             @RequestParam(value = "address", defaultValue = "Iuliu Maniu") String address,
                                             @RequestParam(value = "mobile", defaultValue = "0") String mobile,
                                             @RequestParam(value = "email", defaultValue = "admin@gmail.com") String email,
                                             @RequestParam(value = "username", defaultValue = "user_1") String username,
                                             @RequestParam(value = "password", defaultValue = "0000") String password) {
        this.jdbcTemplate.update("INSERT into Users values(?, ?, ?, ?, ?, ?)", name, address, mobile, email, username, password);
        return "Succes";
    }

    @RequestMapping(value = "/findUser", method = RequestMethod.GET)
    public String findUser(@RequestParam(value = "username", defaultValue = "user_1") String username,
                           @RequestParam(value = "password", defaultValue = "0000") String password) {
        if (this.jdbcTemplate.queryForList("SELECT * FROM Users WHERE Username = ? AND Password = ?", username, password).size() != 0){
            return "Succes";
        }
        return "Invalid username or password";
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.PUT)
    public void updateUser(@RequestParam(value = "iusername", defaultValue = "user_1") String username,
                           @RequestParam(value = "name", defaultValue = "Admin") String name,
                           @RequestParam(value = "address", defaultValue = "Iuliu Maniu") String address,
                           @RequestParam(value = "mobile", defaultValue = "0") String mobile,
                           @RequestParam(value = "email", defaultValue = "admin@gmail.com") String email) {
        this.jdbcTemplate.update("UPDATE Users Set Name = ?, Address = ?,  MobileNo = ?,  Email = ? WHERE Usename = ?", name, address, mobile, email, username);
    }

    @RequestMapping(value = "/removeUser", method = RequestMethod.DELETE)
    public void removeUser(@RequestParam(value = "username", defaultValue = "user_1") String username) {
        this.jdbcTemplate.update("DELETE FROM Users WHERE Username = ?", username);
    }

    @RequestMapping(value = "/fetchUser", method = RequestMethod.GET)
    public List<Map<String, Object>> fetchUser(@RequestParam(value = "username", defaultValue = "user_1") String username) {
        return this.jdbcTemplate.queryForList("SELECT * FROM Users WHERE Username = ?", username);
    }

    @RequestMapping(value = "/listUsers", method = RequestMethod.GET)
    public List<Map<String, Object>> listUsers() {
        return this.jdbcTemplate.queryForList("SELECT * FROM Users;");
    }
}
