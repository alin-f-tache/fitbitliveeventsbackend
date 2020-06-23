package live_events.fle_backend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.common.protocol.types.Field;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import okhttp3.OkHttpClient;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class MetricsController {
    InfluxDB influxDB;
    private final JdbcTemplate jdbcTemplate;
    List<Double> lat_route;
    List<Double> lng_route;
    List<String> finished;

    public static double distance(double lat1,
                                  double lat2, double lon1,
                                  double lon2) {

        // The math module contains a function
        // named toRadians which converts from
        // degrees to radians.
        lon1 = Math.toRadians(lon1);
        lon2 = Math.toRadians(lon2);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        // Haversine formula
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2),2);

        double c = 2 * Math.asin(Math.sqrt(a));

        // Radius of earth in kilometers. Use 3956
        // for miles
        double r = 6371;

        // calculate the result
        return(c * r);
    }

    public static double distance(double lat1, double lon1, double lat2, double lon2, double lat3, double lon3) {
        double y = Math.sin(lon3 - lon1) * Math.cos(lat3);
        double x = Math.cos(lat1) * Math.sin(lat3) - Math.sin(lat1) * Math.cos(lat3) * Math.cos(lat3 - lat1);
        double bearing1 = Math.toDegrees(Math.atan2(y, x));
        bearing1 = 360 - ((bearing1 + 360) % 360);

        double y2 = Math.sin(lon2 - lon1) * Math.cos(lat2);
        double x2 = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1) * Math.cos(lat2) * Math.cos(lat2 - lat1);
        double bearing2 = Math.toDegrees(Math.atan2(y2, x2));
        bearing2 = 360 - ((bearing2 + 360) % 360);

        double lat1Rads = Math.toRadians(lat1);
        double lat3Rads = Math.toRadians(lat3);
        double dLon = Math.toRadians(lon3 - lon1);

        double distanceAC = Math.acos(Math.sin(lat1Rads) * Math.sin(lat3Rads)+Math.cos(lat1Rads)*Math.cos(lat3Rads)*Math.cos(dLon)) * 6371.0;
        double min_distance = Math.abs(Math.asin(Math.sin(distanceAC/6371.0)*Math.sin(Math.toRadians(bearing1)-Math.toRadians(bearing2))) * 6371.0);
        return min_distance;
    }

    public  static Boolean check_point(List<Double> lat_route, List<Double> lng_route, Double lat, Double lng) {
        Boolean is_valid = false;
        for(int i = 0; i < (lat_route.size() - 1); i++) {
            if(distance(lat_route.get(i), lng_route.get(i), lat_route.get(i+1), lng_route.get(i+1), lat, lng) < 0.01) {
                is_valid = true;
                return is_valid;
            }
        }
        return is_valid;
    }

    public MetricsController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        OkHttpClient.Builder okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

        influxDB = InfluxDBFactory.connect(
                "https://34.106.229.28:8086/", "admin", "root", okHttpClient);
        this.finished = new ArrayList<>();
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
    public QueryResult getMetrics(@RequestParam(value = "username", required = false) String username) {
        if (username != null) {
            Query queryMetrics = new Query("Select last(*) from user_metrics where username='" + username + "'", "metrics");

            return influxDB.query(queryMetrics);
        }
        Query queryMetrics = new Query("Select * from user_metrics", "metrics");

        return influxDB.query(queryMetrics);
    }

    @DeleteMapping("/userMetrics")
    public QueryResult deleteMetrics(@RequestParam(value = "username", required = false) String username) {
        Query queryMetrics = new Query("Delete from user_metrics  where username='" + username + "'", "metrics");

        return influxDB.query(queryMetrics);
    }

    @PostMapping("/userMetrics")
    public void postMetrics(@RequestParam(value = "username") String username,
                            @RequestParam(value = "heartrate") String heartrate,
                            @RequestParam(value = "latitude") String latitude,
                            @RequestParam(value = "longitude") String longitude,
                            @RequestParam(value = "current_speed") String current_speed,
                            @RequestParam(value = "average_speed") String average_speed) {

        Query queryMetrics = new Query("Select last(*) from user_metrics where username='" + username + "'", "metrics");
        try {
            Double lat = (Double)influxDB.query(queryMetrics).getResults().get(0).getSeries().get(0).getValues().get(0).get(4);
            Double lng = (Double)influxDB.query(queryMetrics).getResults().get(0).getSeries().get(0).getValues().get(0).get(5);
            Double distance = distance(lat, Double.valueOf(latitude),
                    lng, Double.valueOf(longitude));
            Double finish_distance = distance(lat_route.get(lat_route.size() - 1), Double.valueOf(latitude),
                    lng_route.get(lng_route.size() - 1), Double.valueOf(longitude));
            String finish = "0";
            if (finish_distance < 0.001) {
                System.out.println("FINISH " + username);
                List<Double> last_rank = this.jdbcTemplate.query("SELECT count(*) FROM UsersRank WHERE Finish!=0;", new RowMapper<Double>(){
                    public Double mapRow(ResultSet rs, int rowNum)
                            throws SQLException {
                        return rs.getDouble(1);
                    }
                });
                Double rank = last_rank.get(0) + 1;
                finish = rank.toString();
                if(!finished.contains(username)) {
                    finished.add(username);
                    this.jdbcTemplate.update("UPDATE UsersRank set Finish=?" +
                            " WHERE Username=?", finish, username);
                    this.jdbcTemplate.update("INSERT INTO ParticipantsEventsSummary values(?, ?, ?, ?, ?, ?)" ,
                            "2", username, finish, "1", average_speed, "3600");
                }
            }

            if(check_point(this.lat_route, this.lng_route, Double.valueOf(latitude), Double.valueOf(longitude)) == false) {
                System.out.println(latitude);
                System.out.println(longitude);
                this.jdbcTemplate.update("UPDATE UsersRank set Distance=Distance+?, TotalPoints=TotalPoints+1" +
                        " WHERE Username=?", distance.toString(), username);
            } else {
                this.jdbcTemplate.update("UPDATE UsersRank set Distance=Distance+?, TotalPoints=TotalPoints+1," +
                        " ValidPoints=ValidPoints+1 WHERE Username=?", distance.toString(), username);
            }
            System.out.println(username);
        } catch (Exception e) {
            System.out.println("error: " + e);
            this.jdbcTemplate.update("INSERT into UsersRank values(?, ?, ?, ?, ?)", username, "0", "0", "0", "0");
            this.lat_route = this.jdbcTemplate.query("SELECT * FROM EventRoute WHERE EventId = 2 ORDER BY PointNumber", new RowMapper<Double>(){
                public Double mapRow(ResultSet rs, int rowNum)
                        throws SQLException {
                    return rs.getDouble(3);
                }
            });
            this.lng_route = this.jdbcTemplate.query("SELECT * FROM EventRoute WHERE EventId = 2 ORDER BY PointNumber", new RowMapper<Double>(){
                public Double mapRow(ResultSet rs, int rowNum)
                        throws SQLException {
                    return rs.getDouble(4);
                }
            });
        }

        Point point = Point.measurement("user_metrics")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .addField("heartrate", Integer.valueOf(heartrate))
                .addField("latitude", Float.valueOf(latitude))
                .addField("longitude", Float.valueOf(longitude))
                .addField("current_speed", Float.valueOf(current_speed))
                .addField("average_speed", Float.valueOf(average_speed))
                .tag("username", username)
                .build();

        BatchPoints batchPoints = BatchPoints
                .database("metrics")
                .retentionPolicy("defaultPolicy")
                .build();
        batchPoints.point(point);
        influxDB.write(batchPoints);
    }
}

