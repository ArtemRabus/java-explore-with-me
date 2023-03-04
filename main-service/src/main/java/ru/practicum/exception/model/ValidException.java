package ru.practicum.exception.model;

public class ValidException extends IllegalArgumentException {
    public ValidException(String message) {
        super(message);
    }
}
