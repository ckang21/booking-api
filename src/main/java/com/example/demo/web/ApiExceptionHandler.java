// src/main/java/com/example/demo/web/ApiExceptionHandler.java
package com.example.demo.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiExceptionHandler {
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Map<String, Object> handleValidation(MethodArgumentNotValidException ex) {
    var fields = ex.getBindingResult().getFieldErrors().stream()
        .collect(Collectors.toMap(f -> f.getField(), f -> f.getDefaultMessage(), (a,b) -> a));
    return Map.of("status", 400, "error", "Bad Request", "message", "Validation failed", "fields", fields);
  }
}
