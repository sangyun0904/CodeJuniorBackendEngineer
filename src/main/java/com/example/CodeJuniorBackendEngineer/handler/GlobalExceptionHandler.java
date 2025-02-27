package com.example.CodeJuniorBackendEngineer.handler;

import com.example.CodeJuniorBackendEngineer.exceptions.DuplicatedEmail;
import com.example.CodeJuniorBackendEngineer.exceptions.DuplicatedISBN;
import com.example.CodeJuniorBackendEngineer.exceptions.ISBNValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {DuplicatedEmail.class, DuplicatedISBN.class, ISBNValidationException.class})
    protected ResponseEntity<Object> handleException(Exception ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}