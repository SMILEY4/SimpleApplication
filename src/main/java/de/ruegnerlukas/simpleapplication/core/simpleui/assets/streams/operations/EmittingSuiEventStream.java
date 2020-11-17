package de.ruegnerlukas.simpleapplication.core.simpleui.assets.streams.operations;

import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.Provider;
import de.ruegnerlukas.simpleapplication.common.tags.Tags;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.streams.Pipeline;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.streams.PipelineImpl;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.registry.SuiRegistry;

public class EmittingSuiEventStream<T> extends PipelineImpl<T, T> {


	/**
	 * The tags to add to the events
	 */
	private final Tags tags;

	/**
	 * The instance of the sui registry
	 */
	private final SuiRegistry suiRegistry;




	/**
	 * @param source the source pipeline
	 * @param tags   the tags to add to the events
	 */
	public EmittingSuiEventStream(final Pipeline<?, T> source, final Tags tags) {
		this(source, tags, new Provider<>(SuiRegistry.class).get());
	}




	/**
	 * @param source   the source pipeline
	 * @param tags     the tags to add to the events
	 * @param registry the sui registry instance
	 */
	public EmittingSuiEventStream(final Pipeline<?, T> source, final Tags tags, final SuiRegistry registry) {
		super(source);
		this.tags = tags;
		this.suiRegistry = registry;
	}




	@Override
	protected void process(final T element) {
		suiRegistry.getEventBus().publish(tags, element);
	}

}
