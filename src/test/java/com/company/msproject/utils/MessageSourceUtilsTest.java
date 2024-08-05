package com.company.msproject.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessageSourceUtilsTest {

    @Test
    void getValue() {
        var exceptionResponseDto = MessageSourceUtils.getValue("description.not.found", null);
        assertEquals("Description not found", exceptionResponseDto.getMessage());
    }
}
