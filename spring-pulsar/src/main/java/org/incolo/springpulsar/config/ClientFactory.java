package org.incolo.springpulsar.config;

import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;

public interface ClientFactory {
	PulsarClient createClient() throws PulsarClientException;
}
