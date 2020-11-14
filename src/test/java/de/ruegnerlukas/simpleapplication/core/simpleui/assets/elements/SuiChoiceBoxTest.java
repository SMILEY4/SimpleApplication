package de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements;


import de.ruegnerlukas.simpleapplication.core.simpleui.assets.events.ValueChangedEventData;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.state.SuiState;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.ChoiceBox;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.junit.Test;

import static de.ruegnerlukas.simpleapplication.core.simpleui.assets.SuiElements.choiceBox;
import static org.assertj.core.api.Assertions.assertThat;

public class SuiChoiceBoxTest extends SuiElementTest {


	@Test
	public void test_creating_choice_box() {

		final ChoiceBox<TestItem> choiceBox = buildFxNode(
				state -> choiceBox()
						.contentItems(
								List.of(
										new TestItem("A", 1),
										new TestItem("B", 2),
										new TestItem("C", 3)
								),
								new TestItem("B", 2)
						)
						.contentItemConverter(".", TestItem.class,
								str -> new TestItem(str.split(":")[0].trim(), Integer.parseInt(str.split(":")[1].trim())),
								item -> item.name + ": " + item.number
						)
		);

		assertItems(choiceBox, List.of(
				new TestItem("A", 1),
				new TestItem("B", 2),
				new TestItem("C", 3)
		));

		assertSelected(choiceBox, new TestItem("B", 2));

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
		final ChoiceBox<TestItem> choiceBox = show(testState, new SuiComponent<TestState>(
				state -> choiceBox()
						.contentItems(List.of(
								new TestItem("A", 1),
								new TestItem("B", 2),
								new TestItem("C", 3)
								),
								state.selectedItem
						)
						.eventValueChanged(".", TestItem.class, collectedEvents::add)
		));

		// no value selected at first
		assertSelected(choiceBox, null);

		// set selected value in state
		syncJfxThread(() -> testState.updateUnsafe(TestState.class, state -> {
			state.selectedItem = new TestItem("B", 2);
		}));
		assertSelected(choiceBox, new TestItem("B", 2));
		assertNoEvent(collectedEvents);

		// set selected value to unknown item
		syncJfxThread(() -> testState.updateUnsafe(TestState.class, state -> {
			state.selectedItem = new TestItem("X", -1);
		}));
		assertSelected(choiceBox, null);

	}




	@Test
	public void test_mutate_content_items() {

		// setup
		class TestState extends SuiState {


			private TestItem selected = null;

			private final List<TestItem> items = new ArrayList<>(List.of(
					new TestItem("A", 1),
					new TestItem("B", 2),
					new TestItem("C", 3)
			));

		}

		final TestState testState = new TestState();
		final List<ValueChangedEventData<TestItem>> collectedEvents = new ArrayList<>();

		// create controller with choice-box
		final ChoiceBox<TestItem> choiceBox = show(testState, new SuiComponent<TestState>(
				state -> choiceBox()
						.contentItems(state.items, state.selected)
						.eventValueChanged(".", TestItem.class, collectedEvents::add)
		));

		// correct items were added to choice-box -> [A, B, C]
		assertItems(choiceBox, List.of(
				new TestItem("A", 1),
				new TestItem("B", 2),
				new TestItem("C", 3)
		));

		// remove all items -> []
		syncJfxThread(() -> testState.updateUnsafe(TestState.class, state -> state.items.clear()));
		assertItems(choiceBox, List.of());

		// add all items and select one -> [A2, B2, C2]
		syncJfxThread(() -> testState.updateUnsafe(TestState.class, state -> {
			state.items.addAll(List.of(
					new TestItem("A2", 10),
					new TestItem("B2", 20),
					new TestItem("C2", 30)
			));
			state.selected = new TestItem("B2", 20);
		}));

		assertItems(choiceBox, List.of(
				new TestItem("A2", 10),
				new TestItem("B2", 20),
				new TestItem("C2", 30)
		));
		assertNoEvent(collectedEvents);

		// remove some items -> [B2]
		syncJfxThread(() -> testState.updateUnsafe(TestState.class, state -> {
			state.items.remove(new TestItem("A2", 10));
			state.items.remove(new TestItem("C2", 30));
		}));
		assertItems(choiceBox, List.of(new TestItem("B2", 20)));
		assertSelected(choiceBox, new TestItem("B2", 20));
		assertNoEvent(collectedEvents);

		// add some items -> [B2, X1, X2]
		syncJfxThread(() -> testState.updateUnsafe(TestState.class, state -> {
			state.items.add(new TestItem("X1", -1));
			state.items.add(new TestItem("X2", -2));
		}));

		syncJfxThread(() -> testState.updateUnsafe(TestState.class, state -> {
			state.items.remove(new TestItem("A2", 10));
			state.items.remove(new TestItem("C2", 30));
		}));
		assertItems(choiceBox, List.of(
				new TestItem("B2", 20),
				new TestItem("X1", -1),
				new TestItem("X2", -2)
		));
		assertSelected(choiceBox, new TestItem("B2", 20));
		assertNoEvent(collectedEvents);

	}




	@Test
	public void test_choicebox_with_real_user_interaction() {
		if (shouldSkipFxTest()) {
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

		final ChoiceBox<TestItem> choiceBox = buildFxNode(TestState.class, testState,
				state -> choiceBox()
						.contentItems(state.items, state.selected)
						.eventValueChanged(".", TestItem.class, collectedEvents::add)
		);
		show(choiceBox);

		// select a new item -> item selected + event
		selectItem(choiceBox, 1);
		assertSelected(choiceBox, new TestItem("B", 2));
		assertEvent(collectedEvents, null, new TestItem("B", 2));

		// select same item again -> item still selected, no event
		selectItem(choiceBox, 0);
		assertSelected(choiceBox, new TestItem("B", 2));
		assertNoEvent(collectedEvents);

		// escape selecting item -> item still selected, no event
		selectItem(choiceBox, 0);
		assertSelected(choiceBox, new TestItem("B", 2));
		assertNoEvent(collectedEvents);

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
