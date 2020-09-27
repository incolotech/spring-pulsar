package org.incolo.springpulsar.annotation;

import org.apache.pulsar.client.api.Schema;
import org.incolo.springpulsar.config.SchemaProvider;
import org.incolo.springpulsar.core.PrimitiveTypeSchema;
import org.springframework.stereotype.Component;

@Component(value = "customSchemaProvider")
public class CustomSchemaProvider implements SchemaProvider {
	@Override
	public Schema<?> getSchema() {
		return PrimitiveTypeSchema.STRING.getSchema();
	}
}
