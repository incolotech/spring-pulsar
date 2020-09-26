package org.incolo.springpulsar.config;

public class PulsarClientProperties {
	private String serviceUrl;

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
