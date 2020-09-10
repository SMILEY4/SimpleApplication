package de.ruegnerlukas.simpleapplication.simpleui.core.state;

import java.util.List;

public interface TaggedSuiStateUpdate<T> extends SuiStateUpdate<T> {


	@Override
	default void doUpdate(T state) {
		doTaggedUpdate(state);
	}

	/**
	 * Update the given state.
	 *
	 * @param state the state to modify
	 * @return the tags that should be attached to this update.
	 */
	List<String> doTaggedUpdate(T state);


}
