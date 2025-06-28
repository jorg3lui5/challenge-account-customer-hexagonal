package com.test.customer.management.infrastructure.exception.custom;

public class ParsingJsonException extends RuntimeException{
    public ParsingJsonException(String message) {
        super(message);
    }
}
