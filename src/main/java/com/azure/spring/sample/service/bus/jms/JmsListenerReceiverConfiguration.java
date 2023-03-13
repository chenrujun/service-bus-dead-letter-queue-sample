package com.azure.spring.sample.service.bus.jms;

import com.azure.spring.cloud.autoconfigure.jms.ServiceBusJmsConnectionFactoryCustomizer;
import org.apache.qpid.jms.JmsConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("JmsListener")
@Component
public class JmsListenerReceiverConfiguration {

    /**
     * Avoid message expiration checking by {@link JmsConnectionFactory#setLocalMessageExpiry}.
     *
     * @return ServiceBusJmsConnectionFactoryCustomizer bean.
     */
    @Bean
    public ServiceBusJmsConnectionFactoryCustomizer configureLocalMessageExpiry() {
        return (factory) -> factory.setLocalMessageExpiry(false);
    }
}
