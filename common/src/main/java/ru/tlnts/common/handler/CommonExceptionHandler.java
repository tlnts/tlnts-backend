package ru.tlnts.common.handler;

import java.util.List;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.tlnts.common.api.ApiResponse;
import ru.tlnts.common.api.ValidationError;
import ru.tlnts.common.exception.TlntsRuntimeException;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static ru.tlnts.common.api.ApiResponse.failure;

/**
 * @author vasev-dm
 */
@Slf4j
public abstract class CommonExceptionHandler {

    @ExceptionHandler(TlntsRuntimeException.class)
    public ResponseEntity<ApiResponse<Void>> handlerRuntimeException(TlntsRuntimeException e) {
        log.error("Runtime exception occurred", e);
        return ResponseEntity.ok(failure(e.getStatusCode(), e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<List<ValidationError>>> handlerValidationException(MethodArgumentNotValidException e) {
        log.warn("Validation error occurred", e);
        var description = String.format("%s is invalid", StringUtils.capitalize(e.getBindingResult().getObjectName()));
        var validationErrors = e.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> ValidationError.builder()
                        .field(error.getField())
                        .description(error.getDefaultMessage())
                        .build())
                .collect(toList());
        return ResponseEntity.ok(failure(BAD_REQUEST, description, validationErrors));
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ApiResponse<Void>> handleFeignException(FeignException e) {
        log.error("Feign exception occurred", e);
        return ResponseEntity.ok(failure(HttpStatus.valueOf(e.status()), e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleInternalError(Exception e) {
        log.error("Internal server error occurred", e);
        return ResponseEntity.ok(failure(INTERNAL_SERVER_ERROR, e.getMessage()));
    }
}
