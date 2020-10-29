package org.incolo.springpulsar.core;

import org.incolo.springpulsar.annotation.AutoAckMode;
import org.incolo.springpulsar.config.PulsarListenerEndpoint;

public class ContainerConfiguration {
	private PulsarListenerEndpoint<?> endpoint;
	private AutoAckMode autoAckMode;

	public ContainerConfiguration(PulsarListenerEndpoint<?> endpoint) {
		this.endpoint = endpoint;
	}

	public AutoAckMode getAutoAckMode() {
		return endpoint.getAutoAckMode() == AutoAckMode.DEFAULT ? autoAckMode : endpoint.getAutoAckMode();
	}

	public ContainerConfiguration setAutoAckMode(AutoAckMode autoAckMode) {
		this.autoAckMode = autoAckMode;
		return this;
	}

	public PulsarListenerEndpoint<?> getEndpoint() {
		return endpoint;
	}

	public ContainerConfiguration setEndpoint(PulsarListenerEndpoint<?> endpoint) {
		this.endpoint = endpoint;
		return this;
	}
}
