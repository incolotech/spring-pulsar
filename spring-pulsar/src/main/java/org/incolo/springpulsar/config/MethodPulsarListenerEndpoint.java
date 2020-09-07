package org.incolo.springpulsar.config;

import org.apache.pulsar.client.api.RegexSubscriptionMode;
import org.apache.pulsar.client.api.SubscriptionInitialPosition;
import org.apache.pulsar.client.api.SubscriptionType;
import org.incolo.springpulsar.annotation.Property;

import java.lang.reflect.Method;
import java.util.Properties;

/**
 * @author Charvak Patel
 */
public class MethodPulsarListenerEndpoint<V> implements PulsarListenerEndpoint{

    private String id;

    private String consumerName;

    private String containerFactory;

    private String[] topics;

    private String topicPattern;

    private RegexSubscriptionMode regexSubscriptionMode;

    private String subscriptionName;

    private SubscriptionType subscriptionType;

    private SubscriptionInitialPosition subscriptionInitialPosition;

    private Properties properties;

    private Method method;

    private String beanName;

    private Object bean;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    @Override
    public String getContainerFactory() {
        return containerFactory;
    }

    public void setContainerFactory(String containerFactory) {
        this.containerFactory = containerFactory;
    }

    @Override
    public String[] getTopics() {
        return topics;
    }

    public void setTopics(String[] topics) {
        this.topics = topics;
    }

    @Override
    public String getTopicPattern() {
        return topicPattern;
    }

    public void setTopicPattern(String topicPattern) {
        this.topicPattern = topicPattern;
    }

    @Override
    public RegexSubscriptionMode getRegexSubscriptionMode() {
        return regexSubscriptionMode;
    }

    public void setRegexSubscriptionMode(RegexSubscriptionMode regexSubscriptionMode) {
        this.regexSubscriptionMode = regexSubscriptionMode;
    }

    @Override
    public String getSubscriptionName() {
        return subscriptionName;
    }

    public void setSubscriptionName(String subscriptionName) {
        this.subscriptionName = subscriptionName;
    }

    @Override
    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(SubscriptionType subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    @Override
    public SubscriptionInitialPosition getSubscriptionInitialPosition() {
        return subscriptionInitialPosition;
    }

    public void setSubscriptionInitialPosition(SubscriptionInitialPosition subscriptionInitialPosition) {
        this.subscriptionInitialPosition = subscriptionInitialPosition;
    }

    @Override
    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    @Override
    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    @Override
    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }
}
