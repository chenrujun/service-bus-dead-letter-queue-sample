package com.azure.spring.sample.service.bus.jms;

import org.apache.qpid.jms.message.JmsMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;

@Profile("JmsListener")
@Component
public class JmsListenerReceiver {

    private final Logger LOGGER = LoggerFactory.getLogger(JmsListenerReceiver.class);

    @JmsListener(
            containerFactory = "jmsListenerContainerFactory",
            destination = "topic-1/subscriptions/subscription-1/$deadletterqueue")
    public void receiveMessageFromSubscription1DeadLetterQueue(JmsMessage message) throws JMSException {
        String messageBody = new String(message.getBody(byte[].class));
        LOGGER.info("Received message from queue: topic-1/subscriptions/subscription-1/$deadletterqueue. body = {}.", messageBody);
    }
}
