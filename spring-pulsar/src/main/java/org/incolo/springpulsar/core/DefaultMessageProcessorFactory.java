package org.incolo.springpulsar.core;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.messaging.converter.GenericMessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

import java.lang.reflect.Method;

public class DefaultMessageProcessorFactory implements MessageProcessorFactory<BaseMessageProcessor> {

	private final MessageHandlerMethodFactory messageHandlerMethodFactory;
	private final MessageConverter messageConverter;

	public DefaultMessageProcessorFactory(BeanFactory beanFactory) {
		this.messageHandlerMethodFactory = setUpMessageHandlerFactory(beanFactory);
		this.messageConverter = new SimpleMessageConverter();
	}

	public DefaultMessageProcessorFactory(BeanFactory beanFactory, MessageConverter messageConverter) {
		this.messageHandlerMethodFactory = setUpMessageHandlerFactory(beanFactory);
		this.messageConverter = messageConverter;
	}

	@Override
	public BaseMessageProcessor createMessageProcessor(Object bean, Method method) {
		return new BaseMessageProcessor(
				this.messageHandlerMethodFactory.createInvocableHandlerMethod(bean, method),
				this.messageConverter
		);
	}

	public static MessageHandlerMethodFactory setUpMessageHandlerFactory(BeanFactory beanFactory) {
		DefaultMessageHandlerMethodFactory defaultFactory = new DefaultMessageHandlerMethodFactory();
		defaultFactory.setBeanFactory(beanFactory);
		DefaultConversionService defaultFormattingConversionService = new DefaultConversionService();
		defaultFactory.setConversionService(defaultFormattingConversionService);
		GenericMessageConverter messageConverter = new GenericMessageConverter(defaultFormattingConversionService);
		defaultFactory.setMessageConverter(messageConverter);
		defaultFactory.afterPropertiesSet();
		return defaultFactory;
	}
}
