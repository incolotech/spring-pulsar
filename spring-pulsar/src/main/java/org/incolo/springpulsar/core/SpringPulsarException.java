package org.incolo.springpulsar.core;

/**
 * Base class for Spring Pulsar Exceptions
 */
public class SpringPulsarException extends RuntimeException {
	public SpringPulsarException() {
	}

	public SpringPulsarException(String s) {
		super(s);
	}

	public SpringPulsarException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public SpringPulsarException(Throwable throwable) {
		super(throwable);
	}

	public SpringPulsarException(String s, Throwable throwable, boolean b, boolean b1) {
		super(s, throwable, b, b1);
	}
}
