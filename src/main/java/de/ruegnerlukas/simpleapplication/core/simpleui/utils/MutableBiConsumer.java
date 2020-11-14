package de.ruegnerlukas.simpleapplication.core.simpleui.utils;

import lombok.Getter;
import lombok.Setter;

import java.util.function.BiConsumer;

public class MutableBiConsumer<T, U> extends MutableWrapper implements BiConsumer<T, U> {


	/**
	 * The wrapped consumer.
	 */
	@Getter
	@Setter
	private BiConsumer<T, U> consumer;




	/**
	 * Default constructor with no actual consumer.
	 */
	public MutableBiConsumer() {
		this(null);
	}




	/**
	 * @param consumer the actual consumer
	 */
	public MutableBiConsumer(final BiConsumer<T, U> consumer) {
		this.consumer = consumer;
	}




	@Override
	public void accept(final T t, final U u) {
		if (!isMuted() && consumer != null) {
			consumer.accept(t, u);
		}
	}


}
