package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;


import de.ruegnerlukas.simpleapplication.simpleui.assets.events.DateSelectedEventData;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.EventProperties;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.builders.PropertyValidation;
import de.ruegnerlukas.simpleapplication.simpleui.core.SuiSceneController;
import javafx.scene.control.DatePicker;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import org.junit.Test;

import java.time.Month;
import java.time.chrono.Chronology;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

public class SuiDatePickerTest extends SuiElementTest {


	@Test
	public void test_create_date_picker() {

		final DatePicker datePicker = (DatePicker) new SuiSceneController(
				SuiDatePicker.datePicker(
						PropertyValidation.promptText("My Date"),
						PropertyValidation.editable(),
						PropertyValidation.chronology(Locale.KOREA)
				)
		).getRootFxNode();

		assertThat(datePicker.getPromptText()).isEqualTo("My Date");
		assertThat(datePicker.isEditable()).isEqualTo(true);
		assertThat(datePicker.getChronology()).isEqualTo(Chronology.ofLocale(Locale.KOREA));
	}




	@Test
	public void test_user_interaction_select_by_typing() {
		if (shouldSkipFxTest()) {
			return;
		}

		final List<DateSelectedEventData> capturedEvents = new ArrayList<>();
		final SuiSceneController controller = new SuiSceneController(
				SuiDatePicker.datePicker(
						PropertyValidation.editable(),
						PropertyValidation.chronology(Locale.GERMANY),
						EventProperties.eventDatePickerAction(".", capturedEvents::add)
				)
		);
		final DatePicker datePicker = (DatePicker) controller.getRootFxNode();
		show(datePicker);

		// enter text for first time
		syncJfxThread(100, () -> clickOn(datePicker, MouseButton.PRIMARY));
		syncJfxThread(100, () -> type(
				KeyCode.getKeyCode("2"),
				KeyCode.PERIOD,
				KeyCode.getKeyCode("1"),
				KeyCode.getKeyCode("0"),
				KeyCode.PERIOD,
				KeyCode.getKeyCode("2"),
				KeyCode.getKeyCode("0"),
				KeyCode.getKeyCode("2"),
				KeyCode.getKeyCode("0"),
				KeyCode.ENTER
		));
		assertDate(datePicker, 2020, Month.OCTOBER, 2);
		assertEvent(capturedEvents, 2020, Month.OCTOBER, 2);

		// enter new text 14.02.2021
		enterDateTyping(datePicker, 2021, Month.FEBRUARY, 14);
		assertDate(datePicker, 2021, Month.FEBRUARY, 14);
		assertEvent(capturedEvents, 2021, Month.FEBRUARY, 14);

		// enter same text again
		enterDateTyping(datePicker, 2021, Month.FEBRUARY, 14);
		assertDate(datePicker, 2021, Month.FEBRUARY, 14);
		assertNoEvent(capturedEvents);

		// enter invalid text
		syncJfxThread(100, () -> clickOn(datePicker, MouseButton.PRIMARY));
		syncJfxThread(100, () -> type(KeyCode.BACK_SPACE, 10));
		syncJfxThread(100, () -> type(
				KeyCode.getKeyCode("A"),
				KeyCode.getKeyCode("B"),
				KeyCode.getKeyCode("C"),
				KeyCode.ENTER
		));
		assertDate(datePicker, 2021, Month.FEBRUARY, 14);
		assertNoEvent(capturedEvents);

	}


}
