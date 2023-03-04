package ru.practicum.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.exception.model.ConflictException;
import ru.practicum.exception.model.NotFoundException;
import ru.practicum.exception.model.ValidException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError manage(final MethodArgumentNotValidException e) {
        log.warn(Objects.requireNonNull(e.getFieldError()).getDefaultMessage());
        return new ApiError(Objects.requireNonNull(e.getFieldError()).getDefaultMessage(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                HttpStatus.BAD_REQUEST.name(),
                LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError manage(final MissingServletRequestParameterException e) {
        log.warn(e.getMessage());
        return new ApiError(e.getMessage(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                HttpStatus.BAD_REQUEST.name(),
                LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError manage(final ConstraintViolationException e) {
        log.warn(e.getMessage());
        return new ApiError(e.getMessage(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                HttpStatus.BAD_REQUEST.name(),
                LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError manage(final IllegalArgumentException e) {
        log.warn(e.getMessage());
        return new ApiError(e.getMessage(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                HttpStatus.BAD_REQUEST.name(),
                LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError manage(final ConflictException e) {
        log.warn(e.getMessage());
        return new ApiError(e.getMessage(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                HttpStatus.CONFLICT.name(),
                LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError manage(final NotFoundException e) {
        log.warn(e.getMessage());
        return new ApiError(e.getMessage(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                HttpStatus.NOT_FOUND.name(),
                LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError manage(final ValidException e) {
        log.warn(e.getMessage());
        return new ApiError(e.getMessage(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                HttpStatus.BAD_REQUEST.name(),
                LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError manage(HttpMessageNotReadableException e) {
        log.warn(e.getMessage());
        return new ApiError(e.getMessage(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                HttpStatus.CONFLICT.name(),
                LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError manage(DataIntegrityViolationException e) {
        log.warn(e.getMessage());
        return new ApiError(e.getMessage(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                HttpStatus.CONFLICT.name(),
                LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError manage(Throwable e) {
        log.warn(e.getMessage());
        return new ApiError(e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                HttpStatus.INTERNAL_SERVER_ERROR.name(),
                LocalDateTime.now());
    }
}
