package live_events.fle_backend;

import java.security.cert.CertificateException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import okhttp3.OkHttpClient;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.*;
import org.influxdb.impl.InfluxDBResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import okhttp3.OkHttpClient.Builder;

import javax.net.ssl.*;

class UnsafeOkHttpClient {
    public static OkHttpClient.Builder getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            OkHttpClient okHttpClient = builder.build();
            return builder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class MetricsController {
    InfluxDB influxDB;

    public MetricsController() {
        OkHttpClient.Builder okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();


        influxDB = InfluxDBFactory.connect(
                "https://34.106.230.241:8086/", "admin", "root", okHttpClient);
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

