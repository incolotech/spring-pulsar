package org.incolo.springpulsar.config;

import org.apache.pulsar.client.api.*;
import org.apache.pulsar.shade.org.apache.commons.lang3.ArrayUtils;
import org.incolo.springpulsar.core.ContainerConfiguration;
import org.incolo.springpulsar.core.ContainerProperties;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.incolo.springpulsar.util.PropertyUtils.*;

public class DefaultConsumerFactory implements ConsumerFactory {

	private final PulsarClient client;

	public DefaultConsumerFactory(PulsarClientFactory pulsarClientFactory) throws PulsarClientException {
		this(pulsarClientFactory.createClient());
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

		executeIfNonNullLong(properties.getAckTimeout(), builder::ackTimeout);
		executeIfNonNullLong(properties.getAckTimeoutTickTime(), builder::ackTimeoutTickTime);
		executeIfNonNullLong(properties.getNegativeAckRedeliveryDelay(), builder::negativeAckRedeliveryDelay);
		executeIfNonNull(properties.getMessageListener(), m -> builder.messageListener((MessageListener) m));
		executeIfNonNull(properties.getCryptoKeyReader(), builder::cryptoKeyReader);
		executeIfNonNull(properties.getMessageCrypto(), builder::messageCrypto);
		executeIfNonNull(properties.getConsumerCryptoFailureAction(), builder::cryptoFailureAction);
		executeIfNonNull(properties.getReceiverQueueSize(), builder::receiverQueueSize);
		executeIfNonNullLong(properties.getAcknowledgmentGroupTime(), builder::acknowledgmentGroupTime);
		executeIfNonNull(properties.getReplicateSubscriptionState(), builder::replicateSubscriptionState);
		executeIfNonNull(properties.getMaxTotalReceiverQueueSizeAcrossPartitions(), builder::maxTotalReceiverQueueSizeAcrossPartitions);
		executeIfNonNull(properties.getConsumerEventListener(), builder::consumerEventListener);
		executeIfNonNull(properties.getReadCompacted(), builder::readCompacted);
		executeIfNonNullInt(properties.getPatternAutoDiscoveryPeriod(), builder::patternAutoDiscoveryPeriod);


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
		executeIfNonNullLong(properties.getExpireTimeOfIncompleteChunkedMessage(), builder::expireTimeOfIncompleteChunkedMessage);


		if (!endpoint.getProperties().isEmpty()) {
			builder.properties(endpoint.getProperties().entrySet().stream()
					.filter(e -> e.getKey() instanceof String && e.getValue() instanceof String)
					.collect(Collectors.toMap(t -> (String) t.getKey(), t -> (String) t.getValue())));
		}


		return builder.subscribe();
	}

}
