package org.lsearch.LRequest.exceptions;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
        Map<String, List<String>> body = new HashMap<>();

        String[] errors = { ex.getMessage() };
        body.put("errors", Arrays.stream(errors).toList());

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex) {
        Map<String, List<String>> body = new HashMap<>();

        String[] errors = { ex.getMessage() };
        body.put("errors", Arrays.stream(errors).toList());

        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }
}
