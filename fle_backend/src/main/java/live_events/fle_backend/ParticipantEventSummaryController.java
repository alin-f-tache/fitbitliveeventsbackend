package live_events.fle_backend;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ParticipantEventSummaryController {
    private final JdbcTemplate jdbcTemplate;

    public ParticipantEventSummaryController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @RequestMapping(value = "/addSummary", method = RequestMethod.POST)
    public void addSummary(@RequestParam(value = "eventid", defaultValue = "10") String eventId,
                           @RequestParam(value = "participantid", defaultValue = "1234") String participantId,
                           @RequestParam(value = "place", defaultValue = "1") String place,
                           @RequestParam(value = "avgspeed", defaultValue = "100") String avgSpeed,
                           @RequestParam(value = "time", defaultValue = "3600") String time) {
        this.jdbcTemplate.update("INSERT INTO ParticipantsEventsSummary values(?, ?, ?, ?, ?)",
                eventId, participantId, place, avgSpeed, time);
    }

    @RequestMapping(value = "/removeSummary", method = RequestMethod.DELETE)
    public void removeSummary(@RequestParam(value = "eventid", defaultValue = "10") String eventId,
                              @RequestParam(value = "participantid", defaultValue = "1234") String participantId) {
        this.jdbcTemplate.update("DELETE FROM ParticipantsEventsSummary WHERE EventId = ? AND ParticipantId = ?", eventId, participantId);
    }

    @RequestMapping(value = "/removeEventSummary", method = RequestMethod.DELETE)
    public void removeEventSummary(@RequestParam(value = "eventid", defaultValue = "10") String eventId) {
        this.jdbcTemplate.update("DELETE FROM ParticipantsEventsSummary WHERE EventId = ?", eventId);
    }

    @RequestMapping(value = "/removeParticipantSummary", method = RequestMethod.DELETE)
    public void removeParticipantSummary(@RequestParam(value = "participantid", defaultValue = "1234") String participantId) {
        this.jdbcTemplate.update("DELETE FROM ParticipantsEventsSummary WHERE ParticipantId = ?", participantId);
    }

    @RequestMapping(value = "/fetchSummary", method = RequestMethod.GET)
    public List<Map<String, Object>> fetchSummary(@RequestParam(value = "eventid", defaultValue = "10") String eventId,
                                                  @RequestParam(value = "participantid", defaultValue = "1234") String participantId) {
        return this.jdbcTemplate.queryForList("SELECT * FROM ParticipantsEventsSummary WHERE EventId = ? AND ParticipantId = ?", eventId, participantId);
    }

    @RequestMapping(value = "/fetchEventSummary", method = RequestMethod.GET)
    public List<Map<String, Object>> fetchEventSummary(@RequestParam(value = "eventid", defaultValue = "10") String eventId) {
        return this.jdbcTemplate.queryForList("SELECT * FROM ParticipantsEventsSummary WHERE EventId = ?", eventId);
    }

    @RequestMapping(value = "/fetchParticipantSummary", method = RequestMethod.GET)
    public List<Map<String, Object>> fetchParticipantSummary(@RequestParam(value = "participantid", defaultValue = "10") String participantId) {
        return this.jdbcTemplate.queryForList("SELECT * FROM ParticipantsEventsSummary WHERE participantId = ?", participantId);
    }
}
