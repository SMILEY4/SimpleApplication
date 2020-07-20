package de.ruegnerlukas.simpleapplication.simpleui;

public interface SUIState {


	/**
	 * Links this state to the given context.
	 * A state can only be linked to a context once.
	 *
	 * @param context the context to link to
	 */
	void linkToContext(SUISceneContext context);

	/**
	 * Modifies this state via the given update.
	 * The update is executed by the linked scene context.
	 *
	 * @param update the state update
	 */
	void update(SUIStateUpdate update);

}
