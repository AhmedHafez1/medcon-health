package com.medcon.exception;

import lombok.Getter;

@Getter
public class AlreadyExistsException extends RuntimeException {
    private final String entityName;

    public AlreadyExistsException(String message, String entityName) {
        super(message);
        this.entityName = entityName;
    }
}
