package org.incolo.springpulsar.core;

import org.apache.pulsar.client.api.*;
import org.incolo.springpulsar.annotation.AutoAckMode;

import java.time.Duration;
import java.util.List;
import java.util.Map;

/**
 * @author Charvak Patel
 */
public class ContainerProperties {
	private AutoAckMode autoAckMode;
	private Duration ackTimeout;
	private Duration ackTimeoutTickTime;
	private Duration negativeAckRedeliveryDelay;
	private MessageListener<?> messageListener;
	private CryptoKeyReader cryptoKeyReader;
	private MessageCrypto<?, ?> messageCrypto;
	private ConsumerCryptoFailureAction consumerCryptoFailureAction;
	private Integer receiverQueueSize;
	private Duration acknowledgmentGroupTime;
	private Boolean replicateSubscriptionState;
	private Integer maxTotalReceiverQueueSizeAcrossPartitions;
	private ConsumerEventListener consumerEventListener;
	private Boolean readCompacted;
	private Duration patternAutoDiscoveryPeriod;
	private Map<String, String> properties;
	private List<ConsumerInterceptor<?>> interceptors;
	private Boolean autoUpdatePartitions;
	private KeySharedPolicy keySharedPolicy;
	private Boolean startMessageIdInclusive;
	private BatchReceivePolicy batchReceivePolicy;
	private Boolean retryEnable;
	private Integer maxPendingChuckedMessage;
	private Boolean autoAckOldestChunkedMessageOnQueueFull;
	private Duration expireTimeOfIncompleteChunkedMessage;

	public AutoAckMode getAutoAckMode() {
		return autoAckMode;
	}

	public ContainerProperties setAutoAckMode(AutoAckMode autoAckMode) {
		this.autoAckMode = autoAckMode;
		return this;
	}

	public Duration getAckTimeout() {
		return ackTimeout;
	}

	public ContainerProperties setAckTimeout(Duration ackTimeout) {
		this.ackTimeout = ackTimeout;
		return this;
	}

	public Duration getAckTimeoutTickTime() {
		return ackTimeoutTickTime;
	}

	public ContainerProperties setAckTimeoutTickTime(Duration ackTimeoutTickTime) {
		this.ackTimeoutTickTime = ackTimeoutTickTime;
		return this;
	}

	public Duration getNegativeAckRedeliveryDelay() {
		return negativeAckRedeliveryDelay;
	}

	public ContainerProperties setNegativeAckRedeliveryDelay(Duration negativeAckRedeliveryDelay) {
		this.negativeAckRedeliveryDelay = negativeAckRedeliveryDelay;
		return this;
	}

	public MessageListener<?> getMessageListener() {
		return messageListener;
	}

	public ContainerProperties setMessageListener(MessageListener<?> messageListener) {
		this.messageListener = messageListener;
		return this;
	}

	public CryptoKeyReader getCryptoKeyReader() {
		return cryptoKeyReader;
	}

	public ContainerProperties setCryptoKeyReader(CryptoKeyReader cryptoKeyReader) {
		this.cryptoKeyReader = cryptoKeyReader;
		return this;
	}

	public MessageCrypto<?, ?> getMessageCrypto() {
		return messageCrypto;
	}

	public ContainerProperties setMessageCrypto(MessageCrypto<?, ?> messageCrypto) {
		this.messageCrypto = messageCrypto;
		return this;
	}

	public ConsumerCryptoFailureAction getConsumerCryptoFailureAction() {
		return consumerCryptoFailureAction;
	}

	public ContainerProperties setConsumerCryptoFailureAction(ConsumerCryptoFailureAction consumerCryptoFailureAction) {
		this.consumerCryptoFailureAction = consumerCryptoFailureAction;
		return this;
	}

	public Integer getReceiverQueueSize() {
		return receiverQueueSize;
	}

	public ContainerProperties setReceiverQueueSize(Integer receiverQueueSize) {
		this.receiverQueueSize = receiverQueueSize;
		return this;
	}

	public Duration getAcknowledgmentGroupTime() {
		return acknowledgmentGroupTime;
	}

	public ContainerProperties setAcknowledgmentGroupTime(Duration acknowledgmentGroupTime) {
		this.acknowledgmentGroupTime = acknowledgmentGroupTime;
		return this;
	}

	public Boolean getReplicateSubscriptionState() {
		return replicateSubscriptionState;
	}

