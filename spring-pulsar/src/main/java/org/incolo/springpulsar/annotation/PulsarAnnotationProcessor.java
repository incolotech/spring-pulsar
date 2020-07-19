package org.incolo.springpulsar.annotation;


import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;

public class PulsarAnnotationProcessor {

	Optional<PulsarListener> findClassLevelAnnotation(final Class<?> clz) {
		throw new UnsupportedOperationException();
	}

	Map<Method, PulsarListener> findMethodLevelAnnotations(final Class<?> clz) {
		throw new UnsupportedOperationException();
	}

	Optional<PulsarListener> findMethodLevelAnnotation(final Method method) {
		throw new UnsupportedOperationException();
	}

	Map<Method, PulsarHandler> findMethodLevelHandlerAnnotations(final Class<?> clz) {
		throw new UnsupportedOperationException();
	}

	Optional<PulsarHandler> findMethodLevelHandlerAnnotation(final Method method) {
		throw new UnsupportedOperationException();
	}
}
