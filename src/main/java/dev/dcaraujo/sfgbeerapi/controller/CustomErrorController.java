package dev.dcaraujo.sfgbeerapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@ControllerAdvice
public class CustomErrorController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<Map<String, String>>> handleBindErrors(MethodArgumentNotValidException exception) {
        var errors = exception.getFieldErrors().stream()
                .map(fieldError -> {
                    var message = Objects.requireNonNullElse(fieldError.getDefaultMessage(), "");
                    return Map.of(fieldError.getField(), message);
                })
                .toList();
        return ResponseEntity.badRequest().body(errors);
    }
}
