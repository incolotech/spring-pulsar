package org.incolo.springpulsar.core;

import org.incolo.springpulsar.annotation.PulsarAnnotationParser;
import org.incolo.springpulsar.config.PulsarListenerContainerRegistry;
import org.springframework.beans.factory.BeanFactory;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Charvak Patel
 */
public class MethodLevelPulsarListenerPipeline {

	private final PulsarAnnotationParser annotationParser = new PulsarAnnotationParser();
	private final PulsarAnnotationTransformer annotationTransformer;
	private final PulsarListenerContainerRegistry containerRegistry;

	public MethodLevelPulsarListenerPipeline(PulsarListenerContainerRegistry containerRegistry, BeanFactory beanFactory) {
		this.containerRegistry = containerRegistry;
		this.annotationTransformer = new PulsarAnnotationTransformer(beanFactory);
	}

	public void process(Object bean, String beanName) {
		Optional.ofNullable(bean)
				.map(Object::getClass)
				.map(annotationParser::findMethodLevelAnnotations)
				.map(Map::entrySet)
				.map(Collection::stream)
				.orElse(Stream.empty())
				.map(entry -> annotationTransformer.process(entry.getValue(), entry.getKey(), bean, beanName))
				.forEach(containerRegistry::registerContainer)
		;

	}
}
