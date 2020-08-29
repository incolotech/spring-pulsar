package org.incolo.springpulsar.core;

import org.springframework.context.SmartLifecycle;

/**
 * @author Charvak Patel
 */
public interface PulsarListenerContainer extends SmartLifecycle {

    void setUp();

}
