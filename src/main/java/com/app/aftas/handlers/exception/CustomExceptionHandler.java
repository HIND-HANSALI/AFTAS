package com.app.aftas.handlers.exception;

import com.app.aftas.handlers.response.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, List<String>> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.groupingBy(FieldError::getField, Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())));

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(OperationException.class)
    public ResponseEntity<ResponseMessage> handleOperationException(OperationException ex) {

        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), ex.getMessage());

        return ResponseEntity.badRequest().body(responseMessage);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseMessage> handleResourceNotFoundException(ResourceNotFoundException ex) {

        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), ex.getMessage());

        return ResponseEntity.badRequest().body(responseMessage);
    }
}
