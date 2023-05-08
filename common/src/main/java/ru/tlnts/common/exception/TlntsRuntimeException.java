package ru.tlnts.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * @author vasev-dm
 */
@Getter
public class TlntsRuntimeException
        extends RuntimeException
{
    private final HttpStatus statusCode;

    public TlntsRuntimeException(String message) {
        super(message);
        this.statusCode = INTERNAL_SERVER_ERROR;
    }

    public TlntsRuntimeException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public TlntsRuntimeException(String message, Throwable cause, HttpStatus statusCode) {
        super(message, cause);
        this.statusCode = statusCode;
    }
}
