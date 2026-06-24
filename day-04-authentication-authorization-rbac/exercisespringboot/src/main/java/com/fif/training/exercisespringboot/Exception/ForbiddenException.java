package com.fif.training.exercisespringboot.Exception;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException() {
        super("You do not have permission to access this resource!");
    }

    public ForbiddenException(String message) {
        super(message);
    }

}
