package org.incolo.springpulsar.config;

import org.incolo.springpulsar.annotation.AutoAckMode;
import org.incolo.springpulsar.core.*;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.messaging.converter.GenericMessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

/**
 * @author Charvak Patel
 */
public class DefaultPulsarListenerContainerFactory implements PulsarListenerContainerFactory<DefaultPulsarListenerContainer>, BeanFactoryAware, InitializingBean {

	private final ConsumerFactory consumerFactory;
	private SimpleAsyncTaskExecutor executor;
	private BeanFactory beanFactory;
	private MessageProcessorFactory<? extends MessageProcessor> defaultMessageProcessorFactory;
	private final MessageConverter messageConverter;
	private final AutoAckMode autoAckMode = AutoAckMode.AUTO_ACK_NACK;

	public DefaultPulsarListenerContainerFactory(ConsumerFactory consumerFactory) {
		this.consumerFactory = consumerFactory;
		this.messageConverter = new SimpleMessageConverter();
	}

	public DefaultPulsarListenerContainerFactory(ConsumerFactory consumerFactory, MessageConverter messageConverter) {
		this.consumerFactory = consumerFactory;
		this.messageConverter = messageConverter;
	}


	@Override
	public DefaultPulsarListenerContainer createListenerContainer(PulsarListenerEndpoint<?> endpoint) {
		return new DefaultPulsarListenerContainer(endpoint, consumerFactory, executor, defaultMessageProcessorFactory, getAutoAckMode());
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	public AutoAckMode getAutoAckMode() {
		return autoAckMode;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		executor = new SimpleAsyncTaskExecutor();
		defaultMessageProcessorFactory = new DefaultMessageProcessorFactory(this.beanFactory, this.messageConverter);
	}
}
