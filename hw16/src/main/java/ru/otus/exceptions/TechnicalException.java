package ru.otus.exceptions;

public class TechnicalException extends RuntimeException {
    public TechnicalException(String message) {
        super(message);
    }
}
