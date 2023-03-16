# Consume messages in Azure Service Bus Dead Letter Queue

This project is mainly used to demonstrate how to consume [Azure Service Bus](https://learn.microsoft.com/en-us/azure/service-bus-messaging/service-bus-messaging-overview) [dead-letter queues](https://learn.microsoft.com/en-us/azure/service-bus-messaging/service-bus-dead-letter-queues) messages in Spring Boot application. 2 methods are introduced:

 - Consume message by Java SDK: [azure-messaging-servicebus:7.13.0](https://github.com/Azure/azure-sdk-for-java/tree/azure-messaging-servicebus_7.13.0/sdk/servicebus/azure-messaging-servicebus).
 - Consume message by [@JmsListener in spring-jms:5.3.25](https://docs.spring.io/spring-framework/docs/5.3.25/javadoc-api/org/springframework/jms/annotation/JmsListener.html)

## Prerequisites
1. JDK. Version = 1.8.
2. An Azure subscription. If you don't have a subscription, create a [free account](https://azure.microsoft.com/free/) before you begin.

## Create Azure resources.
1. Create an Azure Service Bus namespace, topic and subscription. Refs: [Use the Azure portal to create a Service Bus topic and subscriptions to the topic](https://learn.microsoft.com/en-us/azure/service-bus-messaging/service-bus-quickstart-topics-subscriptions-portal).
2. Set Message expiration to 5 seconds for the created subscription. Refs: [Azure Service Bus - Message expiration (Time to Live)](https://learn.microsoft.com/en-us/azure/service-bus-messaging/message-expiration).
3. Enable dead lettering on message expiration. Refs: [Enable dead lettering on message expiration for Azure Service Bus queues and subscriptions](https://learn.microsoft.com/en-us/azure/service-bus-messaging/enable-dead-letter).

## Update sample project
Fulfill the following properties in [application.yml](src/main/resources/application.yml):
```yaml
connection-string:
topic-name:
subscription-name:
```

## Run application

### Receive message by SDK
1. In application.yml, set **spring.profiles.active=SDK**.
2. Start Application.
3. Send a message to the created topic. Refs: [Send a message to a queue or topic](https://learn.microsoft.com/en-us/azure/service-bus-messaging/explorer#send-a-message-to-a-queue-or-topic).
4. Wait for a while to make the message to be expired and put to dead-letter queue. Then the app can consume the message in dead-letter queue. The result can be validated by app log:
```text
xxx.SdkReceiver            : Received message from dead-letter queue. Sequence #: xxx. DeliveryCount xxx. Body: xxx
```

### Receive message by @JmsListener
Same as previous step, just change **spring.profiles.active** from **SDK** to **JmsListener** in application.yml. The result can be validated by app log:
```text
xxx.JmsListenerReceiver    : Received message from queue: topic-1/subscriptions/subscription-1/$deadletterqueue. body = xxx.
```

