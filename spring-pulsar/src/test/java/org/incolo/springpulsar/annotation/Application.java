package org.incolo.springpulsar.annotation;

import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.SubscriptionType;
import org.incolo.springpulsar.core.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDateTime;

/**
 * @author Charvak Patel
 */
@SpringJUnitConfig
public class Application {

    @Test
    public void init() throws InterruptedException {
    	Thread.sleep(10000);
	}
    PulsarListenerAnnotationBeanPostProcessor processor;

    @Autowired
	PulsarListenerContainerRegistry registry;

    @Autowired
	Config config;



    @Configuration
    @EnablePulsar
    public static class Config {

    	@Bean
		public PulsarClient pulsarClient() throws PulsarClientException {
    		return PulsarClient.builder()
					.serviceUrl("pulsar://localhost:6650")
					.build();
		}

		@Bean
		public ConsumerFactory consumerFactory(PulsarClient client) {
			return new ConsumerFactory(client);
		}

		@Bean
		PulsarListenerContainerFactory<?> defaultPulsarListenerContainerFactory(ConsumerFactory consumerFactory) {
			return new DefaultPulsarListenerContainerFactory(consumerFactory);
		}

    	@PulsarListener(
    			topics = "persistent://public/default/sample-test",
				subscriptionName = "sample-test",
				subscriptionType = SubscriptionType.Shared
		)
    	public void testing(@Payload String msg, @Header("key") String key) {
			System.out.println("Message : " + msg);
			System.out.println("Key : " + msg);
		}
    }
}
