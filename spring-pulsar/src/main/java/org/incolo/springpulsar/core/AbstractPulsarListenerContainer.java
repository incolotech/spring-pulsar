package org.incolo.springpulsar.core;

import org.apache.commons.logging.LogFactory;
import org.apache.pulsar.client.api.PulsarClientException;
import org.incolo.springpulsar.config.ConsumerFactory;
import org.incolo.springpulsar.config.PulsarListenerEndpoint;
import org.springframework.core.log.LogAccessor;
import org.springframework.core.task.AsyncListenableTaskExecutor;

public abstract class AbstractPulsarListenerContainer implements PulsarListenerContainer {
	static final LogAccessor logger = new LogAccessor(LogFactory.getLog(AbstractPulsarListenerContainer.class));

	protected final ConsumerFactory consumerFactory;
	protected final AsyncListenableTaskExecutor taskExecutor;
	protected final MessageProcessorFactory<? extends MessageProcessor> messageProcessorFactory;
	protected final ContainerConfiguration containerConfiguration;

	protected volatile boolean isRunning = false;
	protected final Object lifecycleMonitor = new Object();


	protected AbstractPulsarListenerContainer(
			ContainerConfiguration containerConfiguration,
			ConsumerFactory consumerFactory,
			AsyncListenableTaskExecutor taskExecutor,
			MessageProcessorFactory<? extends MessageProcessor> messageProcessorFactory) {
		this.containerConfiguration = containerConfiguration;
		this.consumerFactory = consumerFactory;
		this.taskExecutor = taskExecutor;
		this.messageProcessorFactory = messageProcessorFactory;
	}

	@Override
	public ContainerConfiguration getContainerConfiguration() {
		return containerConfiguration;
	}

	public PulsarListenerEndpoint<?> getPulsarListenerEndpoint() {
		return containerConfiguration.getEndpoint();
	}

	@Override
	public boolean isRunning() {
		return this.isRunning;
	}

	@Override
	public void start() {
		synchronized (this.lifecycleMonitor) {
			if (!isRunning()) {
				try {
					this.isRunning = true;
					doStart();
				} catch (SpringPulsarException e) {
					logger.error(e, "Error while starting the container");
					throw e;
				}
			}
		}
	}

	protected abstract void doStart() throws SpringPulsarException;

	@Override
	public void stop() {
		synchronized (this.lifecycleMonitor) {
			logger.info("Stopping container");
			if (isRunning()) {
				isRunning = false;
				try {
					doStop();
				} catch (SpringPulsarException e) {
					logger.warn(e, "Error while stopping the container");
				}

			}
			logger.info("Container stopped");
		}
	}

	protected abstract void doStop() throws SpringPulsarException;
}
