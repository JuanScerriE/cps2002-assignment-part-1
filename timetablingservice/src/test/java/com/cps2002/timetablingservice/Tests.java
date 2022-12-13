package com.cps2002.timetablingservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public abstract class Tests {
    private static ObjectMapper mapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();

    public static String toJsonString(final Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJsonString(final String content, Class<T> valueType) {
        try {
            return mapper.readValue(content, valueType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
