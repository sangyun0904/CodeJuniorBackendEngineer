package com.example.CodeJuniorBackendEngineer.exceptions;

public class DuplicatedEmail extends RuntimeException {
    public DuplicatedEmail(String errorMessage) {
        super(errorMessage);
    }
}
