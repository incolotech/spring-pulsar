package org.incolo.springpulsar.config;

import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;

public class DefaultClientFactory implements ClientFactory{

	PulsarClientProperties properties;

	public DefaultClientFactory(PulsarClientProperties properties) {
		this.properties = properties;
	}

	@Override
	public PulsarClient createClient() throws PulsarClientException {
		return PulsarClient.builder()
				.serviceUrl(this.properties.getServiceUrl())
				.build();
	}
}
