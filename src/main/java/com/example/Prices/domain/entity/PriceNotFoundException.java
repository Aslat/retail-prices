package com.example.Prices.domain.entity;

import org.springframework.http.HttpStatus;

public class PriceNotFoundException extends RuntimeException{
    private HttpStatus httpStatus = HttpStatus.NOT_FOUND;

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public PriceNotFoundException(String message) {
        super(message);
    }
}