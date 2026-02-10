package com.kargin.autoflow.exception;

import java.io.Serial;

/**
 * Исключение, выбрасываемое когда компонент уже используется в другом автомобиле
 */
public class ComponentInUseException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;
    
    public ComponentInUseException(String message) {
        super(message);
    }
    
    public ComponentInUseException(String message, Throwable cause) {
        super(message, cause);
    }
}
