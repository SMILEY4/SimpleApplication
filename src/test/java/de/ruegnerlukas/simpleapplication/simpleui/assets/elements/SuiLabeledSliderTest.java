package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;


import de.ruegnerlukas.simpleapplication.common.validation.ValidateInputException;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.jfxelements.ExtendedSlider;
import de.ruegnerlukas.simpleapplication.simpleui.assets.events.ValueChangedEventData;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TickMarkProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.Setter;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static de.ruegnerlukas.simpleapplication.simpleui.assets.SuiElements.labeledSlider;
import static org.assertj.core.api.Assertions.assertThat;

public class SuiLabeledSliderTest extends SuiElementTest {


	@Test
	public void test_create_slider() {

		final Pane sliderRoot = buildFxNode(
				state -> labeledSlider()
						.minMax(16, 42)
						.blockIncrement(3)
						.tickMarks(TickMarkProperty.TickMarkStyle.ONLY_LABELS, 4, 2, true)
						.orientation(Orientation.VERTICAL)
						.alignment(Pos.CENTER_RIGHT)
						.editable()
		);

		assertThat(sliderRoot.getChildren()).hasSize(2);
		assertThat(sliderRoot.getChildren().get(0).getClass()).isEqualTo(ExtendedSlider.class);
		assertThat(sliderRoot.getChildren().get(1).getClass()).isEqualTo(TextField.class);

		final Slider slider = (Slider) sliderRoot.getChildren().get(0);
		assertThat((int) slider.getMin()).isEqualTo(16);
		assertThat((int) slider.getMax()).isEqualTo(42);
		assertThat((int) slider.getBlockIncrement()).isEqualTo(3);
		assertThat(slider.isShowTickLabels()).isEqualTo(true);
		assertThat(slider.isShowTickMarks()).isEqualTo(false);
		assertThat(slider.getMajorTickUnit()).isEqualTo(4);
		assertThat(slider.getMinorTickCount()).isEqualTo(2);
		assertThat(slider.isSnapToTicks()).isEqualTo(true);
		assertThat(slider.getOrientation()).isEqualTo(Orientation.VERTICAL);

		final TextField label = (TextField) sliderRoot.getChildren().get(1);
		assertThat(label.isEditable()).isTrue();
	}




	@Test (expected = ValidateInputException.class)
	public void test_create_slider_swapped_min_max() {
		buildFxNode(state -> labeledSlider().minMax(42, 16));
	}




	@Test
	public void test_create_slider_only_min() {

		final Pane sliderRoot = buildFxNode(state -> labeledSlider().minMax(16, null));

		final Slider slider = (Slider) sliderRoot.getChildren().get(0);
		assertThat((int) slider.getMin()).isEqualTo(16);
		assertThat((int) slider.getMax()).isGreaterThan(9999999);
	}




	@Test
	public void test_editable_label() {

		final List<ValueChangedEventData<Number>> capturedEvents = new ArrayList<>();

		final Pane sliderRoot = buildFxNode(
				state -> labeledSlider()
						.minMax(10, 30)
						.editable()
						.eventValueChanged(".", Number.class, capturedEvents::add)
						.labelFormatter(".", value -> value.intValue() + " units")
						.alignment(Pos.CENTER_RIGHT)
		);

		final Slider slider = (Slider) sliderRoot.getChildren().get(0);
		final TextField label = (TextField) sliderRoot.getChildren().get(1);

		// set initial value -> expect correct label and event
		capturedEvents.clear();
		syncJfxThread(() -> slider.setValue(20));
		assertThat(label.getText()).isEqualTo("20 units");
		capturedEvents.clear();

		// set value less than min -> expect new value equals min and event
		syncJfxThread(() -> slider.setValue(-10));
		assertThat(label.getText()).isEqualTo("10 units");
		assertSlider(slider, 10, 30, 10);
		capturedEvents.clear();

		// set new value via label
		syncJfxThread(() -> {
			label.setText("15  ");
			label.fireEvent(new ActionEvent());
		});
		assertThat(label.getText()).isEqualTo("15 units");
		assertSlider(slider, 10, 30, 15);
		assertEvent(capturedEvents, 10.0, 15.0);

		// set new value via label as math expression
		syncJfxThread(() -> {
			label.setText("30-3");
			label.fireEvent(new ActionEvent());
		});
		assertThat(label.getText()).isEqualTo("27 units");
		assertSlider(slider, 10, 30, 27);
		assertEvent(capturedEvents, 15.0, 27.0);

	}




	@Test
	public void test_mutate_min_max() {

		@Getter
		@Setter
		class TestState extends SuiState {


			private int min = 0;
			private int max = 100;

		}

		final TestState testState = new TestState();

		final List<ValueChangedEventData<Number>> capturedEvents = new ArrayList<>();

		final Pane sliderRoot = buildFxNode(TestState.class, testState,
				state -> labeledSlider()
						.anchorsFitParent()
						.minMax(state.getMin(), state.getMax())
						.eventValueChanged(".", Number.class, capturedEvents::add)
		);
		final Slider slider = (Slider) sliderRoot.getChildren().get(0);

		// mutate state -> set min/max to 10/90, expect value to stay the same
		syncJfxThread(() -> slider.setValue(50));
		capturedEvents.clear();
		syncJfxThread(() -> testState.updateUnsafe(TestState.class, state -> {
			state.setMin(10);
			state.setMax(90);
		}));
		assertSlider(slider, 10, 90, 50);
		assertNoEvent(capturedEvents);

		// mutate state -> set min to 30, expect new value = 30
		syncJfxThread(() -> slider.setValue(25));
		capturedEvents.clear();
		syncJfxThread(() -> testState.updateUnsafe(TestState.class, state -> state.setMin(30)));
		assertSlider(slider, 30, 90, 30);
		assertNoEvent(capturedEvents);

		// mutate state -> set max to 70, expect new value = 70
		syncJfxThread(() -> slider.setValue(75));
		capturedEvents.clear();
		syncJfxThread(() -> testState.updateUnsafe(TestState.class, state -> state.setMax(70)));
		assertSlider(slider, 30, 70, 70);
		assertNoEvent(capturedEvents);

		// mutate state -> set min/max to -10/2
		syncJfxThread(() -> slider.setValue(50));
		capturedEvents.clear();
		syncJfxThread(() -> testState.updateUnsafe(TestState.class, state -> {
			state.setMin(-10);
			state.setMax(2);
		}));
		assertSlider(slider, -10, 2, 2);
		assertNoEvent(capturedEvents);
	}


}
