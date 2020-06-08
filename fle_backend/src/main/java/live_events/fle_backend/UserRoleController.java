package live_events.fle_backend;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class UserRoleController {
    private final JdbcTemplate jdbcTemplate;

    public UserRoleController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/userRoles")
    public List<Map<String, Object>> getUserRoles(@RequestParam(value = "username", required = false) String username,
                                                  @RequestParam(value = "role", required = false) String role,
                                                  @RequestParam(value = "id", required = false) String id,
                                                  @RequestParam(value = "time", required = false) String time) {
        if (username != null && role != null && time != null) {
            if (time.equals("past")) {
                return this.jdbcTemplate.queryForList("SELECT * FROM UsersRole u" +
                        " JOIN Events e ON u.EventId =  e.Id" +
                        " WHERE u.Username = ? AND u.Role = ? AND e.Date < CURDATE();", username, role);
            } else if (time.equals("future")) {
                return this.jdbcTemplate.queryForList("SELECT * FROM UsersRole u" +
                        " JOIN Events e ON u.EventId =  e.Id" +
                        " WHERE u.Username = ? AND u.Role = ? AND e.Date > CURDATE();", username, role);
            }
        }

        if (username != null && role != null) {
            if (role.equals("all")) {
                return this.jdbcTemplate.queryForList("SELECT * FROM UsersRole WHERE Username = ?", username);
            }
            return this.jdbcTemplate.queryForList("SELECT * FROM UsersRole WHERE Username = ? and Role = ?", username, role);
        }

        if (id != null & role != null) {
            return this.jdbcTemplate.queryForList("SELECT * FROM UsersRole u" +
                    " JOIN Events e ON u.EventId =  e.Id" +
                    " WHERE u.EventId = ? AND u.Role = ? AND e.Date > CURDATE();", id, role);
        }

        return null;
    }

    @PostMapping("/userRoles")
    public String postUserRole(@RequestParam(value = "eventid", defaultValue = "0000") String eventId,
                               @RequestParam(value = "username", defaultValue = "user_1") String username,
                               @RequestParam(value = "role", defaultValue = "organizer") String role) {
        this.jdbcTemplate.update("INSERT into UsersRole values(?, ?, ?)", eventId, username, role);
        return "Succes";
    }

    @DeleteMapping("/userRoles")
    public String deleteUserRole(@RequestParam(value = "eventid", defaultValue = "0000") String eventId,
                                 @RequestParam(value = "username", defaultValue = "user_1") String username,
                                 @RequestParam(value = "role", defaultValue = "organizer") String role) {
        this.jdbcTemplate.update("DELETE FROM UsersRole WHERE EventId = ? and Username = ? and Role = ?", eventId, username, role);
        return "Succes";
    }
}