package de.ruegnerlukas.test;

import de.ruegnerlukas.simpleapplication.common.events.Event;
import de.ruegnerlukas.simpleapplication.common.events.ListenableEvent;
import de.ruegnerlukas.simpleapplication.common.events.SimpleEvent;
import de.ruegnerlukas.simpleapplication.common.events.TriggerableEvent;
import de.ruegnerlukas.simpleapplication.core.presentation.uimodule.ModuleView;
import de.ruegnerlukas.simpleapplication.core.presentation.utils.Anchors;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.Map;

public class ModuleViewImpl implements ModuleView {


	/**
	 * The vbox.
	 */
	@FXML
	private VBox boxList;


	/**
	 * The content pane
	 */
	@FXML
	private AnchorPane contentPane;

	/**
	 * The button
	 */
	private Button button;

	/**
	 * The event when the button was pressed.
	 */
	private final Event btnEvent = new SimpleEvent();

	/**
	 * The functionality to rename the button.
	 */
	private final Event<String> renameButtonFunction = new SimpleEvent<>();




	@Override
	public void initializeView() {
		this.button = new Button("Im a Button :)");
		Anchors.setAnchors(button, 0);
		contentPane.getChildren().add(button);

		button.setOnAction(e -> btnEvent.trigger());

		renameButtonFunction.addListener(data -> {
			button.setText(data);
		});

	}




	@Override
	public Map<String, ListenableEvent> getEventEndpoints() {
		return Map.of("btn_event", btnEvent);
	}




	@Override
	public Map<String, TriggerableEvent> getFunctionEndpoints() {
		return Map.of("rename_button_function", renameButtonFunction);
	}


}
