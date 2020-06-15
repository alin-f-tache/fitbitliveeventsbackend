package live_events.fle_backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class EventController {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    Producer producer;

    public EventController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/events")
    public List<Map<String, Object>> getEvent(@RequestParam(value = "id", required = false) String id,
                                              @RequestParam(value = "time", required = false) String time) {
        if (id != null) {
            return this.jdbcTemplate.queryForList("SELECT * FROM Events WHERE Id = ?", id);
        } else {
            if (time != null && time.equals("past")) {
                return this.jdbcTemplate.queryForList("SELECT * FROM Events WHERE Date < CURDATE();");
            } else if (time != null && time.equals("future")) {
                return this.jdbcTemplate.queryForList("SELECT * FROM Events WHERE Date > CURDATE();");
            } else {
                return this.jdbcTemplate.queryForList("SELECT * from Events where DATE_FORMAT(Date, \"%y-%m-%d\") = CURDATE()");
            }
        }
    }

    @PostMapping("/events")
    public String postEvent(@RequestParam(value = "id", defaultValue = "1234") String id,
                            @RequestParam(value = "title", defaultValue = "My race") String title,
                            @RequestParam(value = "city", defaultValue = "Bucuresti") String city,
                            @RequestParam(value = "street", defaultValue = "Iuliu Maniu") String street,
                            @RequestParam(value = "number", defaultValue = "15H") String number,
                            @RequestParam(value = "starttime", defaultValue = "11:00") @DateTimeFormat(pattern="HH:mm") Date startTime,
                            @RequestParam(value = "endtime", defaultValue = "14:00") @DateTimeFormat(pattern="HH:mm") Date endTime,
                            @RequestParam(value = "participantsnumber", defaultValue = "10") String participantsNumber,
                            @RequestParam(value = "description", defaultValue = "Maraton Herastrau - Cismigiu") String description,
                            @RequestParam(value = "date", defaultValue = "2020-12-12") @DateTimeFormat(pattern="yyyy-MM-dd") Date date,
                            @RequestParam(value = "hashapenned", defaultValue = "0") String hasHappened,
                            @RequestParam(value = "type", defaultValue = "Marathon") String type,
                            @RequestParam(value = "username", defaultValue = "user_1") String username) {
        this.jdbcTemplate.update("INSERT INTO Events VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                id, title, city, street, number, startTime, endTime, participantsNumber, description, date, hasHappened, type);
        this.jdbcTemplate.update("INSERT into UsersRole values(?, ?, ?, ?, ?)", id, username, "organizer", date, "0");
        return "Succes";
    }

    @PutMapping("/events")
    public String putEvent(@RequestParam(value = "id", defaultValue = "1234") String id,
                           @RequestParam(value = "title", defaultValue = "My race") String title,
                           @RequestParam(value = "city", defaultValue = "Bucuresti") String city,
                           @RequestParam(value = "street", defaultValue = "Iuliu Maniu") String street,
                           @RequestParam(value = "number", defaultValue = "15H") String number,
                           @RequestParam(value = "starttime", defaultValue = "11:00") @DateTimeFormat(pattern="HH:mm") Date startTime,
                           @RequestParam(value = "endtime", defaultValue = "14:00") @DateTimeFormat(pattern="HH:mm") Date endTime,
                           @RequestParam(value = "participantsnumber", defaultValue = "10") String participantsNumber,
                           @RequestParam(value = "description", defaultValue = "Maraton Herastrau - Cismigiu") String description,
                           @RequestParam(value = "date", defaultValue = "2020-12-12") @DateTimeFormat(pattern="yyyy-MM-dd") Date date,
                           @RequestParam(value = "hashapenned", defaultValue = "0") String hasHappened,
                           @RequestParam(value = "type", defaultValue = "Marathon") String type) {
        this.jdbcTemplate.update("UPDATE Events SET Title = ?, City = ?, Street = ?, Number = ?, StartTime = ?," +
                        "EndTime = ?, ParticipantsNumber = ?, Description = ?, Date = ?, HasHappend = ?, Type = ? WHERE Id = ?",
                title, city, street, number, startTime, endTime, participantsNumber, description, date, hasHappened, type, id);
        return "Succes";
    }

    @DeleteMapping("/events")
    public String deleteEvent(@RequestParam(value = "id", defaultValue = "1234") String id) {
        this.jdbcTemplate.update("DELETE FROM Events WHERE Id = ?", id);
        producer.sendEmail(new SampleMessage(0, "DELETE: " + id));
        return "Succes";
    }
}
