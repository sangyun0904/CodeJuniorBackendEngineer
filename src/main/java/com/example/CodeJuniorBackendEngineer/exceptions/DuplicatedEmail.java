package com.example.CodeJuniorBackendEngineer.exceptions;

public class DuplicatedEmail extends Exception {
    public DuplicatedEmail(String errorMessage) {
        super(errorMessage);
    }
}
