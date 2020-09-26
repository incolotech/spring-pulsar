package org.incolo.springpulsar.annotation;

import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.SubscriptionType;
import org.incolo.springpulsar.config.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/**
 * @author Charvak Patel
 */
@SpringJUnitConfig
public class Application {

    @Test
    public void init() throws InterruptedException {
    	Thread.sleep(10000);
	}
    @Autowired
	Config config;



	@Configuration
	@EnablePulsar
	public static class Config {


		@Bean
		public ClientFactory clientFactory() throws PulsarClientException {
			return new DefaultClientFactory(
					new PulsarClientProperties("pulsar://localhost:6650")
			);
		}

		@Bean
		public ConsumerFactory consumerFactory(ClientFactory client) throws PulsarClientException {
			return new DefaultConsumerFactory(client);
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
