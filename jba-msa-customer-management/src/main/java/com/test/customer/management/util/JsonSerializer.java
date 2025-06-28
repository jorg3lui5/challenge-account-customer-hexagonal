package com.test.customer.management.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.test.customer.management.infrastructure.exception.custom.ParsingJsonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JsonSerializer {

    private JsonSerializer() {}

    private static final ObjectMapper objectMapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();

    public static <T> T jsonStringToObject(String jsonString, Class<T> desiredClass) throws ParsingJsonException {

        if(jsonString == null || jsonString.equals("")) {
            return null;
        }

        T jsonObject;
        try {
            jsonObject = objectMapper.readValue(jsonString, desiredClass);
        } catch (JsonProcessingException error) {
            log.error("ERROR JsonSerializer jsonStringToObject error {}", error.getMessage());
            throw new ParsingJsonException("Error");
        }
        return jsonObject;
    }

}
