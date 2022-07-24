package com.chepogi.inventory.exceptions;

public class AppRoleException extends RuntimeException {
    public AppRoleException(String message) {
        super(message);
    }

    public AppRoleException(String message, Throwable cause) {
        super(message, cause);
    }
}
