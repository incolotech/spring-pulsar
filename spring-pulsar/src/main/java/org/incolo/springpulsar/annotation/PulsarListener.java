package org.incolo.springpulsar.annotation;

import com.google.protobuf.GeneratedMessageV3;
import org.apache.pulsar.client.api.RegexSubscriptionMode;
import org.apache.pulsar.client.api.Schema;
import org.apache.pulsar.client.api.SubscriptionInitialPosition;
import org.apache.pulsar.client.api.SubscriptionType;
import org.incolo.springpulsar.config.SchemaProvider;
import org.incolo.springpulsar.core.Acknowledgement;
import org.incolo.springpulsar.core.PrimitiveTypeSchema;

import javax.validation.constraints.Null;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Charvak Patel
 */
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PulsarListener {
	String id() default "";

	String consumerName() default "";

	String containerFactory() default "";

	String[] topics() default {};

	String topicPattern() default "";

	RegexSubscriptionMode regexSubscriptionMode() default RegexSubscriptionMode.PersistentOnly;

	String subscriptionName() default "";

	SubscriptionType subscriptionType() default SubscriptionType.Exclusive;

	SubscriptionInitialPosition subscriptionInitialPosition() default SubscriptionInitialPosition.Earliest;

	PrimitiveTypeSchema primitiveTypeSchema() default PrimitiveTypeSchema.BYTES;

	Class<?> jsonSchema() default NullType.class;

	Class<? extends GeneratedMessageV3> protobufSchema() default NullType.class;

	Class<?> avroSchema() default NullType.class;

	Class<? extends SchemaProvider> schemaProviderBeanClass() default NullType.class;

	String schemaProviderBeanName() default "";

	boolean isAutoSchema() default false;

	Property[] properties() default {};

	/**
	 *	Specifies how to handle acknowledgements <br>
	 *	This value is not used if the user has one argument {@link Acknowledgement}, in that case the mode automatically becomes manual.
	 */
	AutoAckMode autoAckMode() default AutoAckMode.DEFAULT;
}
