package org.incolo.springpulsar.core;

import org.apache.pulsar.client.api.PulsarClientException;

public interface Acknowledgement {
	void ack() throws PulsarClientException;
	void nack();
}
