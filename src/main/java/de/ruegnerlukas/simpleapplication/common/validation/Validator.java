package de.ruegnerlukas.simpleapplication.common.validation;

import org.slf4j.helpers.MessageFormatter;

import java.io.File;
import java.util.Collection;
import java.util.Map;

/**
 * Class for validating objects and values
 */
public class Validator {


	/**
	 * Formats the given message and inserts the given arguments.
	 *
	 * @param message the message
	 * @param args    the arguments
	 * @return the formatted message
	 */
	private String formatMessage(final String message, final Object... args) {
		String result = message;
		if (args != null && args.length > 0) {
			result = MessageFormatter.arrayFormat(message, args).getMessage();
		}
		return result;
	}




	/**
	 * Throws the exception on failed validation.
	 *
	 * @param message the message of the exception
	 */
	protected void failedValidation(final String message) {
		// do nothing by default
	}




	/**
	 * Assert that the given object is not null.
	 *
	 * @param object       the object to examine
	 * @param errorMessage the error message when the object is null
	 * @param <T>          Generic type
	 * @return the object itself
	 */
	public <T> T notNull(final T object, final String errorMessage, final Object... args) {
		if (object == null) {
			failedValidation(formatMessage(errorMessage, args));
		}
		return object;
	}




	/**
	 * Assert that the given object is null.
	 *
	 * @param object       the object to examine
	 * @param errorMessage the error message when the object is not null
	 * @param args         the arguments to insert into the message
	 * @param <T>          Generic type
	 * @return the object itself
	 */
	public <T> T isNull(final T object, final String errorMessage, final Object... args) {
		if (object != null) {
			failedValidation(formatMessage(errorMessage, args));
		}
		return object;
	}




	/**
	 * Assert that the given string is neither null nor empty
	 *
	 * @param string       the string to examine
	 * @param errorMessage the error message when the string is null or empty
	 * @param args         the arguments to insert into the message
	 * @return the string itself
	 */
	public String notEmpty(final String string, final String errorMessage, final Object... args) {
		if (string == null || string.isEmpty()) {
			failedValidation(formatMessage(errorMessage, args));
		}
		return string;
	}




	/**
	 * Assert that the given string is neither null nor blank
	 *
	 * @param string       the string to examine
	 * @param errorMessage the error message when the string is null, empty or contains only whitespaces
	 * @param args         the arguments to insert into the message
	 * @return the string itself
	 */
	public String notBlank(final String string, final String errorMessage, final Object... args) {
		if (string == null || string.isBlank()) {
			failedValidation(formatMessage(errorMessage, args));
		}
		return string;
	}




	/**
	 * Assert that the given collection is neither null nor empty
	 *
	 * @param collection   the collection to examine
	 * @param errorMessage the error message when the collection is null or empty
	 * @param args         the arguments to insert into the message
	 * @return the collection itself
	 */
	public <T> Collection<T> notEmpty(final Collection<T> collection, final String errorMessage, final Object... args) {
		if (collection == null || collection.isEmpty()) {
			failedValidation(formatMessage(errorMessage, args));
		}
		return collection;
	}




	/**
	 * Assert that the given array is neither null nor empty
	 *
	 * @param array        the array to examine
	 * @param errorMessage the error message when the array is null or empty
	 * @param args         the arguments to insert into the message
	 * @return the array itself
	 */
	public <T> T[] notEmpty(final T[] array, final String errorMessage, final Object... args) {
		if (array == null || array.length == 0) {
			failedValidation(formatMessage(errorMessage, args));
		}
		return array;
	}




	/**
	 * Assert that the given string is null or empty
	 *
	 * @param string       the string to examine
	 * @param errorMessage the error message when the string is neither null nor empty
	 * @param args         the arguments to insert into the message
	 * @return the string itself
	 */
	public String isEmpty(final String string, final String errorMessage, final Object... args) {
		if (string != null && !string.isEmpty()) {
			failedValidation(formatMessage(errorMessage, args));
		}
		return string;
	}




	/**
	 * Assert that the given string is null or contains only whitespaces
	 *
	 * @param string       the string to examine
	 * @param errorMessage the error message when the string is neither null nor blank
	 * @param args         the arguments to insert into the message
	 * @return the string itself
	 */
	public String isBlank(final String string, final String errorMessage, final Object... args) {
		if (string != null && !string.isBlank()) {
			failedValidation(formatMessage(errorMessage, args));
		}
		return string;
	}




