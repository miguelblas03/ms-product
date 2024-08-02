package com.company.msproject.utils;

import com.company.msproject.dto.ExceptionResponseDto;

import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class MessageSourceUtils {

    private MessageSourceUtils() {}

    public static final String DESCRIPTION_NOT_FOUND = "Description not found";
    public static final String CODE_NOT_FOUND = "Code not found";
    private static final ResourceBundle messagesResourceBundle = ResourceBundle.getBundle("message");
    private static final ResourceBundle codesResourceBundle = ResourceBundle.getBundle("code");

    public static ExceptionResponseDto getValue(String key, List<String> errors) {
        String message = getMessage(key);
        String code = getCode(key);
        return new ExceptionResponseDto(code, message, errors);
    }

    private static String getMessage(String key) {
        try {
            return messagesResourceBundle.getString(key);
        } catch (MissingResourceException e) {
            return DESCRIPTION_NOT_FOUND;
        }
    }

    private static String getCode(String key) {
        try {
            return codesResourceBundle.getString(key);
        } catch (MissingResourceException e) {
            return CODE_NOT_FOUND;
        }
    }
}
