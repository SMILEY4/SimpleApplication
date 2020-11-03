package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;


import de.ruegnerlukas.simpleapplication.simpleui.assets.SuiElements;
import de.ruegnerlukas.simpleapplication.simpleui.assets.events.CheckedEventData;
import javafx.scene.control.CheckBox;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SuiCheckboxTest extends SuiElementTest {


	@Test
	public void test_pressing_checkbox_triggers_actions() {
		if (shouldSkipFxTest()) {
			return;
		}

		final List<CheckedEventData> capturedEvents = new ArrayList<>();
		final CheckBox checkbox = buildFxNode(state -> SuiElements.checkBox().eventChecked(".", capturedEvents::add));

		show(checkbox);

		clickButton(checkbox);
		assertEvent(capturedEvents, true);

		clickButton(checkbox);
		assertEvent(capturedEvents, false);
	}


}
