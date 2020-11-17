package de.ruegnerlukas.simpleapplication.common.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * A pair of two values.
 *
 * @param <L> the generic type of the left value
 * @param <R> the generic type of the right value
 */
@Getter
@AllArgsConstructor
public class Pair<L, R> {


	/**
	 * The left value.
	 */
	private final L left;

	/**
	 * The right value.
	 */
	private final R right;




	/**
	 * Creates a new pair with the two given values.
	 *
	 * @param left  the left value
	 * @param right the right value
	 * @param <L>   the generic type of the left value
	 * @param <R>   the generic type of the right value
	 * @return the created pair
	 */
	public static <L, R> Pair<L, R> of(final L left, final R right) {
		return new Pair<>(left, right);
	}




	@Override
	public String toString() {
		return "<" + left + "," + right + ">";
	}

}

