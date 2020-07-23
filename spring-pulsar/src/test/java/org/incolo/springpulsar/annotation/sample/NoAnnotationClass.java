package org.incolo.springpulsar.annotation.sample;

import org.incolo.springpulsar.annotation.PulsarListener;

public class NoAnnotationClass {

    @PulsarListener
    public void methodWithAnnotation1(Object payload) {

    }

    @PulsarListener
    public void methodWithAnnotation2(Object payload) {

    }

    public void methodWithoutAnnotation(Object payload) {

    }
}
