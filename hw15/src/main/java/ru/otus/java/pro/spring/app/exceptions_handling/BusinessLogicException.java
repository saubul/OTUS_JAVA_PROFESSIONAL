package ru.otus.java.pro.spring.app.exceptions_handling;

public class BusinessLogicException extends RuntimeException {
    private String code;

    public String getCode() {
        return code;
    }

    public BusinessLogicException(String code, String message) {
        super(message);
        this.code = code;
    }
}
