package live_events.fle_backend;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class EventRouteController {
    private final JdbcTemplate jdbcTemplate;

    public EventRouteController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/eventRoutes")
    public List<Map<String, Object>> listEventRoute(@RequestParam(value = "id", required = false) String id) {
        if (id != null) {
            return this.jdbcTemplate.queryForList("SELECT * FROM EventRoute WHERE EventId = ? ORDER BY PointNumber", id);
        } else {
            return this.jdbcTemplate.queryForList("SELECT * FROM EventRoute ORDER BY PointNumber;");
        }
    }

    @PostMapping("/eventRoutes")
    public String postEventRoute(@RequestParam(value = "id", defaultValue = "1234") String id,
                                 @RequestParam(value = "pointnumber", defaultValue = "5") String point_number,
                                 @RequestParam(value = "lat", defaultValue = "0") String lat,
                                 @RequestParam(value = "lng", defaultValue = "0") String lng) {
        this.jdbcTemplate.update("INSERT into EventRoute values(?, ?, ?, ?)", id, point_number, lat, lng);
        return "Succes";
    }

    @DeleteMapping("/eventRoutes")
    public String deleteEventRoute(@RequestParam(value = "id", defaultValue = "1234") String id) {
        this.jdbcTemplate.update("DELETE from EventRoute WHERE EventId=?", id);
        return "Succes";
    }
}
