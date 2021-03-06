package de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements;


import de.ruegnerlukas.simpleapplication.core.simpleui.assets.SuiElements;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.events.TabActionEventData;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.state.SuiState;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import lombok.Getter;
import lombok.Setter;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class SuiTabPaneTest extends SuiElementTest {


	@Test
	public void test_create_tab_pane() {
		if (shouldSkipFxTest()) {
			return;
		}

		final TabPane tabPane = buildFxNode(
				state -> SuiElements.tabPane()
						.items(
								SuiElements.button()
										.id("item-0")
										.textContent("Button 0")
										.title("Tab 0"),
								SuiElements.button()
										.id("item-1")
										.textContent("Button 1")
										.title("Tab 1"),
								SuiElements.button()
										.id("item-2")
										.textContent("Button 2")
										.title("Tab 2"),
								SuiElements.button()
										.id("item-3")
										.textContent("Button 3")
										.title("Tab 3")
						)
		);

		assertThat(tabPane.getTabs()).hasSize(4);

		assertThat(tabPane.getTabs().get(0).getText()).isEqualTo("Tab 0");
		assertThat(tabPane.getTabs().get(1).getText()).isEqualTo("Tab 1");
		assertThat(tabPane.getTabs().get(2).getText()).isEqualTo("Tab 2");
		assertThat(tabPane.getTabs().get(3).getText()).isEqualTo("Tab 3");

		assertThat(tabPane.getTabs().get(0).getContent()).isInstanceOf(Button.class);
		assertThat(tabPane.getTabs().get(1).getContent()).isInstanceOf(Button.class);
		assertThat(tabPane.getTabs().get(2).getContent()).isInstanceOf(Button.class);
		assertThat(tabPane.getTabs().get(3).getContent()).isInstanceOf(Button.class);

		assertThat(((Button) tabPane.getTabs().get(0).getContent()).getText()).isEqualTo("Button 0");
		assertThat(((Button) tabPane.getTabs().get(1).getContent()).getText()).isEqualTo("Button 1");
		assertThat(((Button) tabPane.getTabs().get(2).getContent()).getText()).isEqualTo("Button 2");
		assertThat(((Button) tabPane.getTabs().get(3).getContent()).getText()).isEqualTo("Button 3");

		assertThat(tabPane.getTabs().get(0).getText()).isEqualTo("Tab 0");
		assertThat(tabPane.getTabs().get(1).getText()).isEqualTo("Tab 1");
		assertThat(tabPane.getTabs().get(2).getText()).isEqualTo("Tab 2");
		assertThat(tabPane.getTabs().get(3).getText()).isEqualTo("Tab 3");

	}




	@Test
	public void test_user_interaction_select_tabs() {
		if (shouldSkipFxTest()) {
			return;
		}

		final List<TabActionEventData> collectedCloseEvents = new ArrayList<>();
		final List<TabActionEventData> collectedSelectEvents = new ArrayList<>();

		final TabPane tabPane = buildFxNode(
				state -> SuiElements.tabPane()
						.eventClosedTab(".", collectedCloseEvents::add)
						.eventSelectedTab(".", collectedSelectEvents::add)
						.items(
								SuiElements.button()
										.id("item-0")
										.textContent("Button 0")
										.title("Tab 0"),
								SuiElements.button()
										.id("item-1")
										.textContent("Button 1")
										.title("Tab 1"),
								SuiElements.button()
										.id("item-2")
										.textContent("Button 2")
										.title("Tab 2"),
								SuiElements.button()
										.id("item-3")
										.textContent("Button 3")
										.title("Tab 3")
						)
		);
		show(tabPane);

		assertThat(tabPane.getTabs()).hasSize(4);
		assertNoEvent(collectedCloseEvents);
		assertNoEvent(collectedSelectEvents);

		// select a tab
		syncJfxThread(100, () -> clickOn("Tab 2"));
		assertThat(tabPane.getSelectionModel().getSelectedItem()).isNotNull();
		assertThat(tabPane.getSelectionModel().getSelectedItem().getText()).isEqualTo("Tab 2");
		assertNoEvent(collectedCloseEvents);
		assertThat(collectedSelectEvents).hasSize(1);
		assertThat(collectedSelectEvents.get(0).getTitle()).isEqualTo("Tab 2");
		collectedSelectEvents.clear();

		// select a different tab
		syncJfxThread(100, () -> clickOn("Tab 0"));
		assertThat(tabPane.getSelectionModel().getSelectedItem()).isNotNull();
		assertThat(tabPane.getSelectionModel().getSelectedItem().getText()).isEqualTo("Tab 0");
		assertNoEvent(collectedCloseEvents);
		assertThat(collectedSelectEvents).hasSize(1);
		assertThat(collectedSelectEvents.get(0).getTitle()).isEqualTo("Tab 0");
		collectedSelectEvents.clear();
	}




	@Test
	public void test_mutate_tabs() {
		if (shouldSkipFxTest()) {
			return;
		}

		class TestState extends SuiState {


			@Getter
			@Setter
			private List<String> tabs = new ArrayList<>(List.of("0", "1", "2", "3"));

		}

		final TestState testState = new TestState();
		final List<TabActionEventData> collectedCloseEvents = new ArrayList<>();
		final List<TabActionEventData> collectedSelectEvents = new ArrayList<>();

		final TabPane tabPane = show(testState, new SuiComponent<TestState>(
				state -> SuiElements.tabPane()
						.tabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS)
						.eventClosedTab(".", collectedCloseEvents::add)
						.eventSelectedTab(".", collectedSelectEvents::add)
						.items(
								() -> state.tabs.stream().map(
										value -> SuiElements.button()
												.id("item-" + value)
												.textContent("Button " + value)
												.title("Tab " + value)
								).collect(Collectors.toList())
						)
		));

		assertThat(tabPane.getTabs()).hasSize(4);
		assertNoEvent(collectedCloseEvents);
		assertNoEvent(collectedSelectEvents);

		// Remove a single tab
		syncJfxThread(() -> testState.updateUnsafe(TestState.class, state -> {
			state.getTabs().remove(3); // remove "Tab 3"
		}));
		assertThat(tabPane.getTabs()).hasSize(3);
		assertNoEvent(collectedCloseEvents);
		assertNoEvent(collectedSelectEvents);

		// Remove the selected tab
		syncJfxThread(100, () -> tabPane.getSelectionModel().select(1));
		collectedSelectEvents.clear();

		syncJfxThread(() -> testState.updateUnsafe(TestState.class, state -> {
			state.getTabs().remove(1); // remove "Tab 1"
		}));
		assertThat(tabPane.getTabs()).hasSize(2);
		assertThat(tabPane.getSelectionModel().getSelectedItem().getText()).isEqualTo("Tab 0");
		assertNoEvent(collectedCloseEvents);
		assertNoEvent(collectedSelectEvents);

		// Add a new tab
		syncJfxThread(() -> testState.updateUnsafe(TestState.class, state -> {
			state.getTabs().add("X");
		}));
		assertThat(tabPane.getTabs()).hasSize(3);
		assertNoEvent(collectedCloseEvents);
		assertNoEvent(collectedSelectEvents);

	}


}
