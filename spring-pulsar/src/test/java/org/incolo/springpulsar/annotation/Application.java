package org.incolo.springpulsar.annotation;

import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.SubscriptionType;
import org.incolo.springpulsar.annotation.sample.CustomData;
import org.incolo.springpulsar.config.*;
import org.incolo.springpulsar.core.JsonMessageConverter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/**
 * @author Charvak Patel
 */
@SpringJUnitConfig
public class Application {

	@Test
	public void init() throws InterruptedException {
		Thread.sleep(100000);
	}

	@Autowired
	public Config config;


	@Configuration
	@EnablePulsar
	@ComponentScan(basePackageClasses = CustomSchemaProvider.class)
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
			return new SimplePulsarListenerContainerFactory(consumerFactory, new JsonMessageConverter());
		}

		@PulsarListener(
				topics = "persistent://public/default/sample-test",
				subscriptionName = "sample-test",
				subscriptionType = SubscriptionType.Shared
		)
		public void testing(CustomData payload, Message<?> msg) {
			System.out.println("Message : " + payload.getField1());
			System.out.println("Key : " + msg);
		}
	}
}
