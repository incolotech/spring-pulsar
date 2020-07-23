package org.incolo.springpulsar.annotation.sample;

import org.incolo.springpulsar.annotation.PulsarHandler;
import org.incolo.springpulsar.annotation.PulsarListener;

@PulsarListener
public class ClassLevelAnnotation {

    @PulsarHandler
    public void methodWithAnnotation1(Object payload) {

    }

    @PulsarHandler
    public void methodWithAnnotation2(Object payload) {

    }

    public void methodWithoutAnnotation(Object payload) {

    }
}
