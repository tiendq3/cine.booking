package com.tiendq.cinebooking.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiendq.cinebooking.model.dtos.FilmDTO;

public class JsonConverter {
    public static  <T> T convertJsonToObject(String json, Class<?> classOfT){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            T t = (T) objectMapper.readValue(json, classOfT);
            return t;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
