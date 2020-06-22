package live_events.fle_backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
class Consumer {
    @Autowired
    private JavaMailSender javaMailSender;

    private final JdbcTemplate jdbcTemplate;

    public Consumer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    void sendEmail(String email) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);

        msg.setSubject("Event canceled");
        msg.setText("We are sorry to inform you that one of the event you signed for was canceled. \n\n\n\n" +
                "Fitbit Live Events Team");


        javaMailSender.send(msg);

    }

    @KafkaListener(topics = "${cloudkarafka.topic}")
    public void processMessage(String message,
                               @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
                               @Header(KafkaHeaders.RECEIVED_TOPIC) List<String> topics,
                               @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        System.out.println(message);
        if ("DELETE:".equals(message.split(" ")[0])) {
            String id = message.split(" ")[1];
            System.out.println("Sending...");
            List<Map<String, Object>> emails = this.jdbcTemplate.queryForList("SELECT Email from Users u JOIN" +
                    " UsersRole r on u.Username = r.Username and r.EventId = ? and r.Role != 'Organizer';", id);
            System.out.println(emails);
            for (int i = 0; i < emails.size(); i++) {
                Map.Entry<String, Object> entry = emails.get(i).entrySet().iterator().next();
                System.out.println(entry.getValue().toString());
                sendEmail(entry.getValue().toString());
            }
            this.jdbcTemplate.update("DELETE FROM Events WHERE Id = ?", id);
            this.jdbcTemplate.update("DELETE from EventRoute WHERE EventId = ?", id);
            this.jdbcTemplate.update("DELETE from UsersRole WHERE EventId = ?", id);
            this.jdbcTemplate.update("DELETE from UsersRank;");
        }
        System.out.println(message);
    }

}
