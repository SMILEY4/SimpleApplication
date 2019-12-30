package de.ruegnerlukas.simpleapplication.common.events;

import de.ruegnerlukas.simpleapplication.common.validation.Validator;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ValidationsTest {


	private final TestValidator TEST = new TestValidator();




	@Before
	public void setup() {
		TEST.getAndReset();
	}




	@Test
	public void testNotNull() {
		TEST.notNull(new Object(), "msg");
		assertThat(TEST.getAndReset()).isFalse();

		TEST.notNull(null, "msg");
		assertThat(TEST.getAndReset()).isTrue();
	}




	@Test
	public void testIsNull() {
		TEST.isNull(null, "msg");
		assertThat(TEST.getAndReset()).isFalse();

		TEST.isNull(new Object(), "msg");
		assertThat(TEST.getAndReset()).isTrue();
	}




	@Test
	public void testNotEmptyString() {
		TEST.notEmpty("notEmpty", "msg");
		assertThat(TEST.getAndReset()).isFalse();

		TEST.notEmpty("", "msg");
		assertThat(TEST.getAndReset()).isTrue();

		TEST.notEmpty((String) null, "msg");
		assertThat(TEST.getAndReset()).isTrue();
	}




	@Test
	public void testNotBlank() {
		TEST.notBlank("notEmpty", "msg");
		assertThat(TEST.getAndReset()).isFalse();

		TEST.notBlank("", "msg");
		assertThat(TEST.getAndReset()).isTrue();

		TEST.notBlank("   ", "msg");
		assertThat(TEST.getAndReset()).isTrue();

		TEST.notBlank((String) null, "msg");
		assertThat(TEST.getAndReset()).isTrue();
	}




	@Test
	public void testNotEmptyCollection() {
		TEST.notEmpty(List.of("a", "b"), "msg");
		assertThat(TEST.getAndReset()).isFalse();

		TEST.notEmpty(List.of(), "msg");
		assertThat(TEST.getAndReset()).isTrue();

		TEST.notEmpty((ArrayList) null, "msg");
		assertThat(TEST.getAndReset()).isTrue();
	}




	@Test
	public void testNotEmptyArray() {
		TEST.notEmpty(new String[]{"a", "b"}, "msg");
		assertThat(TEST.getAndReset()).isFalse();

		TEST.notEmpty(new String[]{}, "msg");
		assertThat(TEST.getAndReset()).isTrue();

		TEST.notEmpty((String[]) null, "msg");
		assertThat(TEST.getAndReset()).isTrue();
	}




	@Test
	public void testIsEmptyString() {
		TEST.isEmpty("notEmpty", "msg");
		assertThat(TEST.getAndReset()).isTrue();

		TEST.isEmpty("", "msg");
		assertThat(TEST.getAndReset()).isFalse();

		TEST.isEmpty((String) null, "msg");
		assertThat(TEST.getAndReset()).isFalse();
	}




	@Test
	public void testIsBlank() {
		TEST.isBlank("notEmpty", "msg");
		assertThat(TEST.getAndReset()).isTrue();

		TEST.isBlank("", "msg");
		assertThat(TEST.getAndReset()).isFalse();

		TEST.isBlank("   ", "msg");
		assertThat(TEST.getAndReset()).isFalse();

		TEST.isBlank((String) null, "msg");
		assertThat(TEST.getAndReset()).isFalse();
	}




	@Test
	public void testIsEmptyCollection() {
		TEST.isEmpty(List.of("a", "b"), "msg");
		assertThat(TEST.getAndReset()).isTrue();

		TEST.isEmpty(List.of(), "msg");
		assertThat(TEST.getAndReset()).isFalse();

		TEST.isEmpty((ArrayList) null, "msg");
		assertThat(TEST.getAndReset()).isFalse();
	}




	@Test
	public void testIsEmptyArray() {
		TEST.isEmpty(new String[]{"a", "b"}, "msg");
		assertThat(TEST.getAndReset()).isTrue();

		TEST.isEmpty(new String[]{}, "msg");
		assertThat(TEST.getAndReset()).isFalse();

		TEST.isEmpty((String[]) null, "msg");
		assertThat(TEST.getAndReset()).isFalse();
	}




	@Test
	public void testContainsAtLeastCollection() {
		TEST.containsAtLeast(List.of("a", "b"), 1, "msg");
		assertThat(TEST.getAndReset()).isFalse();

		TEST.containsAtLeast(List.of("a", "b"), 4, "msg");
		assertThat(TEST.getAndReset()).isTrue();

		TEST.containsAtLeast((ArrayList) null, 1, "msg");
		assertThat(TEST.getAndReset()).isTrue();
	}




	@Test
	public void testContainsAtLeastArray() {
		TEST.containsAtLeast(new String[]{"a", "b"}, 1, "msg");
		assertThat(TEST.getAndReset()).isFalse();

		TEST.containsAtLeast(new String[]{"a", "b"}, 4, "msg");
		assertThat(TEST.getAndReset()).isTrue();

		TEST.containsAtLeast((String[]) null, 1, "msg");
		assertThat(TEST.getAndReset()).isTrue();
	}




	@Test
	public void testContainsAtMostCollection() {
		TEST.containsAtMost(List.of("a", "b"), 4, "msg");
		assertThat(TEST.getAndReset()).isFalse();

		TEST.containsAtMost(List.of("a", "b"), 1, "msg");
		assertThat(TEST.getAndReset()).isTrue();

		TEST.containsAtMost((ArrayList) null, 4, "msg");
		assertThat(TEST.getAndReset()).isTrue();
	}




	@Test
	public void testContainsAtMostArray() {
		TEST.containsAtMost(new String[]{"a", "b"}, 4, "msg");
		assertThat(TEST.getAndReset()).isFalse();

		TEST.containsAtMost(new String[]{"a", "b"}, 1, "msg");
		assertThat(TEST.getAndReset()).isTrue();

		TEST.containsAtMost((String[]) null, 4, "msg");
		assertThat(TEST.getAndReset()).isTrue();
	}




	@Test
	public void testContainsExactlyCollection() {
		TEST.containsExactly(List.of("a", "b"), 2, "msg");
		assertThat(TEST.getAndReset()).isFalse();

		TEST.containsExactly(List.of("a", "b"), 4, "msg");
		assertThat(TEST.getAndReset()).isTrue();

		TEST.containsExactly((ArrayList) null, 2, "msg");
		assertThat(TEST.getAndReset()).isTrue();
	}




	@Test
	public void testContainsExactlyArray() {
		TEST.containsExactly(new String[]{"a", "b"}, 2, "msg");
		assertThat(TEST.getAndReset()).isFalse();

		TEST.containsExactly(new String[]{"a", "b"}, 4, "msg");
		assertThat(TEST.getAndReset()).isTrue();

		TEST.containsExactly((String[]) null, 2, "msg");
		assertThat(TEST.getAndReset()).isTrue();
	}




	@Test
	public void testContainsNoNullCollection() {
		TEST.containsNoNull(List.of("a", "b"), "msg");
		assertThat(TEST.getAndReset()).isFalse();

		TEST.containsNoNull(List.of(), "msg");
		assertThat(TEST.getAndReset()).isFalse();

		final List<String> listWithNull = new ArrayList<>();
		listWithNull.add("a");
		listWithNull.add(null);
		listWithNull.add("b");
		TEST.containsNoNull(listWithNull, "msg");
		assertThat(TEST.getAndReset()).isTrue();

		TEST.containsNoNull((ArrayList) null, "msg");
		assertThat(TEST.getAndReset()).isTrue();
	}




	@Test
	public void testContainsNoNullrray() {
		TEST.containsNoNull(new String[]{"a", "b"}, "msg");
		assertThat(TEST.getAndReset()).isFalse();

		TEST.containsNoNull(new String[]{}, "msg");
		assertThat(TEST.getAndReset()).isFalse();

		TEST.containsNoNull(new String[]{"a", null, "b"}, "msg");
		assertThat(TEST.getAndReset()).isTrue();

		TEST.containsNoNull((String[]) null, "msg");
		assertThat(TEST.getAndReset()).isTrue();
	}




	@Test
	public void testIsTrue() {
		TEST.isTrue(true, "msg");
		assertThat(TEST.getAndReset()).isFalse();

		TEST.isTrue(false, "msg");
		assertThat(TEST.getAndReset()).isTrue();
	}




	@Test
	public void testIsFalse() {
		TEST.isFalse(false, "msg");
		assertThat(TEST.getAndReset()).isFalse();

		TEST.isFalse(true, "msg");
		assertThat(TEST.getAndReset()).isTrue();
	}




	@Test
	public void testIsNegative() {
		TEST.isNegative(-42, "msg");
		assertThat(TEST.getAndReset()).isFalse();

		TEST.isNegative(0, "msg");
		assertThat(TEST.getAndReset()).isTrue();

		TEST.isNegative(+42, "msg");
		assertThat(TEST.getAndReset()).isTrue();
	}




	@Test
	public void testIsNotNegative() {
		TEST.isNotNegative(42, "msg");
		assertThat(TEST.getAndReset()).isFalse();

		TEST.isNotNegative(0, "msg");
		assertThat(TEST.getAndReset()).isFalse();

		TEST.isNotNegative(-42, "msg");
		assertThat(TEST.getAndReset()).isTrue();
	}




	@Test
	public void testIsPositive() {
		TEST.isPositive(+42, "msg");
		assertThat(TEST.getAndReset()).isFalse();

		TEST.isPositive(0, "msg");
		assertThat(TEST.getAndReset()).isTrue();

		TEST.isPositive(-42, "msg");
		assertThat(TEST.getAndReset()).isTrue();
	}




	@Test
	public void testIsGreaterThan() {
		TEST.isGreaterThan(2, 1, "msg");
		assertThat(TEST.getAndReset()).isFalse();

		TEST.isGreaterThan(2, 2, "msg");
		assertThat(TEST.getAndReset()).isTrue();

		TEST.isGreaterThan(1, 2, "msg");
		assertThat(TEST.getAndReset()).isTrue();
	}




	@Test
	public void testIsLessThan() {
		TEST.isLessThan(1, 2, "msg");
		assertThat(TEST.getAndReset()).isFalse();

		TEST.isLessThan(2, 2, "msg");
		assertThat(TEST.getAndReset()).isTrue();

		TEST.isLessThan(2, 1, "msg");
		assertThat(TEST.getAndReset()).isTrue();
	}




	@Test
	public void testIsEqual() {

		final Object objA = new Object();
		final Object objB = new Object();

		TEST.isEqual(2, 2, "msg");
		assertThat(TEST.getAndReset()).isFalse();

		TEST.isEqual(objA, objA, "msg");
		assertThat(TEST.getAndReset()).isFalse();

		TEST.isEqual(1, 2, "msg");
		assertThat(TEST.getAndReset()).isTrue();

		TEST.isEqual("a", "b", "msg");
		assertThat(TEST.getAndReset()).isTrue();

		TEST.isEqual(objA, objB, "msg");
		assertThat(TEST.getAndReset()).isTrue();
	}




	@Test
	public void testIsNotEqual() {

		final Object objA = new Object();
		final Object objB = new Object();

		TEST.notEqual(1, 2, "msg");
		assertThat(TEST.getAndReset()).isFalse();

		TEST.notEqual("a", "b", "msg");
		assertThat(TEST.getAndReset()).isFalse();

		TEST.notEqual(objA, objB, "msg");
		assertThat(TEST.getAndReset()).isFalse();

		TEST.notEqual(2, 2, "msg");
		assertThat(TEST.getAndReset()).isTrue();

		TEST.notEqual(objA, objA, "msg");
		assertThat(TEST.getAndReset()).isTrue();
	}




	static class TestValidator extends Validator {


		private boolean failed = false;




		public boolean getAndReset() {
			final boolean wasFailed = failed;
			this.failed = false;
			return wasFailed;
		}




		@Override
		protected void failedValidation(final String message) {
			failed = true;
		}

	}


}
