package de.ruegnerlukas.simpleapplication.simpleui.elements;

import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.SUISceneContext;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.properties.MutationBehaviourProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.registry.SUIRegistry;
import de.ruegnerlukas.simpleapplication.simpleui.utils.PropertyTestUtils;
import de.ruegnerlukas.simpleapplication.simpleui.utils.TestUtils;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static de.ruegnerlukas.simpleapplication.simpleui.elements.SUIRaw.rawBehaviour;
import static org.assertj.core.api.Assertions.assertThat;

public class SUIRawTest extends ApplicationTest {


	@Override
	public void start(Stage stage) {
		SUIRegistry.initialize();
	}




	@Test
	public void testSNode() {

		final ElementTestState state = new ElementTestState();

		NodeFactory raw = SUIRaw.raw(
				rawBehaviour(suiNode -> new Button("Raw Button")),
				Properties.id("myRawNode"),
				Properties.staticSubtree()
		);

		SUINode node = raw.create(state);

		TestUtils.assertNode(node, SUIRaw.class);
		PropertyTestUtils.assertIdProperty(node, "myRawNode");
		PropertyTestUtils.assertMutationBehaviour(node, MutationBehaviourProperty.MutationBehaviour.STATIC_SUBTREE);
	}


	@Test
	public void testMutate() {

		final ElementTestState state = new ElementTestState();

		NodeFactory raw = SUIRaw.raw(
				rawBehaviour(suiNode -> new Button("Raw Button")),
				Properties.id("myRawNode")
		);

		NodeFactory rawTarget = SUIRaw.raw(
				rawBehaviour(suiNode -> new Button("Mutated Raw Button")),
				Properties.id("myRawNode")
		);

		SUISceneContext context = new SUISceneContext(state, raw);
		SUINode original = context.getRootNode();
		SUINode target = rawTarget.create(state);

		SUINode mutatedNode = context.getMasterNodeHandlers().getMutator().mutate(original, target);
		System.out.println();
//		assertThat(mutatedNode).isEqualTo(original);
//
//		TestUtils.assertNode(mutatedNode, SUIButton.class);
//		PropertyTestUtils.assertIdProperty(mutatedNode, "myButton");
//		PropertyTestUtils.assertSizeMinProperty(mutatedNode, 1000.0, 2000.0);
//		PropertyTestUtils.assertSizePreferredProperty(mutatedNode, 3.0, 4.0);
//		PropertyTestUtils.assertSizeMaxProperty(mutatedNode, 5.0, 6.0);
//		PropertyTestUtils.assertSizeProperty(mutatedNode, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0);
//		PropertyTestUtils.assertTextContentProperty(mutatedNode, "My New Text");
//		PropertyTestUtils.assertWrapTextProperty(mutatedNode, true);
//		PropertyTestUtils.assertDisabledProperty(mutatedNode, true);
//		PropertyTestUtils.assertStyle(mutatedNode, "-fx-background-color: blue");

	}





	@Test
	public void testFxNode() {

		final ElementTestState state = new ElementTestState();

		NodeFactory raw = SUIRaw.raw(
				rawBehaviour(suiNode -> new Button("Raw Button")),
				Properties.id("myRawNode"),
				Properties.staticSubtree()
		);

		SUISceneContext context = new SUISceneContext(state, raw);
		SUINode node = context.getRootNode();

		Node fxNode = node.getFxNode();

		assertThat(fxNode instanceof Button).isTrue();
		assertThat(((Button) fxNode).getText()).isEqualTo("Raw Button");
	}


}
