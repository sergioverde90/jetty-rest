package com.intelliment.control.exception;

public class NotAllowedException extends Exception {

    public NotAllowedException(Exception e){
        super(e);
    }

    public NotAllowedException(String message, Exception e){
        super(message, e);
    }

    public NotAllowedException(String message){
        super(message);
    }
}
