package de.ruegnerlukas.simpleapplication.core.simpleui.core.state;

import de.ruegnerlukas.simpleapplication.common.tags.Tags;

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
	Tags doTaggedUpdate(T state);


}
