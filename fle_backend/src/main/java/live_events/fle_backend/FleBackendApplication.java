package live_events.fle_backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit ;
import java.util.Date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@SpringBootApplication
@AutoConfigureMockMvc
public class FleBackendApplication {

    @Autowired
    MockMvc mockMvc;

//    @Autowired
//    private RestTemplate restTemplate;

    public static void main(String[] args) {
        SpringApplication.run(FleBackendApplication.class, args);
    }

    @Bean
    public ApplicationRunner runner() {
        return (args) -> {
            List<Double> avg_speed = new ArrayList<Double>() {{
                add(1.2);
                add(2.2);
                add(3.1);
                add(1.2);
                add(2.2);
                add(3.1);
                add(1.2);
                add(2.2);
                add(4.1);
                add(1.2);
                add(5.2);
                add(3.1);
                add(7.2);
                add(2.2);
                add(3.1);
                add(2.2);
                add(2.2);
                add(8.1);
                add(1.2);
                add(9.2);
                add(3.1);
            }};
            List<Double> speed = new ArrayList<Double>() {{
                add(5.2);
                add(3.2);
                add(2.1);
                add(1.2);
                add(5.2);
                add(3.1);
                add(2.2);
                add(8.2);
                add(9.1);
                add(2.2);
                add(3.2);
                add(6.1);
                add(7.2);
                add(3.2);
                add(1.1);
                add(2.2);
                add(6.2);
                add(9.1);
                add(0.2);
                add(0.2);
                add(0.1);
            }};
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(System.currentTimeMillis());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, 1);
            Date today = calendar.getTime();
            String key = this.mockMvc.perform(post("/authenticate?username=anacalin&password=1234")).andReturn().getResponse().getContentAsString();
            System.out.println(key);
            System.out.println(this.mockMvc.perform(post("/events?id=0002&title=Demo&date=" +
                    formatter.format(today) + "" +
                    "&username=anacalin")
                    .header("Authorization" , "Bearer " + key))
                    .andReturn().getResponse().getContentAsString());
            System.out.println(this.mockMvc.perform(get("/events?id=0002")
                    .header("Authorization" , "Bearer " + key))
                    .andReturn().getResponse().getContentAsString());
            System.out.println(this.mockMvc.perform(post("/userRoles?eventid=0002&username=anacalin&date=" +
                    formatter.format(date) +
                    "&role=participant")
                    .header("Authorization" , "Bearer " + key))
                    .andReturn().getResponse().getContentAsString());
            System.out.println(this.mockMvc.perform(post("/userRoles?eventid=0002&username=ioni&date=" +
                    formatter.format(date) +
                    "&role=participant")
                    .header("Authorization" , "Bearer " + key))
                    .andReturn().getResponse().getContentAsString());

            for (int i = 0; i < avg_speed.size(); i++) {
                System.out.println(this.mockMvc.perform(post("/userMetrics?heartrate=1&username=anacalin" +
                        "&latitude=1.0&longitude=1.0&current_speed=" + speed.get(i).toString() + "&average_speed=" +
                        avg_speed.get(i).toString())
                        .header("Authorization" , "Bearer " + key))
                        .andReturn().getResponse().getContentAsString());
                System.out.println(this.mockMvc.perform(post("/userMetrics?heartrate=1&username=ioni" +
                        "&latitude=1.0&longitude=1.0&current_speed=" + speed.get(i).toString() + "&average_speed=" +
                        avg_speed.get(i).toString())
                        .header("Authorization" , "Bearer " + key))
                        .andReturn().getResponse().getContentAsString());
                TimeUnit.SECONDS.sleep(5);
            }

//            TimeUnit.SECONDS.sleep(3);
//            System.out.println(this.mockMvc.perform(get("/userMetrics?username=anacalin")
//                    .header("Authorization" , "Bearer " + key))
//                    .andReturn().getResponse().getContentAsString());
//


            TimeUnit.SECONDS.sleep(60);
            System.out.println(this.mockMvc.perform(delete("/events?id=0002")
                    .header("Authorization" , "Bearer " + key))
                    .andReturn().getResponse().getContentAsString());

            System.out.println(this.mockMvc.perform(delete("/userMetrics")
                    .header("Authorization" , "Bearer " + key))
                    .andReturn().getResponse().getContentAsString());
        };
    }

}
