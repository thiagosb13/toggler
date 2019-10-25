package com.thiagobezerra.toggler.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@ControllerAdvice
public class ExceptionHandlers extends ResponseEntityExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlers.class);

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<Object> handleNotFound(RuntimeException ex, WebRequest request) {
        return handleException(NOT_FOUND, ex, request);
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<Object> handleAccessDenied(RuntimeException ex, WebRequest request) {
        return handleException(FORBIDDEN, ex, request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Object> handleIllegalArgument(RuntimeException ex, WebRequest request) {
        return handleException(UNPROCESSABLE_ENTITY, ex, request);
    }

    @ExceptionHandler(Throwable.class)
    protected ResponseEntity<Object> handleExceptionConverter(RuntimeException ex, WebRequest request) {
        return handleException(INTERNAL_SERVER_ERROR, ex, request);
    }

    private ResponseEntity<Object> handleException(HttpStatus httpStatus, RuntimeException ex, WebRequest request) {
        var bodyOfResponse = getErrorResponse(httpStatus, ex);
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), httpStatus, request);
    }

    private ErrorResponse getErrorResponse(HttpStatus httpStatus, Throwable e) {
        LOGGER.error(e.getMessage(), e);
        return new ErrorResponse(httpStatus, e.getMessage());
    }
}
