package de.ruegnerlukas.simpleapplication.simpleui;

import lombok.Getter;
import lombok.Setter;

public abstract class State {


	@Getter
	@Setter
	private StateListener listener;




	public void update(StateUpdate update) {
		update.doUpdate(this);
		if (listener != null) {
			listener.onUpdate(update);
		}
	}




	public interface StateUpdate {


		void doUpdate(State state);

	}






	public interface StateListener {


		void onUpdate(StateUpdate update);

	}

}
