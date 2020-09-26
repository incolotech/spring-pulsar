package org.incolo.springpulsar.config;

import org.apache.pulsar.client.api.Schema;

public interface SchemaProvider {
	Schema<?> getSchema();
}
