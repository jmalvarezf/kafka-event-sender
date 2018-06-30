package es.eci.elejandria.event.sender;

import es.eci.elejandria.event.sender.beans.EventBean;
import es.eci.elejandria.event.sender.kafka.KafkaEventSender;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static java.nio.charset.Charset.forName;

@SpringBootApplication
public class Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private KafkaEventSender sender;

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
                .overrideDefaultInitialization(false)
                .build();
        while(true) {
            sender.send(random.nextObject(EventBean.class));
            Thread.sleep(5000);
        }

    }
}
