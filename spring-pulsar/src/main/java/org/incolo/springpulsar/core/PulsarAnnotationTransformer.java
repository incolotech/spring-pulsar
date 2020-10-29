package org.incolo.springpulsar.core;

import org.apache.pulsar.shade.org.apache.commons.lang3.StringUtils;
import org.incolo.springpulsar.annotation.Property;
import org.incolo.springpulsar.annotation.PulsarListener;
import org.incolo.springpulsar.config.MethodPulsarListenerEndpoint;
import org.incolo.springpulsar.config.PulsarListenerEndpoint;
import org.incolo.springpulsar.util.SpringUtils;
import org.springframework.beans.factory.BeanFactory;

import java.lang.reflect.Method;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Charvak Patel
 */
public class PulsarAnnotationTransformer {

	private final AtomicInteger counter = new AtomicInteger();

	public static final String ID_PREFIX = "org.incolo.springpulsar.core.PulsarAnnotationTransformer#";

	private final SchemaProviderFactory schemaProviderFactory;

	public PulsarAnnotationTransformer(BeanFactory beanFactory) {
		this.schemaProviderFactory = new SchemaProviderFactory(beanFactory);
	}

	/**
	 * For the case when a method is annotated with {@link PulsarListener}
	 */
	public PulsarListenerEndpoint<?> process(final PulsarListener annotation, final Method method, final Object bean, final String beanName) {
		Method methodToUse = SpringUtils.checkProxy(method, bean);
		MethodPulsarListenerEndpoint<?> endpoint = new MethodPulsarListenerEndpoint<>();
		endpoint.setMethod(methodToUse);
		endpoint.setBean(bean);
		endpoint.setBeanName(beanName);

		endpoint.setId(StringUtils.isBlank(annotation.id()) ? ID_PREFIX + counter.incrementAndGet() : annotation.id());
		endpoint.setConsumerName(annotation.consumerName());
		endpoint.setContainerFactory(annotation.containerFactory());
		endpoint.setTopics(annotation.topics());
		endpoint.setTopicPattern(annotation.topicPattern());
		endpoint.setRegexSubscriptionMode(annotation.regexSubscriptionMode());
		endpoint.setSubscriptionName(annotation.subscriptionName());
		endpoint.setSubscriptionType(annotation.subscriptionType());
		endpoint.setSubscriptionInitialPosition(annotation.subscriptionInitialPosition());
		endpoint.setSchemaProvider(schemaProviderFactory.getSchemaProvider(annotation));
		endpoint.setAutoAckMode(annotation.autoAckMode());
		endpoint.setConcurrency(annotation.concurrency());

		Properties properties = new Properties();
		properties.putAll(Stream.of(annotation.properties())
				.collect(Collectors.toMap(Property::key, Property::value)));
		endpoint.setProperties(properties);
		return endpoint;
	}
}
