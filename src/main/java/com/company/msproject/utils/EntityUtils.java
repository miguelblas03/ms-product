package com.company.msproject.utils;

import com.company.msproject.exception.ConvertValueRuntimeException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EntityUtils {

    private EntityUtils() {}

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static <T> T convertValue(Object source, Class<T> tarjetClass) {
        log.trace("parsing [{}] to [{}]", source.getClass(), tarjetClass);
        try {
            return objectMapper.convertValue(source, tarjetClass);
        } catch (Exception e) {
            throw new ConvertValueRuntimeException("It was not possible to parse [" + source.getClass() + "] to [" + tarjetClass + "]", e);
        }
    }
}
