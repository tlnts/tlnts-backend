package ru.tlnts.common.kafka;

/**
 * @author vasev-dm
 */
public interface CommonKafkaProperties {

    String getBootstrapServers();

    String getProducerGroupId();
}
