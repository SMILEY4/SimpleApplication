package de.ruegnerlukas.simpleapplication.core.simpleui.assets.streams.operations;

import de.ruegnerlukas.simpleapplication.core.simpleui.assets.streams.Pipeline;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.streams.PipelineImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class WaitForStream<T> extends PipelineImpl<T, T> {


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
	public WaitForStream(final Pipeline<?, T> source, final Predicate<T> predicate, final boolean includeMatching) {
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
			for (T e : heldBackElements) {
				pushElementToNext(e);
			}
			heldBackElements.clear();
		} else {
			heldBackElements.add(element);
		}
	}

}
