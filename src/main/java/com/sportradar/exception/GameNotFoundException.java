package com.sportradar.exception;

public class GameNotFoundException extends RuntimeException {

    public GameNotFoundException() {
        super("Game was not found");
    }

    public GameNotFoundException(String message) {
        super(message);
    }

}
