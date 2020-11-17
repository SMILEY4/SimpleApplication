package de.ruegnerlukas.simpleapplication.core.simpleui.assets.streams.operations;

import de.ruegnerlukas.simpleapplication.core.simpleui.assets.streams.Pipeline;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.streams.PipelineImpl;

import java.util.ArrayList;
import java.util.List;

public class LastNStream<T> extends PipelineImpl<T, List<T>> {


	/**
	 * The number of elements to keep
	 */
	private final int n;

	/**
	 * The held back elements waiting for a matching element.
	 */
	private final List<T> heldBackElements;




	/**
	 * @param source the source pipeline
	 * @param n      the number of elements  to keep
	 */
	public LastNStream(final Pipeline<?, T> source, final int n) {
		super(source);
		this.n = n;
		this.heldBackElements = new ArrayList<>(n);
	}




	@Override
	protected void process(final T element) {
		heldBackElements.add(element);
		if (heldBackElements.size() > n) {
			heldBackElements.remove(0);
		}
		pushElementToNext(new ArrayList<>(heldBackElements));
	}

}
