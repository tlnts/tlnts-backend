package ru.tlnts.feed.api;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tlnts.common.handler.CommonExceptionHandler;

/**
 * @author vasev-dm
 */
@RestControllerAdvice
public class ApiExceptionHandler
        extends CommonExceptionHandler
{
}
