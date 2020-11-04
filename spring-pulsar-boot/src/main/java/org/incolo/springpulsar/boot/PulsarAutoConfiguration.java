package org.incolo.springpulsar.boot;

import org.apache.pulsar.client.api.PulsarClientException;
import org.incolo.springpulsar.annotation.EnablePulsar;
import org.incolo.springpulsar.annotation.PulsarListenerAnnotationBeanPostProcessor;
import org.incolo.springpulsar.config.*;
import org.incolo.springpulsar.core.MessageConverter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Charvak Patel
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(EnablePulsar.class)
@EnableConfigurationProperties(PulsarProperties.class)
public class PulsarAutoConfiguration {

	private PulsarProperties pulsarProperties;

	PulsarAutoConfiguration(PulsarProperties pulsarProperties) {
		this.pulsarProperties = pulsarProperties;
	}

	@Bean
	@ConditionalOnMissingBean(name = "pulsarClientFactory")
	public PulsarClientFactory pulsarClientFactory(PulsarProperties pulsarProperties) throws PulsarClientException {
		return new DefaultPulsarClientFactory(pulsarProperties.getClient());
	}

	@Bean
	@ConditionalOnMissingBean(name = "pulsarListenerContainerFactory")
	ConcurrentPulsarListenerContainerFactory pulsarListenerContainerFactory(
			PulsarProperties pulsarProperties,
			PulsarClientFactory pulsarClientFactory,
			ObjectProvider<MessageConverter> messageConverter,
			ObjectProvider<ConsumerFactory> consumerFactory) throws PulsarClientException {
		ConcurrentPulsarListenerContainerFactory factory = new ConcurrentPulsarListenerContainerFactory(pulsarClientFactory, pulsarProperties.getConsumer());
		messageConverter.ifUnique(factory::setMessageConverter);
		consumerFactory.ifUnique(factory::setConsumerFactory);
		return factory;
	}

	@Configuration(proxyBeanMethods = false)
	@EnablePulsar
	@ConditionalOnMissingBean(name = PulsarListenerAnnotationBeanPostProcessor.PULSAR_LISTENER_ANNOTATION_PROCESSOR_BEAN_NAME)
	static class EnableKafkaConfiguration {

	}
}
