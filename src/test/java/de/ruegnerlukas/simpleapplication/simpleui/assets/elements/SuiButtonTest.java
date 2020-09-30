package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;


import de.ruegnerlukas.simpleapplication.simpleui.assets.events.ActionEventData;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.EventProperties;
import de.ruegnerlukas.simpleapplication.simpleui.core.SuiSceneController;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Phaser;

import static org.assertj.core.api.Assertions.assertThat;

public class SuiButtonTest extends SuiElementTest {


	@Test
	public void test_pressing_button_triggers_action() {

		final Phaser phaser = new Phaser(2);
		final List<ActionEventData> capturedEvents = new ArrayList<>();
		final Button button = (Button) new SuiSceneController(
				SuiButton.button(
						EventProperties.eventAction(".", e -> {
							capturedEvents.add(e);
							phaser.arrive();
						})
				)
		).getRootFxNode();

		show(button);

		Platform.runLater(() -> clickOn(button, MouseButton.PRIMARY));
		phaser.arriveAndAwaitAdvance();

		assertThat(capturedEvents).hasSize(1);
	}


}
