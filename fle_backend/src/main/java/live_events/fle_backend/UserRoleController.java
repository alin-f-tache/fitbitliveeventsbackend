package live_events.fle_backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class UserRoleController {
    private final JdbcTemplate jdbcTemplate;

    public UserRoleController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @RequestMapping(value = "/addUserRole", method = RequestMethod.POST)
    public String addUserRole(@RequestParam(value = "eventid", defaultValue = "0000") String eventId,
                            @RequestParam(value = "username", defaultValue = "user_1") String username,
                            @RequestParam(value = "role", defaultValue = "organizer") String role) {
        this.jdbcTemplate.update("INSERT into UsersRole values(?, ?, ?)", eventId, username, role);

        return "Succes";
    }

    @RequestMapping(value = "/removeUserRole", method = RequestMethod.DELETE)
    public String removeUserRole(@RequestParam(value = "eventid", defaultValue = "0000") String eventId,
                               @RequestParam(value = "username", defaultValue = "user_1") String username,
                               @RequestParam(value = "role", defaultValue = "organizer") String role) {
        this.jdbcTemplate.update("DELETE FROM UsersRole WHERE EventId = ? and Username = ? and Role = ?", eventId, username, role);

        return "Succes";
    }

    @RequestMapping(value = "/listAllEvents", method = RequestMethod.GET)
    public List<Map<String, Object>> listAllEvents(@RequestParam(value = "username", defaultValue = "user_1") String username,
                                                   @RequestParam(value = "role", defaultValue = "all") String role) {
        if(role.equals("all")) {
            return this.jdbcTemplate.queryForList("SELECT * FROM UsersRole WHERE Username = ?", username);
        }
        return this.jdbcTemplate.queryForList("SELECT * FROM UsersRole WHERE Username = ? and Role = ?", username, role);
    }

    @RequestMapping(value = "/fetchUserPastEvents", method = RequestMethod.GET)
    public List<Map<String, Object>> fetchUserPastEvents(@RequestParam(value = "username", defaultValue = "user_1") String username,
                                                         @RequestParam(value = "role", defaultValue = "all") String role) {
        return this.jdbcTemplate.queryForList("SELECT * FROM UsersRole u" +
                                                    " JOIN Events e ON u.EventId =  e .Id" +
                                                    " WHERE u.Username = ? AND u.Role = ? AND e.Date < CURDATE();", username, role);
    }

    @RequestMapping(value = "/fetchUserFutureEvents", method = RequestMethod.GET)
    public List<Map<String, Object>> fetchUserFutureEvents(@RequestParam(value = "username", defaultValue = "user_1") String username,
                                                         @RequestParam(value = "role", defaultValue = "all") String role) {
        return this.jdbcTemplate.queryForList("SELECT * FROM UsersRole u" +
                " JOIN Events e ON u.EventId =  e .Id" +
                " WHERE u.Username = ? AND u.Role = ? AND e.Date > CURDATE();", username, role);
    }

    @RequestMapping(value = "/fetchEventUser", method = RequestMethod.GET)
    public List<Map<String, Object>> fetchEventUser(@RequestParam(value = "id", defaultValue = "0000") String id,
                                                           @RequestParam(value = "role", defaultValue = "all") String role) {
        return this.jdbcTemplate.queryForList("SELECT * FROM UsersRole u" +
                " JOIN Events e ON u.EventId =  e .Id" +
                " WHERE u.EventId = ? AND u.Role = ? AND e.Date > CURDATE();", id, role);
    }
}