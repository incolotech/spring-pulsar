package org.incolo.springpulsar.core;

import org.springframework.context.Lifecycle;
import org.springframework.context.SmartLifecycle;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Charvak Patel
 */
public class PulsarListenerContainerRegistry implements SmartLifecycle {

    private ConcurrentHashMap<String, PulsarListenerContainer> listeners = new ConcurrentHashMap<>();

    private boolean isRunning;

    @Override
    public void start() {
        listeners.forEachValue(0, Lifecycle::start);
        isRunning = true;
    }

    @Override
    public void stop() {
        listeners.forEachValue(0, Lifecycle::stop);
        isRunning = false;
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }
}
