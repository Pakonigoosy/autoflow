package com.kargin.autoflow.exception;

import java.io.Serial;

/**
 * Исключение, выбрасываемое когда компонент не найден
 */
public class ComponentNotFoundException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;
    
    public ComponentNotFoundException(String message) {
        super(message);
    }
    
    public ComponentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