	/**
	 * Assert that the given collection is null or empty
	 *
	 * @param collection   the collection to examine
	 * @param errorMessage the error message when the collection is neither null nor empty
	 * @param args         the arguments to insert into the message
	 * @return the collection itself
	 */
	public <T> Collection<T> isEmpty(final Collection<T> collection, final String errorMessage, final Object... args) {
		if (collection != null && !collection.isEmpty()) {
			failedValidation(formatMessage(errorMessage, args));
		}
		return collection;
	}




	/**
	 * Assert that the given array is null or empty
	 *
	 * @param array        the array to examine
	 * @param errorMessage the error message when the array is neither null nor empty
	 * @param args         the arguments to insert into the message
	 * @return the array itself
	 */
	public <T> T[] isEmpty(final T[] array, final String errorMessage, final Object... args) {
		if (array != null && array.length != 0) {
			failedValidation(formatMessage(errorMessage, args));
		}
		return array;
	}




	/**
	 * Assert that the given collection is not null and contains at least min amount of elements
	 *
	 * @param collection   the collection to examine
	 * @param min          the min amount of elements
	 * @param errorMessage the error message when the collection is null or contains less elements
	 * @param args         the arguments to insert into the message
	 * @param <T>          Generic type
	 * @return the collection itself
	 */
	public <T> Collection<T> containsAtLeast(final Collection<T> collection, final int min,
											 final String errorMessage, final Object... args) {
		if (collection == null || collection.size() < min) {
			failedValidation(formatMessage(errorMessage, args));
		}
		return collection;
	}




	/**
	 * Assert that the given array is not null and contains at least min amount of elements
	 *
	 * @param array        the array to examine
	 * @param min          the min amount of elements
	 * @param errorMessage the error message when the array is null or contains less elements
	 * @param args         the arguments to insert into the message
	 * @param <T>          Generic type
	 * @return the array itself
	 */
	public <T> T[] containsAtLeast(final T[] array, final int min, final String errorMessage, final Object... args) {
		if (array == null || array.length < min) {
			failedValidation(formatMessage(errorMessage, args));
		}
		return array;
	}




	/**
	 * Assert that the given collection is not null and contains not more than max amount of elements
	 *
	 * @param collection   the collection to examine
	 * @param max          the max amount of elements
	 * @param errorMessage the error message when the collection is null or contains more elements
	 * @param args         the arguments to insert into the message
	 * @param <T>          Generic type
	 * @return the collection itself
	 */
	public <T> Collection<T> containsAtMost(final Collection<T> collection, final int max,
											final String errorMessage, final Object... args) {
		if (collection == null || collection.size() > max) {
			failedValidation(formatMessage(errorMessage, args));
		}
		return collection;
	}




	/**
	 * Assert that the given array is not null and contains not more than max amount of elements
	 *
	 * @param array        the array to examine
	 * @param max          the max amount of elements
	 * @param errorMessage the error message when the array is null or contains more elements
	 * @param args         the arguments to insert into the message
	 * @param <T>          Generic type
	 * @return the array itself
	 */
	public <T> T[] containsAtMost(final T[] array, final int max, final String errorMessage, final Object... args) {
		if (array == null || array.length > max) {
			failedValidation(formatMessage(errorMessage, args));
		}
		return array;
	}




	/**
	 * Assert that the given collection is not null and contains exactly n amount of elements
	 *
	 * @param collection   the collection to examine
	 * @param n            the amount of elements
	 * @param errorMessage the error message when the collection is null or does not contain n elements
	 * @param args         the arguments to insert into the message
	 * @param <T>          Generic type
	 * @return the collection itself
	 */
	public <T> Collection<T> containsExactly(final Collection<T> collection, final int n,
											 final String errorMessage, final Object... args) {
		if (collection == null || collection.size() != n) {
			failedValidation(formatMessage(errorMessage, args));
		}
		return collection;
	}




	/**
	 * Assert that the given array is not null and contains exactly n amount of elements
	 *
	 * @param array        the array to examine
	 * @param n            the amount of elements
	 * @param errorMessage the error message when the array is null or does not contain n elements
	 * @param args         the arguments to insert into the message
	 * @param <T>          Generic type
	 * @return the array itself
	 */
	public <T> T[] containsExactly(final T[] array, final int n, final String errorMessage, final Object... args) {
		if (array == null || array.length != n) {
			failedValidation(formatMessage(errorMessage, args));
		}
		return array;
	}




