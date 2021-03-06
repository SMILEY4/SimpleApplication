package de.ruegnerlukas.simpleapplication.core.simpleui.assets.streams.operations;

import de.ruegnerlukas.simpleapplication.core.simpleui.assets.streams.Pipeline;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.streams.PipelineImpl;

import java.util.function.Consumer;

public class PeekStream<T> extends PipelineImpl<T, T> {


	/**
	 * The function to run for each element.
	 */
	private final Consumer<T> consumer;




	/**
	 * @param source   the source pipeline
	 * @param consumer the function to run for each element.
	 */
	public PeekStream(final Pipeline<?, T> source, final Consumer<T> consumer) {
		super(source);
		this.consumer = consumer;
	}




	@Override
	protected void process(final T element) {
		consumer.accept(element);
		pushElementToNext(element);
	}

}
