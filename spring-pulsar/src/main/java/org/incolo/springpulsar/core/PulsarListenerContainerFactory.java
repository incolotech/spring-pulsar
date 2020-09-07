package org.incolo.springpulsar.core;

import org.apache.pulsar.client.api.MessageListener;
import org.incolo.springpulsar.config.PulsarListenerEndpoint;

/**
 * @author Charvak Patel
 */
public interface PulsarListenerContainerFactory<ContainerT extends PulsarListenerContainer> {

    ContainerT createListenerContainer(PulsarListenerEndpoint endpoint);
}
