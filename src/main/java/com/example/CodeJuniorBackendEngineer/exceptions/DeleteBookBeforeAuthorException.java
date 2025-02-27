package com.example.CodeJuniorBackendEngineer.exceptions;

public class DeleteBookBeforeAuthorException extends RuntimeException {
    public DeleteBookBeforeAuthorException(String errorMessage) {
        super(errorMessage);
    }
}
