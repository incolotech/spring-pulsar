package org.incolo.springpulsar.annotation.sample;

import org.incolo.springpulsar.annotation.PulsarListener;

public class MethodLevelKafkaListenerClass {

    @PulsarListener
    public void methodWithAnnotation1(Object payload) {

    }

    @PulsarListener
    public void methodWithAnnotation2(Object payload) {

    }
}
