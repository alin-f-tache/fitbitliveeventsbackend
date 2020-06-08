package live_events.fle_backend;

import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class UserController {
    private final JdbcTemplate jdbcTemplate;

    public UserController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/users")
    public List<Map<String, Object>> getUser(@RequestParam(value = "username", required = false) String username) {
        if (username != null) {
            return this.jdbcTemplate.queryForList("SELECT * FROM Users WHERE Username = ?", username);
        } else {
            return this.jdbcTemplate.queryForList("SELECT * FROM Users;");
        }
    }

    @PostMapping("/users")
    public String postUser(@RequestParam(value = "name", defaultValue = "Admin") String name,
                           @RequestParam(value = "address", defaultValue = "Iuliu Maniu") String address,
                           @RequestParam(value = "mobile", defaultValue = "0") String mobile,
                           @RequestParam(value = "email", defaultValue = "admin@gmail.com") String email,
                           @RequestParam(value = "username", defaultValue = "user_1") String username,
                           @RequestParam(value = "password", defaultValue = "0000") String password) {
        this.jdbcTemplate.update("INSERT into Users values(?, ?, ?, ?, ?, ?)", name, address, mobile, email, username, password);
        return "Succes";
    }

    @PutMapping("/users")
    public void putUser(@RequestParam(value = "username", defaultValue = "user_1") String username,
                        @RequestParam(value = "name", defaultValue = "Admin") String name,
                        @RequestParam(value = "address", defaultValue = "Iuliu Maniu") String address,
                        @RequestParam(value = "mobile", defaultValue = "0") String mobile,
                        @RequestParam(value = "email", defaultValue = "admin@gmail.com") String email) {
        this.jdbcTemplate.update("UPDATE Users Set Name = ?, Address = ?,  MobileNo = ?,  Email = ? WHERE Usename = ?", name, address, mobile, email, username);
    }

    @DeleteMapping("/users")
    public void deleteUser(@RequestParam(value = "username", defaultValue = "user_1") String username) {
        this.jdbcTemplate.update("DELETE FROM Users WHERE Username = ?", username);
    }
}