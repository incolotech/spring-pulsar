package org.incolo.springpulsar.core;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.apache.pulsar.client.api.Consumer;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;

/**
 * Converts the incoming payload to the target inferredType payload type. If no inferredType is present, then it passes the payload as is. <br>
 * By default, it will use raw byte array to convert to the target object . <br>
 * Although there is an option to use decoded value ( as per schema specified, if the schema is ByteArray then either way is the same ) by turning on the d. <br>
 * If one chooses to do so, make sure that Schema is one of following {@link PrimitiveTypeSchema#BYTES}, {@link PrimitiveTypeSchema#BYTEBUFFER}, {@link PrimitiveTypeSchema#STRING}
 */
public class JsonMessageConverter implements MessageConverter {

	private final ObjectMapper objectMapper;
	private boolean useSchemaValue = false;

	public JsonMessageConverter(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	public JsonMessageConverter() {
		this.objectMapper = new ObjectMapper();
	}

	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}

	public boolean isUseSchemaValue() {
		return useSchemaValue;
	}

	public void setUseSchemaValue(boolean useSchemaValue) {
		this.useSchemaValue = useSchemaValue;
	}

	@Override
	public Message<?> toMessage(org.apache.pulsar.client.api.Message<?> message,
								Acknowledgement acknowledgment,
								Consumer<?> consumer,
								Type inferredType) throws UnsupportedTypeException, IOException {
		Object payload = isUseSchemaValue() ? message.getValue() : message.getData();
		Object convertedPayload = payload;
		if (inferredType != null) {
			JavaType javaType = TypeFactory.defaultInstance().constructType(inferredType);
			if (payload instanceof byte[]) {
				convertedPayload = objectMapper.readValue((byte[]) payload, javaType);
			} else if (payload instanceof ByteBuffer) {
				ByteBuffer byteBuffer = (ByteBuffer) payload;
				convertedPayload = objectMapper.readValue(byteBuffer.array(), javaType);
			} else if (payload instanceof String) {
				convertedPayload = objectMapper.readValue((String) payload, javaType);
			} else {
				throw new UnsupportedTypeException("Given type not supported in JsonMessageConverter", payload);
			}
		}
		return MessageBuilder.createMessage(convertedPayload, getMessageHeaders(message));
	}
}
