package com.system.bank.devops.core.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.Base64;

public final class ObjectMapperUtils {
    private static ObjectMapper instance;

    private ObjectMapperUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String toString(Object value) {
        try {
            return getInstance().writeValueAsString(value);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

    public static <T> T convertValue(Object value, Class<T> clazz) {
        try {
            return getInstance().convertValue(value, clazz);
        } catch (IllegalArgumentException e) {
            return (T) new Object();
        }
    }

    public static <T> T loadObject(String content, Class<T> clazz) {
        try {
            return getInstance().readValue(content, clazz);
        } catch (JsonProcessingException e) {
            return (T) new Object();
        }
    }

    public static <T> T loadPayload(String jwt, Class<T> clazz) {
        String[] chunks = jwt.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));
        try {
            return getInstance().readValue(payload, clazz);
        } catch (JsonProcessingException e) {
            return (T) new Object();
        }
    }

    private static synchronized ObjectMapper getInstance() {
        if (instance == null) {
            instance =
                    new ObjectMapper()
                            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                            .setDateFormat(new StdDateFormat())
                            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                            .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
                            .enable(JsonGenerator.Feature.IGNORE_UNKNOWN)
                            .enable(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_COMMENTS)
                            .registerModule(new JavaTimeModule());
        }

        return instance;
    }
}