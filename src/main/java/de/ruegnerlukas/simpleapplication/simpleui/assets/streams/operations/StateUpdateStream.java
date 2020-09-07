package de.ruegnerlukas.simpleapplication.simpleui.assets.streams.operations;

import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;
import de.ruegnerlukas.simpleapplication.simpleui.assets.streams.Pipeline;
import de.ruegnerlukas.simpleapplication.simpleui.assets.streams.PipelineImpl;

import java.util.function.BiConsumer;

public class StateUpdateStream<S extends SuiState, T> extends PipelineImpl<T, T> {


	/**
	 * Whether to update the state silently.
	 */
	private final boolean updateSilent;

	/**
	 * The function to run for each element.
	 */
	private final BiConsumer<S, T> consumer;

	/**
	 * The type of the state.
	 */
	private final Class<S> stateType;

	/**
	 * The state to update.
	 */
	private final S state;




	/**
	 * @param source       the source pipeline
	 * @param updateSilent whether to update the state silently
	 * @param stateType    the type of the state
	 * @param state        the state to update
	 * @param consumer     the function to run for each element
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
