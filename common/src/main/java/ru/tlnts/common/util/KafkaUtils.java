package ru.tlnts.common.util;

import java.util.UUID;

import lombok.experimental.UtilityClass;

/**
 * @author vasev-dm
 */
@UtilityClass
public class KafkaUtils {

    public String generateKey() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
