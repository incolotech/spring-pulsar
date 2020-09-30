package org.incolo.springpulsar.core;

import org.incolo.springpulsar.config.PulsarListenerEndpoint;
import org.springframework.context.SmartLifecycle;

/**
 * @author Charvak Patel
 */
public interface PulsarListenerContainer extends SmartLifecycle {
	ContainerConfiguration getContainerConfiguration();
	PulsarListenerEndpoint<?> getPulsarListenerEndpoint();
}
