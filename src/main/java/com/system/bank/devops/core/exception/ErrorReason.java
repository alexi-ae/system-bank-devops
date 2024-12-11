package com.system.bank.devops.core.exception;

import org.springframework.http.HttpStatus;

public enum ErrorReason {
    BAD_REQUEST(ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(ErrorCode.UNAUTHORIZED, HttpStatus.UNAUTHORIZED),
    FORBIDDEN(ErrorCode.FORBIDDEN, HttpStatus.FORBIDDEN),
    NOT_FOUND(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND),
    METHOD_NOT_ALLOWED(ErrorCode.METHOD_NOT_ALLOWED, HttpStatus.METHOD_NOT_ALLOWED),
    CONFLICT(ErrorCode.CONFLICT, HttpStatus.CONFLICT),
    INTERNAL_SERVER_ERROR(ErrorCode.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR),
    SERVICE_UNAVAILABLE(ErrorCode.SERVICE_UNAVAILABLE, HttpStatus.SERVICE_UNAVAILABLE),
    GATEWAY_TIMEOUT(ErrorCode.GATEWAY_TIMEOUT, HttpStatus.GATEWAY_TIMEOUT),
    HTTP_COMMUNICATION_FAILURE(ErrorCode.HTTP_COMMUNICATION_FAILURE,
            HttpStatus.INTERNAL_SERVER_ERROR),
    REQUEST_PARAMETER_NOT_VALID(ErrorCode.REQUEST_PARAMETER_NOT_VALID, HttpStatus.BAD_REQUEST),
    REQUEST_PARAMETER_MISSING(ErrorCode.REQUEST_PARAMETER_MISSING, HttpStatus.BAD_REQUEST),
    REQUEST_BODY_EMPTY(ErrorCode.REQUEST_BODY_EMPTY, HttpStatus.BAD_REQUEST),
    REQUEST_PARAMETER_TYPE_MISMATCH(ErrorCode.REQUEST_PARAMETER_TYPE_MISMATCH,
            HttpStatus.BAD_REQUEST),
    REQUEST_BODY_INVALID_FORMAT(ErrorCode.REQUEST_BODY_INVALID_FORMAT, HttpStatus.BAD_REQUEST),
    JSON_MAPPING_NOT_VALID(ErrorCode.JSON_MAPPING_NOT_VALID, HttpStatus.BAD_REQUEST),
    GENERIC_ERROR(ErrorCode.GENERIC_ERROR, HttpStatus.INTERNAL_SERVER_ERROR),
    ATTEMPT_LIMIT_EXCEEDED(ErrorCode.ATTEMPT_LIMIT_EXCEEDED, HttpStatus.TOO_MANY_REQUESTS),
    JWK_NOT_FOUND(ErrorCode.JWK_NOT_FOUND, HttpStatus.NOT_FOUND),
    INVALID_JWT_FORMAT(ErrorCode.INVALID_JWT_FORMAT, HttpStatus.BAD_REQUEST),
    INVALID_JWT_SIGNATURE(ErrorCode.INVALID_JWT_SIGNATURE, HttpStatus.UNAUTHORIZED),
    JWT_EXPIRED(ErrorCode.JWT_EXPIRED, HttpStatus.UNAUTHORIZED),
    INVALID_JWT_AUDIENCE(ErrorCode.INVALID_JWT_AUDIENCE, HttpStatus.UNAUTHORIZED),

    USER_ALREADY_EXISTS(ErrorCode.USER_ALREADY_EXISTS, HttpStatus.BAD_REQUEST),

    AUTHENTICATION_FAILED(ErrorCode.AUTHENTICATION_FAILED, HttpStatus.UNAUTHORIZED),

    USER_NOT_FOUND(ErrorCode.USER_NOT_FOUND, HttpStatus.NOT_FOUND);

    private final ErrorCode errorCode;
    private final HttpStatus httpStatus;

    private ErrorReason(ErrorCode errorCode, HttpStatus httpStatus) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

    public String getErrorCode() {
        return this.errorCode.getValue();
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }
}
