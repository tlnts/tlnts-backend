package ru.tlnts.common.api;

import java.util.Optional;

import lombok.Getter;
import lombok.ToString;

import static java.util.Optional.ofNullable;
import static lombok.AccessLevel.NONE;
import static org.apache.commons.lang3.StringUtils.EMPTY;

/**
 * @author vasev-dm
 */
@Getter
@ToString
public class ApiResponse<T> {

    private final StatusCode status;
    private final String description;
    @Getter(value = NONE)
    private final T data;

    public ApiResponse(StatusCode status, T data) {
        this.status = status;
        this.description = EMPTY;
        this.data = data;
    }

    public ApiResponse(StatusCode status, String description) {
        this.status = status;
        this.description = description;
        this.data = null;
    }

    public static <T> ApiResponse<T> success(StatusCode status, T data) {
        return new ApiResponse<>(status, data);
    }

    public static <T> ApiResponse<T> failure(StatusCode status, String description) {
        return new ApiResponse<>(status, description);
    }

    public Optional<T> getData() {
        return ofNullable(data);
    }
}
