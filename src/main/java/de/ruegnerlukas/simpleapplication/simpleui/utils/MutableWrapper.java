package de.ruegnerlukas.simpleapplication.simpleui.utils;

import lombok.Getter;
import lombok.Setter;

import java.util.function.Supplier;

public class MutableWrapper {


	/**
	 * Whether the mutable should be called
	 */
	@Getter
	@Setter
	private boolean muted = false;




	/**
	 * Mutes this mutable. It will no longer be called.
	 */
	public void mute() {
		setMuted(true);
	}




	/**
	 * Unmutes this mutable.
	 */
	public void unmute() {
		setMuted(false);
	}




	/**
	 * Runs the given action while making sure this mutable is muted
	 *
	 * @param action the action to run
	 */
	public void runMuted(final Runnable action) {
		mute();
		try {
			action.run();
		} finally {
			unmute();
		}
	}




	/**
	 * Runs the given action while making sure this mutable is muted
	 *
	 * @param action the action to run
	 */
	public <R> R runMuted(final Supplier<R> action) {
		mute();
		R result = null;
		try {
			result = action.get();
		} finally {
			unmute();
		}
		return result;
	}


}
