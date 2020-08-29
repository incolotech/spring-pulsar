package org.incolo.springpulsar.core;

import org.incolo.springpulsar.config.PulsarListenerEndpoint;

/**
 * @author Charvak Patel
 */
public class SimplePulsarListenerContainerFactory implements PulsarListenerContainerFactory<DefaultPulsarListenerContainer> {

    @Override
    public DefaultPulsarListenerContainer createListenerContainer(PulsarListenerEndpoint endpoint) {
        return new DefaultPulsarListenerContainer(endpoint);
    }
}
