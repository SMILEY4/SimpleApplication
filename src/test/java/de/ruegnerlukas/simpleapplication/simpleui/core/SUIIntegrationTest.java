package de.ruegnerlukas.simpleapplication.simpleui.core;

import de.ruegnerlukas.simpleapplication.common.validation.ValidateStateException;
import de.ruegnerlukas.simpleapplication.simpleui.assets.SuiElements;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiComponent;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiElementTest;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;
import de.ruegnerlukas.simpleapplication.simpleui.core.tags.Tags;
import java.util.concurrent.Phaser;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.scene.Node;
import javafx.scene.control.Button;
import org.junit.Test;

import static de.ruegnerlukas.simpleapplication.simpleui.assets.SuiElements.component;
import static org.assertj.core.api.Assertions.assertThat;

public class SUIIntegrationTest extends SuiElementTest {



	@Test
	public void test_create_button_and_check_fx_node_and_events() {


		final AtomicInteger buttonPressCounter = new AtomicInteger(0);

		final NodeFactory factory = component(SuiState.class,
				state -> SuiElements.button()
						.textContent("Some Button")
						.wrapText()
						.eventAction(".", e -> buttonPressCounter.incrementAndGet())
		);
		final SuiNode suiNode = factory.create(new SuiState(), Tags.empty());
		SuiServices.get().enrichWithFxNodes(suiNode);

		final Node fxNode = suiNode.getFxNodeStore().get();
		assertThat(fxNode instanceof Button).isTrue();

		final Button fxButton = (Button) fxNode;
		assertThat(fxButton.getText()).isEqualTo("Some Button");
		assertThat(fxButton.isWrapText()).isTrue();

		fxButton.fire();
		assertThat(buttonPressCounter.get()).isEqualTo(1);

	}




	@Test
	public void test_create_and_mutate_button() {

		// setup
		final TestState testState = new TestState();

		final Button button = show(testState, new SuiComponent<TestState>(
				state -> SuiElements.button()
						.textContent("counter = " + testState.counter)
						.eventAction(".", e -> state.update(TestState.class, TestState::increment))
		));

		assertThat(button.getText()).isEqualTo("counter = 0");
		clickButton(button);
		assertThat(button.getText()).isEqualTo("counter = 1");
	}




	@Test
	public void test_silent_state_update_does_not_mutate_elements() {

		// setup
		final Phaser phaser = new Phaser(2);
		final TestState testState = new TestState();
		testState.addStateListener((state, update, tags) -> phaser.arrive());

		final NodeFactory factory = component(TestState.class,
				state -> SuiElements.button()
						.textContent("counter = " + testState.counter)
						.eventAction(".", e -> state.update(TestState.class, true, s -> {
							s.increment();
							phaser.arrive();
						}))
		);
		final SuiNode suiNode = factory.create(testState, Tags.empty());
		SuiServices.get().enrichWithFxNodes(suiNode);

		// assert button text
		final Button fxButton = (Button) suiNode.getFxNodeStore().get();
		assertThat(fxButton.getText()).isEqualTo("counter = 0");

		// press button -> silent update state
		fxButton.fire();

		// assert button text didnt change
		phaser.arriveAndAwaitAdvance();
		assertThat(fxButton.getText()).isEqualTo("counter = 0");
		assertThat(testState.counter).isEqualTo(1);

	}




	@Test (expected = ValidateStateException.class)
	public void test_build_children_with_duplicate_ids_expect_failed_validation_and_missing_children() {

		final TestState testState = new TestState();

		final NodeFactory factory = component(TestState.class,
				state -> SuiElements.vBox()
						.id("myVBox")
						.items(
								SuiElements.button().id("sameId").textContent("Child Button 1"),
								SuiElements.button().id("diffId").textContent("Child Button 2"),
								SuiElements.button().id("sameId").textContent("Child Button 3")
						)
		);
		final SuiNode suiNode = factory.create(testState, Tags.empty());
		SuiServices.get().enrichWithFxNodes(suiNode);

		assertThat(suiNode.getChildNodeStore().count()).isEqualTo(0);
	}




	static class TestState extends SuiState {


		public int counter = 0;




		public void increment() {
			counter++;
		}

	}

}
