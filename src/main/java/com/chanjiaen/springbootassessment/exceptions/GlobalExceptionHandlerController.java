package com.chanjiaen.springbootassessment.exceptions;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;

@ControllerAdvice
public class GlobalExceptionHandlerController extends ResponseEntityExceptionHandler {
    // handler for @PostMapping (if constraints are violated)
    @ExceptionHandler (ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException constraintViolationException) {

        // making a set to store the types of constraint violations
        Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();

        // initializing a list to store violation messages
        List errors = new ArrayList<>();

        // if there are violations, use a for each loop to add each message to the errors List
        if (!violations.isEmpty()) {
            violations.forEach(violation -> {
                errors.add(violation.getMessage());
            });
        } else {
            // if the specific constraint violation is unknown, this message will be added instead
            errors.add("Constraint violation occurred.");
        }

        // sort the error list alphabetically
        Collections.sort(errors);

        // creating a HashMap to store the string "error" and each error in errors to form a key value pair
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


    // TODO exception handling for non boolean when creating task
    /*@ExceptionHandler (MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException validEx) {
        BindingResult result = validEx.getBindingResult();

        List <FieldError> errors = result.getFieldErrors();

        Map<String, String> errorResponse = new HashMap<>();

        for (FieldError error : errors) {
            errorResponse.put(error.getField(), error.getDefaultMessage());
        }
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }*/

}