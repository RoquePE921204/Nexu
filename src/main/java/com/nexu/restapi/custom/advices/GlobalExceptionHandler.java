package com.nexu.restapi.custom.advices;

import com.nexu.restapi.custom.exceptions.GeneralControlledException;
import com.nexu.restapi.responses.GlobalExceptionResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GlobalExceptionResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        ex.getBindingResult().getGlobalErrors()
                .forEach(error -> errors.put(error.getObjectName(), error.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GlobalExceptionResponse(errors));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<GlobalExceptionResponse> handleValidationControllerExceptions(ConstraintViolationException ex) {
        Map<String, String> errors = ex.getConstraintViolations().stream()
                .collect(Collectors.toMap(violation -> violation.getPropertyPath().toString()
                        ,ConstraintViolation::getMessage,
                        (existing, replacement) -> existing));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GlobalExceptionResponse(errors));
    }

    @ExceptionHandler(GeneralControlledException.class)
    public ResponseEntity<GlobalExceptionResponse> handleGlobalException(GeneralControlledException ex) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new GlobalExceptionResponse(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GlobalExceptionResponse> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new GlobalExceptionResponse("Oops, no esperábamos eso, por favor contacte con el administrador."));
    }

    private static String extractValue(String errorMessage) {
        String value = null;
        Pattern pattern = Pattern.compile("\\(([^)]+)\\)");
        Matcher matcher = pattern.matcher(errorMessage);

        int count = 0;
        while (matcher.find()) {
            count++;
            if (count == 2) {
                value = matcher.group(1);
                break;
            }
        }

        return value;
    }

}
