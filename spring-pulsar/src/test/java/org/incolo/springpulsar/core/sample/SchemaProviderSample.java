package org.incolo.springpulsar.core.sample;

import org.incolo.springpulsar.annotation.PulsarListener;
import org.incolo.springpulsar.core.PrimitiveTypeSchema;

public class SchemaProviderSample {
	@PulsarListener(
			jsonSchema = Object.class,
			primitiveTypeSchema = PrimitiveTypeSchema.BOOLEAN
	)
	public void methodWithMultipleProperties() {

	}

	@PulsarListener
	public void methodWithNoFieldSet() {

	}

	@PulsarListener(
			primitiveTypeSchema = PrimitiveTypeSchema.DATE
	)
	public void methodWithPrimitiveTypeSet() {

	}

	@PulsarListener(
			jsonSchema = CustomData.class
	)
	public void methodWithJsonSchemaSet() {

	}

	@PulsarListener(
			avroSchema = CustomData.class
	)
	public void methodWithAvroSchemaSet() {

	}

	@PulsarListener(
			protobufSchema = CustomData.class
	)
	public void methodWithProtobufSchemaSet() {

	}

	@PulsarListener(
			isAutoSchema = true
	)
	public void methodWithAutoSchema() {

	}

}
