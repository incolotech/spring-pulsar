package org.incolo.springpulsar.core;

import org.apache.pulsar.client.api.Consumer;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import java.lang.reflect.Type;
import java.util.HashMap;

public interface MessageConverter {

	Message<?> toMessage(org.apache.pulsar.client.api.Message<?> message, Acknowledgement acknowledgment, Consumer<?> consumer, Type inferredType);

	default MessageHeaders getMessageHeaders(org.apache.pulsar.client.api.Message<?> msg) {
		return new MessageHeaders(new HashMap<String, Object>() {{
			put(PulsarHeaders.KEY, msg.getKey());
			put(PulsarHeaders.PRODUCER_NAME, msg.getProducerName());
			put(PulsarHeaders.EVENT_TIME, msg.getEventTime());
			put(PulsarHeaders.ENCRYPTION_CTX, msg.getEncryptionCtx());
			put(PulsarHeaders.MESSAGE_ID, msg.getMessageId());
			put(PulsarHeaders.ORDERING_KEY, msg.getOrderingKey());
			put(PulsarHeaders.PUBLISH_TIME, msg.getPublishTime());
			put(PulsarHeaders.REDELIVERY_COUNT, msg.getRedeliveryCount());
			put(PulsarHeaders.REPLICATED_FROM, msg.getReplicatedFrom());
			put(PulsarHeaders.SCHEMA_VERSION, msg.getSchemaVersion());
			put(PulsarHeaders.SEQUENCE_ID, msg.getSequenceId());
			putAll(msg.getProperties());
		}});
	}

}
