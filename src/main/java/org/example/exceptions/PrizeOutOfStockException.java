package org.example.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class PrizeOutOfStockException extends RuntimeException {

    public PrizeOutOfStockException(String prizeName, Throwable cause) {
        super("Prize '" + prizeName + "' is out of stock", cause);
    }
}
