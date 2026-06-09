package org.example.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotEnoughPointsForPrizeException extends RuntimeException {

    public NotEnoughPointsForPrizeException(String prizeName) {
        super("Client does not have enough points for " + prizeName);
    }
}
