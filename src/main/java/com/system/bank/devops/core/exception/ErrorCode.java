package com.system.bank.devops.core.exception;

public enum ErrorCode {
    BAD_REQUEST("000400",
            "Response indicating a client-side error due to a malformed or invalid request."),
    UNAUTHORIZED("000401",
            "Response triggered when authentication is required but missing or invalid."),
    FORBIDDEN("000403", "Response returned when the client lacks permission to access the resource."),
    NOT_FOUND("000404",
            "Response indicating the requested resource could not be found on the server."),
    METHOD_NOT_ALLOWED("000405",
            "Response sent when the HTTP method used is not supported for the endpoint."),
    CONFLICT("000409",
            "Response signaling a conflict, such as duplicate resources or inconsistent states."),
    INTERNAL_SERVER_ERROR("000500",
            "Generic server error when an unexpected condition prevents fulfilling the request."),
    SERVICE_UNAVAILABLE("000503",
            "Response indicating the server is currently unable to handle the request."),
    GATEWAY_TIMEOUT("000504",
            "Response sent when the server fails to receive a timely response from an upstream server."),
    HTTP_COMMUNICATION_FAILURE("000600", "Error caused by a failure in HTTP communication."),
    REQUEST_PARAMETER_NOT_VALID("000601", "Error due to invalid parameters in the HTTP request."),
    REQUEST_PARAMETER_MISSING("000602",
            "Error triggered when required parameters are missing in the request."),
    REQUEST_BODY_EMPTY("000603",
            "Error indicating the HTTP request body is empty when it shouldn't be."),
    REQUEST_PARAMETER_TYPE_MISMATCH("000604",
            "Error due to a mismatch in the expected types of request parameters."),
    REQUEST_BODY_INVALID_FORMAT("000605", "Error caused by an incorrectly formatted request body."),
    JSON_SYNTAX_NOT_VALID("000606", "Error due to invalid JSON syntax in the request body."),
    JSON_MAPPING_NOT_VALID("000607", "Error mapping the JSON content to the expected structure."),
    GENERIC_ERROR("000609", "Response for an unhandled or unknown error."),
    ATTEMPT_LIMIT_EXCEEDED("000611",
            "Error triggered when the maximum number of allowed attempts has been reached."),
    JWK_NOT_FOUND("000614", "Error returned when the specified JSON Web Key (JWK) cannot be found."),
    INVALID_JWT_FORMAT("000615", "Error due to an improperly formatted JSON Web Token (JWT)."),
    INVALID_JWT_SIGNATURE("000616",
            "Error indicating the signature of the JSON Web Token (JWT) is invalid."),
    JWT_EXPIRED("000617", "Error when the JSON Web Token (JWT) has expired."),
    INVALID_JWT_AUDIENCE("000618",
            "Error indicating the audience claim of the JWT does not match the expected value."),

    USER_ALREADY_EXISTS("001400",
            "Error indicating the audience claim of the JWT does not match the expected value."),

    AUTHENTICATION_FAILED("001401",
            "Response indicating that the authentication of the customer account was unsuccessful."),

    USER_NOT_FOUND("001404",
            "Error indicating the audience claim of the JWT does not match the expected value.");

    private final String value;
    private final String description;

    private ErrorCode(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return this.value;
    }

    public String getDescription() {
        return this.description;
    }
}
