package ru.tlnts.oauth.api;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tlnts.common.handler.CommonResponseHandler;

/**
 * @author mamedov
 */
@RestControllerAdvice
public class ApiResponseHandler extends CommonResponseHandler {

    @Override
    protected String getPathPrefix() {
        return "/api";
    }

}
