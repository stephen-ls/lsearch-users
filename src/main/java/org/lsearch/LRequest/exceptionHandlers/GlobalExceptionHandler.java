package org.lsearch.LRequest.exceptionHandlers;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, List<String>> body = new HashMap<>();

        List<String> errors = ex
                .getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .toList();

        body.put("errors", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<Object> handleRuntimeException(RuntimeException ex) {
        HttpStatus status = resolveAnnotatedResponseStatus(ex);
        String body = ex.getMessage() != null ? ex.getMessage() : "An unexpected error occurred";

        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex) {
        Map<String, List<String>> body = new HashMap<>();

        String[] errors = { ex.getMessage() };
        body.put("errors", Arrays.stream(errors).toList());

        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

    private HttpStatus resolveAnnotatedResponseStatus(Throwable ex) {
        ResponseStatus responseStatus = ex.getClass().getAnnotation(ResponseStatus.class);
        return responseStatus != null ? responseStatus.value() : HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
