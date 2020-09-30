package org.incolo.springpulsar.core;

import org.incolo.springpulsar.config.ConsumerFactory;
import org.incolo.springpulsar.config.PulsarListenerEndpoint;
import org.springframework.core.task.AsyncListenableTaskExecutor;

public abstract class AbstractPulsarListenerContainer implements PulsarListenerContainer {
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

}
