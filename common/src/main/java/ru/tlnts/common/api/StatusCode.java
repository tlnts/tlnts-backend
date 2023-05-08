package ru.tlnts.common.api;

import org.springframework.http.HttpStatus;

/**
 * @author vasev-dm
 */
public interface StatusCode {

    HttpStatus getStatus();
}
