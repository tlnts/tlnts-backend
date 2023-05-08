package ru.tlnts.feed.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Component;
import ru.tlnts.common.kafka.CommonKafkaProducer;
import ru.tlnts.feed.configuration.properties.NotificationsKafkaProperties;
import ru.tlnts.feed.model.kafka.CommentNotificationMessage;

/**
 * @author vasev-dm
 */
@Component
@RequiredArgsConstructor
public class NotificationsKafkaProducer {

    private final NotificationsKafkaProperties properties;
    private final CommonKafkaProducer<String, CommentNotificationMessage> commonKafkaProducer;

    @Bean("notificationsCommentsKafkaProducer")
    public KafkaTemplate<String, CommentNotificationMessage> notificationsKafkaProducer() {
        return commonKafkaProducer.buildKafkaProducer(
                properties,
                StringSerializer.class,
                JsonSerializer.class,
                new StringSerializer(),
                commentNotificationSerializer()
        );
    }

    private Serializer<CommentNotificationMessage> commentNotificationSerializer() {
        var jsonSerializer = new JsonSerializer<CommentNotificationMessage>();
        jsonSerializer.setAddTypeInfo(false);
        jsonSerializer.setUseTypeMapperForKey(false);
        return jsonSerializer;
    }
}