	/**
	 * Assert that the given collection is not null and contains no null elements
	 *
	 * @param collection   the collection to examine
	 * @param errorMessage the error message when the collection is null or contains null elements.
	 * @param args         the arguments to insert into the message
	 * @param <T>          Generic type
	 * @return the collection itself
	 */
	public <T> Collection<T> containsNoNull(final Collection<T> collection, final String errorMessage, final Object... args) {
		if (collection == null) {
			failedValidation(formatMessage(errorMessage, args));
		} else {
			for (T element : collection) {
				if (element == null) {
					failedValidation(formatMessage(errorMessage, args));
					break;
				}
			}
		}
		return collection;
	}




	/**
	 * Assert that the given array is not null and contains no null elements
	 *
	 * @param array        the array to examine
	 * @param errorMessage the error message when the array is null or contains null elements.
	 * @param args         the arguments to insert into the message
	 * @param <T>          Generic type
	 * @return the array itself
	 */
	public <T> T[] containsNoNull(final T[] array, final String errorMessage, final Object... args) {
		if (array == null) {
			failedValidation(formatMessage(errorMessage, args));
		} else {
			for (int i = 0, n = array.length; i < n; i++) {
				if (array[i] == null) {
					failedValidation(formatMessage(errorMessage, args));
					break;
				}
			}
		}
		return array;
	}




	/**
	 * Asserts that the given collection contains the given object (and is not null).
	 *
	 * @param collection   the collection to examine
	 * @param obj          the object
	 * @param errorMessage the error message when the collection does not contain the object or is null.
	 * @param args         the arguments to insert into the message
	 * @param <T>          Generic type
	 * @return the collection itself
	 */
	public <T> Collection<T> contains(final Collection<T> collection, final T obj, final String errorMessage, final Object... args) {
		if (collection == null || !collection.contains(obj)) {
			failedValidation(formatMessage(errorMessage, args));
		}
		return collection;
	}




	/**
	 * Asserts that the given array contains the given object (and is not null).
	 *
	 * @param array        the array to examine
	 * @param obj          the object
	 * @param errorMessage the error message when the array does not contain the object or is null.
	 * @param args         the arguments to insert into the message
	 * @param <T>          Generic type
	 * @return the array itself
	 */
	public <T> T[] contains(final T[] array, final T obj, final String errorMessage, final Object... args) {
		if (array == null) {
			failedValidation(formatMessage(errorMessage, args));
		} else {
			boolean containsObject = false;
			for (final T element : array) {
				if (element != null && element.equals(obj)) {
					containsObject = true;
					break;
				}
			}
			if (!containsObject) {
				failedValidation(formatMessage(errorMessage, args));
			}
		}
		return array;
	}




	/**
	 * Asserts that the given map contains the given key (and is not null).
	 *
	 * @param map          the map to examine
	 * @param key          the key
	 * @param errorMessage the error message when the map does not contain the key or is null.
	 * @param args         the arguments to insert into the message
	 * @param <K>          Generic type of the key
	 * @param <V>          Generic type of the value
	 * @return the map itself
	 */
	public <K, V> Map<K, V> containsKey(final Map<K, V> map, final K key, final String errorMessage, final Object... args) {
		if (map == null) {
			failedValidation(formatMessage(errorMessage, args));
		} else {
			if (!map.containsKey(key)) {
				failedValidation(formatMessage(errorMessage, args));
			}
		}
		return map;
	}




	/**
	 * Asserts that the given map contains the given value (and is not null).
	 *
	 * @param map          the map to examine
	 * @param value        the value
	 * @param errorMessage the error message when the map does not contain the value or is null.
	 * @param args         the arguments to insert into the message
	 * @param <K>          Generic type of the key
	 * @param <V>          Generic type of the value
	 * @return the map itself
	 */
	public <K, V> Map<K, V> containsValue(final Map<K, V> map, final V value, final String errorMessage, final Object... args) {
		if (map == null) {
			failedValidation(formatMessage(errorMessage, args));
		} else {
			if (!map.containsValue(value)) {
				failedValidation(formatMessage(errorMessage, args));
			}
		}
		return map;
	}




	/**
	 * Asserts that the given collection does not contain the given object (and is not null).
	 *
	 * @param collection   the collection to examine
	 * @param obj          the object
	 * @param errorMessage the error message when the collection contains the object or is null.
	 * @param args         the arguments to insert into the message
	 * @param <T>          Generic type
	 * @return the collection itself
	 */
	public <T> Collection<T> containsNot(final Collection<T> collection, final T obj, final String errorMessage, final Object... args) {
		if (collection == null || collection.contains(obj)) {
			failedValidation(formatMessage(errorMessage, args));
		}
		return collection;
	}




