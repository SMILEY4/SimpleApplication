package de.ruegnerlukas.simpleapplication.core.persistence.json;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Random;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DummyObject {


	/**
	 * @return a completely random created dummy object.
	 */
	public static DummyObject random() {
		return random(new Random().nextLong());
	}




	/**
	 * @return a random created dummy object /from the given seed).
	 */
	public static DummyObject random(final long seed) {
		final Random random = new Random(seed);
		return DummyObject.builder()
				.name("Dummy Object #" + random.nextInt())
				.timestamp(System.currentTimeMillis())
				.someNumber(random.nextInt())
				.build();
	}




	/**
	 * a string field
	 */
	private String name;

	/**
	 * a long field (here always the timestamp in ms)
	 */
	private long timestamp;

	/**
	 * a integer field
	 */
	private int someNumber;

}
