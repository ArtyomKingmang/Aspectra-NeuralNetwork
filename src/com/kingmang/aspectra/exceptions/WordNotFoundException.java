package com.kingmang.aspectra.exceptions;

public class WordNotFoundException extends Exception {
    public WordNotFoundException(String message) {
            super(message);
    }

    public WordNotFoundException(String message, Throwable throwable) {
            super(message, throwable);
    }
}