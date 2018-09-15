package es.eci.elejandria.event.sender;

import es.eci.elejandria.event.sender.beans.EventBean;
import es.eci.elejandria.event.sender.kafka.KafkaEventSender;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.StreamsBuilderFactoryBean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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


    @RequestMapping(method = RequestMethod.POST, path = "/events")
    public void addEvent(@RequestBody EventBean bean) {
        eventSender.send(bean);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/events/{id}")
    public EventBean getEvent(@PathVariable String id) {
        KafkaStreams streams = kStreamBuilderFactoryBean.getKafkaStreams();
        ReadOnlyKeyValueStore<String, EventBean> keyValueStore =
                streams.store(eventStoreName, QueryableStoreTypes.keyValueStore());
        return keyValueStore.get(id);
    }
}
