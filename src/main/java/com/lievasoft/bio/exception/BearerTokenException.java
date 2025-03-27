package com.lievasoft.bio.exception;

public class BearerTokenException extends RuntimeException {
    public BearerTokenException() {
        super("Bearer token header is invalid");
    }
}
