package com.company.msproject.utils;

import com.company.msproject.exception.ConvertValueRuntimeException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityUtils {

    private EntityUtils() {}

    private static final ObjectMapper objectMapper;
    private static final Logger log = LoggerFactory.getLogger(EntityUtils.class);

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
