package org.incolo.springpulsar.core;

import org.incolo.springpulsar.config.PulsarListenerEndpoint;

/**
 * @author Charvak Patel
 */
public class DefaultPulsarListenerContainer implements PulsarListenerContainer {

    private final PulsarListenerEndpoint endpoint;

    public DefaultPulsarListenerContainer(PulsarListenerEndpoint endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public void start() {
        System.out.println("Started");
    }

    @Override
    public void stop() {
        System.out.println("Started");
    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public void setUp() {

    }
}
