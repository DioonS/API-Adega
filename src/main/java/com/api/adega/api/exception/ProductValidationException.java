package com.api.adega.api.exception;

public class ProductValidationException extends RuntimeException {
    public ProductValidationException(String message) {
        super(message);
    }
}
