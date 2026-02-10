package com.kargin.autoflow.exception;

import java.io.Serial;

/**
 * Исключение, выбрасываемое когда VIN уже существует
 */
public class DuplicateVinException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;
    
    public DuplicateVinException(String message) {
        super(message);
    }
    
    public DuplicateVinException(String message, Throwable cause) {
        super(message, cause);
    }
}
