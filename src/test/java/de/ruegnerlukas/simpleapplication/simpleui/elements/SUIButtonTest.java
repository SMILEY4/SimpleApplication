package de.ruegnerlukas.simpleapplication.simpleui.elements;

import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.SUISceneContext;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.registry.SUIRegistry;
import de.ruegnerlukas.simpleapplication.simpleui.utils.FxTestUtils;
import de.ruegnerlukas.simpleapplication.simpleui.utils.PropertyTestUtils;
import de.ruegnerlukas.simpleapplication.simpleui.utils.TestUtils;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.assertj.core.api.Assertions.assertThat;

public class SUIButtonTest extends ApplicationTest {


	@Override
	public void start(Stage stage) {
		SUIRegistry.initialize();
	}




	@Test
	public void testSNode() {

		final ElementTestState state = new ElementTestState();

		NodeFactory button = SUIButton.button(
				Properties.id("myButton"),
				Properties.minSize(1.0, 2.0),
				Properties.preferredSize(3.0, 4.0),
				Properties.maxSize(5.0, 6.0),
				Properties.size(10.0, 11.0, 12.0, 13.0, 14.0, 15.0),
				Properties.textContent("My Text"),
				Properties.wrapText(),
				Properties.disabled(false),
				Properties.style("-fx-background-color: red"),
				Properties.alignment(Pos.CENTER),
				Properties.defaultMutationBehaviour(),
				Properties.buttonListener(() -> {
				})
		);

		SUINode node = button.create(state);

		TestUtils.assertNode(node, SUIButton.class);
		PropertyTestUtils.assertIdProperty(node, "myButton");
		PropertyTestUtils.assertSizeMinProperty(node, 1.0, 2.0);
		PropertyTestUtils.assertSizePreferredProperty(node, 3.0, 4.0);
		PropertyTestUtils.assertSizeMaxProperty(node, 5.0, 6.0);
		PropertyTestUtils.assertSizeProperty(node, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0);
		PropertyTestUtils.assertTextContentProperty(node, "My Text");
		PropertyTestUtils.assertWrapTextProperty(node, true);
		PropertyTestUtils.assertDisabledProperty(node, false);
		PropertyTestUtils.assertActionListenerProperty(node);
		PropertyTestUtils.assertStyle(node, "-fx-background-color: red");
	}




	@Test
	public void testMutate() {

		final ElementTestState state = new ElementTestState();

		NodeFactory button = SUIButton.button(
				Properties.id("myButton"),
				Properties.minSize(1.0, 2.0),
				Properties.preferredSize(3.0, 4.0),
				Properties.maxSize(5.0, 6.0),
				Properties.size(10.0, 11.0, 12.0, 13.0, 14.0, 15.0),
				Properties.textContent("My Text"),
				Properties.wrapText(),
				Properties.disabled(false),
				Properties.style("-fx-background-color: red"),
				Properties.buttonListener(() -> {
				})
		);

		NodeFactory buttonTarget = SUIButton.button(
				Properties.id("myButton"),
				Properties.minSize(1000.0, 2000.0),
				Properties.preferredSize(3.0, 4.0),
				Properties.maxSize(5.0, 6.0),
				Properties.size(10.0, 11.0, 12.0, 13.0, 14.0, 15.0),
				Properties.textContent("My New Text"),
				Properties.wrapText(),
				Properties.disabled(true),
				Properties.style("-fx-background-color: blue"),
				Properties.buttonListener(() -> {
				})
		);

		SUISceneContext context = new SUISceneContext(state, button);
		SUINode original = context.getRootNode();
		SUINode target = buttonTarget.create(state);

		SUINode mutatedNode = context.getMasterNodeHandlers().getMutator().mutate(original, target);
		assertThat(mutatedNode).isEqualTo(original);

		TestUtils.assertNode(mutatedNode, SUIButton.class);
		PropertyTestUtils.assertIdProperty(mutatedNode, "myButton");
		PropertyTestUtils.assertSizeMinProperty(mutatedNode, 1000.0, 2000.0);
		PropertyTestUtils.assertSizePreferredProperty(mutatedNode, 3.0, 4.0);
		PropertyTestUtils.assertSizeMaxProperty(mutatedNode, 5.0, 6.0);
		PropertyTestUtils.assertSizeProperty(mutatedNode, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0);
		PropertyTestUtils.assertTextContentProperty(mutatedNode, "My New Text");
		PropertyTestUtils.assertWrapTextProperty(mutatedNode, true);
		PropertyTestUtils.assertDisabledProperty(mutatedNode, true);
		PropertyTestUtils.assertActionListenerProperty(mutatedNode);
		PropertyTestUtils.assertStyle(mutatedNode, "-fx-background-color: blue");

	}




	@Test
	public void testFxNode() {

		final ElementTestState state = new ElementTestState();

		NodeFactory button = SUIButton.button(
				Properties.id("myButton"),
				Properties.minSize(1.0, 2.0),
				Properties.preferredSize(3.0, 4.0),
				Properties.maxSize(5.0, 6.0),
				Properties.size(10.0, 11.0, 12.0, 13.0, 14.0, 15.0),
				Properties.textContent("My Text"),
				Properties.wrapText(),
				Properties.disabled(false),
				Properties.style("-fx-background-color: red"),
				Properties.buttonListener(() -> {
				})
		);

		SUISceneContext context = new SUISceneContext(state, button);
		SUINode node = context.getRootNode();

		FxTestUtils.assertButton((Button) node.getFxNode(), FxTestUtils.ButtonInfo.builder()
				.minWidth(1.0).minHeight(2.0)
				.prefWidth(3.0).prefHeight(4.0)
				.maxWidth(5.0).maxHeight(6.0)
				.text("My Text")
				.wrap(true)
				.disabled(false)
				.hasOnActionListener(true)
				.style("-fx-background-color: red")
				.build());

	}


}
