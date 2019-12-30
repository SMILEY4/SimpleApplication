package de.ruegnerlukas.test;

import de.ruegnerlukas.simpleapplication.common.events.OnEvent;
import de.ruegnerlukas.simpleapplication.common.events.TriggerableEvent;
import de.ruegnerlukas.simpleapplication.common.events.ListenableEventGroup;
import de.ruegnerlukas.simpleapplication.common.events.TriggerableEventGroup;
import de.ruegnerlukas.simpleapplication.core.presentation.JFXApplication;
import de.ruegnerlukas.simpleapplication.core.presentation.PresentationConfig;
import de.ruegnerlukas.simpleapplication.core.presentation.uimodule.ModuleController;
import de.ruegnerlukas.simpleapplication.core.presentation.uimodule.ModuleFactory;
import de.ruegnerlukas.simpleapplication.core.presentation.uimodule.UIModule;

public class ModuleControllerImpl implements ModuleController {


	/**
	 * The with of the next scene
	 */
	private static final int WIDTH = 400;

	/**
	 * The height of the next scene
	 */
	private static final int HEIGHT = 600;

	/**
	 * The title of the next scene
	 */
	private static final String TITLE = "Test App 2";


	/**
	 * The group of functions of the view.
	 */
	private TriggerableEventGroup functions;




	@Override
	public void initialize(final ListenableEventGroup events, final TriggerableEventGroup functions) {
		this.functions = functions;
	}




	/**
	 * Called when the "btn_event" is fired.
	 *
	 * @param obj the data of the event
	 */
	@OnEvent (eventName = "btn_event")
	public void onButtonPressed(final Object obj) {
		TriggerableEvent<String> renameBtnFunction = functions.getEvent("rename_button_function");
		renameBtnFunction.trigger("New name " + System.currentTimeMillis());

		JFXApplication.setScene(PresentationConfig.builder()
				.width(WIDTH)
				.height(HEIGHT)
				.title(TITLE)
				.baseModule(new ModuleFactory() {
					@Override
					public UIModule module() {
						return new UIModule(
								new ModuleViewImpl(),
								new ModuleControllerImpl(),
								JFXApplication.class.getResource("/base.fxml"));
					}
				})
				.build());

	}

}
