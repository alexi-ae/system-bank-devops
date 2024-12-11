package com.system.bank.devops.core.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiRestException extends RuntimeException {
    private final ErrorReason reason;
    private final ErrorSource source;

    ApiRestException(ErrorReason reason, ErrorSource source) {
        this.reason = reason;
        this.source = source;
    }

    public static ApiRestExceptionBuilder builder() {
        return new ApiRestExceptionBuilder();
    }


    public static class ApiRestExceptionBuilder {

        private ErrorReason reason;
        private ErrorSource source;

        ApiRestExceptionBuilder() {
        }

        public ApiRestExceptionBuilder reason(ErrorReason reason) {
            this.reason = reason;
            return this;
        }

        public ApiRestExceptionBuilder source(ErrorSource source) {
            this.source = source;
            return this;
        }

        public ApiRestException build() {
            return new ApiRestException(this.reason, this.source);
        }
    }
}
