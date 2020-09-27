package org.incolo.springpulsar.core;

import org.springframework.core.MethodParameter;

import java.lang.reflect.Parameter;
import java.util.List;

public class PayloadTypeInferenceException extends SpringPulsarException {
	private List<MethodParameter> conflictedParameters;

	public PayloadTypeInferenceException(String msg, List<MethodParameter> conflictedParameters) {
		super(msg + "; Parameters in conflict : "
				+ conflictedParameters.stream()
				.map(MethodParameter::getParameter)
				.map(Parameter::getName)
				.reduce((s, s2) -> s + ", " + s2)
				.orElse(""));
		this.conflictedParameters = conflictedParameters;
	}

	public List<MethodParameter> getConflictedParameters() {
		return conflictedParameters;
	}
}
