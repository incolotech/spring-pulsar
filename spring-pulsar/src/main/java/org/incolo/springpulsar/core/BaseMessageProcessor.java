package org.incolo.springpulsar.core;

import org.apache.commons.logging.LogFactory;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.PulsarClientException;
import org.incolo.springpulsar.annotation.AutoAckMode;
import org.springframework.core.log.LogAccessor;
import org.springframework.messaging.handler.invocation.InvocableHandlerMethod;

import java.util.function.Supplier;

public class BaseMessageProcessor implements MessageProcessor {

	protected final LogAccessor logger = new LogAccessor(LogFactory.getLog(getClass()));

	protected final InvocableHandlerMethod invocableHandlerMethod;

	protected final MessageConverter messageConverter;

	protected final Class<?> inferredType;

	protected final AutoAckMode autoAckMode;

	protected final boolean isManualAck;

	public BaseMessageProcessor(InvocableHandlerMethod invocableHandlerMethod,
								MessageConverter messageConverter,
								AutoAckMode autoAckMode) {
		this.invocableHandlerMethod = invocableHandlerMethod;
		this.messageConverter = messageConverter;
		this.autoAckMode = autoAckMode;
		this.inferredType = MessageProcessorUtils.inferPayloadType(invocableHandlerMethod).orElse(null);
		this.isManualAck = MessageProcessorUtils.isManualAck(invocableHandlerMethod);
	}

	@Override
	public void process(Message<?> pulsarMessage, Consumer<?> consumer) throws Exception {
		Acknowledgement acknowledgement = createAck(pulsarMessage, consumer);
		Supplier<org.springframework.messaging.Message<?>> messageSupplier = () -> {
			try {
				return this.messageConverter.toMessage(
						pulsarMessage,
						acknowledgement,
						consumer,
						inferredType);
			} catch (Exception e) {
				throw new SpringPulsarException(e);
			}
		};
		if (isManualAck) {
			processManualAck(pulsarMessage, messageSupplier, acknowledgement);
		} else {
			switch (autoAckMode) {
				case DEFAULT:
				case AUTO_ACK_ALL:
					processAutoAckAll(pulsarMessage, messageSupplier, acknowledgement);
					break;
				case AUTO_ACK_NACK:
					processAckNack(pulsarMessage, messageSupplier, acknowledgement);
					break;
				default:
					throw new UnsupportedOperationException("Ack Mode specified is not supported");
			}
		}
	}

	private void processAutoAckAll(Message<?> pulsarMessage,
								   Supplier<org.springframework.messaging.Message<?>> messageSupplier,
								   Acknowledgement acknowledgement) throws Exception {
		try {
			invocableHandlerMethod.invoke(messageSupplier.get(), pulsarMessage);
		} finally {
			acknowledgement.ack();
		}
	}

	private void processAckNack(Message<?> pulsarMessage,
								Supplier<org.springframework.messaging.Message<?>> messageSupplier,
								Acknowledgement acknowledgement) {
		try {
			invocableHandlerMethod.invoke(messageSupplier.get(), pulsarMessage);
			acknowledgement.ack();
		} catch (Exception e) {
			acknowledgement.nack();
		}
	}

	private void processManualAck(Message<?> pulsarMessage,
								  Supplier<org.springframework.messaging.Message<?>> messageSupplier,
								  Acknowledgement acknowledgement) throws Exception {

		invocableHandlerMethod.invoke(messageSupplier.get(), pulsarMessage, acknowledgement);
	}

	private Acknowledgement createAck(Message<?> pulsarMessage, Consumer<?> consumer) {
		return new Acknowledgement() {
			@Override
			public void ack() throws PulsarClientException {
				consumer.acknowledge(pulsarMessage);
			}

			@Override
			public void nack() {
				consumer.negativeAcknowledge(pulsarMessage);
			}
		};
	}

}
