package org.incolo.springpulsar.core;

public class UnsupportedTypeException extends SpringPulsarException {
	public UnsupportedTypeException(String message, Object object) {
		super(String.format("%s; Unsupported Type : %s ",
				message,
				object != null ? object.getClass().getCanonicalName() : "Null")
		);
	}
}
