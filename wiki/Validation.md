

## Validations

The Validation-class provides a simple but powerful way to check data for validity and to an action in case of a failure, e.g. logging, throwing exceptions, or calling a method.  



### 1. Validator Types

There are three "different" validators that all have the same capability, but can be used in different situations.

```java
Validations.INPUT...    // for validating input data or method parameters. Throws a ValidateInputException by default.
Validations.STATE...    // for validating state. Throws a ValidateStateException by default.
Validations.PRESENCE... // for validating presence. Throws a ValidatePresenceException by default.
```



### 2. Validation Operations

These operations take an object to examine and determine, if it is valid or invalid. Actions can then be triggered with the resulting "ValidationResult" (see 3.).

Example:

```java
void doSomething(String strInput) {
        // check if "strInput" is null. Throw the default exception for INPUT if the string is null.
    Validations.INPUT.notNull(strInput).exception();
    ...
}
```

- ##### fail

  - The result is always a failed validation.

- ##### succeed

  - The result is always a successful validation.

- ##### notNull

  - Applicable to any object.

  - The given object must not be null.

- ##### isNull

  - Applicable to any object.
  - The given object must be null.

- ##### isPresent

  - Applicable to any optional.
  - The given optional  must have a value.

- ##### isNotPresent

  - Applicable to any optional.
  - The given optional must not have a value.

- ##### notEmpty

  - Applicable to strings, arrays and collections.
  - The given object must not be null.
  - A given string must have at least one character.
  - A given collection/array must have a size/length greater 0.

- ##### isEmpty

  - Applicable to strings, arrays and collections.
  - The given object must not be null.
  - A given string must have no character, i.e. a length of 0.
  - A given collection/array must have a size/length of 0.

- ##### notBlank

  - Applicable to strings.
  - The given string must not be null.
  - The given string must have at least one character that is not an empty space.

- ##### isBlank

  - Applicable to strings.
  - The given string must not be null.
  - The given string must have no character or only empty space characters.

- ##### containsAtLeast

  - Applicable to arrays and collections.
  - The given array/collection must not be null.
  - The given array or collection must have at least a given amount of elements.

- ##### containsAtMost

  - Applicable to arrays and collections.
  - The given array/collection must not be null.
  - The given array or collection must have not more than the given amount of elements.

- ##### hasSize

  - Applicable to arrays and collections
  - The given array/collection must not be null.
  - The given array or collection must have exactly the given size/length.

- ##### containsNoNull

  - Applicable to arrays and collections
  - The given array/collection must not be null.
  - The given array or collection must contain no element = null.

- ##### contains

  - Applicable to arrays and collections
  - The given array/collection must not be null.
  - The given array or collection must contain the given object at least once.

- ##### containsNot

  - Applicable to arrays and collections
  - The given array/collection must not be null.
  - The given array or collection must not contain the given object.

- ##### containsKey

  - Applicable to maps.
  - The given map must not be null.
  - The map must contain the given key.

- ##### containsNotKey

  - Applicable to maps.
  - The given map must not be null.
  - The map must not contain the given key.

- ##### containsValue

  - Applicable to maps.
  - The given map must not be null.
  - The map must contain the given value at least once.

- ##### containsNotValue

  - Applicable to maps.
  - The given map must not be null.
  - The map must not contain the given value.

- ##### isValidIndex

  - Applicable to integers.
  - The given integer must be a valid index into the given collection or into a collection with the given size.

- ##### isTrue

  - Applicable to booleans.
  - The boolean must be "true".

- ##### isFalse

  - Applicable to booleans.
  - The boolean must be "false".

- ##### isNegative

  - Applicable to longs and doubles (i.e. any number).
  - The number must be less than 0.

- ##### isNotNegative

  - Applicable to longs and doubles (i.e. any number)
  - The number must be greater or equal than 0.

- ##### isPositive

  - Applicable to longs and doubles (i.e. any number)
  - The number must be greater than 0.

