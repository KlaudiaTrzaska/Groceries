package org.example.services;

import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.example.events.CompletedCheckoutEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CheckoutEventPublisher {

    private final SqsTemplate sqsTemplate;
    private final String analyticsQueue;

    public CheckoutEventPublisher(SqsTemplate sqsTemplate,
                                  @Value("${aws.sqs.analytics-queue}") String analyticsQueue) {
        this.sqsTemplate = sqsTemplate;
        this.analyticsQueue = analyticsQueue;
    }

    public void publishCompletedCheckout(double totalPrice) {
        sqsTemplate.send(analyticsQueue, CompletedCheckoutEvent.withTotalPrice(totalPrice));
    }
}
