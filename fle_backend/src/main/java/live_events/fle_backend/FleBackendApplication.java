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

import java.util.*;
import java.util.concurrent.TimeUnit ;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
            List<Double> lat= new ArrayList<Double>() {{
                add(44.48716); add(44.48673); add(44.48587);
                add(44.48442); add(44.48254); add(44.48139);
                add(44.48076); add(44.48056); add(44.47865);
                add(44.47743); add(44.47706); add(44.47706);
                add(44.47735); add(44.47709); add(44.47709);
                add(44.47709); add(44.47709); add(44.47758);
                add(44.47779); add(44.47738); add(44.47705);
                add(44.47632); add(44.47525); add(44.475);
                add(44.47445); add(44.475); add(44.47611);
                add(44.47901); add(44.48232); add(44.48584);
                add(44.48764); add(44.48871); add(44.48779);

            }};
            List<Double> lng= new ArrayList<Double>() {{
                add(26.08342); add(26.08418); add(26.08452);
                add(26.0842); add(26.08272); add(26.08132);
                add(26.0793); add(26.07819); add(26.0787);
                add(26.07834); add(26.07885); add(26.07885);
                add(26.08115); add(26.08115); add(26.08207);
                add(26.08115); add(26.08207); add(26.08338);
                add(26.08476); add(26.08654); add(26.08758);
                add(26.08861); add(26.08904); add(26.09063);
                add(26.09093); add(26.09153); add(26.08986);
                add(26.08737); add(26.08599); add(26.08574);
                add(26.08561); add(26.08453); add(26.083);
            }};

            List<Double> lat_participant2= new ArrayList<Double>() {{
                add(44.48727); add(44.48667); add(44.48592);
                add(44.48442); add(44.48159); add(44.48076);
                add(44.48076); add(44.48056); add(44.47827);
                add(44.47716); add(44.47734); add(44.47706);
                add(44.47777); add(44.47665); add(44.47553);
                add(44.475); add(44.47454); add(44.47521);
                add(44.47933); add(44.48678); add(44.4886);
                add(44.48833); add(44.48779); add(44.48779);
            }};

            List<Double> lng_participant2= new ArrayList<Double>() {{
                add(26.08331); add(26.08425); add(26.08446);
                add(26.0842); add(26.08172); add(26.0793);
                add(26.0793); add(26.07819); add(26.07867);
                add(26.07852); add(26.08077); add(26.08187);
                add(26.08459); add(26.08828); add(26.08889);
                add(26.08996); add(26.0908); add(26.09108);
                add(26.08702); add(26.08575); add(26.08438);
                add(26.08401); add(26.083); add(26.083);
            }};

            List<Double> lat_participant3= new ArrayList<Double>() {{
                add(44.48716); add(44.48669); add(44.48588);
                add(44.48478); add(44.4838); add(44.48312);
                add(44.48219); add(44.48121); add(44.4808);
                add(44.48078); add(44.47997); add(44.48017);
                add(44.4808); add(44.48175); add(44.48254);
                add(44.48337); add(44.48473); add(44.486);
                add(44.48687); add(44.48756); add(44.4879);
                add(44.48781); add(44.48764); add(44.48779);
            }};

            List<Double> lng_participant3= new ArrayList<Double>() {{
                add(26.08342); add(26.08422); add(26.0845);
                add(26.08437); add(26.08383); add(26.08323);
                add(26.08241); add(26.08093); add(26.0796);
                add(26.0793); add(26.07950); add(26.08089);
                add(26.08199); add(26.0834); add(26.08456);
                add(26.08578); add(26.08583); add(26.08583);
                add(26.08574); add(26.08566); add(26.08531);
                add(26.08497); add(26.08402); add(26.083);
            }};

            List<Double> lat_participant1= new ArrayList<Double>() {{
                add(44.48716); add(44.48673); add(44.48587);
                add(44.48076); add(44.48056); add(44.47865);
                add(44.47735); add(44.47709); add(44.47709);
                add(44.47779); add(44.47738); add(44.47705);
                add(44.47632); add(44.47525); add(44.475);
                add(44.47445); add(44.475); add(44.47611);
                add(44.47901); add(44.48232); add(44.48584);
                add(44.48764); add(44.48871); add(44.48779);
            }};

            List<Double> lng_participant1= new ArrayList<Double>() {{
                add(26.08342); add(26.08418); add(26.08452);
                add(26.0793); add(26.07819); add(26.0787);
                add(26.08115); add(26.08115); add(26.08207);
                add(26.08476); add(26.08654); add(26.08758);
                add(26.08861); add(26.08904); add(26.09063);
                add(26.09093); add(26.09153); add(26.08986);
                add(26.08737); add(26.08599); add(26.08574);
                add(26.08561); add(26.08453); add(26.083);
            }};



            List<Double> speed_participant2= new ArrayList<Double>() {{
                add(1.2); add(2.2); add(3.1); add(1.2);
                add(2.2); add(3.1); add(1.2); add(2.2);
                add(4.1); add(1.2); add(5.2); add(3.1);
                add(7.2); add(2.2); add(3.1); add(2.2);
                add(2.2); add(8.1); add(6.2); add(9.2);
                add(3.1); add(2.1); add(1.1); add(0.1);
            }};

            List<Double> speed_participant1= new ArrayList<Double>() {{
                add(0.2); add(1.2); add(2.1); add(3.2);
                add(2.2); add(3.1); add(4.2); add(5.2);
                add(4.1); add(4.2); add(3.2); add(3.1);
                add(5.2); add(6.2); add(7.1); add(3.2);
                add(2.2); add(1.1); add(2.2); add(1.2);
                add(2.1); add(1.1); add(2.1); add(0.1);
            }};

            List<Double> speed_participant3= new ArrayList<Double>() {{
                add(0.2); add(0.2); add(1.1); add(1.2);
                add(2.2); add(3.1); add(1.2); add(1.2);
                add(2.1); add(2.2); add(3.2); add(2.1);
                add(4.2); add(6.2); add(7.1); add(8.2);
                add(3.2); add(3.1); add(3.2); add(2.2);
                add(2.1); add(2.1); add(1.1); add(0.1);
            }};

            String key = this.mockMvc.perform(post("/authenticate?username=anacalin&password=1234")).andReturn().getResponse().getContentAsString();
            System.out.println(key);

            try {

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date(System.currentTimeMillis());
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.DATE, 1);
                Date today = calendar.getTime();
                System.out.println(this.mockMvc.perform(post("/events?id=0002&title=Demo&date=" +
                        formatter.format(today) + "" +
                        "&username=anacalin" +
                        "&street=Elena Vacarescu&number=1" +
                        "&description=See you there! ^^")
                        .header("Authorization", "Bearer " + key))
                        .andReturn().getResponse().getContentAsString());
                for (int i = 0; i < lat.size(); i++) {
                    Integer idx = i;
                    System.out.println(this.mockMvc.perform(post("/eventRoutes?id=0002&pointnumber=" +
                            idx.toString() + "&lat=" + lat.get(i).toString() + "&lng=" + lng.get(i).toString())
                            .header("Authorization", "Bearer " + key))
                            .andReturn().getResponse().getContentAsString());
                }


                System.out.println(this.mockMvc.perform(post("/userRoles?eventid=0002&username=anacalin&date=" +
                        formatter.format(date) +
                        "&role=participant")
                        .header("Authorization", "Bearer " + key))
                        .andReturn().getResponse().getContentAsString());
                System.out.println(this.mockMvc.perform(post("/userRoles?eventid=0002&username=ioni&date=" +
                        formatter.format(date) +
                        "&role=participant")
                        .header("Authorization", "Bearer " + key))
                        .andReturn().getResponse().getContentAsString());
                System.out.println(this.mockMvc.perform(post("/userRoles?eventid=0002&username=ghost_user&date=" +
                        formatter.format(date) +
                        "&role=participant")
                        .header("Authorization", "Bearer " + key))
                        .andReturn().getResponse().getContentAsString());

                Double total_speed_participant1 = 0.;
                Double total_speed_participant2 = 0.;
                Double total_speed_participant3 = 0.;
                for (int i = 0; i < speed_participant1.size(); i++) {
                    total_speed_participant1 += speed_participant1.get(i);
                    total_speed_participant2 += speed_participant2.get(i);
                    total_speed_participant3 += speed_participant3.get(i);
                    Double avg_speed_participant1 = total_speed_participant1 / (i + 1);
                    Double avg_speed_participant2 = total_speed_participant2 / (i + 1);
                    Double avg_speed_participant3 = total_speed_participant3 / (i + 1);
                    System.out.println(this.mockMvc.perform(post("/userMetrics?heartrate=1&username=anacalin" +
                            "&latitude=" + lat_participant1.get(i).toString() +
                            "&longitude=" + lng_participant1.get(i).toString() +
                            "&current_speed=" + speed_participant1.get(i).toString() +
                            "&average_speed=" + avg_speed_participant1.toString())
                            .header("Authorization", "Bearer " + key))
                            .andReturn().getResponse().getContentAsString());
                    System.out.println(this.mockMvc.perform(post("/userMetrics?heartrate=1&username=ioni" +
                            "&latitude=" + lat_participant2.get(i).toString() +
                            "&longitude=" + lng_participant2.get(i).toString() +
                            "&current_speed=" + speed_participant2.get(i).toString() +
                            "&average_speed=" + avg_speed_participant2.toString())
                            .header("Authorization", "Bearer " + key))
                            .andReturn().getResponse().getContentAsString());
                    System.out.println(this.mockMvc.perform(post("/userMetrics?heartrate=1&username=ghost_user" +
                            "&latitude=" + lat_participant3.get(i).toString() +
                            "&longitude=" + lng_participant3.get(i).toString() +
                            "&current_speed=" + speed_participant3.get(i).toString() +
                            "&average_speed=" + avg_speed_participant3.toString())
                            .header("Authorization", "Bearer " + key))
                            .andReturn().getResponse().getContentAsString());
                    TimeUnit.SECONDS.sleep(1);
                }

                TimeUnit.SECONDS.sleep(30);
                System.out.println(this.mockMvc.perform(delete("/events?id=0002&demo=True")
                        .header("Authorization", "Bearer " + key))
                        .andReturn().getResponse().getContentAsString());

                System.out.println(this.mockMvc.perform(delete("/summary?eventid=0002")
                        .header("Authorization", "Bearer " + key))
                        .andReturn().getResponse().getContentAsString());

                System.out.println(this.mockMvc.perform(delete("/userMetrics?username=anacalin")
                        .header("Authorization", "Bearer " + key))
                        .andReturn().getResponse().getContentAsString());
                System.out.println(this.mockMvc.perform(delete("/userMetrics?username=ioni")
                        .header("Authorization", "Bearer " + key))
                        .andReturn().getResponse().getContentAsString());
                System.out.println(this.mockMvc.perform(delete("/userMetrics?username=ghost_user")
                        .header("Authorization", "Bearer " + key))
                        .andReturn().getResponse().getContentAsString());
            } catch (Exception e) {
                System.out.println("ERROR: A problem occured during event Demo simulation");

                System.out.println(this.mockMvc.perform(delete("/events?id=0002&demo=True")
                        .header("Authorization", "Bearer " + key))
                        .andReturn().getResponse().getContentAsString());

                System.out.println(this.mockMvc.perform(delete("/summary?eventid=0002")
                        .header("Authorization", "Bearer " + key))
                        .andReturn().getResponse().getContentAsString());

                System.out.println(this.mockMvc.perform(delete("/userMetrics?username=anacalin")
                        .header("Authorization", "Bearer " + key))
                        .andReturn().getResponse().getContentAsString());
                System.out.println(this.mockMvc.perform(delete("/userMetrics?username=ioni")
                        .header("Authorization", "Bearer " + key))
                        .andReturn().getResponse().getContentAsString());
                System.out.println(this.mockMvc.perform(delete("/userMetrics?username=ghost_user")
                        .header("Authorization", "Bearer " + key))
                        .andReturn().getResponse().getContentAsString());
            }
        };
    }


}
