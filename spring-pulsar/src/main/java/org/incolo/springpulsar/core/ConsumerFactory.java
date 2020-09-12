package org.incolo.springpulsar.core;

import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.ConsumerBuilder;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.shade.org.apache.commons.lang3.ArrayUtils;
import org.apache.pulsar.shade.org.apache.commons.lang3.StringUtils;
import org.incolo.springpulsar.config.PulsarListenerEndpoint;
import org.springframework.messaging.handler.invocation.InvocableHandlerMethod;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ConsumerFactory {

	final PulsarClient client;

	public ConsumerFactory(PulsarClient client) {
		this.client = client;
	}

	public Consumer<byte[]> createConsumer(PulsarListenerEndpoint<?> endpoint) throws PulsarClientException {
		ConsumerBuilder<byte[]> builder = this.client.newConsumer();

		if (StringUtils.isNotBlank(endpoint.getConsumerName())) {
			builder.consumerName(endpoint.getConsumerName());
		}
		if (ArrayUtils.isNotEmpty(endpoint.getTopics())) {
			builder.topics(Arrays.asList(endpoint.getTopics()));
		}
		if (StringUtils.isNotBlank(endpoint.getTopicPattern())) {
			builder.topicsPattern(endpoint.getTopicPattern());
		}
		if (endpoint.getRegexSubscriptionMode() != null) {
			builder.subscriptionTopicsMode(endpoint.getRegexSubscriptionMode());
		}
		if (StringUtils.isNotBlank(endpoint.getSubscriptionName())) {
			builder.subscriptionName(endpoint.getSubscriptionName());
		}
		builder.subscriptionType(endpoint.getSubscriptionType());
		builder.subscriptionInitialPosition(endpoint.getSubscriptionInitialPosition());

		builder.properties(endpoint.getProperties().entrySet().stream()
				.filter(e -> e.getKey() instanceof String && e.getValue() instanceof String)
				.collect(Collectors.toMap(t -> (String) t.getKey(), t -> (String) t.getValue())));


		return builder.subscribe();
	}
}
