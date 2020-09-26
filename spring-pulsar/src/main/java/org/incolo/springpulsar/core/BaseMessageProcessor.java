package org.incolo.springpulsar.core;

import org.apache.commons.logging.LogFactory;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.springframework.core.log.LogAccessor;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.invocation.InvocableHandlerMethod;
import org.springframework.messaging.support.MessageBuilder;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class BaseMessageProcessor implements MessageProcessor {

	protected final LogAccessor logger = new LogAccessor(LogFactory.getLog(getClass()));



	private final InvocableHandlerMethod invocableHandlerMethod;

	public BaseMessageProcessor(InvocableHandlerMethod invocableHandlerMethod) {
		this.invocableHandlerMethod = invocableHandlerMethod;
	}

	@Override
	public void process(Message<?> pulsarMessage, Consumer<?> consumer) throws Exception {
		org.springframework.messaging.Message message =
				MessageBuilder.createMessage(pulsarMessage.getValue(),
						new MessageHeaders(new HashMap<String, Object>() {{
							put("key", pulsarMessage.getKey());
							putAll(pulsarMessage.getProperties());
						}}));
		invocableHandlerMethod.invoke(message, pulsarMessage);
	}

}
