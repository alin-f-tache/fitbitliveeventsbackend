package live_events.fle_backend;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class EventRouteController {
    private final JdbcTemplate jdbcTemplate;

    public EventRouteController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @RequestMapping(value = "/addEventRoute", method = RequestMethod.POST)
    public String addEventRoute(@RequestParam(value = "id", defaultValue = "1234") String id,
                                @RequestParam(value = "pointnumber", defaultValue = "5") String point_number,
                              @RequestParam(value = "lat", defaultValue = "0") String lat,
                              @RequestParam(value = "lng", defaultValue = "0") String lng) {
        this.jdbcTemplate.update("INSERT into EventRoute values(?, ?, ?, ?)", id, point_number, lat, lng);
        return "Succes";
    }

    @RequestMapping(value = "/listEventsRoutes", method = RequestMethod.GET)
    public List<Map<String, Object>> listEventsRoutes() {
        return this.jdbcTemplate.queryForList("SELECT * FROM EventRoute ORDER BY PointNumber;");
    }

    @RequestMapping(value = "/listEventRoutes", method = RequestMethod.GET)
    public List<Map<String, Object>> listEventRoute(@RequestParam(value = "id", defaultValue = "1234") String id) {
        return this.jdbcTemplate.queryForList("SELECT * FROM EventRoute WHERE EventId = ? ORDER BY PointNumber", id);
    }

    @RequestMapping(value = "/deleteEventRoute", method = RequestMethod.DELETE)
    public String deleteEventRoute(@RequestParam(value = "id", defaultValue = "1234") String id) {
        this.jdbcTemplate.update("DELETE from EventRoute WHERE EventId=?", id);
        return "Succes";
    }

}
