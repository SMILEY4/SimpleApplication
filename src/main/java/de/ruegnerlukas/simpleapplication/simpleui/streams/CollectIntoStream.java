package de.ruegnerlukas.simpleapplication.simpleui.streams;

import java.util.Collection;

public class CollectIntoStream<T> extends PipelineImpl<T, T> {


	/**
	 * The collection to add the elements to.
	 */
	private final Collection<T> collection;




	/**
	 * @param source     the source pipeline
	 * @param collection the collection to add the elements to
	 */
	public CollectIntoStream(final Pipeline<?, T> source, final Collection<T> collection) {
		super(source);
		this.collection = collection;
	}




	@Override
	protected void process(final T element) {
		collection.add(element);
	}

}
