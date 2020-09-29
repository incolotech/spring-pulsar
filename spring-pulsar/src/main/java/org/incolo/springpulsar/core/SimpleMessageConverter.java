package org.incolo.springpulsar.core;


import org.apache.pulsar.client.api.Consumer;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.lang.reflect.Type;

/**
 * A pass through converter used as default <br>
 * It passes Payload as is, and useful headers
 */
public class SimpleMessageConverter implements MessageConverter {
	@Override
	public Message<?> toMessage(org.apache.pulsar.client.api.Message<?> message, Acknowledgement acknowledgment, Consumer<?> consumer, Type inferredType) throws Exception {
		return MessageBuilder.createMessage(message.getValue(), getMessageHeaders(message));
	}
}
