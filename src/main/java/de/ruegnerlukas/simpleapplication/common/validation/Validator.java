package de.ruegnerlukas.simpleapplication.common.validation;

import lombok.AllArgsConstructor;

import java.io.File;
import java.util.Collection;
import java.util.Map;

/**
 * Class for validating objects and values
 */
@AllArgsConstructor
public class Validator {


	/**
	 * The exception builder.
	 */
	private final ExceptionBuilder exceptionBuilder;




	/**
	 * Handles the result of the validation
	 *
	 * @param failed whether the validation failed
	 * @return the validation result for further actions
	 */
	private ValidationResult validated(final boolean failed) {
		return new ValidationResult(failed, exceptionBuilder);
	}




	/**
	 * Fail validation without needing to check any objects.
	 */
	public ValidationResult fail() {
		return validated(true);
	}




	/**
	 * Assert that the given object is not null.
	 *
	 * @param object the object to examine
	 * @param <T>    Generic type
	 * @return the object itself
	 */
	public <T> ValidationResult notNull(final T object) {
		return validated(object == null);
	}




	/**
	 * Assert that the given object is null.
	 *
	 * @param object the object to examine
	 * @param <T>    Generic type
	 * @return the object itself
	 */
	public <T> ValidationResult isNull(final T object) {
		return validated(object != null);
	}




	/**
	 * Assert that the given string is neither null nor empty
	 *
	 * @param string the string to examine
	 * @return the string itself
	 */
	public ValidationResult notEmpty(final String string) {
		return validated(string == null || string.isEmpty());
	}




	/**
	 * Assert that the given string is neither null nor blank
	 *
	 * @param string the string to examine
	 * @return the string itself
	 */
	public ValidationResult notBlank(final String string) {
		return validated(string == null || string.isBlank());
	}




	/**
	 * Assert that the given collection is neither null nor empty
	 *
	 * @param collection the collection to examine
	 * @return the collection itself
	 */
	public <T> ValidationResult notEmpty(final Collection<T> collection) {
		return validated(collection == null || collection.isEmpty());
	}




	/**
	 * Assert that the given array is neither null nor empty
	 *
	 * @param array the array to examine
	 * @return the array itself
	 */
	public <T> ValidationResult notEmpty(final T[] array) {
		return validated(array == null || array.length == 0);
	}




	/**
	 * Assert that the given string is null or empty
	 *
	 * @param string the string to examine
	 * @return the string itself
	 */
	public ValidationResult isEmpty(final String string) {
		return validated(string != null && !string.isEmpty());
	}




	/**
	 * Assert that the given string is null or contains only whitespaces
	 *
	 * @param string the string to examine
	 * @return the string itself
	 */
	public ValidationResult isBlank(final String string) {
		return validated(string != null && !string.isBlank());
	}




	/**
	 * Assert that the given collection is null or empty
	 *
	 * @param collection the collection to examine
	 * @return the collection itself
	 */
	public <T> ValidationResult isEmpty(final Collection<T> collection) {
		return validated(collection != null && !collection.isEmpty());
	}




	/**
	 * Assert that the given array is null or empty
	 *
	 * @param array the array to examine
	 * @return the array itself
	 */
	public <T> ValidationResult isEmpty(final T[] array) {
		return validated(array != null && array.length != 0);
	}




	/**
	 * Assert that the given collection is not null and contains at least min amount of elements
	 *
	 * @param collection the collection to examine
	 * @param min        the min amount of elements
	 * @param <T>        Generic type
	 * @return the collection itself
	 */
	public <T> ValidationResult containsAtLeast(final Collection<T> collection, final int min) {
		return validated(collection == null || collection.size() < min);
	}




