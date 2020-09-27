package org.incolo.springpulsar.core;

public interface Acknowledgement {
	void ack();
	void nack();
}
