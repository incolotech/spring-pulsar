package org.incolo.springpulsar.core;

import org.incolo.springpulsar.annotation.PulsarAnnotationParser;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Charvak Patel
 */
public class MethodLevelPulsarListenerPipeline {

	private PulsarAnnotationParser annotationParser = new PulsarAnnotationParser();
	private PulsarAnnotationTransformer annotationTransformer = new PulsarAnnotationTransformer();
	private final PulsarListenerContainerRegistry containerRegistry;

	public MethodLevelPulsarListenerPipeline(PulsarListenerContainerRegistry containerRegistry) {
		this.containerRegistry = containerRegistry;
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
