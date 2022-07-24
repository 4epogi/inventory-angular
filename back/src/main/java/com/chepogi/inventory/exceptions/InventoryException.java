package com.chepogi.inventory.exceptions;

public class InventoryException  extends RuntimeException{
    public InventoryException(String message) {
        super(message);
    }

    public InventoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
