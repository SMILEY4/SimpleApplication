package de.ruegnerlukas.simpleapplication.simpleui.assets.streams.operations;

import de.ruegnerlukas.simpleapplication.simpleui.assets.streams.Pipeline;
import de.ruegnerlukas.simpleapplication.simpleui.assets.streams.PipelineImpl;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
import de.ruegnerlukas.simpleapplication.simpleui.core.tags.Tags;

public class EmittingSuiEventStream<T> extends PipelineImpl<T, T> {


	/**
	 * The tags to add to the events
	 */
	private final Tags tags;




	/**
	 * @param source the source pipeline
	 * @param tags   the tags to add to the events
	 */
	public EmittingSuiEventStream(final Pipeline<?, T> source, final Tags tags) {
		super(source);
		this.tags = tags;
	}




	@Override
	protected void process(final T element) {
		SuiRegistry.get().getEventBus().publish(tags, element);
	}

}
