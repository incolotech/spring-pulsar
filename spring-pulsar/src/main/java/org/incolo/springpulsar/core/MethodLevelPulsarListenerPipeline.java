package org.incolo.springpulsar.core;

import org.incolo.springpulsar.annotation.PulsarAnnotationProcessor;

import java.awt.image.ImageProducer;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Charvak Patel
 */
public class MethodLevelPulsarListenerPipeline {

    private PulsarAnnotationProcessor annotationProcessor = new PulsarAnnotationProcessor();
    private PulsarAnnotationTransformer annotationTransformer = new PulsarAnnotationTransformer();

    public void process(Object bean, String beanName) {
        Optional.ofNullable(bean)
                .map(Object::getClass)
                .map(annotationProcessor::findMethodLevelAnnotations)
                .stream()
                .flatMap(mappings -> mappings.entrySet().stream())
                .map(entry -> annotationTransformer.process(entry.getValue(), entry.getKey(), bean, beanName))
                .forEach(
                        x -> System.out.println(x)
                );
        ;

    }
}
