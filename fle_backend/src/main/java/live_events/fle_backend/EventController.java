package live_events.fle_backend;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class EventController {
    private final JdbcTemplate jdbcTemplate;

    public EventController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @RequestMapping(value = "/createEvent", method = RequestMethod.POST)
    public void createEvent(@RequestParam(value = "id", defaultValue = "1234") String id,
                           @RequestParam(value = "location", defaultValue = "Bucuresti") String location,
                           @RequestParam(value = "starttime", defaultValue = "11:00") @DateTimeFormat(pattern="HH:mm") Date starttime,
                           @RequestParam(value = "endtime", defaultValue = "14:00") @DateTimeFormat(pattern="HH:mm") Date endtime,
                           @RequestParam(value = "startpoint", defaultValue = "Herastrau") String startpoint,
                           @RequestParam(value = "endpoint", defaultValue = "Cismigiu") String endpoint,
                           @RequestParam(value = "participantsnumber", defaultValue = "10") String participantsnumber,
                           @RequestParam(value = "description", defaultValue = "Maraton Herastrau - Cismigiu") String description,
                           @RequestParam(value = "date", defaultValue = "2020-01-01") @DateTimeFormat(pattern="yyyy-MM-dd") Date date,
                           @RequestParam(value = "hashapenned", defaultValue = "0") String hashappened) {
        this.jdbcTemplate.update("INSERT into Events values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                id, location, starttime, endtime, startpoint, endpoint, participantsnumber, description, date, hashappened);
    }

    @RequestMapping(value = "/updateEvent", method = RequestMethod.PUT)
    public void updateEvent(@RequestParam(value = "id", defaultValue = "1234") String id,
                           @RequestParam(value = "location", defaultValue = "Bucuresti") String location,
                           @RequestParam(value = "starttime", defaultValue = "11:00") @DateTimeFormat(pattern="HH:mm") Date starttime,
                           @RequestParam(value = "endtime", defaultValue = "14:00") @DateTimeFormat(pattern="HH:mm") Date endtime,
                           @RequestParam(value = "startpoint", defaultValue = "Herastrau") String startpoint,
                           @RequestParam(value = "endpoint", defaultValue = "Cismigiu") String endpoint,
                           @RequestParam(value = "participantsnumber", defaultValue = "10") String participantsnumber,
                           @RequestParam(value = "description", defaultValue = "Maraton Herastrau - Cismigiu") String description,
                           @RequestParam(value = "date", defaultValue = "2020-01-01") @DateTimeFormat(pattern="yyyy-MM-dd") Date date,
                           @RequestParam(value = "hashapenned", defaultValue = "0") String hashappened) {
        this.jdbcTemplate.update("UPDATE Events SET Id = ?, Location = ?, StartTime = ?, EndTime = ?, StartPoint = ?, " +
                        "EndPoint = ?, ParticipantsNumber = ?, Description = ?, Date = ?, HasHappened = ?",
                id, location, starttime, endtime, startpoint, endpoint, participantsnumber, description, date, hashappened);
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
}
