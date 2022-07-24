package com.chepogi.inventory.exceptions;

public class InventoryAuthenticationException extends RuntimeException {

    public InventoryAuthenticationException(String message) {
        super(message);
    }

    public InventoryAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
