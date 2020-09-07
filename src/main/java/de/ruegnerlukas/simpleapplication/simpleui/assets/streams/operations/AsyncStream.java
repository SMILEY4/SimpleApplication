package de.ruegnerlukas.simpleapplication.simpleui.assets.streams.operations;

import de.ruegnerlukas.simpleapplication.simpleui.assets.streams.Pipeline;
import de.ruegnerlukas.simpleapplication.simpleui.assets.streams.PipelineImpl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncStream<T> extends PipelineImpl<T, T> {


	/**
	 * The excecutor for async tasks.
	 */
	private final ExecutorService executor;




	/**
	 * @param source   the source pipeline
	 * @param poolSize the size of the thread pool. Set to -1 to not limit the amount of threads.
	 */
	public AsyncStream(final Pipeline<?, T> source, final int poolSize) {
		super(source);
		if (poolSize == -1) {
			executor = Executors.newCachedThreadPool();
		} else {
			executor = Executors.newFixedThreadPool(poolSize);

		}
	}




	@Override
	protected void process(final T element) {
		executor.submit(() -> pushElementToNext(element));
	}

}
