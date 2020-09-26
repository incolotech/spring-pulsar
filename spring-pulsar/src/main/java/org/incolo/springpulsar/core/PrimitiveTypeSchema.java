package org.incolo.springpulsar.core;


import org.apache.pulsar.client.api.Schema;

public enum PrimitiveTypeSchema {
	BYTES(Schema.BYTES),
	BYTEBUFFER(Schema.BYTEBUFFER),
	STRING(Schema.STRING),
	INT8(Schema.INT8),
	INT16(Schema.INT16),
	INT32(Schema.INT32),
	INT64(Schema.INT64),
	BOOLEAN(Schema.BOOL),
	FLOAT(Schema.FLOAT),
	DOUBLE(Schema.DOUBLE),
	DATE(Schema.DATE),
	TIME(Schema.TIME),
	TIMESTAMP(Schema.TIMESTAMP);
	private Schema<?> schema;

	PrimitiveTypeSchema(Schema<?> schema) {
		this.schema = schema;
	}

	public Schema<?> getSchema() {
		return schema;
	}
}
