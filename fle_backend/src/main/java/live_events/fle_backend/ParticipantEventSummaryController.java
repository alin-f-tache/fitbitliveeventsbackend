package live_events.fle_backend;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ParticipantEventSummaryController {
    private final JdbcTemplate jdbcTemplate;

    public ParticipantEventSummaryController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @RequestMapping(value = "/addSummary", method = RequestMethod.POST)
    public void addSummary(@RequestParam(value = "eventid", defaultValue = "10") String eventId,
                           @RequestParam(value = "username", defaultValue = "user_1") String username,
                           @RequestParam(value = "place", defaultValue = "1") String place,
                           @RequestParam(value = "ranked", defaultValue = "0") String ranked,
                           @RequestParam(value = "avgspeed", defaultValue = "100") String avgSpeed,
                           @RequestParam(value = "time", defaultValue = "3600") String time) {
        this.jdbcTemplate.update("INSERT INTO ParticipantsEventsSummary values(?, ?, ?, ?, ?, ?)",
                eventId, username, place, ranked, avgSpeed, time);
    }

    @RequestMapping(value = "/removeSummary", method = RequestMethod.DELETE)
    public void removeSummary(@RequestParam(value = "eventid", defaultValue = "10") String eventId,
                              @RequestParam(value = "username", defaultValue = "user_1") String username) {
        this.jdbcTemplate.update("DELETE FROM ParticipantsEventsSummary WHERE EventId = ? AND Username = ?", eventId, username);
    }

    @RequestMapping(value = "/removeEventSummary", method = RequestMethod.DELETE)
    public void removeEventSummary(@RequestParam(value = "eventid", defaultValue = "10") String eventId) {
        this.jdbcTemplate.update("DELETE FROM ParticipantsEventsSummary WHERE EventId = ?", eventId);
    }

    @RequestMapping(value = "/removeParticipantSummary", method = RequestMethod.DELETE)
    public void removeParticipantSummary(@RequestParam(value = "username", defaultValue = "user_1") String username) {
        this.jdbcTemplate.update("DELETE FROM ParticipantsEventsSummary WHERE Username = ?", username);
    }

    @RequestMapping(value = "/fetchSummary", method = RequestMethod.GET)
    public List<Map<String, Object>> fetchSummary(@RequestParam(value = "eventid", defaultValue = "10") String eventId,
                                                  @RequestParam(value = "username", defaultValue = "user_1") String username) {
        return this.jdbcTemplate.queryForList("SELECT * FROM ParticipantsEventsSummary WHERE EventId = ? AND Username = ?", eventId, username);
    }

    @RequestMapping(value = "/fetchEventSummary", method = RequestMethod.GET)
    public List<Map<String, Object>> fetchEventSummary(@RequestParam(value = "eventid", defaultValue = "10") String eventId) {
        return this.jdbcTemplate.queryForList("SELECT * FROM ParticipantsEventsSummary WHERE EventId = ?", eventId);
    }

    @RequestMapping(value = "/fetchParticipantSummary", method = RequestMethod.GET)
    public List<Map<String, Object>> fetchParticipantSummary(@RequestParam(value = "usernem", defaultValue = "user_1") String username) {
        return this.jdbcTemplate.queryForList("SELECT * FROM ParticipantsEventsSummary WHERE Username = ?", username);
    }
}
