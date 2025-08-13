package com.zeta.miniproject2.Restaurant.Management.System.Exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}