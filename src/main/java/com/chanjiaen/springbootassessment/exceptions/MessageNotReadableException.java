package com.chanjiaen.springbootassessment.exceptions;

public class MessageNotReadableException extends RuntimeException {
    public MessageNotReadableException(){
        super("Input entry is invalid. Please try again.");
    }
}