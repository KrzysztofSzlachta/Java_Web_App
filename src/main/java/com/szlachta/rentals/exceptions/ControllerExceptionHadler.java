package com.szlachta.rentals.exceptions;

import com.szlachta.rentals.dto.BasicErrorResponse;
import com.szlachta.rentals.dto.ValidationErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ControllerExceptionHadler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<List<ValidationErrorResponse>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex
    ) {
        List<ValidationErrorResponse> errors = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            String field = error.getObjectName();
            String message = error.getDefaultMessage();
            errors.add(new ValidationErrorResponse(field, message));
        }
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<BasicErrorResponse> handleNotFoundException(
            NotFoundException ex
    ) {
        BasicErrorResponse error = new BasicErrorResponse();
        error.setMessage(ex.getMessage());
        return ResponseEntity.status(404).body(error);
    }
}
