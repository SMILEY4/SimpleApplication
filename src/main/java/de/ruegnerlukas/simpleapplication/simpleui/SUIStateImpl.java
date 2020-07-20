package de.ruegnerlukas.simpleapplication.simpleui;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;

public class SUIStateImpl implements SUIState {


	/**
	 * The context linked to this state.
	 */
	private SUISceneContext context;




	@Override
	public void linkToContext(final SUISceneContext context) {
		Validations.INPUT.notNull(context).exception("The input scene context can not be null.");
		Validations.STATE.isNull(this.context).exception("A scene context is already linked.");
		this.context = context;
	}




	@Override
	public void update(final SUIStateUpdate update) {
		Validations.INPUT.notNull(update).exception("The state update can not be null.");
		Validations.STATE.notNull(this.context).exception("Cannot execute state update. No linked scene context.");
		this.context.applyStateUpdate(update);
	}

}
