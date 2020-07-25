package de.ruegnerlukas.simpleapplication.simpleui;

import de.ruegnerlukas.simpleapplication.simpleui.elements.SUIComponent;
import de.ruegnerlukas.simpleapplication.simpleui.registry.SUIRegistry;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.concurrent.Phaser;
import java.util.concurrent.atomic.AtomicInteger;

import static de.ruegnerlukas.simpleapplication.simpleui.elements.SUIButton.button;
import static de.ruegnerlukas.simpleapplication.simpleui.properties.Properties.buttonListener;
import static de.ruegnerlukas.simpleapplication.simpleui.properties.Properties.textContent;
import static de.ruegnerlukas.simpleapplication.simpleui.properties.Properties.wrapText;
import static org.assertj.core.api.Assertions.assertThat;

public class SUIIntegrationTest extends ApplicationTest {


	@Override
	public void start(Stage stage) {
		SUIRegistry.initialize();
	}




	@Test
	public void testSimpleButton() {


		final AtomicInteger buttonPressCounter = new AtomicInteger(0);

		final SUISceneContext context = new SUISceneContext(
				button(
						textContent("Some Button"),
						wrapText(),
						buttonListener(buttonPressCounter::incrementAndGet)
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
		final SUISceneContext context = new SUISceneContext(testState,
				new SUIComponent<TestState>(state -> button(
						textContent("counter = " + testState.counter),
						buttonListener(() -> state.update(s -> ((TestState) s).increment()))
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
		final SUISceneContext context = new SUISceneContext(testState,
				new SUIComponent<TestState>(state -> button(
						textContent("counter = " + testState.counter),
						buttonListener(() -> state.update(true, s -> {
							((TestState) s).increment();
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




	static class TestState extends SUIState {


		public int counter = 0;




		public void increment() {
			counter++;
		}

	}

}
