package com.kargin.autoflow.exception;

import java.io.Serial;

/**
 * Исключение, выбрасываемое когда автомобиль не найден
 */
public class CarNotFoundException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;
    
    public CarNotFoundException(String message) {
        super(message);
    }
    
    public CarNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
