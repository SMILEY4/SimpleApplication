package de.ruegnerlukas.simpleapplication.core.presentation;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit.ApplicationTest;

@Slf4j
public class TestFX_Test extends ApplicationTest {

	/*
	TestFX: https://github.com/TestFX/TestFX
	 */

	private Button button;




	/**
	 * Will be called with {@code @Before} semantics, i. e. before each test method.
	 */
	@Override
	public void start(Stage stage) {
		log.info("Starting javafx test.");
		button = new Button("click me!");
		button.setOnAction(actionEvent -> button.setText("clicked!"));
		stage.setScene(new Scene(new StackPane(button), 100, 100));
		stage.show();
	}




	@Test
	public void should_contain_button_with_text() {
		Assertions.assertThat(button).hasText("click me!");
	}




	@Test
	public void when_button_is_clicked_text_changes() {
		// when:
		clickOn(".button");

		// then:
		Assertions.assertThat(button).hasText("clicked!");
	}

}