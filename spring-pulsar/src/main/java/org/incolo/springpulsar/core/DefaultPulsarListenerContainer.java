package org.incolo.springpulsar.core;

import org.apache.commons.logging.LogFactory;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.PulsarClientException;
import org.incolo.springpulsar.config.ConsumerFactory;
import org.incolo.springpulsar.config.DefaultConsumerFactory;
import org.incolo.springpulsar.config.PulsarListenerEndpoint;
import org.springframework.core.log.LogAccessor;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.invocation.InvocableHandlerMethod;
import org.springframework.messaging.support.MessageBuilder;

import java.util.HashMap;

/**
 * @author Charvak Patel
 */
public class DefaultPulsarListenerContainer implements PulsarListenerContainer {

	protected final LogAccessor logger = new LogAccessor(LogFactory.getLog(getClass()));

	private final PulsarListenerEndpoint<?> endpoint;
	private final ConsumerFactory consumerFactory;
	private final AsyncListenableTaskExecutor taskExecutor;

	private volatile boolean isRunning = false;
	private final Object lifecycleMonitor = new Object();

	private final MessageProcessorFactory<? extends MessageProcessor> messageProcessorFactory;


	public DefaultPulsarListenerContainer(
			PulsarListenerEndpoint<?> endpoint,
			ConsumerFactory consumerFactory,
			AsyncListenableTaskExecutor taskExecutor,
			MessageProcessorFactory<? extends MessageProcessor> messageProcessorFactory) {
		this.endpoint = endpoint;
		this.consumerFactory = consumerFactory;
		this.taskExecutor = taskExecutor;
		this.messageProcessorFactory = messageProcessorFactory;
	}

	@Override
	public void start() {
		synchronized (this.lifecycleMonitor) {
			if (!isRunning()) {
				try {
					this.isRunning = true;
					this.taskExecutor.submit(
							new ContainerTask(consumerFactory.createConsumer(endpoint),
									messageProcessorFactory.createMessageProcessor(endpoint.getBean(), endpoint.getMethod())));
				} catch (PulsarClientException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void stop() {
		synchronized (this.lifecycleMonitor) {
			if (isRunning()) {
				isRunning = false;
				logger.info("Stopping the container");
			}
		}
	}

	@Override
	public boolean isRunning() {
		return this.isRunning;
	}

	@Override
	public PulsarListenerEndpoint<?> getEndpoint() {
		return endpoint;
	}


	private final class ContainerTask implements Runnable {

		private final Consumer<?> consumer;
		private final MessageProcessor messageProcessor;

		public ContainerTask(Consumer<?> consumer, MessageProcessor messageProcessor) {
			this.consumer = consumer;
			this.messageProcessor = messageProcessor;
		}


		@Override
		public void run() {
			while (isRunning()) {
				try {
					processMessage();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		private void processMessage() throws Exception {
			Message<?> pulsarMessage = consumer.receive();
			this.messageProcessor.process(pulsarMessage, consumer);
		}


	}
}
