package es.eci.elejandria.event.sender.kafka;

import es.eci.elejandria.event.sender.beans.EventBean;
import es.eci.elejandria.event.sender.beans.ProductBean;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KafkaEventSender {

    private static final Logger log = LoggerFactory.getLogger(KafkaEventSender.class);

    @Autowired
    private KafkaTemplate<String, EventBean> kafkaTemplate;

    @Value("${event.sender.topic}")
    private String topic;

    @Value("${event.store.topic}")
    private String eventStoreTopic;

    public void send(EventBean event) {
        if (event.getEventType().equals(EventBean.EventType.INFO)) {
            List<ProductBean> productClicked = new ArrayList<ProductBean>();
            productClicked.add(event.getProducts().get(0));
            event.setProducts(productClicked);
        }
        ProducerRecord<String, EventBean> tableRecord = new ProducerRecord<String, EventBean>(eventStoreTopic, event.getId(), event);
        log.info("sending message='{}' to topic='{}'", event, topic);
        kafkaTemplate.send(topic, event);
        kafkaTemplate.send(tableRecord);
    }
}
