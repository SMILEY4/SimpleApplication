package de.ruegnerlukas.simpleapplication.simpleui.streams.operations;

import de.ruegnerlukas.simpleapplication.simpleui.SuiState;
import de.ruegnerlukas.simpleapplication.simpleui.streams.Pipeline;
import de.ruegnerlukas.simpleapplication.simpleui.streams.PipelineImpl;

import java.util.function.BiConsumer;

public class StateUpdateStream<S extends SuiState, T> extends PipelineImpl<T, T> {


	private final boolean updateSilent;

	/**
	 * The function to run for each element.
	 */
	private final BiConsumer<S, T> consumer;

	private final Class<S> stateType;

	private final S state;




	/**
	 * @param source the source pipeline
	 */
	public StateUpdateStream(final Pipeline<?, T> source,
							 final boolean updateSilent,
							 final Class<S> stateType,
							 final S state,
							 final BiConsumer<S, T> consumer) {
		super(source);
		this.updateSilent = updateSilent;
		this.stateType = stateType;
		this.state = state;
		this.consumer = consumer;
	}




	@Override
	protected void process(final T element) {
		state.update(stateType, updateSilent, s -> consumer.accept(s, element));
	}

}
