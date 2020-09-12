package org.incolo.springpulsar.annotation;

import org.apache.commons.logging.LogFactory;
import org.incolo.springpulsar.core.MethodLevelPulsarListenerPipeline;
import org.incolo.springpulsar.core.PulsarListenerContainerRegistry;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.config.BeanExpressionContext;
import org.springframework.beans.factory.config.BeanExpressionResolver;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.Ordered;
import org.springframework.core.log.LogAccessor;

import static org.incolo.springpulsar.core.PulsarListenerContainerRegistry.PULSAR_LISTENER_CONTAINER_REGISTRY_BEAN_NAME;


/**
 * @author Charvak Patel
 */
public class PulsarListenerAnnotationBeanPostProcessor implements BeanPostProcessor, Ordered, BeanFactoryAware, SmartInitializingSingleton {

	protected final LogAccessor logger = new LogAccessor(LogFactory.getLog(getClass()));

	public static final String PULSAR_LISTENER_ANNOTATION_PROCESSOR_BEAN_NAME = "org.incolo.springpulsar.annotation.DefaultPulsarListenerAnnotationBeanPostProcessor";

	private BeanFactory beanFactory;

	private MethodLevelPulsarListenerPipeline methodLevelPulsarListenerPipeline;

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
		methodLevelPulsarListenerPipeline =
				new MethodLevelPulsarListenerPipeline(
						this.beanFactory.getBean(PULSAR_LISTENER_CONTAINER_REGISTRY_BEAN_NAME, PulsarListenerContainerRegistry.class));
	}

	@Override
	public void afterSingletonsInstantiated() {
	}

	@Override
	public int getOrder() {
		return LOWEST_PRECEDENCE;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		methodLevelPulsarListenerPipeline.process(bean, beanName);
		return bean;
	}
}
