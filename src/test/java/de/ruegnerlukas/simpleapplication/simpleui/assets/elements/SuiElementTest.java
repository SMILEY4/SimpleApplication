package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;

import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import org.testfx.framework.junit.ApplicationTest;

import java.util.concurrent.Phaser;

public class SuiElementTest extends ApplicationTest {


	@Getter
	private Stage stage;




	@Override
	public void start(Stage stage) {
		SuiRegistry.initialize();
		this.stage = stage;
		this.stage.setAlwaysOnTop(true);
		this.stage.show();
	}

	public void show(final Parent node) {
		syncJfxThread(() -> {
			getStage().setScene(new Scene(node));
			getStage().sizeToScene();
		});
		delay(100);
	}




	public void syncJfxThread(final Runnable action) {
		syncJfxThread(0, action);
	}




	public void syncJfxThread(final long postDelayMs, final Runnable action) {
		Phaser phaser = new Phaser(2);
		Platform.runLater(() -> {
			action.run();
			phaser.arrive();
		});
		phaser.arriveAndAwaitAdvance();
		delay(postDelayMs);
	}




	public void delay(final long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}




	public boolean shouldSkipFxTest() {
		return Boolean.getBoolean("skipFxTests");
	}


}
