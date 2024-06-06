package org.example.homework2.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class JsonService {
    private JsonService(){}
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static  <T> String getJson(T object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    public static  <T> String getJson(List<T> objects) throws JsonProcessingException {
        return objectMapper.writeValueAsString(objects);
    }

    public static <T> T getObject(BufferedReader json, Class<T> tClass) throws IOException {
        return objectMapper.readValue(json, tClass);
    }

    public static  <T> List<T> getObjects(BufferedReader json, Class<T> tClass) throws IOException {
        return objectMapper.readValue(json, objectMapper
                .getTypeFactory().constructCollectionType(List.class, tClass));
    }
}
