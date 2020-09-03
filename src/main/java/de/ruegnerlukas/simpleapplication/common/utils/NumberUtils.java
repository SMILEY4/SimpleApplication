package de.ruegnerlukas.simpleapplication.common.utils;

public final class NumberUtils {


	/**
	 * Hidden constructor for utility class
	 */
	private NumberUtils() {
		// Hidden constructor
	}




	/**
	 * Null-Safe check to see if the two given values are equal.
	 *
	 * @param a the first number or null
	 * @param b the second number or null
	 * @return whether the two numbers are equal.
	 */
	public static boolean isEqual(final Number a, final Number b) {
		if (a == null && b == null) {
			return true;
		} else {
			if (a != null) {
				if (b == null) {
					return false;
				} else {
					return compare(a, b) == 0;
				}
			} else {
				return false;
			}
		}
	}




	/**
	 * Compares the two given numbers.
	 *
	 * @param a the first number
	 * @param b the second number
	 * @return the value 0 if a is numerically equal to b;
	 * a value less than 0 if a is numerically less than b;
	 * and a value greater than 0 if a is numerically greater than b.
	 */
	public static int compare(final Number a, final Number b) {
		if (a instanceof Double || a instanceof Float || b instanceof Double || b instanceof Float) {
			return Double.compare(a.doubleValue(), b.doubleValue());
		} else {
			return Long.compare(a.longValue(), b.longValue());
		}
	}

}
