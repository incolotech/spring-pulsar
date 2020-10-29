package org.incolo.springpulsar.core;

import org.apache.commons.logging.LogFactory;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.PulsarClientException;
import org.incolo.springpulsar.config.ConsumerFactory;
import org.incolo.springpulsar.config.PulsarListenerEndpoint;
import org.springframework.core.log.LogAccessor;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author Charvak Patel
 */
public class SimplePulsarListenerContainer extends AbstractPulsarListenerContainer {

	static final LogAccessor logger = new LogAccessor(LogFactory.getLog(SimplePulsarListenerContainer.class));

	private ListenableFuture<?> containerTaskFuture;

	public SimplePulsarListenerContainer(ContainerConfiguration containerConfiguration,
											ConsumerFactory consumerFactory,
											AsyncListenableTaskExecutor taskExecutor,
											MessageProcessorFactory<? extends MessageProcessor> messageProcessorFactory) {
		super(containerConfiguration, consumerFactory, taskExecutor, messageProcessorFactory);
	}

	PulsarListenerEndpoint<?> getEndpoint() {
		return containerConfiguration.getEndpoint();
	}


	protected void doStart() throws SpringPulsarException {
		try {
			this.containerTaskFuture = this.taskExecutor.submitListenable(
					new ContainerTask(consumerFactory.createConsumer(getEndpoint()),
							messageProcessorFactory.createMessageProcessor(
									getEndpoint().getBean(),
									getEndpoint().getMethod(),
									getEndpoint().getAutoAckMode())));
		} catch (PulsarClientException e) {
			throw new SpringPulsarException(e);
		}
	}

	protected void doStop() {
		try {
			this.containerTaskFuture.get();
		} catch (InterruptedException | ExecutionException e) {
			throw new SpringPulsarException(e);
		}
	}


	private final class ContainerTask implements Callable<Object> {

		private final Consumer<?> consumer;
		private final MessageProcessor messageProcessor;
		private final int receiveTimeoutMs = 2000;

		public ContainerTask(Consumer<?> consumer, MessageProcessor messageProcessor) {
			this.consumer = consumer;
			this.messageProcessor = messageProcessor;
		}

		private void close() throws PulsarClientException {
			this.consumer.close();
			logger.info("Consumer closed");
		}

		private void processMessage() throws Exception {
			Message<?> pulsarMessage = consumer.receive(receiveTimeoutMs, TimeUnit.MILLISECONDS);
			if (pulsarMessage != null) {
				this.messageProcessor.process(pulsarMessage, consumer);
			}

		}

		@Override
		public Object call() throws Exception {
			while (isRunning()) {
				try {
					processMessage();
				} catch (Exception e) {
					logger.error(new SpringPulsarException(e), "Error while processing message");
				}
			}
			logger.info("Inside the task");
			try {
				close();
			} catch (PulsarClientException e) {
				throw new SpringPulsarException(e);
			}
			return null;
		}
	}
}
