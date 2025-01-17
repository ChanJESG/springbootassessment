package com.chanjiaen.springbootassessment.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    // if ResourceNotFoundException is thrown, we can use super from the extended RuntimeException class to print a specific error message
    public ResourceNotFoundException() {
        super("Resource not found.");
    }

    // this constructor is used to put in a custom error message
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
