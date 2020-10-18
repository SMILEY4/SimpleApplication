package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;


import de.ruegnerlukas.simpleapplication.simpleui.assets.SuiElements;
import de.ruegnerlukas.simpleapplication.simpleui.assets.events.SectionEventData;
import de.ruegnerlukas.simpleapplication.simpleui.core.SuiSceneController;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;
import javafx.scene.control.Accordion;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SuiAccordionTest extends SuiElementTest {


	@Test
	public void test_expanding_sections_triggers_actions() {
		if (shouldSkipFxTest()) {
			return;
		}

		// build accordion with fixed sections and capture all events in a list
		final List<SectionEventData> capturedEvents = new ArrayList<>();
		final Accordion accordion = (Accordion) new SuiSceneController(
				SuiElements.accordion()
						.items(
								SuiElements.anchorPane().id("section-0").title("Section 0"),
								SuiElements.anchorPane().id("section-1").title("Section 1"),
								SuiElements.anchorPane().id("section-2").title("Section 2"),
								SuiElements.anchorPane().id("section-3").title("Section 3")
						)
						.eventToggleSection(".", capturedEvents::add)
		).getRootFxNode();

		show(accordion);
		capturedEvents.clear();

		// expand section 2
		expandOrCollapse(accordion, 2);
		assertExpanded(accordion, "Section 2");
		assertEvent(capturedEvents, "Section 2", true);

		// expand section 1
		expandOrCollapse(accordion, 1);
		assertExpanded(accordion, "Section 1");
		assertEvent(capturedEvents, "Section 1", true);

		// collapse section 1
		expandOrCollapse(accordion, 1);
		assertExpanded(accordion, null);
		assertEvent(capturedEvents, "Section 1", false);

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
				state -> SuiElements.accordion()
						.items(
								state.sections.stream().map(section -> SuiElements.anchorPane()
										.id("section-" + section)
										.title("Section " + section)
								)
						)
						.eventToggleSection(".", capturedEvents::add)
		);
		final Accordion accordion = (Accordion) controller.getRootFxNode();
		show(accordion);

		// expand "section 1" as preparation, ignore event and assert sections
		accordion.setExpandedPane(accordion.getPanes().get(1));
		capturedEvents.clear();
		assertSections(accordion, "Section 0", "Section 1", "Section 2", "Section 3");

		// remove a section that is not expanded and add a new section
		syncJfxThread(() -> testState.updateUnsafe(TestState.class, state -> {
			state.sections.remove("0");
			state.sections.add("4");
		}));

		// check that no events were triggered and the sections have been modified
		assertNoEvent(capturedEvents);
		assertExpanded(accordion, "Section 1");
		assertSections(accordion, "Section 1", "Section 2", "Section 3", "Section 4");
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
				state -> SuiElements.accordion()
						.items(
								state.sections.stream().map(section -> SuiElements.anchorPane()
										.id("section-" + section)
										.title("Section " + section)
								)
						)
						.eventToggleSection(".", capturedEvents::add)
		);
		final Accordion accordion = (Accordion) controller.getRootFxNode();
		show(accordion);

		// expand "section 1" as preparation, ignore event and assert sections
		accordion.setExpandedPane(accordion.getPanes().get(1));
		capturedEvents.clear();
		assertSections(accordion, "Section 0", "Section 1");

		// remove currently expanded section
		syncJfxThread(() -> testState.update(TestState.class, state -> state.sections.remove("1")));

		// check that no event was triggered and the sections modified
		assertNoEvent(capturedEvents);
		assertExpanded(accordion, null);
		assertSections(accordion, "Section 0");
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
				state -> SuiElements.accordion()
						.expandedSection(state.section)
						.items(
								state.sections.stream().map(section -> SuiElements.anchorPane()
										.id("section-" + section)
										.title("Section " + section)
								)
						)
						.eventToggleSection(".", capturedEvents::add)
		);
		final Accordion accordion = (Accordion) controller.getRootFxNode();
		show(accordion);

		// test initially expanded section
		assertExpanded(accordion, "Section 1");
		assertNoEvent(capturedEvents);

		// set new section as expanded
		syncJfxThread(() -> testState.updateUnsafe(TestState.class, state -> state.section = "Section 3"));
		assertExpanded(accordion, "Section 3");
		assertNoEvent(capturedEvents);

		// set no section as expanded
		syncJfxThread(() -> testState.updateUnsafe(TestState.class, state -> state.section = null));
		assertExpanded(accordion, null);
		assertNoEvent(capturedEvents);

		// set unknown section as expanded
		syncJfxThread(() -> testState.updateUnsafe(TestState.class, state -> state.section = "Unknown"));
		assertExpanded(accordion, null);
		assertNoEvent(capturedEvents);

	}


}
