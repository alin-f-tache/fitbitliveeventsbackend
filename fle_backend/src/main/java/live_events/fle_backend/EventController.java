package live_events.fle_backend;

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

    public EventController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @RequestMapping(value = "/createEvent", method = RequestMethod.POST)
    public String createEvent(@RequestParam(value = "id", defaultValue = "1234") String id,
                           @RequestParam(value = "title", defaultValue = "My race") String title,
                              @RequestParam(value = "city", defaultValue = "Bucuresti") String city,
                              @RequestParam(value = "street", defaultValue = "Iuliu Maniu") String street,
                              @RequestParam(value = "number", defaultValue = "15H") String number,
                           @RequestParam(value = "starttime", defaultValue = "11:00") @DateTimeFormat(pattern="HH:mm") Date startTime,
                           @RequestParam(value = "endtime", defaultValue = "14:00") @DateTimeFormat(pattern="HH:mm") Date endTime,
                           @RequestParam(value = "participantsnumber", defaultValue = "10") String participantsNumber,
                           @RequestParam(value = "description", defaultValue = "Maraton Herastrau - Cismigiu") String description,
                           @RequestParam(value = "date", defaultValue = "2020-01-01") @DateTimeFormat(pattern="yyyy-MM-dd") Date date,
                           @RequestParam(value = "hashapenned", defaultValue = "0") String hasHappened,
                              @RequestParam(value = "type", defaultValue = "Marathon") String type) {
        this.jdbcTemplate.update("INSERT INTO Events VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                id, title, city, street, number, startTime, endTime, participantsNumber, description, date, hasHappened, type);
        return "Succes";
    }

    @RequestMapping(value = "/updateEvent", method = RequestMethod.PUT)
    public void updateEvent(@RequestParam(value = "id", defaultValue = "1234") String id,
                            @RequestParam(value = "location", defaultValue = "Bucuresti") String location,
                            @RequestParam(value = "starttime", defaultValue = "11:00") @DateTimeFormat(pattern="HH:mm") Date startTime,
                            @RequestParam(value = "endtime", defaultValue = "14:00") @DateTimeFormat(pattern="HH:mm") Date endTime,
                            @RequestParam(value = "startpoint", defaultValue = "Herastrau") String startPoint,
                            @RequestParam(value = "endpoint", defaultValue = "Cismigiu") String endPoint,
                            @RequestParam(value = "participantsnumber", defaultValue = "10") String participantsNumber,
                            @RequestParam(value = "description", defaultValue = "Maraton Herastrau - Cismigiu") String description,
                            @RequestParam(value = "date", defaultValue = "2020-01-01") @DateTimeFormat(pattern="yyyy-MM-dd") Date date,
                            @RequestParam(value = "hashapenned", defaultValue = "0") String hasHappened) {
        this.jdbcTemplate.update("UPDATE Events SET Id = ?, Location = ?, StartTime = ?, EndTime = ?, StartPoint = ?, " +
                        "EndPoint = ?, ParticipantsNumber = ?, Description = ?, Date = ?, HasHappened = ?",
                id, location, startTime, endTime, startPoint, endPoint, participantsNumber, description, date, hasHappened);
    }

    @RequestMapping(value = "/removeEvent", method = RequestMethod.DELETE)
    public void removeEvent(@RequestParam(value = "id", defaultValue = "1234") String id) {
        this.jdbcTemplate.update("DELETE FROM Events WHERE Id = ?", id);
    }

    @RequestMapping(value = "/fetchEvent", method = RequestMethod.GET)
    public List<Map<String, Object>> fetchEvent(@RequestParam(value = "id", defaultValue = "1234") String id) {
        return this.jdbcTemplate.queryForList("SELECT * FROM Events WHERE Id = ?", id);
    }

    @RequestMapping(value = "/listEvents", method = RequestMethod.GET)
    public List<Map<String, Object>> listEvents() {
        return this.jdbcTemplate.queryForList("SELECT * FROM Events;");
    }

    @RequestMapping(value = "/listPastEvents", method = RequestMethod.GET)
    public List<Map<String, Object>> listPastEvents() {
        return this.jdbcTemplate.queryForList("SELECT * FROM Events WHERE Date < CURDATE();");
    }

    @RequestMapping(value = "/listFutureEvents", method = RequestMethod.GET)
    public List<Map<String, Object>> listFutureEvents() {
        return this.jdbcTemplate.queryForList("SELECT * FROM Events WHERE Date > CURDATE();");
    }
}
