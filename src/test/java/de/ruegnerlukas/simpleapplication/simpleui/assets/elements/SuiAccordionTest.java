package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;


import de.ruegnerlukas.simpleapplication.simpleui.assets.events.AccordionExpandedEventData;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.EventProperties;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.core.SuiSceneController;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;
import javafx.application.Platform;
import javafx.scene.control.Accordion;
import javafx.scene.control.Labeled;
import javafx.scene.input.MouseButton;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Phaser;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class SuiAccordionTest extends SuiElementTest {


	@Test
	public void test_expanding_sections_triggers_actions() {
		if(shouldSkipFxTest()) {
			return;
		}

		// build accordion with fixed sections and capture all events in a list
		final Phaser phaser = new Phaser(2);
		final List<AccordionExpandedEventData> capturedEvents = new ArrayList<>();
		final Accordion accordion = (Accordion) new SuiSceneController(
				SuiAccordion.accordion(
						Properties.items(
								SuiAnchorPane.anchorPane(
										Properties.id("section-0"),
										Properties.title("Section 0")
								),
								SuiAnchorPane.anchorPane(
										Properties.id("section-1"),
										Properties.title("Section 1")
								),
								SuiAnchorPane.anchorPane(
										Properties.id("section-2"),
										Properties.title("Section 2")
								),
								SuiAnchorPane.anchorPane(
										Properties.id("section-3"),
										Properties.title("Section 3")
								)
						),
						EventProperties.eventAccordionExpanded(".", e -> {
							capturedEvents.add(e);
							phaser.arrive();
						})
				)
		).getRootFxNode();

		show(accordion);

		// expand section 2
		Platform.runLater(() -> clickOn(accordion.getPanes().get(2), MouseButton.PRIMARY));
		phaser.arriveAndAwaitAdvance();
		assertExpandSection(capturedEvents, "Section 2");
		assertThat(accordion.getExpandedPane().getText()).isEqualTo("Section 2");

		// expand section 1
		Platform.runLater(() -> clickOn(accordion.getPanes().get(1), MouseButton.PRIMARY));
		phaser.arriveAndAwaitAdvance();
		assertCollapseSection(capturedEvents, "Section 2");
		phaser.arriveAndAwaitAdvance();
		assertExpandSection(capturedEvents, "Section 1");
		assertThat(accordion.getExpandedPane().getText()).isEqualTo("Section 1");

		// collapse section 1
		Platform.runLater(() -> clickOn(accordion.getPanes().get(1), MouseButton.PRIMARY));
		phaser.arriveAndAwaitAdvance();
		assertCollapseSection(capturedEvents, "Section 1");
		assertThat(accordion.getExpandedPane()).isNull();

	}




	@Test
	public void test_adding_and_removing_collapsed_sections_does_not_trigger_events_or_collapse_expanded() {

		class TestState extends SuiState {


			public final List<String> sections = new ArrayList<>(List.of("0", "1", "2", "3"));

		}

		// build accordion with section from the state and capture all events in a list
		final List<AccordionExpandedEventData> capturedEvents = new ArrayList<>();
		final TestState testState = new TestState();
		final SuiSceneController controller = new SuiSceneController(
				testState,
				TestState.class,
				state -> SuiAccordion.accordion(
						Properties.items(
								state.sections.stream().map(section -> SuiAnchorPane.anchorPane(
										Properties.id("section-" + section),
										Properties.title("Section " + section)
								))
						),
						EventProperties.eventAccordionExpanded(".", capturedEvents::add)
				)
		);
		final Accordion accordion = (Accordion) controller.getRootFxNode();
		show(accordion);

		// expand "section 1" as preparation, ignore event and assert sections
		accordion.setExpandedPane(accordion.getPanes().get(1));
		capturedEvents.clear();
		assertThat(accordion.getPanes().stream().map(Labeled::getText).collect(Collectors.toList()))
				.containsExactly("Section 0", "Section 1", "Section 2", "Section 3");

		// remove and add a section
		syncJfxThread(() -> testState.updateUnsafe(TestState.class, state -> {
			state.sections.remove("0");
			state.sections.add("4");
		}));

		// check that no events were triggered and the sections have been modified
		assertThat(capturedEvents).isEmpty();
		assertThat(accordion.getExpandedPane().getText()).isEqualTo("Section 1");
		assertThat(accordion.getPanes().stream().map(Labeled::getText).collect(Collectors.toList()))
				.containsExactly("Section 1", "Section 2", "Section 3", "Section 4");

	}




	@Test
	public void test_removing_expanded() {

		class TestState extends SuiState {


			public final List<String> sections = new ArrayList<>(List.of("0", "1"));

		}

		// build accordion with section from the state and capture all events in a list
		final List<AccordionExpandedEventData> capturedEvents = new ArrayList<>();
		final TestState testState = new TestState();
		final SuiSceneController controller = new SuiSceneController(
				testState,
				TestState.class,
				state -> SuiAccordion.accordion(
						Properties.items(
								state.sections.stream().map(section -> SuiAnchorPane.anchorPane(
										Properties.id("section-" + section),
										Properties.title("Section " + section)
								))
						),
						EventProperties.eventAccordionExpanded(".", capturedEvents::add)
				)
		);
		final Accordion accordion = (Accordion) controller.getRootFxNode();
		show(accordion);

		// expand "section 1" as preparation, ignore event and assert sections
		accordion.setExpandedPane(accordion.getPanes().get(1));
		capturedEvents.clear();
		assertThat(accordion.getPanes().stream().map(Labeled::getText).collect(Collectors.toList()))
				.containsExactly("Section 0", "Section 1");

		// remove currently expanded section
		syncJfxThread(() -> testState.update(TestState.class, state -> state.sections.remove("1")));

		// check that the collapse-event was triggered and the sections modified.
		assertCollapseSection(capturedEvents, "Section 1");
		assertThat(accordion.getExpandedPane()).isNull();
		assertThat(accordion.getPanes().stream().map(Labeled::getText).collect(Collectors.toList()))
				.containsExactly("Section 0");
	}




	private void assertExpandSection(final List<AccordionExpandedEventData> capturedEvents, final String title) {
		assertThat(capturedEvents).hasSizeGreaterThanOrEqualTo(1);
		AccordionExpandedEventData event = capturedEvents.remove(0);
		assertThat(event.getPrevExpandedTitle()).isEqualTo(null);
		assertThat(event.getExpandedTitle()).isEqualTo(title);
	}




	private void assertCollapseSection(final List<AccordionExpandedEventData> capturedEvents, final String title) {
		assertThat(capturedEvents).hasSizeGreaterThanOrEqualTo(1);
		AccordionExpandedEventData event = capturedEvents.remove(0);
		assertThat(event.getPrevExpandedTitle()).isEqualTo(title);
		assertThat(event.getExpandedTitle()).isEqualTo(null);
	}


}
