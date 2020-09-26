package org.incolo.springpulsar.config;

import org.apache.pulsar.client.api.*;
import org.apache.pulsar.shade.org.apache.commons.lang3.ArrayUtils;
import org.apache.pulsar.shade.org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

public class DefaultConsumerFactory implements ConsumerFactory {

	private final PulsarClient client;

	public DefaultConsumerFactory(ClientFactory clientFactory) throws PulsarClientException {
		this(clientFactory.createClient());
	}

	public DefaultConsumerFactory(PulsarClient client) {
		this.client = client;
	}

	public Consumer<?> createConsumer(PulsarListenerEndpoint<?> endpoint) throws PulsarClientException {
		ConsumerBuilder<?> builder = this.client.newConsumer(endpoint.getSchemaProvider().getSchema());
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

		if (!endpoint.getProperties().isEmpty()) {
			builder.properties(endpoint.getProperties().entrySet().stream()
					.filter(e -> e.getKey() instanceof String && e.getValue() instanceof String)
					.collect(Collectors.toMap(t -> (String) t.getKey(), t -> (String) t.getValue())));
		}


		return builder.subscribe();
	}
}
