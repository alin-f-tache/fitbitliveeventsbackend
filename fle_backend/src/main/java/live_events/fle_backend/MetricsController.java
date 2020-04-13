package live_events.fle_backend;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.*;
import org.influxdb.impl.InfluxDBResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
public class MetricsController {
    InfluxDB influxDB = InfluxDBFactory.connect(
            "http://localhost:8086/", "admin", "root");

    public MetricsController() {

    }


    @GetMapping("/metrics")
    public String home() {
        Pong response = this.influxDB.ping();
        if (response.getVersion().equalsIgnoreCase("unknown")) {
            return "Connection failed";
        }
        influxDB.createDatabase("metrics");
        influxDB.createRetentionPolicy(
                "defaultPolicy", "metrics", "60d", 1, true);
        return "Succes";
    }

    @RequestMapping(value = "/insertMetrics", method = RequestMethod.POST)
    public void insertMetrics(@RequestParam(value = "id", defaultValue = "1234") String id,
                              @RequestParam(value = "speed", defaultValue = "0") String speed,
                              @RequestParam(value = "heartbeat", defaultValue = "70") String heartbeat,
                              @RequestParam(value = "position", defaultValue = "null") String position) {
        Point point = Point.measurement("metrics")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .addField("id", id)
                .addField("speed", speed)
                .addField("heartbeat", heartbeat)
                .addField("position", position)
                .build();

        BatchPoints batchPoints = BatchPoints
                .database("metrics")
                .retentionPolicy("defaultPolicy")
                .build();
        batchPoints.point(point);
        influxDB.write(batchPoints);
    }

    @RequestMapping(value = "/listMetrics", method = RequestMethod.GET)
    public QueryResult listMetrics() {
        Query queryMetrics = new Query("Select * from metrics", "metrics");

        QueryResult result  = influxDB.query(queryMetrics);
        return result;
    }
}
