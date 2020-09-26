package org.incolo.springpulsar.core;

import org.apache.commons.logging.LogFactory;
import org.apache.pulsar.client.api.Schema;
import org.apache.pulsar.shade.org.apache.commons.lang3.StringUtils;
import org.incolo.springpulsar.annotation.NullType;
import org.incolo.springpulsar.annotation.PulsarListener;
import org.incolo.springpulsar.config.SchemaProvider;
import org.incolo.springpulsar.util.CollectionUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.core.log.LogAccessor;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SchemaProviderFactory {

	protected final LogAccessor logger = new LogAccessor(LogFactory.getLog(getClass()));

	private final BeanFactory beanFactory;

	private static final String PROVIDER_METHOD = "getSchema";

	private final List<ProviderHolder> holders;

	public SchemaProviderFactory(BeanFactory beanFactory) {

		this.beanFactory = beanFactory;
		holders = Arrays.asList(
				new ProviderHolder("jsonSchema",
						pl -> pl.jsonSchema() != NullType.class,
						pl -> () -> Schema.JSON(pl.jsonSchema())),
				new ProviderHolder("protoBufSchema",
						pl -> pl.protobufSchema() != NullType.class,
						pl -> () -> Schema.PROTOBUF(pl.protobufSchema())),
				new ProviderHolder("avroSchema",
						pl -> pl.avroSchema() != NullType.class,
						pl -> () -> Schema.AVRO(pl.avroSchema())),
				new ProviderHolder("schemaProviderBeanClass",
						pl -> pl.schemaProviderBeanClass() != NullType.class,
						pl -> getSchemaProvider(beanFactory.getBean(pl.schemaProviderBeanClass()))),
				new ProviderHolder("schemaProviderBeanName",
						pl -> StringUtils.isNotBlank(pl.schemaProviderBeanName()),
						pl -> getSchemaProvider(beanFactory.getBean(pl.schemaProviderBeanClass()))),
				new ProviderHolder("isAutoSchema",
						PulsarListener::isAutoSchema,
						pl -> Schema::AUTO_CONSUME)
		);
	}

	SchemaProvider getSchemaProvider(final PulsarListener annotation) {

		List<ProviderHolder> matchedHolders = holders.stream()
				.filter(providerHolder -> providerHolder.isValid(annotation))
				.collect(Collectors.toList());

		if (matchedHolders.size() == 0) {
			return () -> annotation.primitiveTypeSchema().getSchema();
		}

		if (matchedHolders.size() != 1) {
			throw new SpringPulsarException("More than one property for custom Schema is set. Set properties : "
					+ matchedHolders.stream().map(ProviderHolder::getName).reduce("", (s, s2) -> s + ", " + s2)
			);
		}

		return matchedHolders.get(0).getSchemaProvider(annotation);
	}


	private static SchemaProvider getSchemaProvider(Object providerBean) {
		Method schemaProviderMethod = ReflectionUtils.findMethod(providerBean.getClass(), PROVIDER_METHOD);
		if (schemaProviderMethod == null) {
			throw new SpringPulsarException(
					String.format("Failed to find %s on given provider bean of type %s.", PROVIDER_METHOD, providerBean.getClass()));
		}
		//Important to call it here rather then going lazy eval so that if anything goes wrong it can be found on the startup.
		Object schema = ReflectionUtils.invokeMethod(schemaProviderMethod, providerBean);
		return () -> (Schema<?>) schema;

	}


	//TODO find better name :(
	private static class ProviderHolder {
		private final String name;
		private final Predicate<PulsarListener> isValid;
		private final Function<PulsarListener, SchemaProvider> providerFunction;

		public ProviderHolder(
				String name,
				Predicate<PulsarListener> isValid,
				Function<PulsarListener, SchemaProvider> providerFunction) {
			this.name = name;
			this.isValid = isValid;
			this.providerFunction = providerFunction;
		}

		public String getName() {
			return name;
		}

		public boolean isValid(PulsarListener pulsarListener) {
			return isValid.test(pulsarListener);
		}

		public SchemaProvider getSchemaProvider(PulsarListener pulsarListener) {
			return providerFunction.apply(pulsarListener);
		}
	}
}