- ##### isGreaterThan

  - Applicable to longs and doubles (i.e. any number)
  - The number must be greater than the given value.

- ##### isLessThan

  - Applicable to longs and doubles (i.e. any number)
  - The number must be less than the given value

- ##### inRange

  - Applicable to longs and doubles (i.e. any number)
  - The number must be greater or equal to the min value and less than or equal to the max value.

- ##### isEqual

  - Applicable to any object.
  - The object must be equal to the given other object. If both objects are 'null' the validation will also evaluate to succesful.

- ##### notEqual

  - Applicable to any object.
  - The object must not be equal to the given other object. If both objects are 'null' the validation will also fail.

- ##### exists

  - Applicable to files.
  - The file must not be null.
  - The file must exist.

- ##### typeOf

  - Applicable to any object.
  - The object must not be null.
  - The object must be of the given type.

- ##### typeOfAllowNull

  - Applicable to any object.
  - The object must be of the given type or null.

- ##### allSuccessful

  - Applicable to multiple ValidationResults
  - All given results must be successful



### 3. Validation Actions

Actions that can be triggered when a validation was completed. Either on a failed or successful validation (or on both).

- ##### log the failed validation with a custom message

  Prints the given message to the log using Slf4j at the log-level 'warn'.

  ```java
  Validation.INPUT.notNull(null).log("My log message.");
  Validation.INPUT.notNull(null).log("Validation Result: {}.", "FAIL");
  ```

- ##### throw exception

  Throws the default exception of the used validator without an additional error message.

  ```java
  Validation.INPUT.notNull(null).exception();     // -> ValidateInputException
  Validation.STATE.notNull(null).exception();     // -> ValidateStateException
  Validation.PRESENCE.notNull(null).exception();  // -> ValidatePresenceException
  ```

- ##### throw exception with message

  Throws the default exception of the used validator with the given additional error message.

  ```java
  Validation.INPUT.notNull(null).exception("Oops");                                    // -> ValidateInputException
  Validation.STATE.contains("a", myList).exception("List did not contain: {}.", "a");  // -> ValidateStateException
  Validation.PRESENCE.notNegative(5-10).exception("{}-{} is > 0.", 5, 10);             // -> ValidatePresenceException
  ```

- ##### throw custom exception

  Throws the given exception.

  ```java
  Validation.INPUT.notNull(null).exception(new IllegalArgumentException("Value was null"));   // -> IllegalArgumentException
  ```

- ##### get validation result as boolean

  ```java
  Validation.INPUT.notNull(null).failed();            // -> true
  Validation.INPUT.notNull(null).successful();        // -> false
  Validation.INPUT.notNull("not null").failed();      // -> false
  Validation.INPUT.notNull("not null").successful();  // -> true
  ```

- ##### call a function regardless of the result

  Calls the given function with the result of the validation as a boolean. Returns the same ValidationResult for chaining. 

  ```java
  Validation.INPUT.notNull(null).then(failed -> {
      // parameter "failed" is true when the validation did fail.
  });
  
  Validation.INPUT.notNull(null).then(failed -> {
      // ... do something here
  }).log("The value was null."); // and then log the message if the validation failed.
  ```

- ##### call a function when the validation failed

  Calls the given function when the validation failed. Returns the same ValidationResult for chaining. 

  ```java
  Validation.INPUT.notNull(null).onFail(() -> {
      // ... do something here if the validation failed
  });
  
  Validation.INPUT.notNull(null).onFail(() -> {
      // ... do something here if the validation failed
  }).log("The value was null."); // and then log the message if the validation failed.
  ```

- ##### call a function when the validation was successful

  Calls the given function when the validation was successful. Returns the same ValidationResult for chaining. 

  ```java
  Validation.INPUT.notNull("not null").onSuccess(() -> {
      // ... do something here if the validation was successful
  });
  
  Validation.INPUT.notNull("not null").onSuccess(() -> {
      // ... do something here if the validation was successful
  }).log("The value was null."); // and then log the message if the validation failed (in this case never).
  ```

  