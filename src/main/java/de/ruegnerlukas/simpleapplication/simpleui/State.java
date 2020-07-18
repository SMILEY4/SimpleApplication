package de.ruegnerlukas.simpleapplication.simpleui;

import lombok.Getter;
import lombok.Setter;

public abstract class State {


	/**
	 * The listener listening to state changes.
	 */
	@Getter
	@Setter
	private StateListener listener;




	/**
	 * Update this state with the given update
	 *
	 * @param update the update modifying this state
	 */
	public void update(final StateUpdate update) {
		update.doUpdate(this);
		if (listener != null) {
			listener.onUpdate(update);
		}
	}




	public interface StateUpdate {


		/**
		 * update the given state
		 *
		 * @param state the state to modify
		 */
		void doUpdate(State state);

	}






	public interface StateListener {


		/**
		 * Called after the state was modified by the given update.
		 *
		 * @param update the update
		 */
		void onUpdate(StateUpdate update);

	}

}
