package com.system.bank.devops.core.exception;

public enum ErrorSource {
    ADAPTER_COMPONENT("Error caused by the failure of a component adapter."),

    UTILS("Failure occurred in the utility function or class."),

    HELPER_COMPONENT("Error due to the failure of a component's helper."),

    HTTP_CLIENT_COMPONENT("Failure in the HTTP client component."),

    BUSINESS_SERVICE("Error triggered by the failure of a business service."),

    DATA_REPOSITORY("Failure encountered in the data repository."),

    REST_CONTROLLER("Error caused by the failure of the REST controller."),

    UNKNOWN_SOURCE("Error with an unknown or unspecified source.");

    private final String message;

    private ErrorSource(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
