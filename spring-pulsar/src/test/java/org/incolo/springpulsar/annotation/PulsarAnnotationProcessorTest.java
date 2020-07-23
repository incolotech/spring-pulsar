package org.incolo.springpulsar.annotation;

import org.incolo.springpulsar.annotation.sample.ClassLevelAnnotation;
import org.incolo.springpulsar.annotation.sample.MethodLevelKafkaListenerClass;
import org.incolo.springpulsar.annotation.sample.NoAnnotationClass;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PulsarAnnotationProcessorTest {


    @Test
    public void shouldReturnOptionalNull() {
        PulsarAnnotationProcessor sut = new PulsarAnnotationProcessor();
        assertTrue(sut.findClassLevelAnnotation(NoAnnotationClass.class).isEmpty());
        assertTrue(sut.findMethodLevelAnnotations(NoAnnotationClass.class).isEmpty());
        assertTrue(sut.findMethodLevelHandlerAnnotations(NoAnnotationClass.class).isEmpty());
    }

    @Test
    public void testClassLevelAnnotation() {
        PulsarAnnotationProcessor sut = new PulsarAnnotationProcessor();
        assertTrue(sut.findClassLevelAnnotation(ClassLevelAnnotation.class).isPresent());
        assertEquals(2, sut.findMethodLevelHandlerAnnotations(ClassLevelAnnotation.class).size());
        assertTrue(sut.findMethodLevelAnnotations(ClassLevelAnnotation.class).isEmpty());
    }

    @Test
    public void testMethodLevelAnnotatiions() {
        PulsarAnnotationProcessor sut = new PulsarAnnotationProcessor();
        assertEquals(2, sut.findMethodLevelAnnotations(MethodLevelKafkaListenerClass.class).size());
        assertTrue(sut.findMethodLevelHandlerAnnotations(MethodLevelKafkaListenerClass.class).isEmpty());
    }

}