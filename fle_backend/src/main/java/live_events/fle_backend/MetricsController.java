package live_events.fle_backend;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.*;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class MetricsController {
    InfluxDB influxDB;

    public MetricsController() {
        OkHttpClient.Builder okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();


        influxDB = InfluxDBFactory.connect(
                "https://34.106.36.59:8086/", "admin", "root", okHttpClient);
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

    @GetMapping("/userMetrics")
    public QueryResult getMetrics() {
        Query queryMetrics = new Query("Select * from metrics", "metrics");

        QueryResult result  = influxDB.query(queryMetrics);
        return result;
    }

    @PostMapping("/userMetrics")
    public void postMetrics(@RequestParam(value = "id", defaultValue = "1234") String id,
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
}

