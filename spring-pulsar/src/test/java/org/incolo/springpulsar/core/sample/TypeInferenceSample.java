package org.incolo.springpulsar.core.sample;

import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.springframework.messaging.handler.annotation.Payload;

//Don't do method overloading in this class, for each case method name should be different
public class TypeInferenceSample {

	public void methodWithReservedTypes(String payload, Consumer<?> consumer, Message msg) {
	}

	public void methodWithMultiplePayloadTypes(String payload1, Object payload2,Consumer<?> consumer, Message msg) {
	}

	public void methodWithMultiplePayloadTypesWithSingleAnnotation(String payload1, @Payload Object payload2, Consumer<?> consumer, Message msg) {
	}

	public void methodWithMultiplePayloadAnnotations(@Payload String payload1, @Payload Object payload2, Consumer<?> consumer, Message msg) {
	}

	public void methodWithNoPayload(Consumer<?> consumer, Message<?> message) {
	}
}
