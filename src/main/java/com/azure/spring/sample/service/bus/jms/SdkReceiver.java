package com.azure.spring.sample.service.bus.jms;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusReceiverClient;
import com.azure.messaging.servicebus.models.SubQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Profile("SDK")
@Component
public class SdkReceiver {

    private final Logger LOGGER = LoggerFactory.getLogger(SdkReceiver.class);

    @Value("${connection-string}")
    String connectionString;
    @Value("${topic-name}")
    String topicName;
    @Value("${subscription-name}")
    String subscriptionName;

    @Bean
    public CommandLineRunner receiveMessageRunner() {
        return args -> new SimpleAsyncTaskExecutor().execute(this::receiveMessage);
    }

    public void receiveMessage() {
        LOGGER.info("Create deadLetterQueueClient begin.");
        ServiceBusReceiverClient deadLetterQueueClient = new ServiceBusClientBuilder()
                .connectionString(connectionString)
                .receiver()
                .topicName(topicName)
                .subscriptionName(subscriptionName)
                .subQueue(SubQueue.DEAD_LETTER_QUEUE)
                .buildClient();
        LOGGER.info("Create deadLetterQueueClient end.");
        while (true) {
            LOGGER.info("Receiving message from dead-letter queue.");
            deadLetterQueueClient.receiveMessages(10, Duration.ofSeconds(30)).forEach(message -> {
                LOGGER.info("Received message from dead-letter queue. Sequence #: {}. DeliveryCount {}. Body: {}",
                        message.getSequenceNumber(), message.getDeliveryCount(), message.getBody());
                deadLetterQueueClient.complete(message);
            });
        }
    }
}
