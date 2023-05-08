package ru.tlnts.common.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * @author vasev-dm
 */
@Getter
@RequiredArgsConstructor
public enum ApiStatusCode
        implements StatusCode
{
    OK(HttpStatus.OK),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR),
    ;

    private final HttpStatus status;
}
