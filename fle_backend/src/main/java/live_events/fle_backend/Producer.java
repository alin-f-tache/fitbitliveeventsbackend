package live_events.fle_backend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Component
public class Producer {

    private final KafkaTemplate<String, String> kafkaTemplate;


    @Value("${cloudkarafka.topic}")
    private String topic;

    Producer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEmail(SampleMessage message) {
        this.kafkaTemplate.send(topic, message.getMessage());
        System.out.println("Sent sample message [" + message + "] to " + topic);
    }

}
