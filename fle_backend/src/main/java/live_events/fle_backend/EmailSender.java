package live_events.fle_backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class EmailSender {
    @Autowired
    private JavaMailSender javaMailSender;

    void sendEmail(String email, String role, String link) {

        try {

            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(email);

            msg.setSubject("Invitation");
            if (role.equals("Organizer")) {
                msg.setText("We are pleased to invite you to take part as an organizer in our new event: " + link + "\n\n\n\n" +
                        "Fitbit Live Events Team");
            } else {
                msg.setText("We are pleased to invite you to take part as a participant in our new event: " + link + "\n\n\n\n" +
                        "Fitbit Live Events Team");
            }

            javaMailSender.send(msg);
        }
        catch(Exception e) {
            System.out.println("[ERROR]: Email address incorrect.");
        }

    }

    @RequestMapping(value = "/sendInvitation", method = RequestMethod.POST)
    public String sendInvitation(@RequestParam(value = "email", defaultValue = "liveeventsteam@gmail.com") String email,
                                 @RequestParam(value = "role", defaultValue = "participant") String role,
                                 @RequestParam(value = "link", defaultValue = "http://localhost:3000") String link) {
        sendEmail(email, role, link);
        return "Succes";
    }
}
