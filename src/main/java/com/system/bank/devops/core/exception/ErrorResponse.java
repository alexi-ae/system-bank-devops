package com.system.bank.devops.core.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.system.bank.devops.core.utils.ObjectMapperUtils;
import lombok.ToString;

import java.util.List;

@ToString
public class ErrorResponse {

    private String code;
    private ErrorReason reason;
    private ErrorSource source;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> errors;

    public ErrorResponse(String code, ErrorReason reason,
                         ErrorSource source, List<String> errors) {
        this.code = code;
        this.reason = reason;
        this.source = source;
        this.errors = errors;
    }

    public ErrorResponse() {
    }

    public static ErrorResponseBuilder builder() {
        return new ErrorResponseBuilder();
    }

    @Override
    public String toString() {
        return ObjectMapperUtils.toString(this);
    }

    public String getCode() {
        return this.code;
    }

    public ErrorReason getReason() {
        return this.reason;
    }

    public ErrorSource getSource() {
        return this.source;
    }

    public List<String> getErrors() {
        return this.errors;
    }


    public static class ErrorResponseBuilder {

        private String code;
        private ErrorReason reason;
        private ErrorSource source;
        private List<String> errors;

        ErrorResponseBuilder() {
        }

        public ErrorResponseBuilder code(String code) {
            this.code = code;
            return this;
        }

        public ErrorResponseBuilder reason(ErrorReason reason) {
            this.reason = reason;
            return this;
        }

        public ErrorResponseBuilder source(ErrorSource source) {
            this.source = source;
            return this;
        }

        public ErrorResponseBuilder errors(List<String> errors) {
            this.errors = errors;
            return this;
        }

        public ErrorResponse build() {
            return new ErrorResponse(code, reason, source, errors);
        }
    }
}