package org.incolo.springpulsar.core;

import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.incolo.springpulsar.util.CollectionUtils;
import org.springframework.core.MethodParameter;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.invocation.InvocableHandlerMethod;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MessageProcessorUtils {

	private static final List<Type> SPRING_PULSAR_TYPES = Arrays.asList(Message.class, Consumer.class);

	public static Optional<Class<?>> inferPayloadType(InvocableHandlerMethod invocableHandlerMethod) throws PayloadTypeInferenceException {
		List<MethodParameter> parameters = Arrays.stream(invocableHandlerMethod.getMethodParameters())
				.filter(param -> SPRING_PULSAR_TYPES.stream()
						.noneMatch(rt -> parameterIsType(param.getGenericParameterType(), rt)))
				.filter(param -> param.getParameterAnnotations().length == 0
						|| param.hasParameterAnnotation(Payload.class))
				.collect(Collectors.toList());

		//if no payload type is found then return null. No type conversion will be needed in converters
		if (parameters.size() == 0) {
			return Optional.empty();
		}

		if (parameters.size() == 1) {
			return Optional.ofNullable(parameters.get(0))
					.map(MethodParameter::getParameterType);
		}
		//If more than one are qualifed, try to find one param with Payload annotation, if that is not the case then throw exception.
		MethodParameter ret = parameters.stream().filter(
				p -> p.hasParameterAnnotation(Payload.class)
		).collect(CollectionUtils.toSingletonOrThrow(
				l -> new PayloadTypeInferenceException(
						"Ambiguous parameters for target payload for method "
								+ invocableHandlerMethod.getMethod().getName(),
						l)
		));
		return Optional.ofNullable(ret).map(MethodParameter::getParameterType);
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
