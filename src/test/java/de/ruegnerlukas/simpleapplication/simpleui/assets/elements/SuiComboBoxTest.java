package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;


import de.ruegnerlukas.simpleapplication.simpleui.assets.events.ValueChangedEventData;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static de.ruegnerlukas.simpleapplication.simpleui.assets.SuiElements.comboBox;
import static org.assertj.core.api.Assertions.assertThat;

public class SuiComboBoxTest extends SuiElementTest {


	@Test
	public void test_creating_combo_box() {
		if (shouldSkipFxTest()) {
			return;
		}

		final ComboBox<TestItem> combobox = buildFxNode(
				state -> comboBox()
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
								item -> item != null ? (item.name + ": " + item.number) : "null"
						)
						.promptText("Test Prompt-Text")
						.editable()
		);

		show(combobox);

		// assert combobox-has correct items
		assertItems(combobox, List.of(
				new TestItem("A", 1),
				new TestItem("B", 2),
				new TestItem("C", 3)
		));

		// the field is editable
		assertThat(combobox.isEditable()).isTrue();
		assertThat(combobox.getEditor().isEditable()).isTrue();

		// the prompt text is shown
		assertThat(combobox.getPromptText()).isEqualTo("Test Prompt-Text");
		assertThat(combobox.getEditor().getPromptText()).isEqualTo("Test Prompt-Text");

		// the converter was added
		assertThat(combobox.converterProperty().get().fromString("X: -1")).isEqualTo(new TestItem("X", -1));
		assertThat(combobox.converterProperty().get().toString(new TestItem("X", -1))).isEqualTo("X: -1");

	}




	@Test
	public void test_real_user_interaction_non_editable() {
		if (shouldSkipFxTest()) {
			return;
		}

		// setup
		class TestState extends SuiState {


			private List<TestItem> items = new ArrayList<>(List.of(
					new TestItem("A", 1),
					new TestItem("B", 2),
					new TestItem("C", 3)
			));

		}

		final TestState testState = new TestState();
		final List<ValueChangedEventData<TestItem>> collectedEvents = new ArrayList<>();

		final ComboBox<TestItem> comboBox = buildFxNode(TestState.class, testState,
				state -> comboBox()
						.sizeMax(100, 40)
						.contentItems(state.items, null)
						.eventValueChanged(".", TestItem.class, collectedEvents::add)
		);

		show(comboBox);

		// select a new item -> item selected + event
		selectItem(comboBox, 1);
		assertSelected(comboBox, new TestItem("B", 2));
		assertEvent(collectedEvents, null, new TestItem("B", 2));

		// select same item again -> item still selected, no event
		selectItem(comboBox, -1);
		assertSelected(comboBox, new TestItem("B", 2));
		assertNoEvent(collectedEvents);

		// escape selecting item -> item still selected, no event
		selectItemEscape(comboBox);
		assertSelected(comboBox, new TestItem("B", 2));
		assertNoEvent(collectedEvents);

	}




	@Test
	public void test_real_user_interaction_searchable() {
		if (shouldSkipFxTest()) {
			return;
		}

		// setup
		class TestState extends SuiState {


			private List<TestItem> items = new ArrayList<>(List.of(
					new TestItem("A", 1),
					new TestItem("B", 2),
					new TestItem("C", 3)
			));

		}

		final TestState testState = new TestState();
		final List<ValueChangedEventData<TestItem>> collectedEvents = new ArrayList<>();

		final ComboBox<TestItem> comboBox = buildFxNode(TestState.class, testState,
				state -> comboBox()
						.sizeMax(100, 40)
						.contentItems(state.items, null)
						.searchable()
						.eventValueChanged(".", TestItem.class, collectedEvents::add)
						.contentItemConverter(".", TestItem.class,
								str -> new TestItem(str.split(":")[0].trim(), Integer.parseInt(str.split(":")[1].trim())),
								item -> item != null ? (item.name + ": " + item.number) : "null"
						)
		);
		show(comboBox);

		// select a new item -> item selected + event
		selectItem(comboBox, 1);
		assertSelected(comboBox, new TestItem("B", 2));
		assertEvent(collectedEvents, null, new TestItem("B", 2));

		// select same item again -> item still selected, no event
		selectItem(comboBox, -1);
		assertSelected(comboBox, new TestItem("B", 2));
		assertNoEvent(collectedEvents);

		// escape while selecting item -> item still selected, no event
		selectItemEscape(comboBox);
		assertSelected(comboBox, new TestItem("B", 2));
		assertNoEvent(collectedEvents);

		// search for item "C"
		selectItemTyped(comboBox, KeyCode.C);
		assertSelected(comboBox, new TestItem("C", 3));
		assertEvent(collectedEvents, new TestItem("B", 2), new TestItem("C", 3));

	}




	@Test
	public void test_real_user_interaction_editable() {
		if (shouldSkipFxTest()) {
			return;
		}

		// setup
		class TestState extends SuiState {


			private List<TestItem> items = new ArrayList<>(List.of(
					new TestItem("A", 1),
					new TestItem("B", 2),
					new TestItem("C", 3)
			));

		}

		final TestState testState = new TestState();
		final List<ValueChangedEventData<TestItem>> collectedEvents = new ArrayList<>();


		final ComboBox<TestItem> comboBox = buildFxNode(TestState.class, testState,
				state -> comboBox()
						.sizeMax(100, 40)
						.contentItems(state.items, null)
						.editable()
						.eventValueChanged(".", TestItem.class, collectedEvents::add)
						.contentItemConverter(".", TestItem.class,
								str -> new TestItem(String.valueOf(str.charAt(0)).toUpperCase(), Integer.parseInt(String.valueOf(str.charAt(1)))),
								item -> item != null ? (item.name + item.number) : "null"
						)
		);
		show(comboBox);

		// select a new item -> item selected + event
		selectItem(comboBox, true, 1);
		assertSelected(comboBox, new TestItem("B", 2));
		assertEvent(collectedEvents, null, new TestItem("B", 2));

		// edit and select item "C"
		syncJfxThread(200, () -> clickOn(comboBox, MouseButton.PRIMARY));
		syncJfxThread(100, () -> eraseText(4));
		syncJfxThread(200, () -> type(KeyCode.C, KeyCode.DIGIT3));
		syncJfxThread(200, () -> type(KeyCode.ENTER));
		assertSelected(comboBox, new TestItem("C", 3));
		assertEvent(collectedEvents, new TestItem("B", 2), new TestItem("C", 3));

		// edit and select unknown item "X"
		syncJfxThread(200, () -> clickOn(comboBox, MouseButton.PRIMARY));
		syncJfxThread(100, () -> eraseText(2));
		syncJfxThread(200, () -> type(KeyCode.X, KeyCode.DIGIT9));
		syncJfxThread(200, () -> type(KeyCode.ENTER));
		assertSelected(comboBox, new TestItem("C", 3));
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
