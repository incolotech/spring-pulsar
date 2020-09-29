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
public class DefaultPulsarListenerContainer implements PulsarListenerContainer {

	static final LogAccessor logger = new LogAccessor(LogFactory.getLog(DefaultPulsarListenerContainer.class));

	private final PulsarListenerEndpoint<?> endpoint;
	private final ConsumerFactory consumerFactory;
	private final AsyncListenableTaskExecutor taskExecutor;

	private volatile boolean isRunning = false;
	private final Object lifecycleMonitor = new Object();

	private final MessageProcessorFactory<? extends MessageProcessor> messageProcessorFactory;
	private ListenableFuture<?> containerTaskFuture;


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
					this.containerTaskFuture = this.taskExecutor.submitListenable(
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
			logger.info("Stopping container");
			if (isRunning()) {
				isRunning = false;

			}
			try {
				this.containerTaskFuture.get();
			} catch (InterruptedException | ExecutionException e) {
				logger.warn(e, "Exception while stopping consumer thread");
			}
			logger.info("Container is stopped");
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
			this.messageProcessor.process(pulsarMessage, consumer);
		}

		@Override
		public Object call() throws Exception {
			while (isRunning()) {
				try {
					processMessage();
				}
				catch (Exception e) {
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
