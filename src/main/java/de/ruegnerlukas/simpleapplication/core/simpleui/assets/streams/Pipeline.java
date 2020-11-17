package de.ruegnerlukas.simpleapplication.core.simpleui.assets.streams;

import java.util.function.Consumer;

public abstract class Pipeline<IN, OUT> implements SuiStream<IN, OUT> {


	/**
	 * The next step in the pipeline, i.e. the next pipeline that receives an element that this step emits.
	 */
	private Consumer<OUT> consumer;




	/**
	 * Creates a new pipeline with no source.
	 */
	public Pipeline() {
	}




	/**
	 * Creates a new pipeline with the given pipeline as a source. This pipeline is a subscriber/consumer of the given pipeline.
	 *
	 * @param source the source pipeline
	 */
	public Pipeline(final Pipeline<?, IN> source) {
		source.subscribe(this::process);
	}




	/**
	 * Process the given element. This method is called for each element of the source/previous stream.
	 *
	 * @param element the element
	 */
	protected abstract void process(IN element);




	/**
	 * Subscribes the given consumer to the output of this pipeline. Only one consumer can be subscribed at any time.
	 *
	 * @param consumer the subscriber
	 */
	private void subscribe(final Consumer<OUT> consumer) {
		this.consumer = consumer;
	}




	/**
	 * Pushes the given element to the next step, i.e. to the subscriber of this pipeline (if one exists).
	 *
	 * @param element the element to hand to the subscriber
	 */
	protected synchronized void pushElementToNext(final OUT element) {
		if (consumer != null) {
			consumer.accept(element);
		}
	}


}
