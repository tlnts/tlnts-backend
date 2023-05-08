package ru.tlnts.common.api;

import lombok.Builder;
import lombok.Data;

/**
 * @author vasev-dm
 */
@Data
@Builder
public class ValidationError {

    private final String field;
    private final String description;
}
