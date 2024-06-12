package com.sportradar.exception;

public class NegativeScoreException extends RuntimeException {

    public NegativeScoreException() {
        super("Score cannot be negative");
    }

    public NegativeScoreException(String message) {
        super(message);
    }

}
