package de.ruegnerlukas.simpleapplication.core.presentation;

import de.ruegnerlukas.simpleapplication.common.resources.Resource;
import de.ruegnerlukas.simpleapplication.core.presentation.style.StringStyle;
import de.ruegnerlukas.simpleapplication.core.presentation.style.Style;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.assertj.core.api.Assertions.assertThat;

public class StyleTest extends ApplicationTest {


	@Override
	public void start(Stage stage) {
		stage.setScene(new Scene(new Pane(), 100, 100));
		stage.show();
	}




	@Test
	public void testStringStyleApply() {
		final Node node = new Button();
		final Style style = StringStyle.fromString("-fx-background-color: red", "-fx-border-color: blue");
		style.applyTo(node);
		assertThat(node.getStyle().replace(" ", "")).isEqualTo("-fx-background-color:red;-fx-border-color:blue;");
	}




	@Test
	public void testStringStyleApplyMultiple() {
		final Node node = new Button();
		final Style styleA = StringStyle.fromString("-fx-background-color: red", "-fx-border-color: red");
		final Style styleB = StringStyle.fromString("-fx-background-color: blue", "-fx-border-color: blue");
		styleA.applyTo(node);
		styleB.applyTo(node);
		assertThat(node.getStyle().replace(" ", "")).isEqualTo("-fx-background-color:red;-fx-border-color:red;-fx-background-color:blue;-fx-border-color:blue;");
	}




	@Test
	public void testStringStyleApplyOnly() {
		final Node node = new Button();
		final Style styleA = StringStyle.fromString("-fx-background-color: red", "-fx-border-color: red");
		final Style styleB = StringStyle.fromString("-fx-background-color: blue", "-fx-border-color: blue");
		styleA.applyTo(node);
		styleB.applyToOnly(node);
		assertThat(node.getStyle().replace(" ", "")).isEqualTo("-fx-background-color:blue;-fx-border-color:blue;");
	}




	@Test
	public void testStringStyleRemove() {
		final Node node = new Button();
		final Style styleA = StringStyle.fromString("-fx-background-color: red", "-fx-border-color: red");
		final Style styleB = StringStyle.fromString("-fx-background-color: green", "-fx-border-color: green");
		final Style styleC = StringStyle.fromString("-fx-background-color: blue", "-fx-border-color: blue");
		styleA.applyTo(node);
		styleB.applyTo(node);
		styleC.applyTo(node);
		styleB.removeFrom(node);
		assertThat(node.getStyle().replace(" ", "")).isEqualTo("-fx-background-color:red;-fx-border-color:red;-fx-background-color:blue;-fx-border-color:blue;");
	}




	@Test
	public void testResourceStyleApply() {
		final Parent node = new Button();
		final Style style = StringStyle.fromFile(Resource.external("test.css"));
		style.applyTo(node);
		assertThat(node.getStylesheets()).hasSize(1);
		assertThat(node.getStylesheets()).containsExactlyInAnyOrder("file:test.css");
	}




	@Test
	public void testResourceStyleApplyMultiple() {
		final Parent node = new Button();
		final Style styleA = StringStyle.fromFile(Resource.external("testA.css"));
		final Style styleB = StringStyle.fromFile(Resource.external("testB.css"));
		styleA.applyTo(node);
		styleB.applyTo(node);
		assertThat(node.getStylesheets()).hasSize(2);
		assertThat(node.getStylesheets()).containsExactlyInAnyOrder("file:testA.css", "file:testB.css");
	}




	@Test
	public void testResourceStyleApplyOnly() {
		final Parent node = new Button();
		final Style styleA = StringStyle.fromFile(Resource.external("testA.css"));
		final Style styleB = StringStyle.fromFile(Resource.external("testB.css"));
		styleA.applyTo(node);
		styleB.applyToOnly(node);
		assertThat(node.getStylesheets()).hasSize(1);
		assertThat(node.getStylesheets()).containsExactlyInAnyOrder("file:testB.css");
	}




	@Test
	public void testResourceStyleRemove() {
		final Parent node = new Button();
		final Style styleA = StringStyle.fromFile(Resource.external("testA.css"));
		final Style styleB = StringStyle.fromFile(Resource.external("testB.css"));
		final Style styleC = StringStyle.fromFile(Resource.external("testC.css"));
		styleA.applyTo(node);
		styleB.applyTo(node);
		styleC.applyTo(node);
		styleB.removeFrom(node);
		assertThat(node.getStylesheets()).hasSize(2);
		assertThat(node.getStylesheets()).containsExactlyInAnyOrder("file:testA.css", "file:testC.css");
	}

}
