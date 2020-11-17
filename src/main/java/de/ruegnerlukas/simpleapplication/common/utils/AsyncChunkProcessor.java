package de.ruegnerlukas.simpleapplication.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

public class AsyncChunkProcessor<S, T> {


	/**
	 * The list of tasks.
	 */
	private final List<CompletableFuture<List<T>>> tasks;




	/**
	 * @param list      the input list to process
	 * @param chunkSize the (max) size of the chunks that get processed asynchronously
	 * @param function  the function to apply to all elements in the input list
	 */
	public AsyncChunkProcessor(final List<S> list, final int chunkSize, final Function<List<S>, List<T>> function) {
		List<List<S>> chunks = createChunks(list, chunkSize);
		this.tasks = createTasks(chunks, function);
	}




	/**
	 * Waits for the result and returns the list of processed elements. The order of the elements does not change.
	 *
	 * @return the processed elements.
	 */
	public List<T> get() {
		List<T> result = new ArrayList<>();
		tasks.forEach(task -> {
			try {
				result.addAll(task.get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		});
		return result;
	}




	/**
	 * Splits the input list into chunks of the given size.
	 *
	 * @param list      the input list
	 * @param chunkSize the max amount of elements in a chunk
	 * @return the resulting chunks
	 */
	private List<List<S>> createChunks(final List<S> list, final int chunkSize) {
		if (chunkSize == 0) {
			return List.of(list);
		}
		final List<List<S>> chunks = new ArrayList<>();
		int index = 0;
		while (index < list.size()) {
			final int from = index;
			final int to = Math.min(list.size(), from + chunkSize);
			chunks.add(list.subList(from, to));
			index = to;
		}
		return chunks;
	}




	/**
	 * Creates completable future to process the given chunks.
	 *
	 * @param chunks   the chunks to process in parallel
	 * @param function the function to apply to every element of each chunk
	 * @return the completable future that can process all chunks
	 */
	private List<CompletableFuture<List<T>>> createTasks(final List<List<S>> chunks, final Function<List<S>, List<T>> function) {
		final List<CompletableFuture<List<T>>> tasks = new ArrayList<>();
		chunks.forEach(chunk -> tasks.add(CompletableFuture.supplyAsync(() -> function.apply(chunk))));
		return tasks;
	}

}
