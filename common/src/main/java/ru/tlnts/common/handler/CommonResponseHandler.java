package ru.tlnts.common.handler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import ru.tlnts.common.api.ApiResponse;

import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.OK;

/**
 * @author vasev-dm
 */
public abstract class CommonResponseHandler
        implements ResponseBodyAdvice<Object>
{
    protected abstract String getPathPrefix();

    @Override
    public Object beforeBodyWrite(
            @Nullable Object body,
            @Nonnull MethodParameter returnType,
            @Nonnull MediaType selectedContentType,
            @Nonnull Class selectedConverterType,
            @Nonnull ServerHttpRequest request,
            @Nonnull ServerHttpResponse response)
    {
        if (isSuccess((ServletServerHttpResponse) response)
                && isNotResponseBody(body)
                && request.getURI().getPath().startsWith(getPathPrefix()))
        {
            return new ApiResponse<>(OK, body);
        }
        return body;
    }

    @Override
    public boolean supports(
            @Nonnull MethodParameter returnType,
            @Nonnull Class<? extends HttpMessageConverter<?>> converterType)
    {
        return AbstractJackson2HttpMessageConverter.class.isAssignableFrom(converterType);
    }

    private boolean isSuccess(ServletServerHttpResponse response) {
        return response.getServletResponse().getStatus() == OK.value();
    }

    private boolean isNotResponseBody(Object body) {
        return nonNull(body) && !ApiResponse.class.isAssignableFrom(body.getClass());
    }
}
