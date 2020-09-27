package org.incolo.springpulsar.core;

import org.apache.pulsar.client.api.Schema;
import org.apache.pulsar.common.schema.SchemaInfo;
import org.apache.pulsar.common.schema.SchemaType;
import org.incolo.springpulsar.annotation.PulsarAnnotationParser;
import org.incolo.springpulsar.annotation.PulsarListener;
import org.incolo.springpulsar.core.sample.SchemaProviderSample;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.BeanFactory;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class SchemaProviderFactoryTest {

	@Mock
	BeanFactory beanFactory;

	@InjectMocks
	SchemaProviderFactory providerFactory = null;
	private PulsarAnnotationParser annotationParser = new PulsarAnnotationParser();

	void setBeanFactoryReturn(Object returnValue) {
		when(beanFactory.getBean(any(String.class))).thenReturn(returnValue);
		when(beanFactory.getBean(any(Class.class))).thenReturn(returnValue);
	}

	@Test
	void shouldThrowErrorIfMultiplePropertiesAreSet() throws NoSuchMethodException {
		Assertions.assertThrows(SpringPulsarException.class,
				() -> providerFactory.getSchemaProvider(getAnnotationForMethod("methodWithMultipleProperties")));
	}

	@Test
	void shouldReturnBytesIfNothingIsSet() throws NoSuchMethodException {
		Assertions.assertEquals(SchemaType.BYTES,
				 providerFactory.getSchemaProvider(getAnnotationForMethod("methodWithNoFieldSet")).getSchema().getSchemaInfo().getType());
	}

	@Test
	void shouldReturnPrimitiveType() throws NoSuchMethodException {
		Assertions.assertEquals(SchemaType.DATE,
				providerFactory.getSchemaProvider(getAnnotationForMethod("methodWithPrimitiveTypeSet")).getSchema().getSchemaInfo().getType());
	}

	@Test
	void shouldReturnJsonSchema() throws NoSuchMethodException {
		Assertions.assertEquals(SchemaType.JSON,
				providerFactory.getSchemaProvider(getAnnotationForMethod("methodWithJsonSchemaSet")).getSchema().getSchemaInfo().getType());
	}

	@Test
	void shouldReturnAvroSchema() throws NoSuchMethodException {
		Assertions.assertEquals(SchemaType.AVRO,
				providerFactory.getSchemaProvider(getAnnotationForMethod("methodWithAvroSchemaSet")).getSchema().getSchemaInfo().getType());
	}

	//TODO investigate issue with this test
//	@Test
//	void shouldReturnProtobufSchema() throws NoSuchMethodException {
//		Assertions.assertEquals(SchemaType.PROTOBUF,
//				providerFactory.getSchemaProvider(getAnnotationForMethod("methodWithProtobufSchemaSet")).getSchema().getSchemaInfo().getType());
//	}

	@Test
	void shouldReturnAutoSchema() throws NoSuchMethodException {
		Assertions.assertNull(
				providerFactory.getSchemaProvider(getAnnotationForMethod("methodWithAutoSchema")).getSchema().getSchemaInfo());
	}

	public PulsarListener getAnnotationForMethod(String methodName) throws NoSuchMethodException {
		return annotationParser.findMethodLevelAnnotation(SchemaProviderSample.class.getMethod(methodName))
				.orElseThrow(() -> new IllegalArgumentException("Method not present"));
	}
}