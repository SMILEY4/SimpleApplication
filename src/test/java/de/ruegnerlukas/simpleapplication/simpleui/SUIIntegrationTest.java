package de.ruegnerlukas.simpleapplication.simpleui;

import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiComponent;
import de.ruegnerlukas.simpleapplication.simpleui.core.SuiSceneController;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.concurrent.Phaser;
import java.util.concurrent.atomic.AtomicInteger;

import static de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiButton.button;
import static de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Properties.textContent;
import static de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Properties.wrapText;
import static de.ruegnerlukas.simpleapplication.simpleui.assets.properties.EventProperties.eventAction;
import static org.assertj.core.api.Assertions.assertThat;

public class SUIIntegrationTest extends ApplicationTest {


	@Override
	public void start(Stage stage) {
		SuiRegistry.initialize();
	}




	@Test
	public void testSimpleButton() {


		final AtomicInteger buttonPressCounter = new AtomicInteger(0);

		final SuiSceneController context = new SuiSceneController(
				button(
						textContent("Some Button"),
						wrapText(),
						eventAction(e -> buttonPressCounter.incrementAndGet())
				)
		);

		final Node fxNode = context.getRootFxNode();
		assertThat(fxNode instanceof Button).isTrue();

		final Button fxButton = (Button) fxNode;
		assertThat(fxButton.getText()).isEqualTo("Some Button");
		assertThat(fxButton.isWrapText()).isTrue();

		fxButton.fire();
		assertThat(buttonPressCounter.get()).isEqualTo(1);

	}




	@Test
	public void testSimpleMutation() {

		// setup
		final Phaser phaser = new Phaser(2);
		final TestState testState = new TestState();
		final SuiSceneController context = new SuiSceneController(testState,
				new SuiComponent<TestState>(state -> button(
						textContent("counter = " + testState.counter),
						eventAction(e -> state.update(TestState.class, TestState::increment))
				))
		);
		testState.addStateListener((state, update) -> phaser.arrive());

		// assert button text
		final Button fxButton = (Button) context.getRootFxNode();
		assertThat(fxButton.getText()).isEqualTo("counter = 0");

		// press button -> update state
		fxButton.fire();

		// assert that button text changed
		phaser.arriveAndAwaitAdvance();
		assertThat(fxButton.getText()).isEqualTo("counter = 1");
	}




	@Test
	public void testSilentUpdate() {

		// setup
		final Phaser phaser = new Phaser(2);
		final TestState testState = new TestState();
		final SuiSceneController context = new SuiSceneController(testState,
				new SuiComponent<TestState>(state -> button(
						textContent("counter = " + testState.counter),
						eventAction(e -> state.update(TestState.class, true, s -> {
							s.increment();
							phaser.arrive();
						}))
				))
		);
		testState.addStateListener((state, update) -> phaser.arrive());

		// assert button text
		final Button fxButton = (Button) context.getRootFxNode();
		assertThat(fxButton.getText()).isEqualTo("counter = 0");

		// press button -> silent update state
		fxButton.fire();

		// assert button text didnt change
		phaser.arriveAndAwaitAdvance();
		assertThat(fxButton.getText()).isEqualTo("counter = 0");
		assertThat(testState.counter).isEqualTo(1);

	}




	static class TestState extends SuiState {


		public int counter = 0;




		public void increment() {
			counter++;
		}

	}

}
