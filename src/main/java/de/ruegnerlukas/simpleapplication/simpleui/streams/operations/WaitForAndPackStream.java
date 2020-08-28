package de.ruegnerlukas.simpleapplication.simpleui.streams.operations;

import de.ruegnerlukas.simpleapplication.simpleui.streams.Pipeline;
import de.ruegnerlukas.simpleapplication.simpleui.streams.PipelineImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class WaitForAndPackStream<T> extends PipelineImpl<T, List<T>> {


	/**
	 * The predicate to match.
	 */
	private final Predicate<T> predicate;

	/**
	 * whether to pass along the matching element
	 */
	private final boolean includeMatching;

	/**
	 * The held back elements waiting for a matching element.
	 */
	private final List<T> heldBackElements = new ArrayList<>();




	/**
	 * @param source          the source pipeline
	 * @param predicate       the predicate to match
	 * @param includeMatching whether to pass along the matching element
	 */
	public WaitForAndPackStream(final Pipeline<?, T> source, final Predicate<T> predicate, final boolean includeMatching) {
		super(source);
		this.predicate = predicate;
		this.includeMatching = includeMatching;
	}




	@Override
	protected void process(final T element) {
		if (predicate.test(element)) {
			if (includeMatching) {
				heldBackElements.add(element);
			}
			pushElementToNext(new ArrayList<>(heldBackElements));
			heldBackElements.clear();
		} else {
			heldBackElements.add(element);
		}
	}

}
