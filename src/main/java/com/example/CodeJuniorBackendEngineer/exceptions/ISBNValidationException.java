package com.example.CodeJuniorBackendEngineer.exceptions;

public class ISBNValidationException extends RuntimeException {
    public ISBNValidationException(String errorMessage) {
        super(errorMessage);
    }
}