	public ContainerProperties setReplicateSubscriptionState(Boolean replicateSubscriptionState) {
		this.replicateSubscriptionState = replicateSubscriptionState;
		return this;
	}

	public Integer getMaxTotalReceiverQueueSizeAcrossPartitions() {
		return maxTotalReceiverQueueSizeAcrossPartitions;
	}

	public ContainerProperties setMaxTotalReceiverQueueSizeAcrossPartitions(Integer maxTotalReceiverQueueSizeAcrossPartitions) {
		this.maxTotalReceiverQueueSizeAcrossPartitions = maxTotalReceiverQueueSizeAcrossPartitions;
		return this;
	}

	public ConsumerEventListener getConsumerEventListener() {
		return consumerEventListener;
	}

	public ContainerProperties setConsumerEventListener(ConsumerEventListener consumerEventListener) {
		this.consumerEventListener = consumerEventListener;
		return this;
	}

	public Boolean getReadCompacted() {
		return readCompacted;
	}

	public ContainerProperties setReadCompacted(Boolean readCompacted) {
		this.readCompacted = readCompacted;
		return this;
	}

	public Duration getPatternAutoDiscoveryPeriod() {
		return patternAutoDiscoveryPeriod;
	}

	public ContainerProperties setPatternAutoDiscoveryPeriod(Duration patternAutoDiscoveryPeriod) {
		this.patternAutoDiscoveryPeriod = patternAutoDiscoveryPeriod;
		return this;
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	public ContainerProperties setProperties(Map<String, String> properties) {
		this.properties = properties;
		return this;
	}

	public List<ConsumerInterceptor<?>> getInterceptors() {
		return interceptors;
	}

	public ContainerProperties setInterceptors(List<ConsumerInterceptor<?>> interceptors) {
		this.interceptors = interceptors;
		return this;
	}

	public Boolean getAutoUpdatePartitions() {
		return autoUpdatePartitions;
	}

	public ContainerProperties setAutoUpdatePartitions(Boolean autoUpdatePartitions) {
		this.autoUpdatePartitions = autoUpdatePartitions;
		return this;
	}

	public KeySharedPolicy getKeySharedPolicy() {
		return keySharedPolicy;
	}

	public ContainerProperties setKeySharedPolicy(KeySharedPolicy keySharedPolicy) {
		this.keySharedPolicy = keySharedPolicy;
		return this;
	}

	public Boolean getStartMessageIdInclusive() {
		return startMessageIdInclusive;
	}

	public ContainerProperties setStartMessageIdInclusive(Boolean startMessageIdInclusive) {
		this.startMessageIdInclusive = startMessageIdInclusive;
		return this;
	}

	public BatchReceivePolicy getBatchReceivePolicy() {
		return batchReceivePolicy;
	}

	public ContainerProperties setBatchReceivePolicy(BatchReceivePolicy batchReceivePolicy) {
		this.batchReceivePolicy = batchReceivePolicy;
		return this;
	}

	public Boolean getRetryEnable() {
		return retryEnable;
	}

	public ContainerProperties setRetryEnable(Boolean retryEnable) {
		this.retryEnable = retryEnable;
		return this;
	}


	public Integer getMaxPendingChuckedMessage() {
		return maxPendingChuckedMessage;
	}

	public ContainerProperties setMaxPendingChuckedMessage(Integer maxPendingChuckedMessage) {
		this.maxPendingChuckedMessage = maxPendingChuckedMessage;
		return this;
	}

	public Boolean getAutoAckOldestChunkedMessageOnQueueFull() {
		return autoAckOldestChunkedMessageOnQueueFull;
	}

	public ContainerProperties setAutoAckOldestChunkedMessageOnQueueFull(Boolean autoAckOldestChunkedMessageOnQueueFull) {
		this.autoAckOldestChunkedMessageOnQueueFull = autoAckOldestChunkedMessageOnQueueFull;
		return this;
	}

	public Duration getExpireTimeOfIncompleteChunkedMessage() {
		return expireTimeOfIncompleteChunkedMessage;
	}

	public ContainerProperties setExpireTimeOfIncompleteChunkedMessage(Duration expireTimeOfIncompleteChunkedMessage) {
		this.expireTimeOfIncompleteChunkedMessage = expireTimeOfIncompleteChunkedMessage;
		return this;
	}
}
