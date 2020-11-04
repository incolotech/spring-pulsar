package org.incolo.springpulsar.config;

import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;

public interface PulsarClientFactory {
	PulsarClient createClient() throws PulsarClientException;
}
