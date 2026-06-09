package org.example.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoPrizeInInventoryException extends RuntimeException {

    public NoPrizeInInventoryException(String prizeName) {
        super("Prize '" + prizeName + "' is not available in inventory");
    }
}
