package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;


import de.ruegnerlukas.simpleapplication.simpleui.assets.events.ValueChangedEventData;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.EventProperties;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.core.SuiSceneController;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.KeyCode;
import org.assertj.core.data.Percentage;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SuiSpinnerTest extends SuiElementTest {


	@Test
	public void test_create_integer_spinner() {

		@SuppressWarnings ("unchecked") final Spinner<Integer> spinner = (Spinner<Integer>) new SuiSceneController(
				SuiSpinner.spinner(
						Properties.editable(),
						Properties.integerSpinnerValues(-3, 7, 2, 1)  // todo: wrap around for all types (int, float, list) ??
				)
		).getRootFxNode();

		assertThat(((SpinnerValueFactory.IntegerSpinnerValueFactory) spinner.getValueFactory()).getMin()).isEqualTo(-3);
		assertThat(((SpinnerValueFactory.IntegerSpinnerValueFactory) spinner.getValueFactory()).getMax()).isEqualTo(7);
		assertThat(((SpinnerValueFactory.IntegerSpinnerValueFactory) spinner.getValueFactory()).getAmountToStepBy()).isEqualTo(2);
		assertThat(spinner.getValueFactory().getValue().intValue()).isEqualTo(1);
		assertThat(spinner.isEditable()).isTrue();
	}




	@Test
	public void test_create_float_spinner() {

		@SuppressWarnings ("unchecked") final Spinner<Double> spinner = (Spinner<Double>) new SuiSceneController(
				SuiSpinner.spinner(
						Properties.editable(),
						Properties.floatingPointSpinnerValues(-3.6, 7.2, 1.4, 1.3) // todo: wrap around for all types (int, float, list) ??
				)
		).getRootFxNode();

		assertThat(((SpinnerValueFactory.DoubleSpinnerValueFactory) spinner.getValueFactory()).getMin()).isCloseTo(-3.6, Percentage.withPercentage(0.01));
		assertThat(((SpinnerValueFactory.DoubleSpinnerValueFactory) spinner.getValueFactory()).getMax()).isCloseTo(7.2, Percentage.withPercentage(0.01));
		assertThat(((SpinnerValueFactory.DoubleSpinnerValueFactory) spinner.getValueFactory()).getAmountToStepBy()).isCloseTo(1.4, Percentage.withPercentage(0.01));
		assertThat(spinner.getValueFactory().getValue().doubleValue()).isCloseTo(1.3, Percentage.withPercentage(0.01));
		assertThat(spinner.isEditable()).isTrue();
	}




	@Test
	public void test_create_list_spinner() {

		@SuppressWarnings ("unchecked") final Spinner<String> spinner = (Spinner<String>) new SuiSceneController(
				SuiSpinner.spinner(
						Properties.editable(),
						Properties.listSpinnerValues(List.of("A", "B", "C"), true) // todo: missing initial value ??
				)
		).getRootFxNode();

		assertThat(((SpinnerValueFactory.ListSpinnerValueFactory<String>) spinner.getValueFactory()).getItems()).containsExactly("A", "B", "C");
		assertThat(spinner.getValueFactory().isWrapAround()).isTrue();
		assertThat(spinner.isEditable()).isTrue();
	}




	@Test
	public void test_spinner_listener() {

		final List<ValueChangedEventData<Integer>> capturedEvents = new ArrayList<>();

		final SuiSceneController controller = new SuiSceneController(
				SuiSpinner.spinner(
						Properties.integerSpinnerValues(-3, 7, 2, 1),
						EventProperties.eventValueChangedType(".", Integer.class, capturedEvents::add)
				)
		);

		@SuppressWarnings ("unchecked") final Spinner<Integer> spinner = (Spinner<Integer>) controller.getRootFxNode();
		show(spinner);

		// increment value once
		syncJfxThread(spinner::increment);
		assertThat(spinner.getValue()).isEqualTo(3);
		assertThat(capturedEvents).hasSize(1);
		assertThat(capturedEvents.get(0).getValue()).isEqualTo(3);
		assertThat(capturedEvents.get(0).getPrevValue()).isEqualTo(1);
		capturedEvents.clear();

		// increment value again
		syncJfxThread(spinner::increment);
		assertThat(spinner.getValue()).isEqualTo(5);
		assertThat(capturedEvents).hasSize(1);
		assertThat(capturedEvents.get(0).getValue()).isEqualTo(5);
		assertThat(capturedEvents.get(0).getPrevValue()).isEqualTo(3);
		capturedEvents.clear();

		// increment value until limit reached
		syncJfxThread(() -> spinner.increment(10));
		assertThat(spinner.getValue()).isEqualTo(7);
		assertThat(capturedEvents).hasSize(1);
		assertThat(capturedEvents.get(0).getValue()).isEqualTo(7);
		assertThat(capturedEvents.get(0).getPrevValue()).isEqualTo(5);
		capturedEvents.clear();

		// increment value again -> nothing happens
		syncJfxThread(spinner::increment);
		assertThat(spinner.getValue()).isEqualTo(7);
		assertThat(capturedEvents).isEmpty();

	}




	@Test
	public void test_editable_number_spinner() {
		if (shouldSkipFxTest()) {
			return;
		}

		final List<ValueChangedEventData<Integer>> capturedEvents = new ArrayList<>();
		final SuiSceneController controller = new SuiSceneController(
				SuiSpinner.spinner(
						Properties.editable(),
						Properties.integerSpinnerValues(-3, 7, 2, 1),
						EventProperties.eventValueChangedType(".", Integer.class, capturedEvents::add)
				)
		);

		@SuppressWarnings ("unchecked") final Spinner<Integer> spinner = (Spinner<Integer>) controller.getRootFxNode();
		show(spinner);

		assertThat(spinner.getValue()).isEqualTo(1);
		capturedEvents.clear(); // todo: value changed event "null -> 1" triggered at creation ?

		// type in new value -> value changes + event
		syncJfxThread(100, () -> clickOn(spinner));
		syncJfxThread(100, () -> eraseText(1));
		syncJfxThread(100, () -> type(KeyCode.getKeyCode("4"), KeyCode.ENTER));
		assertThat(spinner.getValue()).isEqualTo(4);
		assertThat(capturedEvents).hasSize(1);
		assertThat(capturedEvents.get(0).getValue()).isEqualTo(4);
		assertThat(capturedEvents.get(0).getPrevValue()).isEqualTo(1);
		capturedEvents.clear();

		// type in same value -> same value + no event
		syncJfxThread(100, () -> clickOn(spinner));
		syncJfxThread(100, () -> eraseText(1));
		syncJfxThread(100, () -> type(KeyCode.getKeyCode("4"), KeyCode.ENTER));
		assertThat(spinner.getValue()).isEqualTo(4);
		assertThat(capturedEvents).isEmpty();

		// type in value > max -> value = max + event
		syncJfxThread(100, () -> clickOn(spinner));
		syncJfxThread(100, () -> eraseText(1));
		syncJfxThread(100, () -> type(KeyCode.getKeyCode("9"), KeyCode.ENTER));
		assertThat(spinner.getValue()).isEqualTo(7);
		assertThat(capturedEvents).hasSize(2); // todo: triggers unwanted change event "4 -> 9" and then "9 -> 7"
		assertThat(capturedEvents.get(1).getValue()).isEqualTo(7);
//		assertThat(capturedEvents.get(1).getPrevValue()).isEqualTo(4);
		capturedEvents.clear();

		// enter invalid text -> prev value + no event
		syncJfxThread(() -> spinner.getValueFactory().setValue(4));
		capturedEvents.clear();
		syncJfxThread(100, () -> clickOn(spinner));
		syncJfxThread(100, () -> eraseText(1));
		syncJfxThread(100, () -> type(KeyCode.getKeyCode("A"), KeyCode.ENTER));
		assertThat(spinner.getValue()).isEqualTo(4);
		assertThat(capturedEvents).isEmpty();
	}




	@Test
	public void test_editable_list_spinner() {
		if (shouldSkipFxTest()) {
			return;
		}

		final List<ValueChangedEventData<String>> capturedEvents = new ArrayList<>();
		final SuiSceneController controller = new SuiSceneController(
				SuiSpinner.spinner(
						Properties.editable(),
						Properties.listSpinnerValues(List.of("first", "second", "third"), true),
						EventProperties.eventValueChangedType(".", String.class, capturedEvents::add)
				)
		);

		@SuppressWarnings ("unchecked") final Spinner<String> spinner = (Spinner<String>) controller.getRootFxNode();
		show(spinner);

		spinner.getValueFactory().setValue("first");
		capturedEvents.clear();

		// type in new value -> value changes + event
		syncJfxThread(100, () -> clickOn(spinner));
		syncJfxThread(100, () -> eraseText(10));
		syncJfxThread(100, () -> type(
				KeyCode.getKeyCode("T"),
				KeyCode.getKeyCode("H"),
				KeyCode.getKeyCode("I"),
				KeyCode.getKeyCode("R"),
				KeyCode.getKeyCode("D"),
				KeyCode.ENTER)
		);
		assertThat(spinner.getValue()).isEqualTo("third");
		assertThat(capturedEvents).hasSize(1);
		assertThat(capturedEvents.get(0).getValue()).isEqualTo("third");
		assertThat(capturedEvents.get(0).getPrevValue()).isEqualTo("first");
		capturedEvents.clear();

		// enter invalid text -> prev value + no event
		syncJfxThread(100, () -> clickOn(spinner));
		syncJfxThread(100, () -> eraseText(10));
		syncJfxThread(100, () -> type(
				KeyCode.getKeyCode("I"),
				KeyCode.getKeyCode("N"),
				KeyCode.getKeyCode("V"),
				KeyCode.getKeyCode("A"),
				KeyCode.getKeyCode("L"),
				KeyCode.getKeyCode("I"),
				KeyCode.getKeyCode("D"),
				KeyCode.ENTER)
		);
		assertThat(spinner.getValue()).isEqualTo("third");
		assertThat(capturedEvents).hasSize(2); // todo: dont trigger events here
	}


}
