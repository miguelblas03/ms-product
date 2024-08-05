package com.company.msproject.exception;

import com.company.msproject.dto.ExceptionResponseDto;
import com.company.msproject.utils.MessageSourceUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class ResponseExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({ ProductNotFoundException.class })
    public final ExceptionResponseDto handleProductNotFoundException(ProductNotFoundException exception) {
        log.error("handleProductNotFoundException: {}", exception.getMessage());
        return MessageSourceUtils.getValue("msg.error.product.not.found", null);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({ CategoryNotFoundException.class })
    public final ExceptionResponseDto handleCategoryNotFoundException(CategoryNotFoundException exception) {
        log.error("handleCategoryNotFoundException: {}", exception.getMessage());
        return MessageSourceUtils.getValue("msg.error.category.not.found", null);
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
