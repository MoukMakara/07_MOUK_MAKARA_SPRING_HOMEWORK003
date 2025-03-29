package org.ksga._07_mouk_makara_spring_homework003.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(VenueNotFoundException.class)
    public ProblemDetail handleVenueNotFoundException(VenueNotFoundException ex, HttpServletRequest request) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());

        // Set details
        detail.setType(URI.create("about:blank"));
        detail.setTitle("Not Found");
        detail.setDetail(ex.getMessage());
        detail.setInstance(URI.create(request.getRequestURI()));
        detail.setProperty("timestamp", LocalDateTime.now());

        return detail;
    }
    // validate create request by using MethodArgumentNotValidException
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {

        HashMap<String , String> errors = new HashMap<>();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Invalid Fields");
        detail.setType(URI.create("about:blank"));
        detail.setTitle("Bad Request");
        detail.setInstance(URI.create(request.getRequestURI()));
        detail.setProperty("timestamp", LocalDateTime.now());
        detail.setProperty("errors", errors);
        return detail;
    }

    // validate number
    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleConstraintViolationException(ConstraintViolationException e) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        Map<String, String> errors = new HashMap<>();

        e.getConstraintViolations().forEach(violation ->
                errors.put(violation.getPropertyPath().toString(), violation.getMessage())
        );

        detail.setType(URI.create("about:blank"));
        detail.setTitle("Bad Request");
        detail.setDetail("Invalid parameters");
        detail.setProperty("timestamp", LocalDateTime.now());
        detail.setProperty("errors", errors);

        return detail;
    }

    // validate pagination
    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());

        detail.setType(URI.create("about:blank"));
        detail.setTitle("Bad Request");
        detail.setInstance(URI.create(request.getRequestURI()));
        detail.setProperty("timestamp", LocalDateTime.now());

        Map<String, String> errors = new HashMap<>();

        if (ex.getMessage().contains("must be greater than 0")) {
            if (ex.getMessage().contains("offset")) {
                errors.put("offset", "must be greater than 0");
            }
            if (ex.getMessage().contains("limit")) {
                errors.put("limit", "must be greater than 0");
            }
        }

        if (!errors.isEmpty()) {
            detail.setProperty("errors", errors);
        }

        return detail;
    }
}
