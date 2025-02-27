package com.example.CodeJuniorBackendEngineer.exceptions;

public class DuplicatedISBN extends RuntimeException {
    public DuplicatedISBN(String errorMessage) {
        super(errorMessage);
    }
}
