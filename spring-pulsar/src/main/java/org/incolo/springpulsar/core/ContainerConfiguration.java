package org.incolo.springpulsar.core;

import org.incolo.springpulsar.config.PulsarListenerEndpoint;

public class ContainerConfiguration {
	private PulsarListenerEndpoint<?> endpoint;
	private ContainerProperties containerProperties;

	public ContainerConfiguration(PulsarListenerEndpoint<?> endpoint, ContainerProperties containerProperties) {
		this.endpoint = endpoint;
		this.containerProperties = containerProperties;
	}
	public PulsarListenerEndpoint<?> getEndpoint() {
		return endpoint;
	}

	public ContainerConfiguration setEndpoint(PulsarListenerEndpoint<?> endpoint) {
		this.endpoint = endpoint;
		return this;
	}

	public ContainerProperties getContainerProperties() {
		return containerProperties;
	}

	public ContainerConfiguration setContainerProperties(ContainerProperties containerProperties) {
		this.containerProperties = containerProperties;
		return this;
	}
}