	/**
	 * Asserts that the given array does not contain the given object (and is not null).
	 *
	 * @param array        the array to examine
	 * @param obj          the object
	 * @param errorMessage the error message when the array contains the object or is null.
	 * @param args         the arguments to insert into the message
	 * @param <T>          Generic type
	 * @return the array itself
	 */
	public <T> T[] containsNot(final T[] array, final T obj, final String errorMessage, final Object... args) {
		if (array == null) {
			failedValidation(formatMessage(errorMessage, args));
		} else {
			boolean containsObject = false;
			for (final T element : array) {
				if (element != null && element.equals(obj)) {
					containsObject = true;
					break;
				}
			}
			if (containsObject) {
				failedValidation(formatMessage(errorMessage, args));
			}
		}
		return array;
	}




	/**
	 * Asserts that the given map does not contain the given key (and is not null).
	 *
	 * @param map          the map to examine
	 * @param key          the key
	 * @param errorMessage the error message when the map does contain the key or is null.
	 * @param args         the arguments to insert into the message
	 * @param <K>          Generic type of the key
	 * @param <V>          Generic type of the value
	 * @return the map itself
	 */
	public <K, V> Map<K, V> containsNotKey(final Map<K, V> map, final K key, final String errorMessage, final Object... args) {
		if (map == null) {
			failedValidation(formatMessage(errorMessage, args));
		} else {
			if (map.containsKey(key)) {
				failedValidation(formatMessage(errorMessage, args));
			}
		}
		return map;
	}




	/**
	 * Asserts that the given map does not contain the given value (and is not null).
	 *
	 * @param map          the map to examine
	 * @param value        the value
	 * @param errorMessage the error message when the map does contain the value or is null.
	 * @param args         the arguments to insert into the message
	 * @param <K>          Generic type of the key
	 * @param <V>          Generic type of the value
	 * @return the map itself
	 */
	public <K, V> Map<K, V> containsNotValue(final Map<K, V> map, final V value, final String errorMessage, final Object... args) {
		if (map == null) {
			failedValidation(formatMessage(errorMessage, args));
		} else {
			if (map.containsValue(value)) {
				failedValidation(formatMessage(errorMessage, args));
			}
		}
		return map;
	}




	/**
	 * Assert that the given boolean is true
	 *
	 * @param bool         the boolean to examine
	 * @param errorMessage the error message when the boolean is false
	 * @param args         the arguments to insert into the message
	 * @return the boolean itself
	 */
	public boolean isTrue(final boolean bool, final String errorMessage, final Object... args) {
		if (!bool) {
			failedValidation(formatMessage(errorMessage, args));
		}
		return bool;
	}




	/**
	 * Assert that the given boolean is false
	 *
	 * @param bool         the boolean to examine
	 * @param errorMessage the error message when the boolean is true
	 * @param args         the arguments to insert into the message
	 * @return the boolean itself
	 */
	public boolean isFalse(final boolean bool, final String errorMessage, final Object... args) {
		if (bool) {
			failedValidation(formatMessage(errorMessage, args));
		}
		return bool;
	}




	/**
	 * Assert that the given number is negative
	 *
	 * @param number       the number to examine
	 * @param errorMessage the error message when the number positive or 0
	 * @param args         the arguments to insert into the message
	 * @return the number itself
	 */
	public Number isNegative(final long number, final String errorMessage, final Object... args) {
		if (number >= 0) {
			failedValidation(formatMessage(errorMessage, args));
		}
		return number;
	}




	/**
	 * Assert that the given number is negative
	 *
	 * @param number       the number to examine
	 * @param errorMessage the error message when the number positive or 0
	 * @param args         the arguments to insert into the message
	 * @return the number itself
	 */
	public Number isNegative(final double number, final String errorMessage, final Object... args) {
		if (number >= 0) {
			failedValidation(formatMessage(errorMessage, args));
		}
		return number;
	}




	/**
	 * Assert that the given number is not negative
	 *
	 * @param number       the number to examine
	 * @param errorMessage the error message when the number negative
	 * @param args         the arguments to insert into the message
	 * @return the number itself
	 */
	public Number isNotNegative(final long number, final String errorMessage, final Object... args) {
		if (number < 0) {
			failedValidation(formatMessage(errorMessage, args));
		}
		return number;
	}




	/**
	 * Assert that the given number is not negative
	 *
	 * @param number       the number to examine
	 * @param errorMessage the error message when the number negative
	 * @param args         the arguments to insert into the message
	 * @return the number itself
	 */
	public Number isNotNegative(final double number, final String errorMessage, final Object... args) {
		if (number < 0) {
			failedValidation(formatMessage(errorMessage, args));
		}
		return number;
	}




