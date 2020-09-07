package de.ruegnerlukas.simpleapplication.simpleui;

import de.ruegnerlukas.simpleapplication.common.utils.Pair;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SuiButton;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SuiComponent;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SuiScrollPane;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SuiVBox;
import de.ruegnerlukas.simpleapplication.simpleui.properties.DuplicatePropertiesException;
import de.ruegnerlukas.simpleapplication.simpleui.properties.InjectionIndexMarker;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.List;
import java.util.stream.Collectors;

import static de.ruegnerlukas.simpleapplication.simpleui.properties.Properties.id;
import static de.ruegnerlukas.simpleapplication.simpleui.properties.Properties.textContent;
import static org.assertj.core.api.Assertions.assertThat;

public class InjectionTest extends ApplicationTest {


	@Override
	public void start(Stage stage) {
	}




	@Before
	public void setup() {
		SuiRegistry.initialize();
	}




	@Test
	public void testInjectIntoVBox() {

		final String INJECTION_POINT_ID = "injection_point";
		final NodeFactory nodeFactory = vboxWithInjectableItems(INJECTION_POINT_ID);

		injectButtonsInto(INJECTION_POINT_ID, List.of(
				Pair.of("ij_btn1", "Injected Button 1"),
				Pair.of("ij_btn2", "Injected Button 2"))
		);
		injectButtonsInto(INJECTION_POINT_ID, List.of(
				Pair.of("ij_btn3", "Injected Button 3")
		));

		final VBox fxNode = (VBox) createJFXNode(nodeFactory);

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
		final NodeFactory nodeFactory = vboxWithInjectableItems(INJECTION_POINT_ID);
		final VBox fxNode = (VBox) createJFXNode(nodeFactory);
		assertThat(fxNode.getChildren()).isEmpty();
	}




	@Test
	public void testInjectIntoVBoxWithDefault() {

		final String INJECTION_POINT_ID = "injection_point";

		final NodeFactory nodeFactory = SuiVBox.vbox(
				Properties.itemsInjectable(
						INJECTION_POINT_ID,
						InjectionIndexMarker.injectAt(1),
						SuiButton.button(
								id("btn1"),
								textContent("Button 1")
						),
						SuiButton.button(
								id("btn2"),
								textContent("Button 2")
						))
		);

		injectButtonsInto(INJECTION_POINT_ID, List.of(
				Pair.of("ij_btn1", "Injected Button 1"),
				Pair.of("ij_btn2", "Injected Button 2"))
		);

		final VBox fxNode = (VBox) createJFXNode(nodeFactory);

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
		SuiVBox.vbox(
				Properties.items(SuiButton.button(
						id("btn"),
						textContent("Button")
				)),
				Properties.itemsInjectable("injection_point")
		);
	}




	@Test
	public void testInjectIntoScrollPane() {

		final String INJECTION_POINT_ID = "injection_point";

		final NodeFactory nodeFactory = SuiScrollPane.scrollPane(
				Properties.itemInjectable(INJECTION_POINT_ID)
		);

		injectButtonsInto(INJECTION_POINT_ID, List.of(
				Pair.of("ij_btn", "Injected Button")
		));

		final ScrollPane fxNode = (ScrollPane) createJFXNode(nodeFactory);

		assertThat(fxNode.getContent()).isNotNull();
		assertThat(fxNode.getContent() instanceof Button).isTrue();
		assertThat(((Button) fxNode.getContent()).getText()).isEqualTo("Injected Button");
	}




	@Test
	public void testInjectIntoScrollPaneWithDefault() {

		final String INJECTION_POINT_ID = "injection_point";

		final NodeFactory nodeFactory = SuiScrollPane.scrollPane(
				Properties.itemInjectable(INJECTION_POINT_ID,
						SuiButton.button(
								id("btn"),
								textContent("Button")
						))
		);

		final ScrollPane fxNode = (ScrollPane) createJFXNode(nodeFactory);

		assertThat(fxNode.getContent()).isNotNull();
		assertThat(fxNode.getContent() instanceof Button).isTrue();
		assertThat(((Button) fxNode.getContent()).getText()).isEqualTo("Button");
	}




	@Test
	public void testInjectIntoVBoxAndMutate() {

		final String INJECTION_POINT_ID = "injection_point";

		final NodeFactory nodeFactory = SuiVBox.vbox(
				Properties.itemsInjectable(
						INJECTION_POINT_ID,
						InjectionIndexMarker.injectAt(1),
						SuiButton.button(
								id("btn1"),
								textContent("Button 1")
						),
						SuiButton.button(
								id("btn2"),
								textContent("Button 2")
						))
		);

		SuiRegistry.get().inject(INJECTION_POINT_ID,
				new SuiComponent<TestState>(
						localState -> SuiButton.button(
								id("ij_btn1"),
								textContent(localState.text)
						))
		);

		final TestState state = new TestState("Text 1");
		final SuiSceneContext context = new SuiSceneContext(state, nodeFactory);
		final SuiNode node = context.getRootNode();
		final VBox fxNode = (VBox) node.getFxNode();

		assertThat(fxNode.getChildren()).hasSize(3);
		assertThat(fxNode.getChildren().get(0) instanceof Button).isTrue();
		assertThat(fxNode.getChildren().get(1) instanceof Button).isTrue();
		assertThat(fxNode.getChildren().get(2) instanceof Button).isTrue();
		assertThat(((Button) fxNode.getChildren().get(0)).getText()).isEqualTo("Button 1");
		assertThat(((Button) fxNode.getChildren().get(1)).getText()).isEqualTo("Text 1");
		assertThat(((Button) fxNode.getChildren().get(2)).getText()).isEqualTo("Button 2");

		state.text = "Text 2";

		final SuiNode mutatedNode = context.getMasterNodeHandlers().getMutator().mutate(node, nodeFactory.create(state));
		final VBox fxNodeMutated = (VBox) mutatedNode.getFxNode();

		assertThat(fxNodeMutated.getChildren()).hasSize(3);
		assertThat(fxNodeMutated.getChildren().get(0) instanceof Button).isTrue();
		assertThat(fxNodeMutated.getChildren().get(1) instanceof Button).isTrue();
		assertThat(fxNodeMutated.getChildren().get(2) instanceof Button).isTrue();
		assertThat(((Button) fxNodeMutated.getChildren().get(0)).getText()).isEqualTo("Button 1");
		assertThat(((Button) fxNodeMutated.getChildren().get(1)).getText()).isEqualTo("Text 2");
		assertThat(((Button) fxNodeMutated.getChildren().get(2)).getText()).isEqualTo("Button 2");
	}




	private NodeFactory vboxWithInjectableItems(final String injectionPointId) {
		return SuiVBox.vbox(
				Properties.itemsInjectable(injectionPointId)
		);
	}




	private void injectButtonsInto(final String injectionPointId, final List<Pair<String, String>> buttons) {
		SuiRegistry.get().inject(injectionPointId,
				buttons.stream()
						.map(pair -> SuiButton.button(id(pair.getLeft()), textContent(pair.getRight())))
						.collect(Collectors.toList())
		);
	}




	private Node createJFXNode(final NodeFactory nodeFactory) {
		SuiSceneContext context = new SuiSceneContext(new TestState(), nodeFactory);
		return context.getRootNode().getFxNode();
	}


}
