package org.incolo.springpulsar.config;

import org.apache.pulsar.shade.org.apache.commons.lang3.StringUtils;
import org.incolo.springpulsar.core.PulsarListenerContainer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.Lifecycle;
import org.springframework.context.SmartLifecycle;
import org.springframework.util.Assert;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Charvak Patel
 */
public class PulsarListenerContainerRegistry implements SmartLifecycle, BeanFactoryAware {

	public static final String PULSAR_LISTENER_CONTAINER_REGISTRY_BEAN_NAME = "org.incolo.springpulsar.annotation.DefaultPulsarListenerContainerRegistry";

	public static final String DEFAULT_CONTAINER_FACTORY_BEAN_NAME = "pulsarListenerContainerFactory";

	private final ConcurrentHashMap<String, PulsarListenerContainer> containers = new ConcurrentHashMap<>();

	private boolean isRunning;

	private String defaultContainerFactoryBeanName = DEFAULT_CONTAINER_FACTORY_BEAN_NAME;
	private BeanFactory beanFactory;

	@Override
	public void start() {
		containers.forEachValue(0, Lifecycle::start);
		isRunning = true;
	}

	@Override
	public void stop() {
		System.out.println("Stopping");
		containers.forEachValue(0, Lifecycle::stop);
		isRunning = false;
	}

	public void registerContainer(PulsarListenerEndpoint<?> endpoint) {
		PulsarListenerContainer container = resolveFactory(endpoint).createListenerContainer(endpoint);
		String id = container.getPulsarListenerEndpoint().getId();
		synchronized (this.containers) {
			Assert.state(!this.containers.containsKey(id),
					"Another endpoint is already registered with id '" + id + "'");

			this.containers.put(id, container);
			container.start();
		}
	}

	private PulsarListenerContainerFactory<?> resolveFactory(PulsarListenerEndpoint<?> endpoint) {
		if (StringUtils.isNotBlank(endpoint.getContainerFactory())) {
			return this.beanFactory.getBean(endpoint.getContainerFactory(), PulsarListenerContainerFactory.class);
		} else {
			return this.beanFactory.getBean(this.defaultContainerFactoryBeanName, PulsarListenerContainerFactory.class);
		}
	}

	@Override
	public boolean isRunning() {
		return isRunning;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	@Override
	public int getPhase() {
		return DEFAULT_PHASE - 100;
	}
}
