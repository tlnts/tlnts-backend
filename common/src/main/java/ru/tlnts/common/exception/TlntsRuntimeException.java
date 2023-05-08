package ru.tlnts.common.exception;

import lombok.Getter;
import ru.tlnts.common.api.StatusCode;

import static ru.tlnts.common.api.ApiStatusCode.INTERNAL_ERROR;

/**
 * @author vasev-dm
 */
@Getter
public class TlntsRuntimeException
        extends RuntimeException
{
    private final StatusCode statusCode;

    public TlntsRuntimeException(String message) {
        super(message);
        this.statusCode = INTERNAL_ERROR;
    }

    public TlntsRuntimeException(String message, StatusCode statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public TlntsRuntimeException(String message, Throwable cause, StatusCode statusCode) {
        super(message, cause);
        this.statusCode = statusCode;
    }
}
