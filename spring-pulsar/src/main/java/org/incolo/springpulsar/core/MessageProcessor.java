package org.incolo.springpulsar.core;

import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;

public interface MessageProcessor {
	void process(Message<?> pulsarMessage, Consumer<?> consumer) throws Exception;
}
