package com.chanjiaen.springbootassessment.exceptions;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;

@ControllerAdvice
public class GlobalExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler (ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException constraintViolationException) {
        Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();

        List errors = new ArrayList<>();

        if (!violations.isEmpty()) {
            violations.forEach(violation -> {
                errors.add(violation.getMessage());
            });
        } else {
            errors.add("Constraint violation occurred.");
        }

        Collections.sort(errors);

        Map<String, List<String>> errorList = new HashMap<>();
        errorList.put("error", errors);

        return new ResponseEntity<>(errorList, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler (ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException) {

        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", resourceNotFoundException.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

}