package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;


import de.ruegnerlukas.simpleapplication.simpleui.assets.events.ValueChangedEventData;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.EventProperties;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.core.SuiSceneController;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SuiChoiceBoxTest extends SuiElementTest {


	@Test
	public void test_creating_choice_box() {

		@SuppressWarnings ("unchecked") final ChoiceBox<TestItem> choiceBox = (ChoiceBox<TestItem>) new SuiSceneController(
				SuiChoiceBox.choiceBox(
						Properties.contentItems(List.of(
								new TestItem("A", 1),
								new TestItem("B", 2),
								new TestItem("C", 3)
						)),
						Properties.selectedItem(new TestItem("B", 2)),
						Properties.contentItemConverter(".", TestItem.class,
								str -> new TestItem(str.split(":")[0].trim(), Integer.parseInt(str.split(":")[1].trim())),
								item -> item.name + ": " + item.number
						)
				)
		).getRootFxNode();

		// assert choicebox-has correct items
		assertThat(choiceBox.getItems()).hasSize(3);
		assertThat(choiceBox.getItems().get(0)).isEqualTo(new TestItem("A", 1));
		assertThat(choiceBox.getItems().get(1)).isEqualTo(new TestItem("B", 2));
		assertThat(choiceBox.getItems().get(2)).isEqualTo(new TestItem("C", 3));

		// correct item is selected
		assertThat(choiceBox.getValue()).isEqualTo(new TestItem("B", 2));

		// and the converter was added
		assertThat(choiceBox.converterProperty().get().fromString("X: -1")).isEqualTo(new TestItem("X", -1));
		assertThat(choiceBox.converterProperty().get().toString(new TestItem("X", -1))).isEqualTo("X: -1");

	}




	@Test
	public void test_mutate_selected_item() {

		// setup
		class TestState extends SuiState {


			private TestItem selectedItem = null;

		}

		final TestState testState = new TestState();
		final List<ValueChangedEventData<TestItem>> collectedEvents = new ArrayList<>();

		// create controller with choice-box
		final SuiSceneController controller = new SuiSceneController(
				testState,
				TestState.class,
				state -> SuiChoiceBox.choiceBox(
						Properties.contentItems(List.of(
								new TestItem("A", 1),
								new TestItem("B", 2),
								new TestItem("C", 3)
						)),
						Properties.selectedItem(state.selectedItem),
						EventProperties.eventValueChangedType(".", TestItem.class, collectedEvents::add)
				)
		);
		@SuppressWarnings ("unchecked") final ChoiceBox<TestItem> choiceBox = (ChoiceBox<TestItem>) controller.getRootFxNode();

		// no value selected at first
		assertThat(choiceBox.getValue()).isNull();

		// set selected value in state
		syncJfxThread(() -> testState.updateUnsafe(TestState.class, state -> {
			state.selectedItem = new TestItem("B", 2);
		}));

		// correct item is now selected ...
		assertThat(choiceBox.getValue()).isEqualTo(new TestItem("B", 2));

		// ... and an event was triggered
		// todo: check if we really want to trigger an event here
		assertThat(collectedEvents).hasSize(1);
		assertThat(collectedEvents.get(0).getPrevValue()).isNull();
		assertThat(collectedEvents.get(0).getValue()).isEqualTo(new TestItem("B", 2));
		collectedEvents.clear();

		// set selected value to unknown item
		syncJfxThread(() -> testState.updateUnsafe(TestState.class, state -> {
			state.selectedItem = new TestItem("X", -1);
		}));

		// unknown item is now selected
		// todo: check if we really want to allow selecting items not in list
		assertThat(choiceBox.getValue()).isEqualTo(new TestItem("X", -1));

	}




	@Test
	public void test_mutate_content_items() {

		// setup
		class TestState extends SuiState {


			private TestItem selected = null;

			private List<TestItem> items = new ArrayList<>(List.of(
					new TestItem("A", 1),
					new TestItem("B", 2),
					new TestItem("C", 3)
			));

		}

		final TestState testState = new TestState();
		final List<ValueChangedEventData<TestItem>> collectedEvents = new ArrayList<>();

		// create controller with choice-box
		final SuiSceneController controller = new SuiSceneController(
				testState,
				TestState.class,
				state -> SuiChoiceBox.choiceBox(
						Properties.selectedItem(state.selected),
						Properties.contentItems(state.items),
						EventProperties.eventValueChangedType(".", TestItem.class, collectedEvents::add)
				)
		);
		@SuppressWarnings ("unchecked") final ChoiceBox<TestItem> choiceBox = (ChoiceBox<TestItem>) controller.getRootFxNode();

		// correct items were added to choice-box -> [A, B, C]
		assertThat(choiceBox.getItems()).hasSize(3);
		assertThat(choiceBox.getItems().get(0)).isEqualTo(new TestItem("A", 1));
		assertThat(choiceBox.getItems().get(1)).isEqualTo(new TestItem("B", 2));
		assertThat(choiceBox.getItems().get(2)).isEqualTo(new TestItem("C", 3));

		// remove all items -> []
		syncJfxThread(() -> testState.updateUnsafe(TestState.class, state -> {
			state.items.clear();
		}));
		assertThat(choiceBox.getItems()).hasSize(0);

		// add all items and select one -> [A2, B2, C2]
		syncJfxThread(() -> testState.updateUnsafe(TestState.class, state -> {
			state.items.addAll(List.of(
					new TestItem("A2", 10),
					new TestItem("B2", 20),
					new TestItem("C2", 30)
			));
			state.selected = new TestItem("B2", 20);
		}));
		assertThat(choiceBox.getItems()).hasSize(3);
		assertThat(choiceBox.getItems().get(0)).isEqualTo(new TestItem("A2", 10));
		assertThat(choiceBox.getItems().get(1)).isEqualTo(new TestItem("B2", 20));
		assertThat(choiceBox.getItems().get(2)).isEqualTo(new TestItem("C2", 30));
		assertThat(choiceBox.getValue()).isEqualTo(new TestItem("B2", 20));
		assertThat(collectedEvents).hasSize(1);
		assertThat(collectedEvents.get(0).getValue()).isEqualTo(new TestItem("B2", 20));
		assertThat(collectedEvents.get(0).getPrevValue()).isNull();
		collectedEvents.clear();

		// remove some items -> [B2]
		syncJfxThread(() -> testState.updateUnsafe(TestState.class, state -> {
			state.items.remove(new TestItem("A2", 10));
			state.items.remove(new TestItem("C2", 30));
		}));
		assertThat(choiceBox.getItems()).hasSize(1);
		assertThat(choiceBox.getItems().get(0)).isEqualTo(new TestItem("B2", 20));
		assertThat(choiceBox.getValue()).isEqualTo(new TestItem("B2", 20));
		assertThat(choiceBox.getValue()).isEqualTo(new TestItem("B2", 20));
		assertThat(collectedEvents).isEmpty();

		// add some items -> [B2, X1, X2]
		syncJfxThread(() -> testState.updateUnsafe(TestState.class, state -> {
			state.items.add(new TestItem("X1", -1));
			state.items.add(new TestItem("X2", -2));
		}));
		assertThat(choiceBox.getItems()).hasSize(3);
		assertThat(choiceBox.getItems().get(0)).isEqualTo(new TestItem("B2", 20));
		assertThat(choiceBox.getItems().get(1)).isEqualTo(new TestItem("X1", -1));
		assertThat(choiceBox.getItems().get(2)).isEqualTo(new TestItem("X2", -2));
		assertThat(choiceBox.getValue()).isEqualTo(new TestItem("B2", 20));
		assertThat(choiceBox.getValue()).isEqualTo(new TestItem("B2", 20));
		assertThat(collectedEvents).isEmpty();

	}




	@Test
	public void test_choicebox_with_real_user_interaction() {
		if(shouldSkipFxTest()) {
			return;
		}

		// setup
		class TestState extends SuiState {


			private TestItem selected = null;

			private List<TestItem> items = new ArrayList<>(List.of(
					new TestItem("A", 1),
					new TestItem("B", 2),
					new TestItem("C", 3)
			));

		}

		final TestState testState = new TestState();
		final List<ValueChangedEventData<TestItem>> collectedEvents = new ArrayList<>();

		// create controller with choice-box
		final SuiSceneController controller = new SuiSceneController(
				testState,
				TestState.class,
				state -> SuiChoiceBox.choiceBox(
						Properties.selectedItem(state.selected),
						Properties.contentItems(state.items),
						EventProperties.eventValueChangedType(".", TestItem.class, collectedEvents::add)
				)
		);
		@SuppressWarnings ("unchecked") final ChoiceBox<TestItem> choiceBox = (ChoiceBox<TestItem>) controller.getRootFxNode();
		show(choiceBox);

		// select a new item -> item selected + event
		syncJfxThread(200, () -> this.clickOn(choiceBox, MouseButton.PRIMARY));
		syncJfxThread(100, () -> type(KeyCode.DOWN));
		syncJfxThread(100, () -> type(KeyCode.ENTER));
		assertThat(choiceBox.getValue()).isEqualTo(new TestItem("B", 2)); // TODO: selected item does no longer reflect state -> check
		assertThat(collectedEvents).hasSize(1);
		assertThat(collectedEvents.get(0).getValue()).isEqualTo(new TestItem("B", 2));
		assertThat(collectedEvents.get(0).getPrevValue()).isNull();
		collectedEvents.clear();

		// select same item again -> item still selected, no event
		syncJfxThread(200, () -> this.clickOn(choiceBox, MouseButton.PRIMARY));
		syncJfxThread(100, () -> type(KeyCode.ENTER));
		assertThat(choiceBox.getValue()).isEqualTo(new TestItem("B", 2));
		assertThat(collectedEvents).isEmpty();

		// escape selecting item -> item still selected, no event
		syncJfxThread(200, () -> this.clickOn(choiceBox, MouseButton.PRIMARY));
		syncJfxThread(100, () -> type(KeyCode.ESCAPE));
		assertThat(choiceBox.getValue()).isEqualTo(new TestItem("B", 2));
		assertThat(collectedEvents).isEmpty();

	}




	@Getter
	@AllArgsConstructor
	@ToString
	private static class TestItem {


		private final String name;

		private final int number;




		public boolean equals(Object o) {
			if (o instanceof TestItem) {
				final TestItem other = (TestItem) o;
				return other.getName().equals(this.getName()) && other.getNumber() == this.getNumber();
			} else {
				return false;
			}
		}




		@Override
		public int hashCode() {
			int result = getName().hashCode();
			result = 31 * result + getNumber();
			return result;
		}

	}

}
