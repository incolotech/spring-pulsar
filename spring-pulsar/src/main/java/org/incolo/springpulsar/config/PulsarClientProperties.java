package org.incolo.springpulsar.config;

import org.apache.pulsar.client.api.Authentication;
import org.apache.pulsar.client.api.ProxyProtocol;

import java.time.Clock;
import java.time.Duration;
import java.util.Map;
import java.util.Set;

public class PulsarClientProperties {
	private String serviceUrl;
	private String serviceUrlProvider;
	private String proxyServiceUrl;
	private ProxyProtocol proxyProtocol;
	private String listenerName;
	private Authentication authentication;
	private String authPluginClassName;
	private String authParams;
	private Map<String, String> authParamsMap;
	private Duration operationTimeout;
	private Integer ioThreads;
	private Integer listenerThreads;
	private Integer connectionsPerBroker;
	private Boolean enableTcpNoDelay;
	@Deprecated
	private Boolean enableTls;
	private String tlsTrustCertsFilePath;
	private Boolean allowTlsInsecureConnection;
	private Boolean enableTlsHostnameVerification;
	private Boolean useKeyStoreTls;
	private String sslProvider;
	private String tlsTrustStoreType;
	private String tlsTrustStorePath;
	private String tlsTrustStorePassword;
	private Set<String> tlsCiphers;
	private Set<String> tlsProtocols;
	private Duration statsInterval;
	private Integer maxConcurrentLookupRequests;
	private Integer maxLookupRequests;
	private Integer maxLookupRedirects;
	private Integer maxNumberOfRejectedRequestPerConnection;
	private Duration keepAliveInterval;
	private Duration connectionTimeout;
	private Duration startingBackoffInterval;
	private Duration maxBackoffInterval;
	private Clock clock;

	public void setAuthentication(String authPluginClassName, String authParamsString) {
		this.authPluginClassName = authPluginClassName;
		this.authParams = authParamsString;
		this.authParamsMap = null;
	}

	public void setAuthentication(String authPluginClassName, Map<String, String> authParams) {
		this.authPluginClassName = authPluginClassName;
		this.authParams = null;
		this.authParamsMap = authParams;
	}

	public void setProxyServiceUrl(String proxyServiceUrl, ProxyProtocol proxyProtocol) {
		this.proxyServiceUrl = proxyServiceUrl;
		this.proxyProtocol = proxyProtocol;
	}

	public void setOperationTimeout(Duration operationTimeout) {
		this.operationTimeout = operationTimeout;
	}

	public void setIoThreads(Integer ioThreads) {
		this.ioThreads = ioThreads;
	}

	public void setListenerThreads(Integer listenerThreads) {
		this.listenerThreads = listenerThreads;
	}

	public void setConnectionsPerBroker(Integer connectionsPerBroker) {
		this.connectionsPerBroker = connectionsPerBroker;
	}

	public void setEnableTcpNoDelay(Boolean enableTcpNoDelay) {
		this.enableTcpNoDelay = enableTcpNoDelay;
	}

	public void setEnableTls(Boolean enableTls) {
		this.enableTls = enableTls;
	}

	public void setTlsTrustCertsFilePath(String tlsTrustCertsFilePath) {
		this.tlsTrustCertsFilePath = tlsTrustCertsFilePath;
	}

	public void setAllowTlsInsecureConnection(Boolean allowTlsInsecureConnection) {
		this.allowTlsInsecureConnection = allowTlsInsecureConnection;
	}

	public void setEnableTlsHostnameVerification(Boolean enableTlsHostnameVerification) {
		this.enableTlsHostnameVerification = enableTlsHostnameVerification;
	}

	public void setUseKeyStoreTls(Boolean useKeyStoreTls) {
		this.useKeyStoreTls = useKeyStoreTls;
	}

	public void setSslProvider(String sslProvider) {
		this.sslProvider = sslProvider;
	}

	public void setTlsTrustStoreType(String tlsTrustStoreType) {
		this.tlsTrustStoreType = tlsTrustStoreType;
	}

	public void setTlsTrustStorePath(String tlsTrustStorePath) {
		this.tlsTrustStorePath = tlsTrustStorePath;
	}

	public void setTlsTrustStorePassword(String tlsTrustStorePassword) {
		this.tlsTrustStorePassword = tlsTrustStorePassword;
	}

	public void setTlsCiphers(Set<String> tlsCiphers) {
		this.tlsCiphers = tlsCiphers;
	}

	public void setTlsProtocols(Set<String> tlsProtocols) {
		this.tlsProtocols = tlsProtocols;
	}

