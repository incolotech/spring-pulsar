package org.incolo.springpulsar.config;

import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.PulsarClientException;

public interface ConsumerFactory {
	Consumer<byte[]> createConsumer(PulsarListenerEndpoint<?> endpoint) throws PulsarClientException;
}