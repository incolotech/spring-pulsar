package org.incolo.springpulsar.annotation;

import org.apache.pulsar.client.api.RegexSubscriptionMode;
import org.apache.pulsar.client.api.SubscriptionInitialPosition;
import org.apache.pulsar.client.api.SubscriptionType;

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

    Property[] properties() default {};
}
