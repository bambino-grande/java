package ru.pogosian.business.excrptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class StorageServiceUnavailableException extends RuntimeException {
    public StorageServiceUnavailableException(String message) {
        super(message);
    }
}
