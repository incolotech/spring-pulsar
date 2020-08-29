package org.incolo.springpulsar.core;

import org.incolo.springpulsar.annotation.Property;
import org.incolo.springpulsar.annotation.PulsarListener;
import org.incolo.springpulsar.config.MethodPulsarListenerEndpoint;
import org.incolo.springpulsar.config.PulsarListenerEndpoint;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Charvak Patel
 */
public class PulsarAnnotationTransformer {

    /**
     * For the case when a method is annotated with {@link PulsarListener}
     */
    public PulsarListenerEndpoint<?> process(PulsarListener annotation, Method method, Object bean, String beanName) {
        Method methodToUse = checkProxy(method, bean);
        MethodPulsarListenerEndpoint<?> endpoint = new MethodPulsarListenerEndpoint<>();
        endpoint.setMethod(methodToUse);
        endpoint.setBean(bean);
        endpoint.setBeanName(beanName);

        endpoint.setId(annotation.id());
        endpoint.setConsumerName(annotation.consumerName());
        endpoint.setContainerFactory(annotation.containerFactory());
        endpoint.setTopics(annotation.topics());
        endpoint.setTopicPattern(annotation.topicPattern());
        endpoint.setRegexSubscriptionMode(annotation.regexSubscriptionMode());
        endpoint.setSubscriptionName(annotation.subscriptionName());
        endpoint.setSubscriptionType(annotation.subscriptionType());
        endpoint.setSubscriptionInitialPosition(annotation.subscriptionInitialPosition());

        Properties properties = new Properties();
        properties.putAll(Stream.of(annotation.properties())
                .collect(Collectors.toMap(Property::key, Property::value)));
        endpoint.setProperties(properties);
        return endpoint;
    }

    private Method checkProxy(Method methodArg, Object bean) {
        Method method = methodArg;
        if (AopUtils.isJdkDynamicProxy(bean)) {
            try {
                // Found a @KafkaListener method on the target class for this JDK proxy ->
                // is it also present on the proxy itself?
                method = bean.getClass().getMethod(method.getName(), method.getParameterTypes());
                Class<?>[] proxiedInterfaces = ((Advised) bean).getProxiedInterfaces();
                for (Class<?> iface : proxiedInterfaces) {
                    try {
                        method = iface.getMethod(method.getName(), method.getParameterTypes());
                        break;
                    } catch (@SuppressWarnings("unused") NoSuchMethodException noMethod) {
                        // NOSONAR
                    }
                }
            } catch (SecurityException ex) {
                ReflectionUtils.handleReflectionException(ex);
            } catch (NoSuchMethodException ex) {
                throw new IllegalStateException(String.format(
                        "@KafkaListener method '%s' found on bean target class '%s', " +
                                "but not found in any interface(s) for bean JDK proxy. Either " +
                                "pull the method up to an interface or switch to subclass (CGLIB) " +
                                "proxies by setting proxy-target-class/proxyTargetClass " +
                                "attribute to 'true'", method.getName(),
                        method.getDeclaringClass().getSimpleName()), ex);
            }
        }
        return method;
    }
}