	/**
	 * Assert that the given number is positive
	 *
	 * @param number       the number to examine
	 * @param errorMessage the error message when the number negative or 0
	 * @param args         the arguments to insert into the message
	 * @return the number itself
	 */
	public Number isPositive(final long number, final String errorMessage, final Object... args) {
		if (number <= 0) {
			failedValidation(formatMessage(errorMessage, args));
		}
		return number;
	}




	/**
	 * Assert that the given number is positive
	 *
	 * @param number       the number to examine
	 * @param errorMessage the error message when the number negative or 0
	 * @param args         the arguments to insert into the message
	 * @return the number itself
	 */
	public Number isPositive(final double number, final String errorMessage, final Object... args) {
		if (number <= 0) {
			failedValidation(formatMessage(errorMessage, args));
		}
		return number;
	}




	/**
	 * Assert that the given number is greater than than the other number
	 *
	 * @param number       the number to examine
	 * @param other        the other value
	 * @param errorMessage the error message when the number smaller or equal the other number
	 * @param args         the arguments to insert into the message
	 * @return the number itself
	 */
	public Number isGreaterThan(final long number, final long other, final String errorMessage, final Object... args) {
		if (number <= other) {
			failedValidation(formatMessage(errorMessage, args));
		}
		return number;
	}




	/**
	 * Assert that the given number is greater than than the other number
	 *
	 * @param number       the number to examine
	 * @param other        the other value
	 * @param errorMessage the error message when the number smaller or equal the other number
	 * @param args         the arguments to insert into the message
	 * @return the number itself
	 */
	public Number isGreaterThan(final double number, final double other, final String errorMessage, final Object... args) {
		if (number <= other) {
			failedValidation(formatMessage(errorMessage, args));
		}
		return number;
	}




	/**
	 * Assert that the given number is less than than the other number
	 *
	 * @param number       the number to examine
	 * @param other        the other value
	 * @param errorMessage the error message when the number greater or equal the other number
	 * @param args         the arguments to insert into the message
	 * @return the number itself
	 */
	public Number isLessThan(final long number, final long other, final String errorMessage, final Object... args) {
		if (number >= other) {
			failedValidation(formatMessage(errorMessage, args));
		}
		return number;
	}




	/**
	 * Assert that the given number is less than than the other number
	 *
	 * @param number       the number to examine
	 * @param other        the other value
	 * @param errorMessage the error message when the number greater or equal the other number
	 * @param args         the arguments to insert into the message
	 * @return the number itself
	 */
	public Number isLessThan(final double number, final double other, final String errorMessage, final Object... args) {
		if (number >= other) {
			failedValidation(formatMessage(errorMessage, args));
		}
		return number;
	}




	/**
	 * Assert that the given object is equal the other object (uses object.equals() for comparison).
	 *
	 * @param object       the object to examine
	 * @param other        the other object
	 * @param errorMessage the error message when the two object are not equal
	 * @param args         the arguments to insert into the message
	 * @param <T>          Generic type
	 * @return the object itself
	 */
	public <T> T isEqual(final T object, final T other, final String errorMessage, final Object... args) {
		if (object == null) {
			if (other != null) {
				failedValidation(formatMessage(errorMessage, args));
			}
		} else {
			if (!object.equals(other)) {
				failedValidation(formatMessage(errorMessage, args));
			}
		}
		return object;
	}




	/**
	 * Assert that the given object is not equal the other object (uses object.equals() for comparison).
	 *
	 * @param object       the object to examine
	 * @param other        the other object
	 * @param errorMessage the error message when the two object are equal
	 * @param args         the arguments to insert into the message
	 * @param <T>          Generic type
	 * @return the object itself
	 */
	public <T> T notEqual(final T object, final T other, final String errorMessage, final Object... args) {
		if (object == null) {
			if (other == null) {
				failedValidation(formatMessage(errorMessage, args));
			}
		} else {
			if (object.equals(other)) {
				failedValidation(formatMessage(errorMessage, args));
			}
		}
		return object;
	}




	/**
	 * Checks if the given file is not null and exists.
	 *
	 * @param file         the file to examine
	 * @param errorMessage the error message when the file is null or does not exist.
	 * @param args         the arguments to insert into the message
	 * @return the file itself
	 */
	public File exists(final File file, final String errorMessage, final Object... args) {
		if (file == null || !file.exists()) {
			failedValidation(formatMessage(errorMessage, args));
		}
		return file;
	}


}
