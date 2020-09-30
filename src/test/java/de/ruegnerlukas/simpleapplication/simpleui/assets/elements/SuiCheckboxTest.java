package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;


import de.ruegnerlukas.simpleapplication.simpleui.assets.events.ActionEventData;
import de.ruegnerlukas.simpleapplication.simpleui.assets.events.CheckedEventData;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.EventProperties;
import de.ruegnerlukas.simpleapplication.simpleui.core.SuiSceneController;
import javafx.application.Platform;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseButton;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Phaser;

import static org.assertj.core.api.Assertions.assertThat;

public class SuiCheckboxTest extends SuiElementTest {


	@Test
	public void test_pressing_checkbox_triggers_actions() {

		final Phaser phaser = new Phaser(3);
		final List<ActionEventData> capturedActionEvents = new ArrayList<>();
		final List<CheckedEventData> capturedCheckedEvents = new ArrayList<>();

		final CheckBox checkbox = (CheckBox) new SuiSceneController(
				SuiCheckbox.checkbox(
						EventProperties.eventAction(".", e -> {
							capturedActionEvents.add(e);
							phaser.arrive();
						}),
						EventProperties.eventChecked(".", e -> {
							capturedCheckedEvents.add(e);
							phaser.arrive();
						}),
						EventProperties.eventUnchecked(".", e -> {
							capturedCheckedEvents.add(e);
							phaser.arrive();
						})
				)
		).getRootFxNode();

		show(checkbox);

		Platform.runLater(() -> clickOn(checkbox, MouseButton.PRIMARY));
		phaser.arriveAndAwaitAdvance();

		assertThat(capturedActionEvents).hasSize(1);
		assertThat(capturedCheckedEvents).hasSize(1);
		assertThat(capturedCheckedEvents.get(0).isChecked()).isTrue();

		capturedActionEvents.clear();
		capturedCheckedEvents.clear();

		Platform.runLater(() -> clickOn(checkbox, MouseButton.PRIMARY));
		phaser.arriveAndAwaitAdvance();

		assertThat(capturedActionEvents).hasSize(1);
		assertThat(capturedCheckedEvents).hasSize(1);
		assertThat(capturedCheckedEvents.get(0).isChecked()).isFalse();

	}


}
