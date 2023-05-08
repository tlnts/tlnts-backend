package ru.tlnts.feed.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tlnts.common.kafka.CommonKafkaProducer;

/**
 * @author vasev-dm
 */
@Configuration
public class KafkaConfiguration {

    @Bean("commonKafkaProducer")
    @SuppressWarnings({"rawtypes"})
    public CommonKafkaProducer commonKafkaProducer() {
        return new CommonKafkaProducer<>();
    }
}
