package org.example.events;

import java.time.LocalDate;

public record CompletedCheckoutEvent(String eventName, LocalDate checkoutDate, double totalPrice) {

    public static final String EVENT_NAME = "completed checkout";

    public static CompletedCheckoutEvent withTotalPrice(double totalPrice) {
        return new CompletedCheckoutEvent(EVENT_NAME, LocalDate.now(), totalPrice);
    }
}
