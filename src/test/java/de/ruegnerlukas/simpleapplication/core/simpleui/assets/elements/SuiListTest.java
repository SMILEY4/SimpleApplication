package de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements;

import de.ruegnerlukas.simpleapplication.core.simpleui.assets.events.ItemSelectedEventData;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.state.SuiState;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static de.ruegnerlukas.simpleapplication.core.simpleui.assets.SuiElements.list;
import static org.assertj.core.api.Assertions.assertThat;

public class SuiListTest extends SuiElementTest {


	@Test
	public void test_create_list() {

		final ListView<TestItem> list = buildFxNode(
				state -> list()
						.contentItems(List.of(
								new TestItem("A", 1),
								new TestItem("B", 2),
								new TestItem("C", 3),
								new TestItem("D", 4)
						))
						.promptText("List is empty.")
						.multiselect()
		);

		assertThat(((Label) list.getPlaceholder()).getText()).isEqualTo("List is empty.");
		assertThat(list.getSelectionModel().getSelectionMode()).isEqualTo(SelectionMode.MULTIPLE);
		assertThat(list.getItems()).hasSize(4);
		assertThat(list.getItems().get(0)).isEqualTo(new TestItem("A", 1));
		assertThat(list.getItems().get(1)).isEqualTo(new TestItem("B", 2));
		assertThat(list.getItems().get(2)).isEqualTo(new TestItem("C", 3));
		assertThat(list.getItems().get(3)).isEqualTo(new TestItem("D", 4));
	}




	@Test
	public void test_mutating_content() {

		class TestState extends SuiState {


			@Getter
			private final List<TestItem> items = new ArrayList<>();

		}

		final TestState testState = new TestState();

		final ListView<TestItem> listView = show(testState, new SuiComponent<TestState>(
				state -> list().contentItems(state.items)
		));
		assertItems(listView, List.of());

		// add all items
		syncJfxThread(() -> testState.updateUnsafe(TestState.class, state -> state.items.addAll(List.of(
				new TestItem("A", 1),
				new TestItem("B", 2),
				new TestItem("C", 3),
				new TestItem("D", 4)
		))));
		assertItems(listView, List.of(
				new TestItem("A", 1),
				new TestItem("B", 2),
				new TestItem("C", 3),
				new TestItem("D", 4)
		));

		// remove some items
		syncJfxThread(() -> testState.updateUnsafe(TestState.class, state -> {
			state.items.remove(3); // D
			state.items.remove(1); // B
		}));
		assertItems(listView, List.of(
				new TestItem("A", 1),
				new TestItem("C", 3)
		));

		// add some items
		syncJfxThread(() -> testState.updateUnsafe(TestState.class, state -> {
			state.items.add(0, new TestItem("X0", 10));
			state.items.add(new TestItem("X1", 11));
		}));
		assertItems(listView, List.of(
				new TestItem("X0", 10),
				new TestItem("A", 1),
				new TestItem("C", 3),
				new TestItem("X1", 11)
		));

		// remove all items
		syncJfxThread(() -> testState.updateUnsafe(TestState.class, state -> {
			state.items.clear();
		}));
		assertItems(listView, List.of());

	}




	@Test
	public void test_selection_single_item() {

		class TestState extends SuiState {


			@Getter
			private final List<TestItem> items = new ArrayList<>(List.of(
					new TestItem("A", 1),
					new TestItem("B", 2),
					new TestItem("C", 3),
					new TestItem("D", 4)
			));

		}

		final TestState testState = new TestState();
		final List<ItemSelectedEventData<TestItem>> capturedEvents = new ArrayList<>();

		final ListView<TestItem> listView = show(testState,new SuiComponent<TestState>(
				state -> list()
						.contentItems(state.items)
						.eventItemSelected(".", TestItem.class, capturedEvents::add)
		));

		// select an item
		syncJfxThread(() -> listView.getSelectionModel().select(1));
		assertThat(listView.getSelectionModel().getSelectedItem()).isEqualTo(new TestItem("B", 2));
		assertThat(listView.getSelectionModel().getSelectedItems()).hasSize(1);
		assertThat(capturedEvents).hasSize(1);
		assertThat(capturedEvents.get(0).getSelection()).hasSize(1);
		assertThat(capturedEvents.get(0).getSelection()).containsExactly(new TestItem("B", 2));
		capturedEvents.clear();

		// select another item
		syncJfxThread(() -> listView.getSelectionModel().select(2));
		assertThat(listView.getSelectionModel().getSelectedItem()).isEqualTo(new TestItem("C", 3));
		assertThat(listView.getSelectionModel().getSelectedItems()).hasSize(1);
		assertThat(capturedEvents).hasSize(1);
		assertThat(capturedEvents.get(0).getSelection()).hasSize(1);
		assertThat(capturedEvents.get(0).getSelection()).containsExactly(new TestItem("C", 3));
		capturedEvents.clear();

		// select same item
		syncJfxThread(() -> listView.getSelectionModel().select(2));
		assertThat(listView.getSelectionModel().getSelectedItem()).isEqualTo(new TestItem("C", 3));
		assertThat(listView.getSelectionModel().getSelectedItems()).hasSize(1);
		assertThat(capturedEvents).hasSize(0);

		// clear selection
		syncJfxThread(() -> listView.getSelectionModel().clearSelection());
		assertThat(listView.getSelectionModel().getSelectedItem()).isNull();
		assertThat(listView.getSelectionModel().getSelectedItems()).hasSize(0);
		assertThat(capturedEvents).hasSize(1);
		assertThat(capturedEvents.get(0).getSelection()).isEmpty();
		capturedEvents.clear();

		// remove selected item from list
		syncJfxThread(() -> listView.getSelectionModel().select(0));
		assertThat(listView.getSelectionModel().getSelectedItem()).isEqualTo(new TestItem("A", 1));
		capturedEvents.clear();

		syncJfxThread(() -> testState.updateUnsafe(TestState.class, state -> state.items.remove(0)));
		assertItems(listView, List.of(
				new TestItem("B", 2),
				new TestItem("C", 3),
				new TestItem("D", 4)
		));
		assertThat(listView.getSelectionModel().getSelectedItem()).isNull();
		assertNoEvent(capturedEvents);
	}




	private void assertItems(final ListView<TestItem> listView, final List<TestItem> expectedItems) {
		assertThat(listView.getItems()).hasSize(expectedItems.size());
		for (int i = 0; i < expectedItems.size(); i++) {
			assertThat(listView.getItems().get(i)).isEqualTo(expectedItems.get(i));
		}
	}




	@Getter
	@AllArgsConstructor
	private static class TestItem {


		private final String name;
		private final int number;




		@Override
		public boolean equals(final Object obj) {
			if (obj instanceof TestItem) {
				TestItem other = (TestItem) obj;
				return getName().equals(other.getName()) && getNumber() == other.getNumber();
			} else {
				return false;
			}
		}

	}

}
