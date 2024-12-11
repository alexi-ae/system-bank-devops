package com.system.bank.devops.core.exception;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import reactor.core.publisher.Mono;

import java.util.Collections;

@RestControllerAdvice
public class ResponseExceptionHandler {


    @ExceptionHandler({ApiRestException.class})
    public Mono<ResponseEntity<Object>> handleApiRestException(ApiRestException ex) {
        return buildResponseEntity(
                ErrorResponse.builder()
                        .code(ex.getReason().getErrorCode())
                        .reason(ex.getReason())
                        .source(ex.getSource())
                        .errors(
                                ex.getMessage() == null
                                        ? Collections.emptyList()
                                        : Collections.singletonList(ex.getMessage()))
                        .build(),
                ex.getReason().getHttpStatus(),
                null);
    }

    @ExceptionHandler({ExpiredJwtException.class})
    public Mono<ResponseEntity<Object>> handleExpiredJwtException(ExpiredJwtException ex) {
        return buildResponseEntity(
                ErrorResponse.builder()
                        .code(ErrorReason.UNAUTHORIZED.getErrorCode())
                        .reason(ErrorReason.UNAUTHORIZED)
                        .source(ErrorSource.BUSINESS_SERVICE)
                        .errors(
                                ex.getMessage() == null
                                        ? Collections.emptyList()
                                        : Collections.singletonList(ex.getMessage()))
                        .build(),
                ErrorReason.UNAUTHORIZED.getHttpStatus(),
                null);
    }

    @ExceptionHandler({BadCredentialsException.class})
    public Mono<ResponseEntity<Object>> handleBadCredentialsException(BadCredentialsException ex) {
        return buildResponseEntity(
                ErrorResponse.builder()
                        .code(ErrorReason.UNAUTHORIZED.getErrorCode())
                        .reason(ErrorReason.UNAUTHORIZED)
                        .source(ErrorSource.BUSINESS_SERVICE)
                        .errors(
                                ex.getMessage() == null
                                        ? Collections.emptyList()
                                        : Collections.singletonList(ex.getMessage()))
                        .build(),
                ErrorReason.UNAUTHORIZED.getHttpStatus(),
                null);
    }

    private Mono<ResponseEntity<Object>> buildResponseEntity(
            ErrorResponse errorResponse, HttpStatus status, WebRequest request) {
        return Mono.just(new ResponseEntity<>(errorResponse, status));
    }
}