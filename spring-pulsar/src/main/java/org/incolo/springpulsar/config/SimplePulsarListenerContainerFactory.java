package org.incolo.springpulsar.config;

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
public class SimplePulsarListenerContainerFactory implements PulsarListenerContainerFactory<SimplePulsarListenerContainer>, BeanFactoryAware, InitializingBean {

	private final ConsumerFactory consumerFactory;
	private SimpleAsyncTaskExecutor executor;
	private BeanFactory beanFactory;
	private MessageProcessorFactory<? extends MessageProcessor> defaultMessageProcessorFactory;
	private final MessageConverter messageConverter;
	private AutoAckMode autoAckMode = AutoAckMode.AUTO_ACK_NACK;

	public SimplePulsarListenerContainerFactory(ConsumerFactory consumerFactory) {
		this.consumerFactory = consumerFactory;
		this.messageConverter = new SimpleMessageConverter();
	}

	public SimplePulsarListenerContainerFactory(ConsumerFactory consumerFactory, MessageConverter messageConverter) {
		this.consumerFactory = consumerFactory;
		this.messageConverter = messageConverter;
	}


	@Override
	public SimplePulsarListenerContainer createListenerContainer(PulsarListenerEndpoint<?> endpoint) {
		return new SimplePulsarListenerContainer(new ContainerConfiguration(endpoint), consumerFactory, executor, defaultMessageProcessorFactory);
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	public void setAutoAckMode(AutoAckMode autoAckMode) {
		this.autoAckMode = autoAckMode;
	}

	public AutoAckMode getAutoAckMode() {
		return autoAckMode;
	}

	private ContainerConfiguration getContainerConfiguration(PulsarListenerEndpoint<?> endpoint) {
		return new ContainerConfiguration(endpoint)
				.setAutoAckMode(autoAckMode);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		executor = new SimpleAsyncTaskExecutor();
		defaultMessageProcessorFactory = new SimpleMessageProcessorFactory(this.beanFactory, this.messageConverter);
	}
}
