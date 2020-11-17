package de.ruegnerlukas.simpleapplication.common.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * A triplet of three values.
 *
 * @param <L> the generic type of the left value
 * @param <M> the generic type of the middle value
 * @param <R> the generic type of the right value
 */
@Getter
@AllArgsConstructor
public class Triplet<L, M, R> {


	/**
	 * The left value.
	 */
	private final L left;

	/**
	 * The middle value.
	 */
	private final M middle;


	/**
	 * The right value.
	 */
	private final R right;




	/**
	 * Creates a new triplet with the three given values.
	 *
	 * @param left   the left value
	 * @param middle the middle value
	 * @param right  the right value
	 * @param <L>    the generic type of the left value
	 * @param <M>    the generic type of the middle value
	 * @param <R>    the generic type of the right value
	 * @return the created triplet
	 */
	public static <L, M, R> Triplet<L, M, R> of(final L left, final M middle, final R right) {
		return new Triplet<>(left, middle, right);
	}




	@Override
	public String toString() {
		return "<" + left + "," + middle + "," + right + ">";
	}

}

