package com.company.msproject.exception;

import com.company.msproject.dto.ExceptionResponseDto;
import com.company.msproject.utils.MessageSourceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ResponseExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({ NotFoundException.class })
    public final ExceptionResponseDto handleNotFoundException(NotFoundException exception) {
        log.error("handleNotFoundException: {}", exception.getMessage());
        return MessageSourceUtils.getValue("msg.error.not.found", null);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public final ExceptionResponseDto methodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.error("methodArgumentNotValidException", exception);

        List<String> errors = new ArrayList<>();
        exception.getBindingResult().getFieldErrors().forEach(error -> errors.add(error.getField() + ": " + error.getDefaultMessage()));
        exception.getBindingResult().getGlobalErrors().forEach(error -> errors.add(error.getObjectName() + ": " + error.getDefaultMessage()));

        return MessageSourceUtils.getValue("msg.error.bad.request", errors);
    }
}
