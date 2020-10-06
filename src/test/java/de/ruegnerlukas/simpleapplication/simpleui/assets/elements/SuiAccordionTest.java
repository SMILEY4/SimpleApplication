package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;


import de.ruegnerlukas.simpleapplication.simpleui.assets.events.SectionEventData;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.EventProperties;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.core.SuiSceneController;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;
import javafx.scene.control.Accordion;
import javafx.scene.control.Labeled;
import javafx.scene.input.MouseButton;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class SuiAccordionTest extends SuiElementTest {


	@Test
	public void test_expanding_sections_triggers_actions() {
		if(shouldSkipFxTest()) {
			return;
		}

		// build accordion with fixed sections and capture all events in a list
		final List<SectionEventData> capturedEvents = new ArrayList<>();
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
						EventProperties.eventAccordionExpanded(".", capturedEvents::add)
				)
		).getRootFxNode();

		show(accordion);
		capturedEvents.clear();

		// expand section 2
		syncJfxThread(100, () -> clickOn(accordion.getPanes().get(2), MouseButton.PRIMARY));
		assertThat(accordion.getExpandedPane().getText()).isEqualTo("Section 2");
		assertThat(capturedEvents).hasSize(1);
		assertThat(capturedEvents.get(0).getSectionTitle()).isEqualTo("Section 2");
		assertThat(capturedEvents.get(0).isExpanded()).isEqualTo(true);
		capturedEvents.clear();

		// expand section 1
		syncJfxThread(100, () -> clickOn(accordion.getPanes().get(1), MouseButton.PRIMARY));
		assertThat(accordion.getExpandedPane().getText()).isEqualTo("Section 1");
		assertThat(capturedEvents).hasSize(1);
		assertThat(capturedEvents.get(0).getSectionTitle()).isEqualTo("Section 1");
		assertThat(capturedEvents.get(0).isExpanded()).isEqualTo(true);
		capturedEvents.clear();

		// collapse section 1
		syncJfxThread(100, () -> clickOn(accordion.getPanes().get(1), MouseButton.PRIMARY));
		assertThat(accordion.getExpandedPane()).isNull();
		assertThat(capturedEvents).hasSize(1);
		assertThat(capturedEvents.get(0).getSectionTitle()).isEqualTo("Section 1");
		assertThat(capturedEvents.get(0).isExpanded()).isEqualTo(false);
		capturedEvents.clear();

	}




	@Test
	public void test_adding_and_removing_collapsed_sections_does_not_trigger_events_or_collapse_expanded() {

		class TestState extends SuiState {


			public final List<String> sections = new ArrayList<>(List.of("0", "1", "2", "3"));

		}

		// build accordion with section from the state and capture all events in a list
		final List<SectionEventData> capturedEvents = new ArrayList<>();
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

		// remove a section that is not expanded and add a new section
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
		final List<SectionEventData> capturedEvents = new ArrayList<>();
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

		// check that no event was triggered and the sections modified
		assertThat(capturedEvents).isEmpty();
		assertThat(accordion.getExpandedPane()).isNull();
		assertThat(accordion.getPanes().stream().map(Labeled::getText).collect(Collectors.toList()))
				.containsExactly("Section 0");
	}



	@Test
	public void test_expanded_section_property() {

		class TestState extends SuiState {

			public final List<String> sections = new ArrayList<>(List.of("0", "1", "2", "3"));

			public String section = "Section 1";

		}

		// build accordion with section from the state and capture all events in a list
		final List<SectionEventData> capturedEvents = new ArrayList<>();
		final TestState testState = new TestState();
		final SuiSceneController controller = new SuiSceneController(
				testState,
				TestState.class,
				state -> SuiAccordion.accordion(
						Properties.expandedSection(state.section),
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

		// test initially expanded section
		assertThat(accordion.getExpandedPane()).isNotNull();
		assertThat(accordion.getExpandedPane().getText()).isEqualTo("Section 1");
		assertThat(capturedEvents).isEmpty();

		// set new section as expanded
		syncJfxThread(() -> testState.updateUnsafe(TestState.class, state -> state.section = "Section 3"));
		assertThat(accordion.getExpandedPane()).isNotNull();
		assertThat(accordion.getExpandedPane().getText()).isEqualTo("Section 3");
		assertThat(capturedEvents).isEmpty();

		// set no section as expanded
		syncJfxThread(() -> testState.updateUnsafe(TestState.class, state -> state.section = null));
		assertThat(accordion.getExpandedPane()).isNull();
		assertThat(capturedEvents).isEmpty();

		// set unknown section as expanded
		syncJfxThread(() -> testState.updateUnsafe(TestState.class, state -> state.section = "Unknown"));
		assertThat(accordion.getExpandedPane()).isNull();
		assertThat(capturedEvents).isEmpty();

	}



}
