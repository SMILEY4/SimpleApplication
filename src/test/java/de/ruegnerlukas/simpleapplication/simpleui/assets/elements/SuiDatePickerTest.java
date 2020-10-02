package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;


import de.ruegnerlukas.simpleapplication.simpleui.assets.events.DatePickerActionEventData;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.EventProperties;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.core.SuiSceneController;
import javafx.scene.control.DatePicker;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import org.junit.Test;

import java.time.LocalDate;
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
						Properties.promptText("My Date"),
						Properties.editable(),
						Properties.chronology(Locale.KOREA)
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

		final List<DatePickerActionEventData> capturedEvents = new ArrayList<>();
		final SuiSceneController controller = new SuiSceneController(
				SuiDatePicker.datePicker(
						Properties.editable(),
						Properties.chronology(Locale.GERMANY),
						EventProperties.eventDatePickerAction(".", capturedEvents::add)
				)
		);
		final DatePicker datePicker = (DatePicker) controller.getRootFxNode();
		show(datePicker);

		// enter text for first time
		syncJfxThread(100, () -> clickOn(datePicker, MouseButton.PRIMARY));
		syncJfxThread(100, () -> type(
				KeyCode.DIGIT2,
				KeyCode.PERIOD,
				KeyCode.DIGIT1,
				KeyCode.DIGIT0,
				KeyCode.PERIOD,
				KeyCode.DIGIT2,
				KeyCode.DIGIT0,
				KeyCode.DIGIT2,
				KeyCode.DIGIT0,
				KeyCode.ENTER
		));
		assertThat(datePicker.getValue()).isAfterOrEqualTo(LocalDate.of(2020, Month.OCTOBER, 2));
		assertThat(capturedEvents).hasSize(1);
		assertThat(capturedEvents.get(0).getItem()).isEqualTo(LocalDate.of(2020, Month.OCTOBER, 2));
		capturedEvents.clear();

		// enter new text 14.02.2021
		syncJfxThread(100, () -> clickOn(datePicker, MouseButton.PRIMARY));
		syncJfxThread(100, () -> type(KeyCode.BACK_SPACE, 10));
		syncJfxThread(100, () -> type(
				KeyCode.getKeyCode("1"),
				KeyCode.getKeyCode("4"),
				KeyCode.PERIOD,
				KeyCode.getKeyCode("2"),
				KeyCode.PERIOD,
				KeyCode.getKeyCode("2"),
				KeyCode.getKeyCode("0"),
				KeyCode.getKeyCode("2"),
				KeyCode.getKeyCode("1"),
				KeyCode.ENTER
		));
		assertThat(datePicker.getValue()).isAfterOrEqualTo(LocalDate.of(2021, Month.FEBRUARY, 14));
		assertThat(capturedEvents).hasSize(1);
		assertThat(capturedEvents.get(0).getItem()).isEqualTo(LocalDate.of(2021, Month.FEBRUARY, 14));
		capturedEvents.clear();

		// enter same text again
		syncJfxThread(100, () -> clickOn(datePicker, MouseButton.PRIMARY));
		syncJfxThread(100, () -> type(KeyCode.BACK_SPACE, 10));
		syncJfxThread(100, () -> type(
				KeyCode.getKeyCode("1"),
				KeyCode.getKeyCode("4"),
				KeyCode.PERIOD,
				KeyCode.getKeyCode("2"),
				KeyCode.PERIOD,
				KeyCode.getKeyCode("2"),
				KeyCode.getKeyCode("0"),
				KeyCode.getKeyCode("2"),
				KeyCode.getKeyCode("1"),
				KeyCode.ENTER
		));
		assertThat(datePicker.getValue()).isAfterOrEqualTo(LocalDate.of(2021, Month.FEBRUARY, 14));
		assertThat(capturedEvents).hasSize(0);


		// enter invalid text
		syncJfxThread(100, () -> clickOn(datePicker, MouseButton.PRIMARY));
		syncJfxThread(100, () -> type(KeyCode.BACK_SPACE, 10));
		syncJfxThread(100, () -> type(
				KeyCode.getKeyCode("A"),
				KeyCode.getKeyCode("B"),
				KeyCode.getKeyCode("C"),
				KeyCode.ENTER
		));
		assertThat(datePicker.getValue()).isAfterOrEqualTo(LocalDate.of(2021, Month.FEBRUARY, 14));
		assertThat(capturedEvents).hasSize(0);

	}


}
