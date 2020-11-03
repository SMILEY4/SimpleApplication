package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;


import de.ruegnerlukas.simpleapplication.simpleui.assets.SuiElements;
import de.ruegnerlukas.simpleapplication.simpleui.assets.events.ActionEventData;
import javafx.scene.control.Button;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SuiButtonTest extends SuiElementTest {


	@Test
	public void test_pressing_button_triggers_action() {
		if (shouldSkipFxTest()) {
			return;
		}

		final List<ActionEventData> capturedEvents = new ArrayList<>();
		final Button button = buildFxNode(state -> SuiElements.button().eventAction(".", capturedEvents::add));

		show(button);
		clickButton(button);
		assertThat(capturedEvents).hasSize(1);
	}


}
