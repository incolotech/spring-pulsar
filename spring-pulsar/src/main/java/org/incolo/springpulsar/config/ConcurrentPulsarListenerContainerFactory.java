package org.incolo.springpulsar.config;

import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.SubscriptionType;
import org.incolo.springpulsar.annotation.AutoAckMode;
import org.incolo.springpulsar.core.*;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

/**
 * @author Charvak Patel
 */
public class ConcurrentPulsarListenerContainerFactory implements PulsarListenerContainerFactory<ConcurrentPulsarListenerContainer>, BeanFactoryAware, InitializingBean {

	private final ConsumerFactory consumerFactory;
	private SimpleAsyncTaskExecutor executor;
	private BeanFactory beanFactory;
	private MessageProcessorFactory<? extends MessageProcessor> defaultMessageProcessorFactory;
	private final MessageConverter messageConverter;
	private ContainerProperties containerProperties;

	public ConcurrentPulsarListenerContainerFactory(ClientFactory clientFactory, ContainerProperties containerProperties) throws PulsarClientException {
		this.consumerFactory = new DefaultConsumerFactory(clientFactory);
		this.containerProperties = containerProperties;
		this.messageConverter = new SimpleMessageConverter();
	}

	public ConcurrentPulsarListenerContainerFactory(ConsumerFactory consumerFactory, ContainerProperties containerProperties) {
		this.consumerFactory = consumerFactory;
		this.containerProperties = containerProperties;
		this.messageConverter = new SimpleMessageConverter();
	}

	public ConcurrentPulsarListenerContainerFactory(ConsumerFactory consumerFactory, ContainerProperties containerProperties, MessageConverter messageConverter) {
		this.consumerFactory = consumerFactory;
		this.containerProperties = containerProperties;
		this.messageConverter = messageConverter;
	}


	@Override
	public ConcurrentPulsarListenerContainer createListenerContainer(PulsarListenerEndpoint<?> endpoint) {
		if (endpoint.getConcurrency() < 1) {
			throw new SpringPulsarException("Concurrency can not be less than 1");
		}
		if (endpoint.getConcurrency() > 1 && endpoint.getSubscriptionType() == SubscriptionType.Exclusive) {
			throw new SpringPulsarException("Concurrency can not be greater than 1 if provided subscription type is Exclusive");
		}
		return new ConcurrentPulsarListenerContainer(getContainerConfiguration(endpoint), consumerFactory, executor, defaultMessageProcessorFactory);
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	private ContainerConfiguration getContainerConfiguration(PulsarListenerEndpoint<?> endpoint) {
		return new ContainerConfiguration(endpoint, containerProperties);
	}

	@Override
	public void afterPropertiesSet() {
		executor = new SimpleAsyncTaskExecutor();
		defaultMessageProcessorFactory = new SimpleMessageProcessorFactory(this.beanFactory, this.messageConverter);
	}


}
