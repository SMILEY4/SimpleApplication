package de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements;


import de.ruegnerlukas.simpleapplication.common.validation.ValidateInputException;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.events.ValueChangedEventData;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.TickMarkProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.state.SuiState;
import javafx.geometry.Orientation;
import javafx.scene.control.Slider;
import lombok.Getter;
import lombok.Setter;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static de.ruegnerlukas.simpleapplication.core.simpleui.assets.SuiElements.slider;
import static org.assertj.core.api.Assertions.assertThat;

public class SuiSliderTest extends SuiElementTest {


	@Test
	public void test_create_slider() {

		final Slider slider = buildFxNode(
				state -> slider()
						.minMax(16, 42)
						.blockIncrement(3)
						.tickMarks(TickMarkProperty.TickMarkStyle.ONLY_LABELS, 4, 2, true)
						.orientation(Orientation.VERTICAL)
		);

		assertThat((int) slider.getMin()).isEqualTo(16);
		assertThat((int) slider.getMax()).isEqualTo(42);
		assertThat((int) slider.getBlockIncrement()).isEqualTo(3);
		assertThat(slider.isShowTickLabels()).isEqualTo(true);
		assertThat(slider.isShowTickMarks()).isEqualTo(false);
		assertThat(slider.getMajorTickUnit()).isEqualTo(4);
		assertThat(slider.getMinorTickCount()).isEqualTo(2);
		assertThat(slider.isSnapToTicks()).isEqualTo(true);
		assertThat(slider.getOrientation()).isEqualTo(Orientation.VERTICAL);
	}




	@Test (expected = ValidateInputException.class)
	public void test_create_slider_swapped_min_max() {
		buildFxNode(state -> slider().minMax(42, 16));
	}




	@Test
	public void test_create_slider_only_min() {

		final Slider slider = buildFxNode(state -> slider().minMax(16, null));

		assertThat((int) slider.getMin()).isEqualTo(16);
		assertThat((int) slider.getMax()).isGreaterThan(9999999);
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

		final Slider slider = show(testState, new SuiComponent<TestState>(
				state -> slider()
						.anchorsFitParent()
						.minMax(state.getMin(), state.getMax())
						.eventValueChanged(".", Number.class, capturedEvents::add)
		));

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
