package de.ruegnerlukas.simpleapplication.core.simpleui.assets.streams.operations;

import de.ruegnerlukas.simpleapplication.core.simpleui.assets.streams.Pipeline;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.streams.PipelineImpl;

import java.util.function.Consumer;

public class HandleErrorStream<T> extends PipelineImpl<T, T> {


	/**
	 * The handler called when an exception is thrown
	 */
	private final Consumer<Exception> handler;




	/**
	 * @param source  the source pipeline
	 * @param handler the handler called when an exception is thrown
	 */
	public HandleErrorStream(final Pipeline<?, T> source, final Consumer<Exception> handler) {
		super(source);
		this.handler = handler;
	}




	@Override
	protected void process(final T element) {
		try {
			pushElementToNext(element);
		} catch (Exception e) {
			handler.accept(e);
		}
	}

}
