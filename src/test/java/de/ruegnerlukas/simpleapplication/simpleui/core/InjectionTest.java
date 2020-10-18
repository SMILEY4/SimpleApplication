package de.ruegnerlukas.simpleapplication.simpleui.core;

import de.ruegnerlukas.simpleapplication.common.utils.Pair;
import de.ruegnerlukas.simpleapplication.common.validation.ValidateStateException;
import de.ruegnerlukas.simpleapplication.simpleui.assets.SuiElements;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiComponent;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.InjectionIndexMarker;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.tags.Tags;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
import de.ruegnerlukas.simpleapplication.simpleui.testutils.TestState;
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
	public void test_inject_twice_into_empty_vbox() {

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
	public void test_inject_nothing_into_empty_vbox() {
		final String INJECTION_POINT_ID = "injection_point";
		final NodeFactory nodeFactory = vboxWithInjectableItems(INJECTION_POINT_ID);
		final VBox fxNode = (VBox) createJFXNode(nodeFactory);
		assertThat(fxNode.getChildren()).isEmpty();
	}




	@Test
	public void test_inject_elements_into_vbox_with_default_elements() {

		final String INJECTION_POINT_ID = "injection_point";

		final NodeFactory nodeFactory = SuiElements.vBox()
				.itemsInjectable(
						INJECTION_POINT_ID,
						InjectionIndexMarker.injectAt(1),
						SuiElements.button()
								.id("btn1")
								.textContent("Button 1"),
						SuiElements.button()
								.id("btn2")
								.textContent("Button 2")
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




	@Test (expected = ValidateStateException.class)
	public void test_conflict_exception_with_items_property_and_itemsinjectable_property() {
		SuiElements.vBox()
				.items(SuiElements.button().id("btn").textContent("Button"))
				.itemsInjectable("injection_point").create(null, Tags.empty());
	}




	@Test
	public void test_inject_single_item_into_scrollpane() {

		final String INJECTION_POINT_ID = "injection_point";

		final NodeFactory nodeFactory = SuiElements.scrollPane()
				.itemInjectable(INJECTION_POINT_ID);

		injectButtonsInto(INJECTION_POINT_ID, List.of(
				Pair.of("ij_btn", "Injected Button")
		));

		final ScrollPane fxNode = (ScrollPane) createJFXNode(nodeFactory);

		assertThat(fxNode.getContent()).isNotNull();
		assertThat(fxNode.getContent() instanceof Button).isTrue();
		assertThat(((Button) fxNode.getContent()).getText()).isEqualTo("Injected Button");
	}




	@Test
	public void test_inject_single_into_scrollpane_with_default_item() {

		final String INJECTION_POINT_ID = "injection_point";

		final NodeFactory nodeFactory = SuiElements.scrollPane()
				.itemInjectable(
						INJECTION_POINT_ID,
						SuiElements.button().id("btn").textContent("Button")
				);

		final ScrollPane fxNode = (ScrollPane) createJFXNode(nodeFactory);

		assertThat(fxNode.getContent()).isNotNull();
		assertThat(fxNode.getContent() instanceof Button).isTrue();
		assertThat(((Button) fxNode.getContent()).getText()).isEqualTo("Button");
	}




	@Test
	public void test_inject_component_into_vbox_and_mutate_everything() {

		final String INJECTION_POINT_ID = "injection_point";

		final NodeFactory nodeFactory = SuiElements.vBox()
				.itemsInjectable(
						INJECTION_POINT_ID,
						InjectionIndexMarker.injectAt(1),
						SuiElements.button().id("btn1").textContent("Button 1"),
						SuiElements.button().id("btn2").textContent("Button 2")
				);

		SuiRegistry.get().inject(INJECTION_POINT_ID,
				new SuiComponent<TestState>(localState -> SuiElements.button().id("ij_btn1").textContent(localState.text))
		);

		final TestState state = new TestState("Text 1");
		final SuiSceneController context = new SuiSceneController(state, nodeFactory);
		final SuiNode node = context.getRootNode();
		final VBox fxNode = (VBox) node.getFxNodeStore().get();

		assertThat(fxNode.getChildren()).hasSize(3);
		assertThat(fxNode.getChildren().get(0) instanceof Button).isTrue();
		assertThat(fxNode.getChildren().get(1) instanceof Button).isTrue();
		assertThat(fxNode.getChildren().get(2) instanceof Button).isTrue();
		assertThat(((Button) fxNode.getChildren().get(0)).getText()).isEqualTo("Button 1");
		assertThat(((Button) fxNode.getChildren().get(1)).getText()).isEqualTo("Text 1");
		assertThat(((Button) fxNode.getChildren().get(2)).getText()).isEqualTo("Button 2");

		state.text = "Text 2";

		final SuiNode mutatedNode = SuiServices.get().mutateNode(node, nodeFactory.create(state, null), Tags.empty());
		final VBox fxNodeMutated = (VBox) mutatedNode.getFxNodeStore().get();

		assertThat(fxNodeMutated.getChildren()).hasSize(3);
		assertThat(fxNodeMutated.getChildren().get(0) instanceof Button).isTrue();
		assertThat(fxNodeMutated.getChildren().get(1) instanceof Button).isTrue();
		assertThat(fxNodeMutated.getChildren().get(2) instanceof Button).isTrue();
		assertThat(((Button) fxNodeMutated.getChildren().get(0)).getText()).isEqualTo("Button 1");
		assertThat(((Button) fxNodeMutated.getChildren().get(1)).getText()).isEqualTo("Text 2");
		assertThat(((Button) fxNodeMutated.getChildren().get(2)).getText()).isEqualTo("Button 2");
	}




	private NodeFactory vboxWithInjectableItems(final String injectionPointId) {
		return SuiElements.vBox().itemsInjectable(injectionPointId);
	}




	private void injectButtonsInto(final String injectionPointId, final List<Pair<String, String>> buttons) {
		SuiRegistry.get().inject(injectionPointId,
				buttons.stream()
						.map(pair -> SuiElements.button().id(pair.getLeft()).textContent(pair.getRight()))
						.collect(Collectors.toList())
		);
	}




	private Node createJFXNode(final NodeFactory nodeFactory) {
		SuiSceneController context = new SuiSceneController(new TestState(), nodeFactory);
		return context.getRootNode().getFxNodeStore().get();
	}


}
