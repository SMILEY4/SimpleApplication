package de.ruegnerlukas.simpleapplication.simpleui.utils;

import lombok.Getter;
import lombok.Setter;

import java.util.function.BiConsumer;

public class MutableBiConsumer<T, U> implements BiConsumer<T, U> {


	/**
	 * The wrapped consumer.
	 */
	@Getter
	@Setter
	private BiConsumer<T, U> consumer;


	/**
	 * Whether the consumer should be called
	 */
	@Getter
	@Setter
	private boolean muted = false;




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
		if (consumer != null) {
			consumer.accept(t, u);
		}
	}




	/**
	 * Mutes the consumer. It will no longer be called.
	 */
	public void mute() {
		setMuted(true);
	}




	/**
	 * Unmutes the consumer.
	 */
	public void unmute() {
		setMuted(false);
	}




	/**
	 * Runs the given action while making sure this consumer is muted
	 *
	 * @param action the action to run
	 */
	public void runMuted(final Runnable action) {
		mute();
		action.run();
		unmute();
	}


}
