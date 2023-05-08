package ru.tlnts.feed.configuration.properties;

import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;
import ru.tlnts.common.kafka.CommonKafkaProperties;

/**
 * @author vasev-dm
 */
@Data
@Validated
@ConstructorBinding
@ConfigurationProperties("tlnts.notifications.kafka")
public class NotificationsKafkaProperties
        implements CommonKafkaProperties
{
    @NotBlank
    private final String bootstrapServers;

    @NotBlank
    private final String producerGroupId;

    @NotBlank
    private final String notificationsTopic;
}
