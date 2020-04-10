package de.ruegnerlukas.simpleapplication.core.presentation;

import de.ruegnerlukas.simpleapplication.core.presentation.style.StringStyle;
import de.ruegnerlukas.simpleapplication.core.presentation.style.Style;
import de.ruegnerlukas.simpleapplication.core.presentation.style.StyleService;
import de.ruegnerlukas.simpleapplication.core.presentation.style.StyleServiceImpl;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class StyleServiceTest extends ApplicationTest {


	@Override
	public void start(Stage stage) {
		stage.setScene(new Scene(new Pane(), 100, 100));
		stage.show();
	}




	@Test
	public void testCreateFromString() {
		final String STYLE = "testStyle";
		final StyleService service = new StyleServiceImpl();
		final Style style = service.createFromString(STYLE, "-fx-background-color: red", "-fx-border-color: red");
		assertThat(style).isNotNull();
		assertThat(service.findStyle(STYLE)).isPresent();
		assertThat(service.findStyle(STYLE).orElse(null)).isEqualTo(style);
	}




	@Test
	public void testRegisterStyle() {
		final String STYLE = "testStyle";
		final StyleService service = new StyleServiceImpl();

		assertThat(service.findStyle(STYLE)).isNotPresent();

		final Style style = StringStyle.fromString("-fx-background-color: red", "-fx-border-color: red");
		service.registerStyle(style, STYLE);
		assertThat(service.findStyle(STYLE)).isPresent();
		assertThat(service.findStyle(STYLE).orElse(null)).isEqualTo(style);
	}




	@Test
	public void testRegisterOverwriteStyle() {
		final String STYLE = "testStyle";
		final StyleService service = new StyleServiceImpl();
		service.createFromString(STYLE, "-fx-background-color: red", "-fx-border-color: red");
		assertThat(service.findStyle(STYLE)).isPresent();

		final Node target = new Button();
		service.applyStyleTo(STYLE, target);
		assertThat(target.getStyle().replace(" ", "")).isEqualTo("-fx-background-color:red;-fx-border-color:red;");

		final Style style = StringStyle.fromString("-fx-background-color: blue", "-fx-border-color: blue");
		service.registerStyle(style, STYLE);
		assertThat(service.findStyle(STYLE)).isPresent();
		assertThat(service.findStyle(STYLE).orElse(null)).isEqualTo(style);
		assertThat(target.getStyle().replace(" ", "")).isEqualTo("-fx-background-color:blue;-fx-border-color:blue;");

	}




	@Test
	public void testDeregisterStyle() {
		final String STYLE_A = "testStyleA";
		final String STYLE_B = "testStyleB";
		final StyleService service = new StyleServiceImpl();
		service.createFromString(STYLE_A, "-fx-background-color: red", "-fx-border-color: red");
		service.createFromString(STYLE_B, "-fx-background-color: blue", "-fx-border-color: blue");

		final Node target = new Button();
		service.applyStylesTo(List.of(STYLE_A, STYLE_B), target);

		service.deregisterStyle(STYLE_B);
		assertThat(service.findStyle(STYLE_A)).isPresent();
		assertThat(service.findStyle(STYLE_B)).isNotPresent();
		assertThat(target.getStyle().replace(" ", "")).isEqualTo("-fx-background-color:red;-fx-border-color:red;");
	}





	@Test
	public void testApplyStyle() {

		final String STYLE = "testStyle";
		final StyleService service = new StyleServiceImpl();
		service.createFromString(STYLE, "-fx-background-color: red", "-fx-border-color: red");

		final Node target = new Button();
		service.applyStyleTo(STYLE, target);
		assertThat(target.getStyle().replace(" ", "")).isEqualTo("-fx-background-color:red;-fx-border-color:red;");
		assertThat(service.getAppliedStyleNames(target)).containsExactlyInAnyOrder(STYLE);
		assertThat(service.getTargets(STYLE)).containsExactlyInAnyOrder(target);
	}




	@Test
	public void testApplyMultipleStyles() {

		final String STYLE_A = "testStyleA";
		final String STYLE_B = "testStyleB";

		final StyleService service = new StyleServiceImpl();
		service.createFromString(STYLE_A, "-fx-background-color: red", "-fx-border-color: red");
		service.createFromString(STYLE_B, "-fx-background-color: blue", "-fx-border-color: blue");

		final Node target = new Button();
		service.applyStylesTo(List.of(STYLE_A, STYLE_B), target);
		assertThat(target.getStyle().replace(" ", "")).isEqualTo("-fx-background-color:red;-fx-border-color:red;-fx-background-color:blue;-fx-border-color:blue;");
		assertThat(service.getAppliedStyleNames(target)).containsExactlyInAnyOrder(STYLE_A, STYLE_B);
		assertThat(service.getTargets(STYLE_A)).containsExactlyInAnyOrder(target);
		assertThat(service.getTargets(STYLE_B)).containsExactlyInAnyOrder(target);
	}




	@Test
	public void testApplyStyleExclusive() {

		final String STYLE_A = "testStyleA";
		final String STYLE_B = "testStyleB";

		final StyleService service = new StyleServiceImpl();
		service.createFromString(STYLE_A, "-fx-background-color: red", "-fx-border-color: red");
		service.createFromString(STYLE_B, "-fx-background-color: blue", "-fx-border-color: blue");

		final Node target = new Button();
		service.applyStyleTo(STYLE_A, target);
		service.applyStyleToExclusive(STYLE_B, target);
		assertThat(target.getStyle().replace(" ", "")).isEqualTo("-fx-background-color:blue;-fx-border-color:blue;");
		assertThat(service.getAppliedStyleNames(target)).containsExactlyInAnyOrder(STYLE_B);
		assertThat(service.getTargets(STYLE_A)).isEmpty();
		assertThat(service.getTargets(STYLE_B)).containsExactlyInAnyOrder(target);
	}




	@Test
	public void testRemoveStyleFrom() {

		final String STYLE_A = "testStyleA";
		final String STYLE_B = "testStyleB";

		final StyleService service = new StyleServiceImpl();
		service.createFromString(STYLE_A, "-fx-background-color: red", "-fx-border-color: red");
		service.createFromString(STYLE_B, "-fx-background-color: blue", "-fx-border-color: blue");

		final Node target = new Button();
		service.applyStylesTo(List.of(STYLE_A, STYLE_B), target);
		service.removeStyleFrom(STYLE_A, target);
		assertThat(target.getStyle().replace(" ", "")).isEqualTo("-fx-background-color:blue;-fx-border-color:blue;");
		assertThat(service.getAppliedStyleNames(target)).containsExactlyInAnyOrder(STYLE_B);
		assertThat(service.getTargets(STYLE_A)).isEmpty();
		assertThat(service.getTargets(STYLE_B)).containsExactlyInAnyOrder(target);
	}




	@Test
	public void testDisconnectNode() {

		final String STYLE_A = "testStyleA";
		final String STYLE_B = "testStyleB";

		final StyleService service = new StyleServiceImpl();
		service.createFromString(STYLE_A, "-fx-background-color: red", "-fx-border-color: red");
		service.createFromString(STYLE_B, "-fx-background-color: blue", "-fx-border-color: blue");

		final Node target = new Button();
		service.applyStylesTo(List.of(STYLE_A, STYLE_B), target);
		service.disconnectNode(target);
		assertThat(target.getStyle()).isEqualTo("");
		assertThat(service.getAppliedStyleNames(target)).isEmpty();
		assertThat(service.getTargets(STYLE_A)).isEmpty();
		assertThat(service.getTargets(STYLE_B)).isEmpty();
	}


}





















