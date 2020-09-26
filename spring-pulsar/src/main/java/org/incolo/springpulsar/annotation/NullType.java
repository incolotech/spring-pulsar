package org.incolo.springpulsar.annotation;

import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Message;
import org.apache.pulsar.client.api.Schema;
import org.incolo.springpulsar.config.SchemaProvider;

/**
 * Used to specify null values for fields ( to make them optional ) in annotation with type class <br>
 * Example <br> {@code Class<?> jsonSchema() default NullType.class}
 */
public class NullType extends GeneratedMessageV3 implements SchemaProvider {
	@Override
	public Schema<Object> getSchema() {
		throw new UnsupportedOperationException("This should never get called. It is there to satisfy the contract");
	}

	@Override
	protected FieldAccessorTable internalGetFieldAccessorTable() {
		throw new UnsupportedOperationException("This should never get called. It is there to satisfy the contract");
	}

	@Override
	protected Message.Builder newBuilderForType(BuilderParent parent) {
		throw new UnsupportedOperationException("This should never get called. It is there to satisfy the contract");
	}

	@Override
	public Message.Builder newBuilderForType() {
		throw new UnsupportedOperationException("This should never get called. It is there to satisfy the contract");
	}

	@Override
	public Message.Builder toBuilder() {
		throw new UnsupportedOperationException("This should never get called. It is there to satisfy the contract");
	}

	@Override
	public Message getDefaultInstanceForType() {
		throw new UnsupportedOperationException("This should never get called. It is there to satisfy the contract");
	}
}
