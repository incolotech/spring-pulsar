package org.incolo.springpulsar.annotation;


import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;

public class PulsarAnnotationProcessor {

	public Optional<PulsarListener> findClassLevelAnnotation(final Class<?> clz) {
		return Optional.ofNullable(clz)
				.map(c -> AnnotatedElementUtils.findMergedAnnotation(clz, PulsarListener.class));
	}

	public Map<Method, PulsarListener> findMethodLevelAnnotations(final Class<?> clz) {
		final MethodIntrospector.MetadataLookup<PulsarListener> selectorFunc =
				method -> findMethodLevelAnnotation(method).orElse(null); //Returning null will not include entry regarding that method in the return map
		return Optional.ofNullable(clz)
				.map(c -> MethodIntrospector.selectMethods(c, selectorFunc))
				.orElse(null);
	}

	public Optional<PulsarListener> findMethodLevelAnnotation(final Method method) {
		return Optional.ofNullable(method)
				.map(m -> AnnotatedElementUtils.findMergedAnnotation(m, PulsarListener.class));
	}

	public Map<Method, PulsarHandler> findMethodLevelHandlerAnnotations(final Class<?> clz) {
		final MethodIntrospector.MetadataLookup<PulsarHandler> selectorFunc =
				method -> findMethodLevelHandlerAnnotation(method).orElse(null); //Returning null will not include entry regarding that method in the return map
		return Optional.ofNullable(clz)
				.map(c -> MethodIntrospector.selectMethods(c, selectorFunc))
				.orElse(null);
	}

	public Optional<PulsarHandler> findMethodLevelHandlerAnnotation(final Method method) {
		return Optional.ofNullable(method)
				.map(m -> AnnotatedElementUtils.findMergedAnnotation(m, PulsarHandler.class));
	}
}
