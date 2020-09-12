package org.incolo.springpulsar.core;

import org.incolo.springpulsar.config.PulsarListenerEndpoint;
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
	private DefaultMessageHandlerMethodFactory defaultMessageHandlerMethodFactory;

	public DefaultPulsarListenerContainerFactory(ConsumerFactory consumerFactory) {
		this.consumerFactory = consumerFactory;
	}


	@Override
	public DefaultPulsarListenerContainer createListenerContainer(PulsarListenerEndpoint endpoint) {
		return new DefaultPulsarListenerContainer(endpoint, consumerFactory, executor, this.defaultMessageHandlerMethodFactory);
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	public DefaultMessageHandlerMethodFactory setUpMessageHandlerFactory() {
		DefaultMessageHandlerMethodFactory defaultFactory = new DefaultMessageHandlerMethodFactory();
		defaultFactory.setBeanFactory(this.beanFactory);
		DefaultConversionService defaultFormattingConversionService = new DefaultConversionService();
		defaultFactory.setConversionService(defaultFormattingConversionService);
		GenericMessageConverter messageConverter = new GenericMessageConverter(defaultFormattingConversionService);
		defaultFactory.setMessageConverter(messageConverter);
		defaultFactory.afterPropertiesSet();
		return defaultFactory;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		executor = new SimpleAsyncTaskExecutor();
		defaultMessageHandlerMethodFactory = setUpMessageHandlerFactory();
	}
}
