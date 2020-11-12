package de.ruegnerlukas.simpleapplication.simpleui.core.style;

import de.ruegnerlukas.simpleapplication.common.resources.Resource;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class SuiApplicationBaseStyleTest extends ApplicationTest {


	private static final String PATH_STYLESHEET_1 = "src\\test\\resources\\testResources\\testStyle_1.css";
	private static final String PATH_STYLESHEET_2 = "src\\test\\resources\\testResources\\testStyle_2.css";




	@Override
	public void start(final Stage stage) {
		SuiRegistry.initialize();
		stage.setScene(new Scene(new Button("Button"), 100, 100));
		stage.show();
	}




	@Test
	public void test_apply_default_javafx_style() {
		SuiRegistry.get().getStyleManager().setApplicationBaseStyle(SuiApplicationBaseStyle.caspian());
		assertThat(Application.getUserAgentStylesheet()).isEqualTo(Application.STYLESHEET_CASPIAN);
	}




	@Test
	public void test_apply_stylesheets() {
		SuiRegistry.get().getStyleManager().setApplicationBaseStyle(SuiApplicationBaseStyle.cssStylesheet(
				Resource.externalRelative(PATH_STYLESHEET_1),
				Resource.externalRelative(PATH_STYLESHEET_2)
		));
		assertStylesheets(Window.getWindows().get(0).getScene(), PATH_STYLESHEET_1, PATH_STYLESHEET_2);
	}




	@Test
	public void test_switch_default_javafx_style() {
		SuiRegistry.get().getStyleManager().setApplicationBaseStyle(SuiApplicationBaseStyle.caspian());
		assertThat(Application.getUserAgentStylesheet()).isEqualTo(Application.STYLESHEET_CASPIAN);
		SuiRegistry.get().getStyleManager().setApplicationBaseStyle(SuiApplicationBaseStyle.modena());
		assertThat(Application.getUserAgentStylesheet()).isEqualTo(Application.STYLESHEET_MODENA);
	}




	@Test
	public void test_switch_stylesheets() {
		SuiRegistry.get().getStyleManager().setApplicationBaseStyle(SuiApplicationBaseStyle.cssStylesheet(Resource.externalRelative(PATH_STYLESHEET_1)));
		assertStylesheets(Window.getWindows().get(0).getScene(), PATH_STYLESHEET_1);

		SuiRegistry.get().getStyleManager().setApplicationBaseStyle(SuiApplicationBaseStyle.cssStylesheet(Resource.externalRelative(PATH_STYLESHEET_2)));
		assertStylesheets(Window.getWindows().get(0).getScene(), PATH_STYLESHEET_2);
	}




	@Test
	public void test_switch_from_javafx_default_style_to_stylesheets() {
		SuiRegistry.get().getStyleManager().setApplicationBaseStyle(SuiApplicationBaseStyle.caspian());
		assertThat(Application.getUserAgentStylesheet()).isEqualTo(Application.STYLESHEET_CASPIAN);

		SuiRegistry.get().getStyleManager().setApplicationBaseStyle(SuiApplicationBaseStyle.cssStylesheet(Resource.externalRelative(PATH_STYLESHEET_1)));
		SuiRegistry.get().getStyleManager().setApplicationBaseStyle(SuiApplicationBaseStyle.cssStylesheet(
				Resource.externalRelative(PATH_STYLESHEET_1),
				Resource.externalRelative(PATH_STYLESHEET_2)
		));
		assertStylesheets(Window.getWindows().get(0).getScene(), PATH_STYLESHEET_1, PATH_STYLESHEET_2);
	}




	@Test
	public void test_switch_from_stylesheets_to_javafx_default_style() {
		SuiRegistry.get().getStyleManager().setApplicationBaseStyle(SuiApplicationBaseStyle.cssStylesheet(Resource.externalRelative(PATH_STYLESHEET_1)));
		SuiRegistry.get().getStyleManager().setApplicationBaseStyle(SuiApplicationBaseStyle.cssStylesheet(
				Resource.externalRelative(PATH_STYLESHEET_1),
				Resource.externalRelative(PATH_STYLESHEET_2)
		));
		assertStylesheets(Window.getWindows().get(0).getScene(), PATH_STYLESHEET_1, PATH_STYLESHEET_2);

		SuiRegistry.get().getStyleManager().setApplicationBaseStyle(SuiApplicationBaseStyle.caspian());
		assertThat(Application.getUserAgentStylesheet()).isEqualTo(Application.STYLESHEET_CASPIAN);
	}




	private void assertStylesheets(final Scene scene, final String... stylesheetPaths) {
		final ObservableList<String> stylesheets = scene.getStylesheets();
		assertThat(stylesheets).hasSize(stylesheetPaths.length);
		assertThat(stylesheets.stream().allMatch(s -> s.startsWith("file:"))).isTrue();
		for (String path : stylesheetPaths) {
			assertThat(stylesheets.stream()
					.map(s -> s.replace("\\", "/"))
					.filter(stylesheet -> stylesheet.endsWith(path.replace("\\", "/")))
					.collect(Collectors.toList()))
					.hasSize(1);
		}
	}


}
