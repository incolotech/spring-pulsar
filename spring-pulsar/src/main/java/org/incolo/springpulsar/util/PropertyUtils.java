package org.incolo.springpulsar.util;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

/**
 * @author Charvak Patel
 */
public class PropertyUtils {
	public static void executeIfNonNullLong(Duration duration, BiConsumer<Long, TimeUnit> consumer) {
		Optional.ofNullable(duration).ifPresent(
				d -> consumer.accept(duration.toNanos(), TimeUnit.NANOSECONDS)
		);
	}

	public static void executeIfNonNullInt(Duration duration, BiConsumer<Integer, TimeUnit> consumer) {
		Optional.ofNullable(duration).ifPresent(
				d -> consumer.accept(Math.toIntExact(duration.toNanos()), TimeUnit.NANOSECONDS)
		);
	}

	public static <T> void executeIfNonNull(T prop, java.util.function.Consumer<T> consumer) {
		Optional.ofNullable(prop).ifPresent(
				p -> consumer.accept(prop)
		);
	}

}
