package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;

import de.ruegnerlukas.simpleapplication.simpleui.core.node.builders.PropertyValidation;
import de.ruegnerlukas.simpleapplication.simpleui.core.SuiSceneController;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;
import javafx.scene.control.Button;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SuiCommonPropertiesTest extends SuiElementTest {


	@Test
	public void test_disabled_property() {

		class TestState extends SuiState {


			public int step = 0;

		}

		final TestState testState = new TestState();
		final SuiSceneController controller = new SuiSceneController(
				testState,
				TestState.class,
				state -> {
					if (state.step == 0) {
						return SuiButton.button();
					}
					if (state.step == 1) {
						return SuiButton.button(PropertyValidation.disabled(true));
					}
					if (state.step == 2) {
						return SuiButton.button(PropertyValidation.disabled(false));
					}
					if (state.step == 3) {
						return SuiButton.button(PropertyValidation.disabled(true));
					}
					if (state.step == 4) {
						return SuiButton.button();
					}
					return null;
				}
		);
		final Button button = (Button) controller.getRootFxNode();
		show(button);

		assertThat(button.isDisabled()).isFalse();

		syncJfxThread(() -> testState.update(TestState.class, state -> state.step = 1));
		assertThat(button.isDisabled()).isTrue();

		syncJfxThread(() -> testState.update(TestState.class, state -> state.step = 2));
		assertThat(button.isDisabled()).isFalse();

		syncJfxThread(() -> testState.update(TestState.class, state -> state.step = 3));
		assertThat(button.isDisabled()).isTrue();

		syncJfxThread(() -> testState.update(TestState.class, state -> state.step = 4));
		assertThat(button.isDisabled()).isFalse();

	}

}
