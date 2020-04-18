package de.ruegnerlukas.simpleapplication.core.presentation;

import de.ruegnerlukas.simpleapplication.common.instanceproviders.factories.InstanceFactory;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.ProviderService;
import de.ruegnerlukas.simpleapplication.core.events.EventService;
import de.ruegnerlukas.simpleapplication.core.events.EventServiceImpl;
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

		ProviderService.registerFactory(new InstanceFactory<>(EventService.class) {
			@Override
			public EventService buildObject() {
				return new EventServiceImpl();
			}
		});

		stage.setScene(new Scene(new Pane(), 100, 100));
		stage.show();
	}




	@Test
	public void testCreateFromString() {
		final String STYLE = "testStyle";
		final StyleService service = new StyleServiceImpl();
		final Style style = service.createFromString(STYLE, "-fx-background-color: red", "-fx-border-color: red");
		assertThat(style).isNotNull();
		assertStyleIsPresent(service, STYLE, style);
	}




	@Test
	public void testRegisterStyle() {
		final String STYLE = "testStyle";
		final StyleService service = new StyleServiceImpl();

		assertThat(service.findStyle(STYLE)).isNotPresent();

		final Style style = StringStyle.fromString("-fx-background-color: red", "-fx-border-color: red");
		service.registerStyle(style, STYLE);
		assertStyleIsPresent(service, STYLE, style);
	}




	@Test
	public void testRegisterOverwriteStyle() {
		final String STYLE = "testStyle";
		final StyleService service = new StyleServiceImpl();
		service.createFromString(STYLE, "-fx-background-color: red", "-fx-border-color: red");
		assertThat(service.findStyle(STYLE)).isPresent();

		final Node target = new Button();
		service.applyStyleTo(STYLE, target);
		assertStyle(target, "-fx-background-color:red", "-fx-border-color:red");

		final Style style = StringStyle.fromString("-fx-background-color: blue", "-fx-border-color: blue");
		service.registerStyle(style, STYLE);
		assertStyleIsPresent(service, STYLE, style);
		assertStyle(target, "-fx-background-color:blue", "-fx-border-color:blue");
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
		assertStyle(target, "-fx-background-color:red", "-fx-border-color:red");
	}




	@Test
	public void testApplyStyle() {

		final String STYLE = "testStyle";
		final StyleService service = new StyleServiceImpl();
		service.createFromString(STYLE, "-fx-background-color: red", "-fx-border-color: red");

		final Node target = new Button();
		service.applyStyleTo(STYLE, target);
		assertStyle(target, "-fx-background-color:red", "-fx-border-color:red");
		assertThat(service.getAppliedStyleNames(target)).containsExactlyInAnyOrder(STYLE);
		assertThat(service.getTargets(STYLE)).containsExactlyInAnyOrder(target);
	}




	@Test
	public void testApplyStyleTwice() {

		final String STYLE = "testStyle";
		final StyleService service = new StyleServiceImpl();
		service.createFromString(STYLE, "-fx-background-color: red", "-fx-border-color: red");

		final Node target = new Button();
		service.applyStyleTo(STYLE, target);
		service.applyStyleTo(STYLE, target);
		assertStyle(target, "-fx-background-color:red", "-fx-border-color:red");
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
		assertStyle(target, "-fx-background-color:red", "-fx-border-color:red", "-fx-background-color:blue", "-fx-border-color:blue");
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
		assertStyle(target, "-fx-background-color:blue", "-fx-border-color:blue");
		assertThat(service.getAppliedStyleNames(target)).containsExactlyInAnyOrder(STYLE_B);
		assertThat(service.getTargets(STYLE_A)).isEmpty();
		assertThat(service.getTargets(STYLE_B)).containsExactlyInAnyOrder(target);
	}




	@Test
	public void testApplyStyleExclusiveWithRootStyles() {

		final String STYLE_A = "testStyleA";
		final String STYLE_B = "testStyleB";
		final String STYLE_ROOT = "testStyleRoot";

		final StyleService service = new StyleServiceImpl();
		service.createFromString(STYLE_A, "-fx-background-color: red", "-fx-border-color: red");
		service.createFromString(STYLE_B, "-fx-background-color: blue", "-fx-border-color: blue");
		service.createFromString(STYLE_ROOT, "-fx-background-color: white", "-fx-border-color: white");
		service.setRootStyle(STYLE_ROOT, true);

		final Node target = new Button();
		service.applyStyleTo(STYLE_A, target);
		service.applyStyleToExclusive(STYLE_B, target);
		assertStyle(target, "-fx-background-color:blue", "-fx-border-color:blue", "-fx-background-color:white", "-fx-border-color:white");
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
		assertStyle(target, "-fx-background-color:blue", "-fx-border-color:blue");
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




	@Test
	public void testRootStyles() {

		final String STYLE_A = "testStyleA";
		final String STYLE_B = "testStyleB";

		final StyleService service = new StyleServiceImpl();
		service.createFromString(STYLE_A, "-fx-background-color: red", "-fx-border-color: red");
		service.createFromString(STYLE_B, "-fx-background-color: blue", "-fx-border-color: blue");

		assertThat(service.getRootStyles()).isEmpty();

		service.setRootStyle(STYLE_A, true);
		service.setRootStyle(STYLE_B, true);
		assertThat(service.getRootStyles()).containsExactlyInAnyOrder(STYLE_A, STYLE_B);

		service.setRootStyle(STYLE_B, false);
		assertThat(service.getRootStyles()).containsExactlyInAnyOrder(STYLE_A);
	}




	private void assertStyle(final Node target, final String... styleStrings) {
		assertThat(target.getStyle().replace(" ", "").split(";")).containsExactlyInAnyOrder(styleStrings);
	}




	private void assertStyleIsPresent(final StyleService service, final String styleName, final Style expectedStyle) {
		assertThat(service.findStyle(styleName)).isPresent();
		assertThat(service.findStyle(styleName).orElse(null)).isEqualTo(expectedStyle);
	}


}





















