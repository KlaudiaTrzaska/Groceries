package org.example.services;

import io.awspring.cloud.sqs.operations.SqsTemplate;
import jakarta.annotation.Nullable;
import org.example.events.CompletedCheckoutEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CheckoutEventPublisher {

    private final SqsTemplate sqsTemplate;
    private final String analyticsQueue;
    private static final Logger log =
            LoggerFactory.getLogger(CheckoutEventPublisher.class);

    public CheckoutEventPublisher(
            @Nullable SqsTemplate sqsTemplate,
            @Value("${aws.sqs.analytics-queue}") String analyticsQueue) {
        this.sqsTemplate = sqsTemplate;
        this.analyticsQueue = analyticsQueue;
    }

    public void publishCompletedCheckout(double totalPrice) {
        var event = CompletedCheckoutEvent.withTotalPrice(totalPrice);

        if (sqsTemplate == null) {
            log.info("LOCAL: Checkout completed, event: {}", event);
            return;
        }

        sqsTemplate.send(analyticsQueue, event);
    }
}
