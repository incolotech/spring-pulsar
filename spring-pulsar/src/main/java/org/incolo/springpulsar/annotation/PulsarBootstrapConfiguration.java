/*
 * Copyright 2002-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.incolo.springpulsar.annotation;

import org.incolo.springpulsar.config.PulsarListenerContainerRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author Charvak Patel
 */
public class PulsarBootstrapConfiguration implements ImportBeanDefinitionRegistrar {

	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		if (!registry.containsBeanDefinition(
				PulsarListenerAnnotationBeanPostProcessor.PULSAR_LISTENER_ANNOTATION_PROCESSOR_BEAN_NAME)) {

			registry.registerBeanDefinition(PulsarListenerAnnotationBeanPostProcessor.PULSAR_LISTENER_ANNOTATION_PROCESSOR_BEAN_NAME,
					new RootBeanDefinition(PulsarListenerAnnotationBeanPostProcessor.class));
		}

		if (!registry.containsBeanDefinition(PulsarListenerContainerRegistry.PULSAR_LISTENER_CONTAINER_REGISTRY_BEAN_NAME)) {
			registry.registerBeanDefinition(PulsarListenerContainerRegistry.PULSAR_LISTENER_CONTAINER_REGISTRY_BEAN_NAME,
					new RootBeanDefinition(PulsarListenerContainerRegistry.class));
		}
	}

}
