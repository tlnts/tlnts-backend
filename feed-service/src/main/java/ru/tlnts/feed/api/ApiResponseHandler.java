package ru.tlnts.feed.api;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tlnts.common.handler.CommonResponseHandler;

/**
 * @author vasev-dm
 */
@RestControllerAdvice
public class ApiResponseHandler
        extends CommonResponseHandler
{
    @Override
    protected String getPathPrefix() {
        return "/api";
    }
}