	/**
	 * Assert that the given array is not null and contains at least min amount of elements
	 *
	 * @param array the array to examine
	 * @param min   the min amount of elements
	 * @param <T>   Generic type
	 * @return the array itself
	 */
	public <T> ValidationResult containsAtLeast(final T[] array, final int min) {
		return validated(array == null || array.length < min);
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
	public <T> ValidationResult containsAtMost(final Collection<T> collection, final int max) {
		return validated(collection == null || collection.size() > max);
	}




	/**
	 * Assert that the given array is not null and contains not more than max amount of elements
	 *
	 * @param array the array to examine
	 * @param max   the max amount of elements
	 * @param <T>   Generic type
	 * @return the array itself
	 */
	public <T> ValidationResult containsAtMost(final T[] array, final int max) {
		return validated(array == null || array.length > max);
	}




	/**
	 * Assert that the given collection is not null and contains exactly n amount of elements
	 *
	 * @param collection the collection to examine
	 * @param n          the amount of elements
	 * @param <T>        Generic type
	 * @return the collection itself
	 */
	public <T> ValidationResult containsExactly(final Collection<T> collection, final int n) {
		return validated(collection == null || collection.size() != n);
	}




	/**
	 * Assert that the given array is not null and contains exactly n amount of elements
	 *
	 * @param array the array to examine
	 * @param n     the amount of elements
	 * @param <T>   Generic type
	 * @return the array itself
	 */
	public <T> ValidationResult containsExactly(final T[] array, final int n) {
		return validated(array == null || array.length != n);
	}




	/**
	 * Assert that the given collection is not null and contains no null elements
	 *
	 * @param collection the collection to examine
	 * @param <T>        Generic type
	 * @return the collection itself
	 */
	public <T> ValidationResult containsNoNull(final Collection<T> collection) {
		if (collection == null) {
			return validated(true);
		} else {
			for (T element : collection) {
				if (element == null) {
					return validated(true);
				}
			}
		}
		return validated(false);
	}




	/**
	 * Assert that the given array is not null and contains no null elements
	 *
	 * @param array the array to examine
	 * @param <T>   Generic type
	 * @return the array itself
	 */
	public <T> ValidationResult containsNoNull(final T[] array) {
		if (array == null) {
			return validated(true);
		} else {
			for (int i = 0, n = array.length; i < n; i++) {
				if (array[i] == null) {
					return validated(true);
				}
			}
		}
		return validated(false);
	}




	/**
	 * Asserts that the given collection contains the given object (and is not null).
	 *
	 * @param collection the collection to examine
	 * @param obj        the object
	 * @param <T>        Generic type
	 * @return the collection itself
	 */
	public <T> ValidationResult contains(final Collection<T> collection, final T obj) {
		return validated(collection == null || !collection.contains(obj));
	}




	/**
	 * Asserts that the given array contains the given object (and is not null).
	 *
	 * @param array the array to examine
	 * @param obj   the object
	 * @param <T>   Generic type
	 * @return the array itself
	 */
	public <T> ValidationResult contains(final T[] array, final T obj) {
		if (array == null) {
			return validated(true);
		} else {
			boolean containsObject = false;
			for (final T element : array) {
				if (element != null && element.equals(obj)) {
					containsObject = true;
					break;
				}
			}
			if (!containsObject) {
				return validated(true);
			}
		}
		return validated(false);
	}




	/**
	 * Asserts that the given map contains the given key (and is not null).
	 *
	 * @param map the map to examine
	 * @param key the key
	 * @param <K> Generic type of the key
	 * @param <V> Generic type of the value
	 * @return the map itself
	 */
	public <K, V> ValidationResult containsKey(final Map<K, V> map, final K key) {
		if (map == null) {
			return validated(true);
		} else {
			if (!map.containsKey(key)) {
				return validated(true);
			}
		}
		return validated(false);
	}




	/**
	 * Asserts that the given map contains the given value (and is not null).
	 *
	 * @param map   the map to examine
	 * @param value the value
	 * @param <K>   Generic type of the key
	 * @param <V>   Generic type of the value
	 * @return the map itself
	 */
	public <K, V> ValidationResult containsValue(final Map<K, V> map, final V value) {
		if (map == null) {
			return validated(true);
		} else {
			if (!map.containsValue(value)) {
				return validated(true);
			}
		}
		return validated(false);
	}




	/**
	 * Asserts that the given collection does not contain the given object (and is not null).
	 *
	 * @param collection the collection to examine
	 * @param obj        the object
	 * @param <T>        Generic type
	 * @return the collection itself
	 */
	public <T> ValidationResult containsNot(final Collection<T> collection, final T obj) {
		return validated(collection == null || collection.contains(obj));
	}




	/**
	 * Asserts that the given array does not contain the given object (and is not null).
	 *
	 * @param array the array to examine
	 * @param obj   the object
	 * @param <T>   Generic type
	 * @return the array itself
	 */
	public <T> ValidationResult containsNot(final T[] array, final T obj) {
		if (array == null) {
			return validated(true);
		} else {
			boolean containsObject = false;
			for (final T element : array) {
				if (element != null && element.equals(obj)) {
					containsObject = true;
					break;
				}
			}
			if (containsObject) {
				return validated(true);
			}
		}
		return validated(false);
	}




	/**
	 * Asserts that the given map does not contain the given key (and is not null).
	 *
	 * @param map the map to examine
	 * @param key the key
	 * @param <K> Generic type of the key
	 * @param <V> Generic type of the value
	 * @return the map itself
	 */
	public <K, V> ValidationResult containsNotKey(final Map<K, V> map, final K key) {
		if (map == null) {
			return validated(true);
		} else {
			if (map.containsKey(key)) {
				return validated(true);
			}
		}
		return validated(false);
	}




	/**
	 * Asserts that the given map does not contain the given value (and is not null).
	 *
	 * @param map   the map to examine
	 * @param value the value
	 * @param <K>   Generic type of the key
	 * @param <V>   Generic type of the value
	 * @return the map itself
	 */
	public <K, V> ValidationResult containsNotValue(final Map<K, V> map, final V value) {
		if (map == null) {
			return validated(true);
		} else {
			if (map.containsValue(value)) {
				return validated(true);
			}
		}
		return validated(false);
	}




	/**
	 * Assert that the given boolean is true
	 *
	 * @param bool the boolean to examine
	 * @return the boolean itself
	 */
	public ValidationResult isTrue(final boolean bool) {
		return validated(!bool);
	}




	/**
	 * Assert that the given boolean is false
	 *
	 * @param bool the boolean to examine
	 * @return the boolean itself
	 */
	public ValidationResult isFalse(final boolean bool) {
		return validated(bool);
	}




	/**
	 * Assert that the given number is negative
	 *
	 * @param number the number to examine
	 * @return the number itself
	 */
	public ValidationResult isNegative(final long number) {
		return validated(number >= 0);
	}




	/**
	 * Assert that the given number is negative
	 *
	 * @param number the number to examine
	 * @return the number itself
	 */
	public ValidationResult isNegative(final double number) {
		return validated(number >= 0);
	}




	/**
	 * Assert that the given number is not negative
	 *
	 * @param number the number to examine
	 * @return the number itself
	 */
	public ValidationResult isNotNegative(final long number) {
		return validated(number < 0);
	}




	/**
	 * Assert that the given number is not negative
	 *
	 * @param number the number to examine
	 * @return the number itself
	 */
	public ValidationResult isNotNegative(final double number) {
		return validated(number < 0);
	}




	/**
	 * Assert that the given number is positive
	 *
	 * @param number the number to examine
	 * @return the number itself
	 */
	public ValidationResult isPositive(final long number) {
		return validated(number <= 0);
	}




	/**
	 * Assert that the given number is positive
	 *
	 * @param number the number to examine
	 * @return the number itself
	 */
	public ValidationResult isPositive(final double number) {
		return validated(number <= 0);
	}




	/**
	 * Assert that the given number is greater than than the other number
	 *
	 * @param number the number to examine
	 * @param other  the other value
	 * @return the number itself
	 */
	public ValidationResult isGreaterThan(final long number, final long other) {
		return validated(number <= other);
	}




	/**
	 * Assert that the given number is greater than than the other number
	 *
	 * @param number the number to examine
	 * @param other  the other value
	 * @return the number itself
	 */
	public ValidationResult isGreaterThan(final double number, final double other) {
		return validated(number <= other);
	}




	/**
	 * Assert that the given number is less than than the other number
	 *
	 * @param number the number to examine
	 * @param other  the other value
	 * @return the number itself
	 */
	public ValidationResult isLessThan(final long number, final long other) {
		return validated(number >= other);
	}




	/**
	 * Assert that the given number is less than than the other number
	 *
	 * @param number the number to examine
	 * @param other  the other value
	 * @return the number itself
	 */
	public ValidationResult isLessThan(final double number, final double other) {
		return validated(number >= other);
	}




	/**
	 * Assert that the given object is equal the other object (uses object.equals() for comparison).
	 *
	 * @param object the object to examine
	 * @param other  the other object
	 * @param <T>    Generic type
	 * @return the object itself
	 */
	public <T> ValidationResult isEqual(final T object, final T other) {
		if (object == null) {
			if (other != null) {
				return validated(true);
			}
		} else {
			if (!object.equals(other)) {
				return validated(true);
			}
		}
		return validated(false);
	}




	/**
	 * Assert that the given object is not equal the other object (uses object.equals() for comparison).
	 *
	 * @param object the object to examine
	 * @param other  the other object
	 * @param <T>    Generic type
	 * @return the object itself
	 */
	public <T> ValidationResult notEqual(final T object, final T other) {
		if (object == null) {
			if (other == null) {
				return validated(true);
			}
		} else {
			if (object.equals(other)) {
				return validated(true);
			}
		}
		return validated(false);
	}




	/**
	 * Checks if the given file is not null and exists.
	 *
	 * @param file the file to examine
	 * @return the file itself
	 */
	public ValidationResult exists(final File file) {
		return validated(file == null || !file.exists());
	}


}
