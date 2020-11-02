package org.incolo.springpulsar.config;

import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.PulsarClientException;
import org.incolo.springpulsar.core.ContainerConfiguration;

public interface ConsumerFactory {
	Consumer<?> createConsumer(ContainerConfiguration containerConfiguration) throws PulsarClientException;
}
