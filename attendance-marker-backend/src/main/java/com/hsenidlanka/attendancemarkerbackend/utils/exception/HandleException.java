package com.hsenidlanka.attendancemarkerbackend.utils.exception;

public class HandleException extends RuntimeException {

    private String message;

    public HandleException() {}
    public HandleException(String message)
    {
        super(message);
        this.message = message;
    }
}