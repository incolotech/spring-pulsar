package org.incolo.springpulsar.config;

import org.apache.pulsar.client.api.*;
import org.apache.pulsar.shade.org.apache.commons.lang3.ArrayUtils;
import org.incolo.springpulsar.core.ContainerConfiguration;
import org.incolo.springpulsar.core.ContainerProperties;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DefaultConsumerFactory implements ConsumerFactory {

	private final PulsarClient client;

	public DefaultConsumerFactory(ClientFactory clientFactory) throws PulsarClientException {
		this(clientFactory.createClient());
	}

	public DefaultConsumerFactory(PulsarClient client) {
		this.client = client;
	}

	public Consumer<?> createConsumer(ContainerConfiguration containerConfiguration) throws PulsarClientException {
		PulsarListenerEndpoint<?> endpoint = containerConfiguration.getEndpoint();
		ContainerProperties properties = containerConfiguration.getContainerProperties();
		ConsumerBuilder<?> builder = this.client.newConsumer(endpoint.getSchemaProvider().getSchema());

		executeIfNonNull(endpoint.getConsumerName(), builder::consumerName);
		if (ArrayUtils.isNotEmpty(endpoint.getTopics())) {
			builder.topics(Arrays.asList(endpoint.getTopics()));
		}
		executeIfNonNull(endpoint.getTopicPattern(), builder::topicsPattern);
		executeIfNonNull(endpoint.getRegexSubscriptionMode(), builder::subscriptionTopicsMode);
		executeIfNonNull(endpoint.getSubscriptionName(), builder::subscriptionName);

		builder.subscriptionType(endpoint.getSubscriptionType());
		builder.subscriptionInitialPosition(endpoint.getSubscriptionInitialPosition());
		builder.priorityLevel(endpoint.getPriorityLevel());

		executeIfNonNull(properties.getAckTimeout(), builder::ackTimeout);
		executeIfNonNull(properties.getAckTimeoutTickTime(), builder::ackTimeoutTickTime);
		executeIfNonNull(properties.getNegativeAckRedeliveryDelay(), builder::negativeAckRedeliveryDelay);
		executeIfNonNull(properties.getMessageListener(), m -> builder.messageListener((MessageListener) m));
		executeIfNonNull(properties.getCryptoKeyReader(), builder::cryptoKeyReader);
		executeIfNonNull(properties.getMessageCrypto(), builder::messageCrypto);
		executeIfNonNull(properties.getConsumerCryptoFailureAction(), builder::cryptoFailureAction);
		executeIfNonNull(properties.getReceiverQueueSize(), builder::receiverQueueSize);
		executeIfNonNull(properties.getAcknowledgmentGroupTime(), builder::acknowledgmentGroupTime);
		executeIfNonNull(properties.getReplicateSubscriptionState(), builder::replicateSubscriptionState);
		executeIfNonNull(properties.getMaxTotalReceiverQueueSizeAcrossPartitions(), builder::maxTotalReceiverQueueSizeAcrossPartitions);
		executeIfNonNull(properties.getConsumerEventListener(), builder::consumerEventListener);
		executeIfNonNull(properties.getReadCompacted(), builder::readCompacted);
		executeIfNonNull(
				properties.getPatternAutoDiscoveryPeriod(),
				(d, timeUnit) -> builder.patternAutoDiscoveryPeriod(Math.toIntExact(d), timeUnit));


		if (!CollectionUtils.isEmpty(properties.getInterceptors())) {
			builder.intercept((ConsumerInterceptor[]) properties.getInterceptors().toArray());
		}

		executeIfNonNull(properties.getAutoUpdatePartitions(), builder::autoUpdatePartitions);
		executeIfNonNull(properties.getKeySharedPolicy(), builder::keySharedPolicy);
		executeIfNonNull(properties.getStartMessageIdInclusive(), b -> {
			if (b) {
				builder.startMessageIdInclusive();
			}
		});
		executeIfNonNull(properties.getBatchReceivePolicy(), builder::batchReceivePolicy);
		executeIfNonNull(properties.getRetryEnable(), builder::enableRetry);
		executeIfNonNull(properties.getMaxPendingChuckedMessage(), builder::maxPendingChuckedMessage);
		executeIfNonNull(properties.getAutoAckOldestChunkedMessageOnQueueFull(), builder::autoAckOldestChunkedMessageOnQueueFull);
		executeIfNonNull(properties.getExpireTimeOfIncompleteChunkedMessage(), builder::expireTimeOfIncompleteChunkedMessage);


		if (!endpoint.getProperties().isEmpty()) {
			builder.properties(endpoint.getProperties().entrySet().stream()
					.filter(e -> e.getKey() instanceof String && e.getValue() instanceof String)
					.collect(Collectors.toMap(t -> (String) t.getKey(), t -> (String) t.getValue())));
		}


		return builder.subscribe();
	}

	private static void executeIfNonNull(Duration duration, BiConsumer<Long, TimeUnit> consumer) {
		Optional.ofNullable(duration).ifPresent(
				d -> consumer.accept(duration.toNanos(), TimeUnit.NANOSECONDS)
		);
	}

	private static <T> void executeIfNonNull(T prop, java.util.function.Consumer<T> consumer) {
		Optional.ofNullable(prop).ifPresent(
				p -> consumer.accept(prop)
		);
	}
}
