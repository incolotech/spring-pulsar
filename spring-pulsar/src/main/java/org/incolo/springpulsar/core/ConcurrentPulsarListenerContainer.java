package org.incolo.springpulsar.core;

import org.incolo.springpulsar.config.ConsumerFactory;
import org.springframework.core.task.AsyncListenableTaskExecutor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Charvak Patel
 */
public class ConcurrentPulsarListenerContainer extends AbstractPulsarListenerContainer {

	private final List<SimplePulsarListenerContainer> containers = new ArrayList<>();

	public ConcurrentPulsarListenerContainer(ContainerConfiguration containerConfiguration,
											 ConsumerFactory consumerFactory,
											 AsyncListenableTaskExecutor taskExecutor,
											 MessageProcessorFactory<? extends MessageProcessor> messageProcessorFactory) {
		super(containerConfiguration, consumerFactory, taskExecutor, messageProcessorFactory);
	}

	private int getConcurrency() {
		return getPulsarListenerEndpoint().getConcurrency();
	}

	@Override
	protected void doStart() throws SpringPulsarException {
		for (int i = 0; i < getConcurrency(); i++) {
			SimplePulsarListenerContainer container = new SimplePulsarListenerContainer(
					containerConfiguration,
					consumerFactory,
					taskExecutor,
					messageProcessorFactory
			);
			container.start();
			containers.add(container);
		}
	}

	@Override
	protected void doStop() throws SpringPulsarException {
		containers.parallelStream().forEach(AbstractPulsarListenerContainer::stop);
		containers.clear();
	}

}
