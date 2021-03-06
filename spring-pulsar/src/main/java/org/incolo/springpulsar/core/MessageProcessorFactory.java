package org.incolo.springpulsar.core;

import org.incolo.springpulsar.annotation.AutoAckMode;

import java.lang.reflect.Method;

public interface MessageProcessorFactory<T extends MessageProcessor> {
	T createMessageProcessor(Object bean, Method method, AutoAckMode autoAckMode);
}
