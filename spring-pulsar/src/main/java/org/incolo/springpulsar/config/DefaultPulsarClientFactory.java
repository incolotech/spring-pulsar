package org.incolo.springpulsar.config;

import org.apache.pulsar.client.api.ClientBuilder;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.incolo.springpulsar.core.SpringPulsarException;

import static org.incolo.springpulsar.util.PropertyUtils.*;

public class DefaultPulsarClientFactory implements PulsarClientFactory {

	PulsarClientProperties properties;

	public DefaultPulsarClientFactory(PulsarClientProperties properties) {
		this.properties = properties;
	}

	@Override
	public PulsarClient createClient() throws PulsarClientException {
		final ClientBuilder builder = PulsarClient.builder();

		executeIfNonNull(properties.getServiceUrl(), builder::serviceUrl);
		executeIfNonNull(properties.getProxyServiceUrl(), s -> builder.proxyServiceUrl(s, properties.getProxyProtocol()));
		executeIfNonNull(properties.getListenerName(), builder::listenerName);
		executeIfNonNull(properties.getAuthentication(), builder::authentication);
		executeIfNonNull(properties.getAuthParams(), authParams -> {
			try {
				builder.authentication(properties.getAuthPluginClassName(), authParams);
			} catch (PulsarClientException.UnsupportedAuthenticationException e) {
				throw new SpringPulsarException(e);
			}
		});
		executeIfNonNull(properties.getAuthParamsMap(), authParams -> {
			try {
				builder.authentication(properties.getAuthPluginClassName(), authParams);
			} catch (PulsarClientException.UnsupportedAuthenticationException e) {
				throw new SpringPulsarException(e);
			}
		});

		executeIfNonNullInt(properties.getOperationTimeout(), builder::operationTimeout);
		executeIfNonNull(properties.getIoThreads(), builder::ioThreads);
		executeIfNonNull(properties.getListenerThreads(), builder::listenerThreads);
		executeIfNonNull(properties.getConnectionsPerBroker(), builder::connectionsPerBroker);
		executeIfNonNull(properties.getEnableTcpNoDelay(), builder::enableTcpNoDelay);
		executeIfNonNull(properties.getEnableTls(), builder::enableTls);
		executeIfNonNull(properties.getTlsTrustCertsFilePath(), builder::tlsTrustCertsFilePath);
		executeIfNonNull(properties.getAllowTlsInsecureConnection(), builder::allowTlsInsecureConnection);
		executeIfNonNull(properties.getEnableTlsHostnameVerification(), builder::enableTlsHostnameVerification);
		executeIfNonNull(properties.getUseKeyStoreTls(), builder::useKeyStoreTls);
		executeIfNonNull(properties.getSslProvider(), builder::sslProvider);
		executeIfNonNull(properties.getTlsTrustStoreType(), builder::tlsTrustStoreType);
		executeIfNonNull(properties.getTlsTrustStorePath(), builder::tlsTrustStorePath);
		executeIfNonNull(properties.getTlsTrustStorePassword(), builder::tlsTrustStorePassword);
		executeIfNonNull(properties.getTlsCiphers(), builder::tlsCiphers);
		executeIfNonNull(properties.getTlsProtocols(), builder::tlsProtocols);
		executeIfNonNullLong(properties.getStatsInterval(), builder::statsInterval);
		executeIfNonNull(properties.getMaxConcurrentLookupRequests(), builder::maxConcurrentLookupRequests);
		executeIfNonNull(properties.getMaxLookupRequests(), builder::maxLookupRequests);
		executeIfNonNull(properties.getMaxLookupRedirects(), builder::maxLookupRedirects);
		executeIfNonNull(properties.getMaxNumberOfRejectedRequestPerConnection(), builder::maxNumberOfRejectedRequestPerConnection);
		executeIfNonNullInt(properties.getKeepAliveInterval(), builder::keepAliveInterval);
		executeIfNonNullInt(properties.getConnectionTimeout(), builder::connectionTimeout);
		executeIfNonNullLong(properties.getStartingBackoffInterval(), builder::startingBackoffInterval);
		executeIfNonNullLong(properties.getMaxBackoffInterval(), builder::maxBackoffInterval);
		executeIfNonNull(properties.getClock(), builder::clock);

		return builder.build();
	}
}
