package org.incolo.springpulsar.core;

import org.incolo.springpulsar.core.sample.TypeInferenceSample;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;
import org.springframework.messaging.handler.invocation.InvocableHandlerMethod;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

class MessageProcessorUtilsTest {

	private static MessageHandlerMethodFactory handlerMethodFactory = null;

	final TypeInferenceSample typeInferenceSample = new TypeInferenceSample();

	//All methods should have different names, no method overloading.
	private static Map<String, Method> methods = null;

	@BeforeAll
	public static void setUp() {
		handlerMethodFactory = SimpleMessageProcessorFactory.setUpMessageHandlerFactory(null);
		methods = Arrays.stream(TypeInferenceSample.class.getDeclaredMethods())
				.collect(Collectors.toMap(Method::getName, Function.identity()));
	}

	private InvocableHandlerMethod getHandlerMethod(String methodName) {
		return handlerMethodFactory.createInvocableHandlerMethod(typeInferenceSample, methods.get(methodName));
	}


	@Test
	public void shouldReturnTypeWhenReservedTypesArePresent() {
		Assertions.assertEquals(String.class,
				MessageProcessorUtils.inferPayloadType(getHandlerMethod("methodWithReservedTypes")).get());
	}

	@Test
	public void shouldReturnErrorIfMultipleTypesAreQualified() {
		Assertions.assertThrows(PayloadTypeInferenceException.class, () -> MessageProcessorUtils.inferPayloadType(getHandlerMethod("methodWithMultiplePayloadTypes")).get());
	}

	@Test
	public void shouldReturnTypeWhenPayloadAnnotationIsThere() {
		Assertions.assertEquals(Object.class,
				MessageProcessorUtils.inferPayloadType(getHandlerMethod("methodWithMultiplePayloadTypesWithSingleAnnotation")).get());
	}

	@Test
	public void shouldThrowErrorIfMultiplePayloadTypes() {
		Assertions.assertThrows(PayloadTypeInferenceException.class,
				() -> MessageProcessorUtils.inferPayloadType(getHandlerMethod("methodWithMultiplePayloadAnnotations")).get());
	}

	@Test
	public void shouldReturnEmptyIfNoPayloadIsThere() {
		Assertions.assertFalse(
				MessageProcessorUtils.inferPayloadType(getHandlerMethod("methodWithNoPayload")).isPresent());
	}
}