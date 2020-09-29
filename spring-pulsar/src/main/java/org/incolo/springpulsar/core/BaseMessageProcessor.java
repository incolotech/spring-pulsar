package org.incolo.springpulsar.core;

import org.apache.commons.logging.LogFactory;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.springframework.core.log.LogAccessor;
import org.springframework.messaging.handler.invocation.InvocableHandlerMethod;

public class BaseMessageProcessor implements MessageProcessor {

	protected final LogAccessor logger = new LogAccessor(LogFactory.getLog(getClass()));

	private final InvocableHandlerMethod invocableHandlerMethod;

	private final MessageConverter messageConverter;

	private final Class<?> inferredType;

	public BaseMessageProcessor(InvocableHandlerMethod invocableHandlerMethod, MessageConverter messageConverter) {
		this.invocableHandlerMethod = invocableHandlerMethod;
		this.messageConverter = messageConverter;
		this.inferredType = MessageProcessorUtils.inferPayloadType(invocableHandlerMethod).orElse(null);
	}

	@Override
	public void process(Message<?> pulsarMessage, Consumer<?> consumer) throws Exception {
		org.springframework.messaging.Message<?> message = this.messageConverter.toMessage(pulsarMessage, null, consumer, inferredType);
		invocableHandlerMethod.invoke(message, pulsarMessage);
	}

}
