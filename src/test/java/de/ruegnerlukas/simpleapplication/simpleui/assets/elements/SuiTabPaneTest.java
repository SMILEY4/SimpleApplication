package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;


import de.ruegnerlukas.simpleapplication.simpleui.assets.events.TabActionEventData;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.EventProperties;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.core.SuiSceneController;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;
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

		final TabPane tabPane = (TabPane) new SuiSceneController(
				SuiTabPane.tabPane(
						Properties.items(
								SuiButton.button(
										Properties.id("item-0"),
										Properties.textContent("Button 0"),
										Properties.title("Tab 0")
								),
								SuiButton.button(
										Properties.id("item-1"),
										Properties.textContent("Button 1"),
										Properties.title("Tab 1")
								),
								SuiButton.button(
										Properties.id("item-2"),
										Properties.textContent("Button 2"),
										Properties.title("Tab 2")
								),
								SuiButton.button(
										Properties.id("item-3"),
										Properties.textContent("Button 3"),
										Properties.title("Tab 3")
								)
						)
				)
		).getRootFxNode();

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

		final SuiSceneController controller = new SuiSceneController(
				SuiTabPane.tabPane(
						EventProperties.eventClosedTab(".", collectedCloseEvents::add),
						EventProperties.eventSelectedTab(".", collectedSelectEvents::add),
						Properties.items(
								SuiButton.button(
										Properties.id("item-0"),
										Properties.textContent("Button 0"),
										Properties.title("Tab 0")
								),
								SuiButton.button(
										Properties.id("item-1"),
										Properties.textContent("Button 1"),
										Properties.title("Tab 1")
								),
								SuiButton.button(
										Properties.id("item-2"),
										Properties.textContent("Button 2"),
										Properties.title("Tab 2")
								),
								SuiButton.button(
										Properties.id("item-3"),
										Properties.textContent("Button 3"),
										Properties.title("Tab 3")
								)
						)
				)
		);

		final TabPane tabPane = (TabPane) controller.getRootFxNode();
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

		final SuiSceneController controller = new SuiSceneController(
				testState,
				TestState.class,
				state -> SuiTabPane.tabPane(
						Properties.tabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS),
						EventProperties.eventClosedTab(".", collectedCloseEvents::add),
						EventProperties.eventSelectedTab(".", collectedSelectEvents::add),
						Properties.items(
								() -> state.tabs.stream().map(
										value -> SuiButton.button(
												Properties.id("item-" + value),
												Properties.textContent("Button " + value),
												Properties.title("Tab " + value)
										)).collect(Collectors.toList())
						)
				)
		);

		final TabPane tabPane = (TabPane) controller.getRootFxNode();
		show(tabPane);

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
