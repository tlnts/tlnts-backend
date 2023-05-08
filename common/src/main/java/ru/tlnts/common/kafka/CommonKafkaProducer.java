package ru.tlnts.common.kafka;

import java.util.Map;

import org.apache.kafka.common.serialization.Serializer;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import static com.google.common.collect.Maps.newHashMap;
import static org.apache.kafka.clients.producer.ProducerConfig.ACKS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.CLIENT_ID_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG;

/**
 * @author vasev-dm
 */
public class CommonKafkaProducer<K, V> {

    public KafkaTemplate<K, V> buildKafkaProducer(
            CommonKafkaProperties properties,
            Class<?> keySerializerClass,
            Class<?> valueSerializerClass,
            Serializer<K> keySerializer,
            Serializer<V> valueSerializer)
    {
        DefaultKafkaProducerFactory<K, V> kafkaProducerFactory =
                new DefaultKafkaProducerFactory<>(producerConfig(properties, keySerializerClass, valueSerializerClass));
        kafkaProducerFactory.setKeySerializer(keySerializer);
        kafkaProducerFactory.setValueSerializer(valueSerializer);

        return new KafkaTemplate<>(kafkaProducerFactory);
    }

    private Map<String, Object> producerConfig(
            CommonKafkaProperties properties,
            Class<?> keySerializerClass,
            Class<?> valueSerializerClass)
    {
        Map<String, Object> props = newHashMap();
        props.put(BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
        props.put(CLIENT_ID_CONFIG, properties.getProducerGroupId());
        props.put(KEY_SERIALIZER_CLASS_CONFIG, keySerializerClass);
        props.put(VALUE_SERIALIZER_CLASS_CONFIG, valueSerializerClass);
        props.put(ENABLE_IDEMPOTENCE_CONFIG, true);
        props.put(ACKS_CONFIG, "all");

        return props;
    }
}
