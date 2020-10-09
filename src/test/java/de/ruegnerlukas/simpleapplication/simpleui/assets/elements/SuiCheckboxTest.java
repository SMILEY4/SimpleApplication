package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;


import de.ruegnerlukas.simpleapplication.simpleui.assets.events.CheckedEventData;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.EventProperties;
import de.ruegnerlukas.simpleapplication.simpleui.core.SuiSceneController;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseButton;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SuiCheckboxTest extends SuiElementTest {


	@Test
	public void test_pressing_checkbox_triggers_actions() {
		if(shouldSkipFxTest()) {
			return;
		}

		final List<CheckedEventData> capturedCheckedEvents = new ArrayList<>();

		final CheckBox checkbox = (CheckBox) new SuiSceneController(
				SuiCheckbox.checkbox(
						EventProperties.eventChecked(".", capturedCheckedEvents::add)
				)
		).getRootFxNode();

		show(checkbox);

		syncJfxThread(100, () -> clickOn(checkbox, MouseButton.PRIMARY));

		assertThat(capturedCheckedEvents).hasSize(1);
		assertThat(capturedCheckedEvents.get(0).isChecked()).isTrue();
		capturedCheckedEvents.clear();

		syncJfxThread(100, () -> clickOn(checkbox, MouseButton.PRIMARY));

		assertThat(capturedCheckedEvents).hasSize(1);
		assertThat(capturedCheckedEvents.get(0).isChecked()).isFalse();

	}


}
