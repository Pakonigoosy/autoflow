package com.kargin.autoflow.exception;

import java.io.Serial;

/**
 * Исключение, выбрасываемое при попытке создания компонента с неуникальным номером
 */
public class DuplicateException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;
    
    public DuplicateException(String message) {
        super(message);
    }
    
    public DuplicateException(String message, Throwable cause) {
        super(message, cause);
    }
}
