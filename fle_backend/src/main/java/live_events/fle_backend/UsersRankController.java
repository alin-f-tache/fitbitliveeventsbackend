package live_events.fle_backend;

import okhttp3.OkHttpClient;
import org.influxdb.InfluxDBFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class UsersRankController {
    private final JdbcTemplate jdbcTemplate;

    public UsersRankController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }

    @GetMapping("/usersRank")
    public List<Map<String, Object>> getEvent(@RequestParam(value = "username", required = false) String username,
                                              @RequestParam(value = "check_finish", required = false) String finish) {
        if (finish != null) {
            return this.jdbcTemplate.queryForList("SELECT * from UsersRank WHERE Username=? AND ValidPoints/TotalPoints>0.9", username);
        }
        return this.jdbcTemplate.queryForList("SELECT * from" +
                " (SELECT Distance, Username, Finish,  @rank := @rank + 1 AS rank " +
                "FROM UsersRank u, (SELECT @rank := (SELECT count(*) FROM UsersRank WHERE Finish!=0 AND ValidPoints/TotalPoints>0.9)) r " +
                "WHERE ValidPoints/TotalPoints>0.9 AND Finish=0 ORDER BY Distance DESC) t " +
                "WHERE Username=?;", username);
    }
}
