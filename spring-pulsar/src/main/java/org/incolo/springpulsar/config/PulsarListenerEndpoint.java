package org.incolo.springpulsar.config;

import org.apache.pulsar.client.api.RegexSubscriptionMode;
import org.apache.pulsar.client.api.SubscriptionInitialPosition;
import org.apache.pulsar.client.api.SubscriptionType;
import org.incolo.springpulsar.annotation.AutoAckMode;

import java.lang.reflect.Method;
import java.util.Properties;

/**
 * @author Charvak Patel
 */
public interface PulsarListenerEndpoint<V> {
    String getId();

    String getConsumerName();

    String getContainerFactory();

    String[] getTopics();

    String getTopicPattern();

    RegexSubscriptionMode getRegexSubscriptionMode();

    String getSubscriptionName();

    SubscriptionType getSubscriptionType();

    SubscriptionInitialPosition getSubscriptionInitialPosition();

    Properties getProperties();

    Method getMethod();

    String getBeanName();

    Object getBean();

	SchemaProvider getSchemaProvider();

	AutoAckMode getAutoAckMode();

	int getConcurrency();

	int getPriorityLevel();
}
