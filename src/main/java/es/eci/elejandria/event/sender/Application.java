package es.eci.elejandria.event.sender;

import es.eci.elejandria.event.sender.beans.CustomerBean;
import es.eci.elejandria.event.sender.beans.EventBean;
import es.eci.elejandria.event.sender.beans.ProductBean;
import es.eci.elejandria.event.sender.beans.randomizers.CustomerRandomizer;
import es.eci.elejandria.event.sender.beans.randomizers.ProductRandomizer;
import es.eci.elejandria.event.sender.kafka.KafkaEventSender;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;

import static java.nio.charset.Charset.forName;

@SpringBootApplication
@EnableKafka
@EnableKafkaStreams
public class Application implements CommandLineRunner {

    public static final int DEFAULT_TIME_BETWEEN_EVENTS_IN_MILLIS = 5000;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private KafkaEventSender sender;

    @Autowired
    private RestController restController;

    @Override
    public void run(String... strings) throws Exception {
        EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandomBuilder()
                .seed(123L)
                .objectPoolSize(100)
                .randomizationDepth(3)
                .charset(forName("UTF-8"))
                .stringLengthRange(5, 50)
                .collectionSizeRange(1, 10)
                .scanClasspathForConcreteTypes(true)
                .randomize(CustomerBean.class, new CustomerRandomizer())
                .randomize(ProductBean.class, new ProductRandomizer())
                .overrideDefaultInitialization(false)
                .dateRange(LocalDate.now(),
                        Instant.ofEpochMilli(Calendar.getInstance().getTimeInMillis() + 86400000).atZone(ZoneId.systemDefault()).toLocalDate())
                .build();
        while (true) {
            if (restController.getSendTimeMillis() == null) {
                sender.send(random.nextObject(EventBean.class));
                Thread.sleep(DEFAULT_TIME_BETWEEN_EVENTS_IN_MILLIS);
            }
            else {
                if (restController.getSendTimeMillis() > 0) {
                    sender.send(random.nextObject(EventBean.class));
                    Thread.sleep(restController.getSendTimeMillis());
                }
                else {
                    Thread.sleep(DEFAULT_TIME_BETWEEN_EVENTS_IN_MILLIS);
                }
            }
        }

    }
}
