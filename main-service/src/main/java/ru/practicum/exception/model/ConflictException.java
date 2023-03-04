package ru.practicum.exception.model;

public class ConflictException extends IllegalArgumentException  {
    public ConflictException(String message) {
        super(message);
    }
}
