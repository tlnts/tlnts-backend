package ru.tlnts.feed.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ru.tlnts.feed.configuration.properties.NotificationsKafkaProperties;

/**
 * @author vasev-dm
 */
@Configuration
@EnableConfigurationProperties({
    NotificationsKafkaProperties.class
})
public class FeedPropertiesConfiguration {
}
