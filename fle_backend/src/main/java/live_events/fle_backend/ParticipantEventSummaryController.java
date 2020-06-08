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

    @GetMapping(value="/summary")
    public List<Map<String, Object>> getSummary(@RequestParam(value = "eventid", required = false) String eventId,
                                                @RequestParam(value = "username", required = false) String username) {
        if (eventId != null && username != null) {
            return this.jdbcTemplate.queryForList("SELECT * FROM ParticipantsEventsSummary WHERE EventId = ? AND Username = ?", eventId, username);
        } else if (eventId != null) {
            return this.jdbcTemplate.queryForList("SELECT * FROM ParticipantsEventsSummary WHERE EventId = ?", eventId);
        } else {
            return this.jdbcTemplate.queryForList("SELECT * FROM ParticipantsEventsSummary WHERE Username = ?", username);
        }
    }

    @PostMapping(value="/summary")
    public void postSummary(@RequestParam(value = "eventid", defaultValue = "10") String eventId,
                            @RequestParam(value = "username", defaultValue = "user_1") String username,
                            @RequestParam(value = "place", defaultValue = "1") String place,
                            @RequestParam(value = "ranked", defaultValue = "0") String ranked,
                            @RequestParam(value = "avgspeed", defaultValue = "100") String avgSpeed,
                            @RequestParam(value = "time", defaultValue = "3600") String time) {
        this.jdbcTemplate.update("INSERT INTO ParticipantsEventsSummary values(?, ?, ?, ?, ?, ?)",
                eventId, username, place, ranked, avgSpeed, time);
    }

    @DeleteMapping(value = "/summary")
    public void deleteSummary(@RequestParam(value = "eventid", required = false) String eventId,
                              @RequestParam(value = "username", required = false) String username) {
        if (eventId != null && username != null) {
            this.jdbcTemplate.update("DELETE FROM ParticipantsEventsSummary WHERE EventId = ? AND Username = ?", eventId, username);
        } else if (eventId != null) {
            this.jdbcTemplate.update("DELETE FROM ParticipantsEventsSummary WHERE EventId = ?", eventId);
        } else {
            this.jdbcTemplate.update("DELETE FROM ParticipantsEventsSummary WHERE Username = ?", username);
        }
    }
}
