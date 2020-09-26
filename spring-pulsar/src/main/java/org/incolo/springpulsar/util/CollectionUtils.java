package org.incolo.springpulsar.util;

import org.incolo.springpulsar.core.SpringPulsarException;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class CollectionUtils {

	/**
	 * Collector to get a single value out of stream.
	 * If stream has anything other than single value, it uses exceptionSupplier to throw exception
	 */
	public static <T> Collector<T, ?, T> toSingletonOrThrow(Function<List<T>, SpringPulsarException> exceptionSupplier) {
		return Collectors.collectingAndThen(
				Collectors.toList(),
				list -> {
					if (list.size() != 1) {
						throw exceptionSupplier.apply(list);
					}
					return list.get(0);
				}
		);
	}
}
