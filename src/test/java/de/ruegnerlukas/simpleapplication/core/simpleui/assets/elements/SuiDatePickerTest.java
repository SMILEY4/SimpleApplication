package de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements;


import de.ruegnerlukas.simpleapplication.core.simpleui.assets.events.DateSelectedEventData;
import javafx.scene.control.DatePicker;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import org.junit.Test;

import java.time.Month;
import java.time.chrono.Chronology;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static de.ruegnerlukas.simpleapplication.core.simpleui.assets.SuiElements.datePicker;
import static org.assertj.core.api.Assertions.assertThat;

public class SuiDatePickerTest extends SuiElementTest {


	@Test
	public void test_create_date_picker() {

		final DatePicker datePicker = buildFxNode(
				state -> 				datePicker()
						.promptText("My Date")
						.editable()
						.chronology(Locale.KOREA)
		);

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
		final DatePicker datePicker = buildFxNode(
				state -> 				datePicker()
						.editable()
						.chronology(Locale.GERMANY)
						.eventSelectedDate(".", capturedEvents::add)
		);
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
