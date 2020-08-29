package de.ruegnerlukas.simpleapplication.simpleui;

import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.elements.ElementTestState;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SUIButton;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SUIComponent;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SUIScrollPane;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SUIVBox;
import de.ruegnerlukas.simpleapplication.simpleui.properties.DuplicatePropertiesException;
import de.ruegnerlukas.simpleapplication.simpleui.properties.InjectionIndexMarker;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.registry.SUIRegistry;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.assertj.core.api.Assertions.assertThat;

public class InjectionTest extends ApplicationTest {


	@Override
	public void start(Stage stage) {
	}




	@Before
	public void setup() {
		SUIRegistry.initialize();
	}




	@Test
	public void testInjectIntoVBox() {

		final String INJECTION_POINT_ID = "injection_point";
		final ElementTestState state = new ElementTestState();

		NodeFactory vbox = SUIVBox.vbox(
				Properties.itemsInjectable(INJECTION_POINT_ID)
		);

		SUIRegistry.get().inject(INJECTION_POINT_ID,
				SUIButton.button(
						Properties.id("ij_btn1"),
						Properties.textContent("Injected Button 1")
				),
				SUIButton.button(
						Properties.id("ij_btn2"),
						Properties.textContent("Injected Button 2")
				)
		);
		SUIRegistry.get().inject(INJECTION_POINT_ID,
				SUIButton.button(
						Properties.id("ij_btn3"),
						Properties.textContent("Injected Button 3")
				)
		);


		SUISceneContext context = new SUISceneContext(state, vbox);
		VBox fxNode = (VBox) context.getRootNode().getFxNode();

		assertThat(fxNode.getChildren()).hasSize(3);

		assertThat(fxNode.getChildren().get(0) instanceof Button).isTrue();
		assertThat(fxNode.getChildren().get(1) instanceof Button).isTrue();
		assertThat(fxNode.getChildren().get(2) instanceof Button).isTrue();

		assertThat(((Button) fxNode.getChildren().get(0)).getText()).isEqualTo("Injected Button 1");
		assertThat(((Button) fxNode.getChildren().get(1)).getText()).isEqualTo("Injected Button 2");
		assertThat(((Button) fxNode.getChildren().get(2)).getText()).isEqualTo("Injected Button 3");

	}




	@Test
	public void testInjectNothingIntoVBox() {

		final String INJECTION_POINT_ID = "injection_point";
		final ElementTestState state = new ElementTestState();

		NodeFactory vbox = SUIVBox.vbox(
				Properties.itemsInjectable(INJECTION_POINT_ID)
		);

		SUISceneContext context = new SUISceneContext(state, vbox);
		VBox fxNode = (VBox) context.getRootNode().getFxNode();
		assertThat(fxNode.getChildren()).isEmpty();
	}




	@Test
	public void testInjectIntoVBoxWithDefault() {

		final String INJECTION_POINT_ID = "injection_point";
		final ElementTestState state = new ElementTestState();

		NodeFactory vbox = SUIVBox.vbox(
				Properties.itemsInjectable(
						INJECTION_POINT_ID,
						InjectionIndexMarker.injectAt(1),
						SUIButton.button(
								Properties.id("btn1"),
								Properties.textContent("Button 1")
						),
						SUIButton.button(
								Properties.id("btn2"),
								Properties.textContent("Button 2")
						))
		);

		SUIRegistry.get().inject(INJECTION_POINT_ID,
				SUIButton.button(
						Properties.id("ij_btn1"),
						Properties.textContent("Injected Button 1")
				),
				SUIButton.button(
						Properties.id("ij_btn2"),
						Properties.textContent("Injected Button 2")
				)
		);


		SUISceneContext context = new SUISceneContext(state, vbox);
		VBox fxNode = (VBox) context.getRootNode().getFxNode();

		assertThat(fxNode.getChildren()).hasSize(4);

		assertThat(fxNode.getChildren().get(0) instanceof Button).isTrue();
		assertThat(fxNode.getChildren().get(1) instanceof Button).isTrue();
		assertThat(fxNode.getChildren().get(2) instanceof Button).isTrue();
		assertThat(fxNode.getChildren().get(3) instanceof Button).isTrue();

		assertThat(((Button) fxNode.getChildren().get(0)).getText()).isEqualTo("Button 1");
		assertThat(((Button) fxNode.getChildren().get(1)).getText()).isEqualTo("Injected Button 1");
		assertThat(((Button) fxNode.getChildren().get(2)).getText()).isEqualTo("Injected Button 2");
		assertThat(((Button) fxNode.getChildren().get(3)).getText()).isEqualTo("Button 2");
	}




