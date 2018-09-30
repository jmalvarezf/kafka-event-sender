package es.eci.elejandria.event.sender;

import es.eci.elejandria.event.sender.beans.EventBean;
import es.eci.elejandria.event.sender.kafka.KafkaEventSender;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.StreamsBuilderFactoryBean;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    private KafkaEventSender eventSender;

    @Autowired
    private StreamsBuilderFactoryBean kStreamBuilderFactoryBean;

    @Value("${event.sender.topic}")
    private String topic;

    @Value("${event.store.name}")
    private String eventStoreName;

    private Integer sendTimeMillis;

    public Integer getSendTimeMillis() {
        return sendTimeMillis;
    }

    public void setSendTimeMillis(Integer sendTimeMillis) {
        this.sendTimeMillis = sendTimeMillis;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/events")
    @CrossOrigin(origins = "*")
    public void addEvent(@RequestBody EventBean bean) {
        if (bean.getId() == null) {
            bean.setId(UUID.randomUUID().toString());
        }
        eventSender.send(bean);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/events/{id}")
    public EventBean getEvent(@PathVariable String id) {
        KafkaStreams streams = kStreamBuilderFactoryBean.getKafkaStreams();
        ReadOnlyKeyValueStore<String, EventBean> keyValueStore =
                streams.store(eventStoreName, QueryableStoreTypes.keyValueStore());
        return keyValueStore.get(id);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/events/time/{timeInMillis}")
    public void setTimeBetweenEvents(@PathVariable String timeInMillis) {
        setSendTimeMillis(Integer.parseInt(timeInMillis));
    }
}
