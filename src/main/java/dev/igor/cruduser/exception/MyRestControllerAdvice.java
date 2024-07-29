package dev.igor.cruduser.exception;

import dev.igor.cruduser.exception.error.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class MyRestControllerAdvice {
    @ExceptionHandler(UserAlreadyExists.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExists(UserAlreadyExists ex) {
        final var err = new ErrorResponse("UserAlreadyExists", ex.getMessage(), null);
        return ResponseEntity.badRequest().body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(item -> {
            errors.add(String.format("%s: %s", item.getField(), item.getDefaultMessage()));
        });
        final var err = new ErrorResponse("MethodArgumentNotValidException", "Invalid body", errors);
        return ResponseEntity.badRequest().body(err);
    }
}
