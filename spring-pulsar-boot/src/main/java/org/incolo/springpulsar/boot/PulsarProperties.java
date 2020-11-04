package org.incolo.springpulsar.boot;

import org.incolo.springpulsar.config.PulsarClientProperties;
import org.incolo.springpulsar.core.ContainerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Charvak Patel
 */
@ConfigurationProperties(prefix = "spring.pulsar")
public class PulsarProperties {
	private final PulsarClientProperties client = new PulsarClientProperties();
	private final ContainerProperties consumer = new ContainerProperties();

	public ContainerProperties getConsumer() {
		return consumer;
	}

	public PulsarClientProperties getClient() {
		return client;
	}

}
