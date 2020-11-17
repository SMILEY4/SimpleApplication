package de.ruegnerlukas.simpleapplication.core.simpleui.utils;

import lombok.Getter;
import lombok.Setter;

import java.util.function.Consumer;

public class MutableConsumer<T> extends MutableWrapper implements Consumer<T> {


	/**
	 * The wrapped consumer.
	 */
	@Getter
	@Setter
	private Consumer<T> consumer;




	/**
	 * Default constructor with no actual consumer.
	 */
	public MutableConsumer() {
		this(null);
	}




	/**
	 * @param consumer the actual consumer
	 */
	public MutableConsumer(final Consumer<T> consumer) {
		this.consumer = consumer;
	}




	@Override
	public void accept(final T t) {
		if (!isMuted() && consumer != null) {
			consumer.accept(t);
		}
	}


}