	public void setStatsInterval(Duration statsInterval) {
		this.statsInterval = statsInterval;
	}

	public void setMaxConcurrentLookupRequests(Integer maxConcurrentLookupRequests) {
		this.maxConcurrentLookupRequests = maxConcurrentLookupRequests;
	}

	public void setMaxLookupRequests(Integer maxLookupRequests) {
		this.maxLookupRequests = maxLookupRequests;
	}

	public void setMaxLookupRedirects(Integer maxLookupRedirects) {
		this.maxLookupRedirects = maxLookupRedirects;
	}

	public void setMaxNumberOfRejectedRequestPerConnection(Integer maxNumberOfRejectedRequestPerConnection) {
		this.maxNumberOfRejectedRequestPerConnection = maxNumberOfRejectedRequestPerConnection;
	}

	public void setKeepAliveInterval(Duration keepAliveInterval) {
		this.keepAliveInterval = keepAliveInterval;
	}

	public void setConnectionTimeout(Duration connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public void setStartingBackoffInterval(Duration startingBackoffInterval) {
		this.startingBackoffInterval = startingBackoffInterval;
	}

	public void setMaxBackoffInterval(Duration maxBackoffInterval) {
		this.maxBackoffInterval = maxBackoffInterval;
	}

	public void setClock(Clock clock) {
		this.clock = clock;
	}

	public String getProxyServiceUrl() {
		return proxyServiceUrl;
	}

	public ProxyProtocol getProxyProtocol() {
		return proxyProtocol;
	}

	public String getAuthPluginClassName() {
		return authPluginClassName;
	}

	public String getAuthParams() {
		return authParams;
	}

	public Map<String, String> getAuthParamsMap() {
		return authParamsMap;
	}

	public Duration getOperationTimeout() {
		return operationTimeout;
	}

	public Integer getIoThreads() {
		return ioThreads;
	}

	public Integer getListenerThreads() {
		return listenerThreads;
	}

	public Integer getConnectionsPerBroker() {
		return connectionsPerBroker;
	}

	public Boolean getEnableTcpNoDelay() {
		return enableTcpNoDelay;
	}

	public Boolean getEnableTls() {
		return enableTls;
	}

	public String getTlsTrustCertsFilePath() {
		return tlsTrustCertsFilePath;
	}

	public Boolean getAllowTlsInsecureConnection() {
		return allowTlsInsecureConnection;
	}

	public Boolean getEnableTlsHostnameVerification() {
		return enableTlsHostnameVerification;
	}

	public Boolean getUseKeyStoreTls() {
		return useKeyStoreTls;
	}

	public String getSslProvider() {
		return sslProvider;
	}

	public String getTlsTrustStoreType() {
		return tlsTrustStoreType;
	}

	public String getTlsTrustStorePath() {
		return tlsTrustStorePath;
	}

	public String getTlsTrustStorePassword() {
		return tlsTrustStorePassword;
	}

	public Set<String> getTlsCiphers() {
		return tlsCiphers;
	}

	public Set<String> getTlsProtocols() {
		return tlsProtocols;
	}

	public Duration getStatsInterval() {
		return statsInterval;
	}

	public Integer getMaxConcurrentLookupRequests() {
		return maxConcurrentLookupRequests;
	}

	public Integer getMaxLookupRequests() {
		return maxLookupRequests;
	}

	public Integer getMaxLookupRedirects() {
		return maxLookupRedirects;
	}

	public Integer getMaxNumberOfRejectedRequestPerConnection() {
		return maxNumberOfRejectedRequestPerConnection;
	}

	public Duration getKeepAliveInterval() {
		return keepAliveInterval;
	}

	public Duration getConnectionTimeout() {
		return connectionTimeout;
	}

	public Duration getStartingBackoffInterval() {
		return startingBackoffInterval;
	}

	public Duration getMaxBackoffInterval() {
		return maxBackoffInterval;
	}

	public Clock getClock() {
		return clock;
	}


	public String getServiceUrlProvider() {
		return serviceUrlProvider;
	}

	public void setServiceUrlProvider(String serviceUrlProvider) {
		this.serviceUrlProvider = serviceUrlProvider;
	}

	public String getListenerName() {
		return listenerName;
	}

	public void setListenerName(String listenerName) {
		this.listenerName = listenerName;
	}

	public Authentication getAuthentication() {
		return authentication;
	}

	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
	}

	public String getServiceUrl() {
		return serviceUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	public PulsarClientProperties(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}
}
