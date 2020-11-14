package de.ruegnerlukas.simpleapplication.core.simpleui.core.profiler;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public class SuiProfiler {


	/**
	 * The current instance.
	 */
	private static SuiProfiler instance = null;




	/**
	 * Get the instance of the profiler. If none was initialized yet, it will be initialized with the default implementation.
	 *
	 * @return an instance of the profiler
	 */
	public static SuiProfiler get() {
		if (instance == null) {
			initializeDefault();
		}
		return instance;
	}




	/**
	 * Initialize the instance of the profiler with the default implementation.
	 */
	public static void initializeDefault() {
		initialize(new SuiProfiler());
	}




	/**
	 * Initialize the instance of the profiler with the given implementation.
	 */
	public static void initialize(final SuiProfiler instance) {
		SuiProfiler.instance = instance;
	}




	/**
	 * Disposes of the current profiler instance.
	 */
	public static void dispose() {
		instance = null;
	}




	/**
	 * The string builder instance.
	 */
	private final StringBuilder stringBuilder = new StringBuilder();


	/**
	 * Counts how often a javafx node was (re-)build.
	 */
	@Getter
	private long counterFxNodeBuild = 0;


	/**
	 * Counts how often a property was build / was applied to a javafx node for the first time.
	 */
	@Getter
	private long counterPropertyBuild = 0;


	/**
	 * Count how often a property was added to a node or an existing property was modified.
	 */
	@Getter
	private long counterPropertyUpsert = 0;

	/**
	 * Count how often a property was removed from an existing node.
	 */
	@Getter
	private long counterPropertyRemoved = 0;


	/**
	 * Count how often the scene was mutated due to a state update.
	 */
	@Getter
	private long counterSceneMutated = 0;

	/**
	 * The duration how long the scene builds take (in milliseconds).
	 */
	@Getter
	private ComplexSampleValue buildDuration = new ComplexSampleValue();

	/**
	 * The duration how long the scene mutations take (in milliseconds).
	 */
	@Getter
	private ComplexSampleValue mutationDuration = new ComplexSampleValue();




	/**
	 * @return the current statistics as a (multiline) string
	 */
	public String getStatisticsAsPrettyString() {
		stringBuilder.setLength(0);
		stringBuilder.append("    fx-node-build: ").append(counterFxNodeBuild).append(System.lineSeparator());
		stringBuilder.append("       prop-build: ").append(counterPropertyBuild).append(System.lineSeparator());
		stringBuilder.append("      prop-upsert: ").append(counterPropertyUpsert).append(System.lineSeparator());
		stringBuilder.append("      prop-remove: ").append(counterPropertyRemoved).append(System.lineSeparator());
		stringBuilder.append("    scene-mutated: ").append(counterSceneMutated).append(System.lineSeparator());

		stringBuilder.append(" scene-build-time: ")
				.append("last: ").append(buildDuration.last).append("ms, ")
				.append("avg: ").append(buildDuration.avg).append("ms, ")
				.append("min: ").append(buildDuration.min).append("ms, ")
				.append("max: ").append(buildDuration.max).append("ms, ")
				.append(System.lineSeparator());

		stringBuilder.append("scene-mutate-time: ")
				.append("last: ").append(mutationDuration.last).append("ms, ")
				.append("avg: ").append(mutationDuration.avg).append("ms, ")
				.append("min: ").append(mutationDuration.min).append("ms, ")
				.append("max: ").append(mutationDuration.max).append("ms, ")
				.append(System.lineSeparator());

		return stringBuilder.toString();
	}




	/**
	 * Reset all counters.
	 */
	public void resetAll() {
		resetFxNodeBuild();
		resetPropertyBuild();
		resetPropertyUpsert();
		resetPropertyRemoved();
		resetSceneMutated();
	}




	/**
	 * Counts how often a javafx node was (re-)build.
	 */
	public void countFxNodeBuild() {
		counterFxNodeBuild++;
	}




	/**
	 * Reset the counter how often a javafx node was (re-)build.
	 */
	public void resetFxNodeBuild() {
		counterFxNodeBuild = 0;
	}




	/**
	 * Counts how often a property was build / was applied to a javafx node for the first time.
	 */
	public void countPropertyBuild() {
		counterPropertyBuild++;
	}




	/**
	 * Reset the counter how often a property was build / was applied to a javafx node for the first time.
	 */
	public void resetPropertyBuild() {
		counterPropertyBuild = 0;
	}




	/**
	 * Count how often a property was added to a node or an existing property was modified.
	 */
	public void countPropertyUpsert() {
		counterPropertyUpsert++;
	}




	/**
	 * Reset the counter how often a property was added to a node or an existing property was modified.
	 */
	public void resetPropertyUpsert() {
		counterPropertyUpsert = 0;
	}




	/**
	 * Count how often a property was removed from an existing node.
	 */
	public void countPropertyRemoved() {
		counterPropertyRemoved++;
	}




	/**
	 * Reset the counter how often a property was removed from an existing node.
	 */
	public void resetPropertyRemoved() {
		counterPropertyRemoved = 0;
	}




	/**
	 * Count how often the scene was mutated due to a state update.
	 */
	public void countSceneMutated() {
		counterSceneMutated++;
	}




	/**
	 * Resets the counter how often the scene was mutated due to a state update.
	 */
	public void resetSceneMutated() {
		counterSceneMutated = 0;
	}




	@Getter
	@Setter (AccessLevel.PROTECTED)
	public static class ComplexSampleValue {


		/**
		 * The last value.
		 */
		private long last = -1;

		/**
		 * The smallest value.
		 */
		private long min = Long.MAX_VALUE;

		/**
		 * The largest value.
		 */
		private long max = -1;

		/**
		 * The average of all values.
		 */
		private double avg = -1;




		/**
		 * Count the given value.
		 *
		 * @param value the value
		 */
		public void count(final long value) {
			last = value;
			min = Math.min(min, value);
			max = Math.max(max, value);
			avg = avg < 0 ? value : (avg + value) / 2.0;
		}

	}


}