	@Test (expected = DuplicatePropertiesException.class)
	public void testVBoxPropertiesConflict() {
		SUIVBox.vbox(
				Properties.items(SUIButton.button(
						Properties.id("btn"),
						Properties.textContent("Button")
				)),
				Properties.itemsInjectable("injection_point")
		);
	}




	@Test
	public void testInjectIntoScrollPane() {

		final String INJECTION_POINT_ID = "injection_point";
		final ElementTestState state = new ElementTestState();

		NodeFactory scrollPane = SUIScrollPane.scrollPane(
				Properties.itemInjectable(INJECTION_POINT_ID)
		);

		SUIRegistry.get().inject(INJECTION_POINT_ID,
				SUIButton.button(
						Properties.id("ij_btn"),
						Properties.textContent("Injected Button")
				)
		);


		SUISceneContext context = new SUISceneContext(state, scrollPane);
		ScrollPane fxNode = (ScrollPane) context.getRootNode().getFxNode();

		assertThat(fxNode.getContent()).isNotNull();
		assertThat(fxNode.getContent() instanceof Button).isTrue();
		assertThat(((Button) fxNode.getContent()).getText()).isEqualTo("Injected Button");
	}




	@Test
	public void testInjectIntoScrollPaneWithDefault() {

		final String INJECTION_POINT_ID = "injection_point";
		final ElementTestState state = new ElementTestState();

		NodeFactory scrollPane = SUIScrollPane.scrollPane(
				Properties.itemInjectable(INJECTION_POINT_ID,
						SUIButton.button(
								Properties.id("btn"),
								Properties.textContent("Button")
						))
		);


		SUISceneContext context = new SUISceneContext(state, scrollPane);
		ScrollPane fxNode = (ScrollPane) context.getRootNode().getFxNode();

		assertThat(fxNode.getContent()).isNotNull();
		assertThat(fxNode.getContent() instanceof Button).isTrue();
		assertThat(((Button) fxNode.getContent()).getText()).isEqualTo("Button");
	}




	@Test
	public void testInjectIntoVBoxAndMutate() {

		final String INJECTION_POINT_ID = "injection_point";
		final ElementTestState state = new ElementTestState();
		state.text = "Text 1";

		NodeFactory vbox = SUIVBox.vbox(
				Properties.itemsInjectable(
						INJECTION_POINT_ID,
						InjectionIndexMarker.injectAt(1),
						SUIButton.button(
								Properties.id("btn1"),
								Properties.textContent("Button 1")
						),
						SUIButton.button(
								Properties.id("btn2"),
								Properties.textContent("Button 2")
						))
		);

		SUIRegistry.get().inject(INJECTION_POINT_ID,
				new SUIComponent<ElementTestState>(
						localState -> SUIButton.button(
								Properties.id("ij_btn1"),
								Properties.textContent(localState.text)
						))
		);


		SUISceneContext context = new SUISceneContext(state, vbox);
		SUINode node = context.getRootNode();
		VBox fxNode = (VBox) node.getFxNode();

		assertThat(fxNode.getChildren()).hasSize(3);
		assertThat(fxNode.getChildren().get(0) instanceof Button).isTrue();
		assertThat(fxNode.getChildren().get(1) instanceof Button).isTrue();
		assertThat(fxNode.getChildren().get(2) instanceof Button).isTrue();
		assertThat(((Button) fxNode.getChildren().get(0)).getText()).isEqualTo("Button 1");
		assertThat(((Button) fxNode.getChildren().get(1)).getText()).isEqualTo("Text 1");
		assertThat(((Button) fxNode.getChildren().get(2)).getText()).isEqualTo("Button 2");

		state.text = "Text 2";

		SUINode mutatedNode = context.getMasterNodeHandlers().getMutator().mutate(node, vbox.create(state));
		VBox fxNodeMutated = (VBox) mutatedNode.getFxNode();

		assertThat(fxNodeMutated.getChildren()).hasSize(3);
		assertThat(fxNodeMutated.getChildren().get(0) instanceof Button).isTrue();
		assertThat(fxNodeMutated.getChildren().get(1) instanceof Button).isTrue();
		assertThat(fxNodeMutated.getChildren().get(2) instanceof Button).isTrue();
		assertThat(((Button) fxNodeMutated.getChildren().get(0)).getText()).isEqualTo("Button 1");
		assertThat(((Button) fxNodeMutated.getChildren().get(1)).getText()).isEqualTo("Text 2");
		assertThat(((Button) fxNodeMutated.getChildren().get(2)).getText()).isEqualTo("Button 2");

	}


}
