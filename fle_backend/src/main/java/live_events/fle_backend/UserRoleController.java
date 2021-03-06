package live_events.fle_backend;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
                                                  @RequestParam(value = "time", required = false) String time,
                                                  @RequestParam(value = "month_distribution", required = false) String distribution_role,
                                                  @RequestParam(value = "invitation_stats", required = false) String invitation_role,
                                                  @RequestParam(value = "participants_list", required = false) String participant_list) {
        if (invitation_role != null) {
            return this.jdbcTemplate.queryForList("SELECT *" +
                    " from UsersRole WHERE EventId=? AND Role = ? AND Invitation=1", id, invitation_role);
        }

        if (distribution_role != null) {
            return this.jdbcTemplate.queryForList("SELECT count(*) as Number, MONTH(Date) as Month" +
                    " from UsersRole WHERE Username=? AND Role = ?" +
                    " GROUP BY MONTH(Date);", username, distribution_role);
        }

        if (participant_list != null) {
            return this.jdbcTemplate.queryForList("SELECT Username FROM UsersRole u" +
                    " JOIN Events e ON u.EventId =  e.Id" +
                    " WHERE u.EventId = ? AND u.Role = ?;", id, role);
        }

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
                               @RequestParam(value = "role", defaultValue = "organizer") String role,
                               @RequestParam(value = "date", defaultValue = "2020-01-01") String date,
                               @RequestParam(value = "invitation", defaultValue = "0") String invitation) {
        this.jdbcTemplate.update("INSERT into UsersRole values(?, ?, ?, ?, ?)", eventId, username, role, date, invitation);
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