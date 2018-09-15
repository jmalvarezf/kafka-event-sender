package es.eci.elejandria.event.sender;

import es.eci.elejandria.event.sender.beans.EventBean;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.Consumed;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.GlobalKTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.core.StreamsBuilderFactoryBean;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class StreamsConfig {

    @Autowired
    private KafkaProperties kafkaProperties;

    @Value("${event.sender.topic}")
    private String topic;

    @Value("${event.store.name}")
    private String eventStoreName;

    @Value("${event.store.topic}")
    private String eventStoreTopic;

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public org.apache.kafka.streams.StreamsConfig kStreamsConfigs() {
        Map<String, Object> props = new HashMap<String, Object>();
        props.put(org.apache.kafka.streams.StreamsConfig.APPLICATION_ID_CONFIG, "event-sender-stream");
        props.put(org.apache.kafka.streams.StreamsConfig.CONSUMER_PREFIX, "event-sender-consumer");
        props.put(org.apache.kafka.streams.StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        props.put(org.apache.kafka.streams.StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(org.apache.kafka.streams.StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, new JsonSerde<>(EventBean.class).getClass());
        props.put(JsonDeserializer.DEFAULT_KEY_TYPE, String.class);
        props.put(JsonDeserializer.DEFAULT_VALUE_TYPE, EventBean.class);
        return new org.apache.kafka.streams.StreamsConfig(props);
    }

    @Bean
    public GlobalKTable<String, EventBean> globalKTable(StreamsBuilder builder) {
        JsonSerde jsonSerde = new JsonSerde<>(EventBean.class);
        Map<String, Object> config = new HashMap<String, Object>();
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        config.put(JsonDeserializer.DEFAULT_KEY_TYPE, String.class);
        config.put(JsonDeserializer.DEFAULT_VALUE_TYPE, EventBean.class);
        jsonSerde.configure(config, false);
        return builder.globalTable(eventStoreTopic, Consumed.with(new Serdes.StringSerde(), jsonSerde), Materialized.as(eventStoreName));
    }
}
