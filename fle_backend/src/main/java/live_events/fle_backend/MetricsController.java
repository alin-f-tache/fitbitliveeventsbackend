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

    /*
    Currently using the following structure:
    INFLUXDB:
        - Database: metrics
            - Measurement: user_metrics
                - Tags: name=user_metrics
                - Fields: username, heartrate, latitude, longitude, current_speed, average_speed
     */

    @GetMapping("/userMetrics")
    public QueryResult getMetrics() {
        Query queryMetrics = new Query("Select * from user_metrics", "metrics");

        return influxDB.query(queryMetrics);
    }

    @PostMapping("/userMetrics")
    public void postMetrics(@RequestParam(value = "username") String username,
                            @RequestParam(value = "heartrate") String heartrate,
                            @RequestParam(value = "latitude") String latitude,
                            @RequestParam(value = "longitude") String longitude,
                            @RequestParam(value = "current_speed") String current_speed,
                            @RequestParam(value = "average_speed") String average_speed) {
        Point point = Point.measurement("user_metrics")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .addField("username", username)
                .addField("heartrate", heartrate)
                .addField("latitude", latitude)
                .addField("longitude", longitude)
                .addField("current_speed", current_speed)
                .addField("average_speed", average_speed)
                .build();

        BatchPoints batchPoints = BatchPoints
                .database("metrics")
                .retentionPolicy("defaultPolicy")
                .build();
        batchPoints.point(point);
        influxDB.write(batchPoints);
    }
}

