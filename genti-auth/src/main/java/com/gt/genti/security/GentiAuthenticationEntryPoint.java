package com.gt.genti.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.gt.genti.error.ExpectedException;
import com.gt.genti.error.ResponseCode;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class GentiAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final HandlerExceptionResolver resolver;

    public GentiAuthenticationEntryPoint(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        Exception exception;
        switch (response.getStatus()) {
            case 200:
                return;
            case 401:
            case 403:
                exception = (Exception) request.getAttribute("exception");
                break;
            case 404:
                exception = ExpectedException.withoutLogging(ResponseCode.NotFound);
                break;
            case 408:
                exception = ExpectedException.withoutLogging(ResponseCode.TimeOut);
                break;
            default:
                exception = ExpectedException.withoutLogging(ResponseCode.UnHandledException);
                break;
        }
        resolver.resolveException(request, response, null, exception);
    }
}
