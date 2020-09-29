package org.incolo.springpulsar.annotation;

import org.incolo.springpulsar.config.PulsarListenerContainerFactory;

/**
 * Specifies the nature of sending acknowledgement to the pulsar <br>
 */
public enum AutoAckMode {
	/**
	 * Same as specified in corresponding {@link PulsarListenerContainerFactory}
	 */
	DEFAULT,

	/**
	 * On successful processing, sends ack <br>
	 * On error, will send nack after calling error handler.
	 */
	AUTO_ACK_NACK,

	/**
	 * Regardless of processing result, will always send ack. <br>
	 * On error, will send ack after calling error handler.
	 */
	AUTO_ACK_ALL,
}
