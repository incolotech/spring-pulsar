package org.incolo.springpulsar.core;

import org.apache.pulsar.client.api.Message;

public interface PulsarHeaders {
	/**
	 * Important to use PREFIX before the constant <br>
	 * {@link Message#getProperties()} are dumped as is as header names.
	 * This prefix prevents the conflicts between header added by spring pulsar and the one coming from method above.
	 */
	String PREFIX = "springPulsar";

	String KEY = PREFIX + "key";
	String PRODUCER_NAME = PREFIX + "producerName";
	String EVENT_TIME = PREFIX + "eventTime";
	String ENCRYPTION_CTX = PREFIX + "encryptionCtx";
	String MESSAGE_ID = PREFIX + "messageId";
	String ORDERING_KEY = PREFIX + "orderingKey";
	String PUBLISH_TIME = PREFIX + "publishTime";
	String REDELIVERY_COUNT = PREFIX + "redeliveryCount";
	String REPLICATED_FROM = PREFIX + "replicatedFrom";
	String SCHEMA_VERSION = PREFIX + "schemaVersion";
	String SEQUENCE_ID = PREFIX + "sequenceId";

}
