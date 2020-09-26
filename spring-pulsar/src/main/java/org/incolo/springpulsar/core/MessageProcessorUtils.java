package org.incolo.springpulsar.core;

import org.apache.pulsar.client.api.Message;
import org.incolo.springpulsar.util.CollectionUtils;
import org.springframework.core.MethodParameter;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.invocation.InvocableHandlerMethod;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class MessageProcessorUtils {

	private static final List<Type> SPRING_PULSAR_TYPES = Collections.singletonList(Message.class);

	public static Class<?> inferPayloadType(InvocableHandlerMethod invocableHandlerMethod) {
		return Arrays.stream(invocableHandlerMethod.getMethodParameters())
				.filter(param -> SPRING_PULSAR_TYPES.stream()
						.noneMatch(rt -> parameterIsType(param.getGenericParameterType(), rt)))
				.filter(param -> param.getParameterAnnotations().length == 0
						|| param.hasParameterAnnotation(Payload.class))
				.collect(CollectionUtils.toSingletonOrThrow(
						l -> new SpringPulsarException("Ambiguous parameters for target payload for method " + invocableHandlerMethod.getMethod().getName()
								+ "; parameters in conflict : "
								+ l.stream().map(MethodParameter::getParameterName).reduce("", (s, s2) -> s + ", " + s2)
								+ "; no inferred type available)")
				))
				.getParameterType();
	}

	public static boolean parameterIsType(Type parameterType, Type type) {
		if (parameterType instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType) parameterType;
			Type rawType = parameterizedType.getRawType();
			if (rawType.equals(type)) {
				return true;
			}
		}
		return parameterType.equals(type);
	}
}
