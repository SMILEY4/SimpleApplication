package de.ruegnerlukas.simpleapplication.common.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * A pair of two integers
 */
@Getter
@AllArgsConstructor
public class IntPair {


	/**
	 * The left value.
	 */
	private final int left;

	/**
	 * The right value.
	 */
	private final int right;




	/**
	 * Creates a new pair with the two given values.
	 *
	 * @param left  the left value
	 * @param right the right value
	 * @return the created pair
	 */
	public static IntPair of(final int left, final int right) {
		return new IntPair(left, right);
	}




	@Override
	public String toString() {
		return "<" + left + "," + right + ">";
	}

}
