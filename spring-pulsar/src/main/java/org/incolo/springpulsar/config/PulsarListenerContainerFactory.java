package org.incolo.springpulsar.config;

import org.apache.pulsar.client.api.MessageListener;
import org.incolo.springpulsar.config.PulsarListenerEndpoint;
import org.incolo.springpulsar.core.PulsarListenerContainer;

/**
 * @author Charvak Patel
 */
public interface PulsarListenerContainerFactory<ContainerT extends PulsarListenerContainer> {

    ContainerT createListenerContainer(PulsarListenerEndpoint endpoint);
}
