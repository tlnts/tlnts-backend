package ru.tlnts.common.api;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.EMPTY;

/**
 * @author vasev-dm
 */
@ToString
@AllArgsConstructor
public class ApiResponse<T> {

    private final HttpStatus status;
    @Getter
    private final String description;
    private final T data;

    public ApiResponse(HttpStatus status, T data) {
        this.status = status;
        this.description = EMPTY;
        this.data = data;
    }

    public ApiResponse(HttpStatus status, String description) {
        this.status = status;
        this.description = description;
        this.data = null;
    }

    public static <T> ApiResponse<T> success(HttpStatus status, T data) {
        return new ApiResponse<>(status, data);
    }

    public static <T> ApiResponse<T> failure(HttpStatus status, String description) {
        return new ApiResponse<>(status, description);
    }

    public static <T> ApiResponse<T> failure(HttpStatus status, String description, T data) {
        return new ApiResponse<>(status, description, data);
    }

    public Optional<T> getData() {
        return ofNullable(data);
    }

    public int getStatus() {
        return status.value();
    }
}
