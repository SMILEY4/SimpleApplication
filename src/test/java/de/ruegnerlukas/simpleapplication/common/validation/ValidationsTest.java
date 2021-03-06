package de.ruegnerlukas.simpleapplication.common.validation;


import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValidationsTest {


	@Test
	public void testNotNull() {
		assertFalse(Validations.STATE.notNull(new Object()).failed());
		assertTrue(Validations.STATE.notNull(null).failed());
	}




	@Test
	public void testIsNull() {
		assertFalse(Validations.STATE.isNull(null).failed());
		assertTrue(Validations.STATE.isNull(new Object()).failed());
	}




	@Test
	public void testNotEmptyString() {
		assertFalse(Validations.STATE.notEmpty("notEmpty").failed());
		assertTrue(Validations.STATE.notEmpty("").failed());
		assertTrue(Validations.STATE.notEmpty((String) null).failed());
	}




	@Test
	public void testNotBlank() {
		assertFalse(Validations.STATE.notBlank("notEmpty").failed());
		assertTrue(Validations.STATE.notBlank("").failed());
		assertTrue(Validations.STATE.notBlank("   ").failed());
		assertTrue(Validations.STATE.notBlank((String) null).failed());
	}




	@Test
	public void testNotEmptyCollection() {
		assertFalse(Validations.STATE.notEmpty(List.of("a", "b")).failed());
		assertTrue(Validations.STATE.notEmpty(List.of()).failed());
		assertTrue(Validations.STATE.notEmpty((ArrayList) null).failed());
	}




	@Test
	public void testNotEmptyArray() {
		assertFalse(Validations.STATE.notEmpty(new String[]{"a", "b"}).failed());
		assertTrue(Validations.STATE.notEmpty(new String[]{}).failed());
		assertTrue(Validations.STATE.notEmpty((String[]) null).failed());
	}




	@Test
	public void testIsEmptyString() {
		assertTrue(Validations.STATE.isEmpty("notEmpty").failed());
		assertFalse(Validations.STATE.isEmpty("").failed());
		assertFalse(Validations.STATE.isEmpty((String) null).failed());
	}




	@Test
	public void testIsBlank() {
		assertTrue(Validations.STATE.isBlank("notEmpty").failed());
		assertFalse(Validations.STATE.isBlank("").failed());
		assertFalse(Validations.STATE.isBlank("   ").failed());
		assertFalse(Validations.STATE.isBlank((String) null).failed());
	}




	@Test
	public void testIsEmptyCollection() {
		assertTrue(Validations.STATE.isEmpty(List.of("a", "b")).failed());
		assertFalse(Validations.STATE.isEmpty(List.of()).failed());
		assertFalse(Validations.STATE.isEmpty((ArrayList) null).failed());
	}




	@Test
	public void testIsEmptyArray() {
		assertTrue(Validations.STATE.isEmpty(new String[]{"a", "b"}).failed());
		assertFalse(Validations.STATE.isEmpty(new String[]{}).failed());
		assertFalse(Validations.STATE.isEmpty((String[]) null).failed());
	}




	@Test
	public void testContainsAtLeastCollection() {
		assertFalse(Validations.STATE.containsAtLeast(List.of("a", "b"), 1).failed());
		assertFalse(Validations.STATE.containsAtLeast(List.of("a", "b"), 2).failed());
		assertTrue(Validations.STATE.containsAtLeast(List.of("a", "b"), 4).failed());
		assertTrue(Validations.STATE.containsAtLeast((ArrayList) null, 1).failed());
	}




	@Test
	public void testContainsAtLeastArray() {
		assertFalse(Validations.STATE.containsAtLeast(new String[]{"a", "b"}, 1).failed());
		assertFalse(Validations.STATE.containsAtLeast(new String[]{"a", "b"}, 2).failed());
		assertTrue(Validations.STATE.containsAtLeast(new String[]{"a", "b"}, 4).failed());
		assertTrue(Validations.STATE.containsAtLeast((String[]) null, 1).failed());
	}




	@Test
	public void testContainsAtMostCollection() {
		assertFalse(Validations.STATE.containsAtMost(List.of("a", "b"), 4).failed());
		assertFalse(Validations.STATE.containsAtMost(List.of("a", "b"), 2).failed());
		assertTrue(Validations.STATE.containsAtMost(List.of("a", "b"), 1).failed());
		assertTrue(Validations.STATE.containsAtMost((ArrayList) null, 4).failed());
	}




	@Test
	public void testContainsAtMostArray() {
		assertFalse(Validations.STATE.containsAtMost(new String[]{"a", "b"}, 4).failed());
		assertFalse(Validations.STATE.containsAtMost(new String[]{"a", "b"}, 2).failed());
		assertTrue(Validations.STATE.containsAtMost(new String[]{"a", "b"}, 1).failed());
		assertTrue(Validations.STATE.containsAtMost((String[]) null, 4).failed());
	}




	@Test
	public void testContainsExactlyCollection() {
		assertFalse(Validations.STATE.hasSize(List.of("a", "b"), 2).failed());
		assertTrue(Validations.STATE.hasSize(List.of("a", "b"), 4).failed());
		assertTrue(Validations.STATE.hasSize((ArrayList) null, 2).failed());
	}




	@Test
	public void testContainsExactlyArray() {
		assertFalse(Validations.STATE.hasSize(new String[]{"a", "b"}, 2).failed());
		assertTrue(Validations.STATE.hasSize(new String[]{"a", "b"}, 4).failed());
		assertTrue(Validations.STATE.hasSize((String[]) null, 2).failed());
	}




	@Test
	public void testContainsNoNullCollection() {
		assertFalse(Validations.STATE.containsNoNull(List.of("a", "b")).failed());
		assertFalse(Validations.STATE.containsNoNull(List.of()).failed());
		final List<String> listWithNull = new ArrayList<>();
		listWithNull.add("a");
		listWithNull.add(null);
		listWithNull.add("b");
		assertTrue(Validations.STATE.containsNoNull(listWithNull).failed());
		assertTrue(Validations.STATE.containsNoNull((ArrayList) null).failed());
	}




	@Test
	public void testContainsNoNullArray() {
		assertFalse(Validations.STATE.containsNoNull(new String[]{"a", "b"}).failed());
		assertFalse(Validations.STATE.containsNoNull(new String[]{}).failed());
		assertTrue(Validations.STATE.containsNoNull(new String[]{"a", null, "b"}).failed());
		assertTrue(Validations.STATE.containsNoNull((String[]) null).failed());
	}




	@Test
	public void testContainsCollection() {
		assertFalse(Validations.STATE.contains(List.of("a", "b", "c"), "b").failed());
		assertTrue(Validations.STATE.contains(List.of("a", "b", "c"), "x").failed());
		assertTrue(Validations.STATE.contains(List.of(), "e").failed());
		assertTrue(Validations.STATE.contains((ArrayList) null, "e").failed());
	}




	@Test
	public void testContainsArray() {
		assertFalse(Validations.STATE.contains(new String[]{"a", "b", "c"}, "b").failed());
		assertTrue(Validations.STATE.contains(new String[]{"a", "b", "c"}, "x").failed());
		assertTrue(Validations.STATE.contains(new String[]{}, "x").failed());
		assertTrue(Validations.STATE.contains((String[]) null, "e").failed());
	}




	@Test
	public void testContainsKey() {
		assertFalse(Validations.STATE.containsKey(Map.of("a", 1, "b", 2, "c", 3), "b").failed());
		assertTrue(Validations.STATE.containsKey(Map.of("a", 1, "b", 2, "c", 3), "x").failed());
		assertTrue(Validations.STATE.containsKey(Map.of(), "e").failed());
		assertTrue(Validations.STATE.containsKey((HashMap) null, "e").failed());
	}




	@Test
	public void testContainsValue() {
		assertFalse(Validations.STATE.containsValue(Map.of("a", 1, "b", 2, "c", 3), 2).failed());
		assertTrue(Validations.STATE.containsValue(Map.of("a", 1, "b", 2, "c", 3), 0).failed());
		assertTrue(Validations.STATE.containsValue(Map.of(), 0).failed());
		assertTrue(Validations.STATE.containsValue((HashMap) null, 0).failed());
	}




	@Test
	public void testContainsNotCollection() {
		assertTrue(Validations.STATE.containsNot(List.of("a", "b", "c"), "b").failed());
		assertFalse(Validations.STATE.containsNot(List.of("a", "b", "c"), "x").failed());
		assertFalse(Validations.STATE.containsNot(List.of(), "e").failed());
		assertTrue(Validations.STATE.containsNot((ArrayList) null, "e").failed());
	}




	@Test
	public void testContainsNotArray() {
		assertTrue(Validations.STATE.containsNot(new String[]{"a", "b", "c"}, "b").failed());
		assertFalse(Validations.STATE.containsNot(new String[]{"a", "b", "c"}, "x").failed());
		assertFalse(Validations.STATE.containsNot(new String[]{}, "x").failed());
		assertTrue(Validations.STATE.containsNot((String[]) null, "e").failed());
	}




	@Test
	public void testContainsNotKey() {
		assertTrue(Validations.STATE.containsNotKey(Map.of("a", 1, "b", 2, "c", 3), "b").failed());
		assertFalse(Validations.STATE.containsNotKey(Map.of("a", 1, "b", 2, "c", 3), "x").failed());
		assertFalse(Validations.STATE.containsNotKey(Map.of(), "e").failed());
		assertTrue(Validations.STATE.containsNotKey((HashMap) null, "e").failed());
	}




	@Test
	public void testContainsNotValue() {
		assertTrue(Validations.STATE.containsNotValue(Map.of("a", 1, "b", 2, "c", 3), 2).failed());
		assertFalse(Validations.STATE.containsNotValue(Map.of("a", 1, "b", 2, "c", 3), 0).failed());
		assertFalse(Validations.STATE.containsNotValue(Map.of(), 0).failed());
		assertTrue(Validations.STATE.containsNotValue((HashMap) null, 0).failed());
	}




	@Test
	public void testIsValidIndex() {
		assertTrue(Validations.STATE.isValidIndex(-3, List.of(1, 2, 3, 4, 5)).failed());
		assertTrue(Validations.STATE.isValidIndex(10, List.of(1, 2, 3, 4, 5)).failed());
		assertTrue(Validations.STATE.isValidIndex(5, List.of(1, 2, 3, 4, 5)).failed());
		assertTrue(Validations.STATE.isValidIndex(0, List.of(1, 2, 3, 4, 5)).successful());
		assertTrue(Validations.STATE.isValidIndex(3, List.of(1, 2, 3, 4, 5)).successful());
	}




	@Test
	public void testIsTrue() {
		assertFalse(Validations.STATE.isTrue(true).failed());
		assertTrue(Validations.STATE.isTrue(false).failed());
	}




	@Test
	public void testIsFalse() {
		assertFalse(Validations.STATE.isFalse(false).failed());
		assertTrue(Validations.STATE.isFalse(true).failed());
	}




	@Test
	public void testIsNegative() {
		assertFalse(Validations.STATE.isNegative(-42).failed());
		assertTrue(Validations.STATE.isNegative(0).failed());
		assertTrue(Validations.STATE.isNegative(+42).failed());
	}




	@Test
	public void testIsNotNegative() {
		assertFalse(Validations.STATE.isNotNegative(42).failed());
		assertFalse(Validations.STATE.isNotNegative(0).failed());
		assertTrue(Validations.STATE.isNotNegative(-42).failed());
	}




	@Test
	public void testIsPositive() {
		assertFalse(Validations.STATE.isPositive(+42).failed());
		assertTrue(Validations.STATE.isPositive(0).failed());
		assertTrue(Validations.STATE.isPositive(-42).failed());
	}




	@Test
	public void testIsGreaterThan() {
		assertFalse(Validations.STATE.isGreaterThan(2, 1).failed());
		assertTrue(Validations.STATE.isGreaterThan(2, 2).failed());
		assertTrue(Validations.STATE.isGreaterThan(1, 2).failed());
	}




	@Test
	public void testIsLessThan() {
		assertFalse(Validations.STATE.isLessThan(1, 2).failed());
		assertTrue(Validations.STATE.isLessThan(2, 2).failed());
		assertTrue(Validations.STATE.isLessThan(2, 1).failed());
	}




	@Test
	public void testIsEqual() {

		final Object objA = new Object();
		final Object objB = new Object();

		assertFalse(Validations.STATE.isEqual(2, 2).failed());
		assertFalse(Validations.STATE.isEqual(objA, objA).failed());
		assertFalse(Validations.STATE.isEqual(null, null).failed());
		assertTrue(Validations.STATE.isEqual(1, 2).failed());
		assertTrue(Validations.STATE.isEqual("a", "b").failed());
		assertTrue(Validations.STATE.isEqual(objA, objB).failed());
		assertTrue(Validations.STATE.isEqual(objA, null).failed());
		assertTrue(Validations.STATE.isEqual(null, objB).failed());
	}




	@Test
	public void testIsNotEqual() {

		final Object objA = new Object();
		final Object objB = new Object();

		assertFalse(Validations.STATE.notEqual(1, 2).failed());
		assertFalse(Validations.STATE.notEqual("a", "b").failed());
		assertFalse(Validations.STATE.notEqual(objA, objB).failed());
		assertFalse(Validations.STATE.notEqual(objA, null).failed());
		assertFalse(Validations.STATE.notEqual(null, objA).failed());
		assertTrue(Validations.STATE.notEqual(null, null).failed());
		assertTrue(Validations.STATE.notEqual(2, 2).failed());
		assertTrue(Validations.STATE.notEqual(objA, objA).failed());
	}




	@Test
	public void testTypeOf() {

		class A {


		}

		class A1 extends A {


		}

		class A2 extends A {


		}

		final Object a = new A();
		final Object a1 = new A1();

		assertFalse(Validations.STATE.typeOf(a, A.class).failed());
		assertFalse(Validations.STATE.typeOf(a1, A.class).failed());
		assertTrue(Validations.STATE.typeOf(a, A1.class).failed());
		assertTrue(Validations.STATE.typeOf(a1, A2.class).failed());
		assertTrue(Validations.STATE.typeOf(null, A2.class).failed());

	}




	@Test
	public void testTypeOfAllowNull() {
		class A {


		}

		class A1 extends A {


		}

		class A2 extends A {


		}

		final Object a = new A();
		final Object a1 = new A1();

		assertTrue(Validations.STATE.typeOfAllowNull(a, A.class).successful());
		assertTrue(Validations.STATE.typeOfAllowNull(a1, A.class).successful());
		assertTrue(Validations.STATE.typeOfAllowNull(a, A1.class).failed());
		assertTrue(Validations.STATE.typeOfAllowNull(a1, A2.class).failed());
		assertTrue(Validations.STATE.typeOfAllowNull(null, A2.class).successful());

	}




	@Test
	public void testIsPresent() {
		final Optional<Object> optionalPresent = Optional.of(new Object());
		final Optional<Object> optionalNull = Optional.empty();
		assertTrue(Validations.STATE.isPresent(optionalPresent).successful());
		assertTrue(Validations.STATE.isPresent(optionalNull).failed());
	}




	@Test
	public void testIsNotPresent() {
		final Optional<Object> optionalPresent = Optional.of(new Object());
		final Optional<Object> optionalNull = Optional.empty();
		assertTrue(Validations.STATE.isNotPresent(optionalPresent).failed());
		assertTrue(Validations.STATE.isNotPresent(optionalNull).successful());
	}




	@Test
	public void testAllSuccessful() {
		final ValidationResult resultSuccessful = Validations.INPUT.allSuccessful(
				Validations.INPUT.isTrue(true),
				Validations.INPUT.isTrue(true),
				Validations.INPUT.isTrue(true),
				Validations.INPUT.isTrue(true)
		);
		assertTrue(resultSuccessful.successful());

		final ValidationResult resultFailed = Validations.INPUT.allSuccessful(
				Validations.INPUT.isTrue(true),
				Validations.INPUT.isTrue(true),
				Validations.INPUT.isTrue(false),
				Validations.INPUT.isTrue(true)
		);
		assertTrue(resultFailed.failed());
	}




	@Test
	public void testResultFailed() {
		assertTrue(Validations.STATE.fail().failed());
		assertFalse(Validations.STATE.succeed().failed());
	}




	@Test
	public void testResultThen() {
		Validations.STATE.fail().then(Assert::assertTrue);
		Validations.STATE.succeed().then(Assert::assertFalse);
	}




	@Test
	public void testResultOnFail() {
		Validations.STATE.fail().onFail(() -> {
		});
		Validations.STATE.succeed().onFail(() -> Assertions.fail(""));
	}




	@Test
	public void testResultOnSuccess() {
		Validations.STATE.fail().onSuccess(() -> Assertions.fail(""));
		Validations.STATE.succeed().onSuccess(() -> {
		});
	}




	@Test (expected = ValidateInputException.class)
	public void testResultExceptionINPUT() {
		Validations.INPUT.fail().exception();
	}




	@Test (expected = ValidateStateException.class)
	public void testResultExceptionSTATE() {
		Validations.STATE.fail().exception();
	}




	@Test (expected = ValidatePresenceException.class)
	public void testResultExceptionPRESENCE() {
		Validations.PRESENCE.fail().exception();
	}




	@Test (expected = ValidateInputException.class)
	public void testResultExceptionMsgINPUT() {
		Validations.INPUT.fail().exception("Msg");
	}




	@Test (expected = ValidateStateException.class)
	public void testResultExceptionMsgSTATE() {
		Validations.STATE.fail().exception("Msg");
	}




	@Test (expected = ValidatePresenceException.class)
	public void testResultExceptionMsgPRESENCE() {
		Validations.PRESENCE.fail().exception("Msg");
	}




	@Test (expected = IllegalArgumentException.class)
	public void testResultCustomException() {
		Validations.INPUT.fail().exception(new IllegalArgumentException());
	}

}
