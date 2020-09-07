package de.ruegnerlukas.simpleapplication.simpleui;

import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SuiButton;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SuiVBox;
import de.ruegnerlukas.simpleapplication.simpleui.properties.MutationBehaviourProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry;
import de.ruegnerlukas.simpleapplication.simpleui.utils.PropertyTestUtils;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MutationBehaviourTest extends ApplicationTest {


	@Override
	public void start(Stage stage) {
		SuiRegistry.initialize();
	}




	@Test
	public void testMutateMissing() {

		final TestState state = new TestState();

		NodeFactory vbox = SuiVBox.vbox(
				Properties.id("myVBox"),
				Properties.alignment(Pos.CENTER),
				Properties.items(
						SuiButton.button(
								Properties.id("btn1"),
								Properties.textContent("Child Button 1")
						),
						SuiButton.button(
								Properties.id("btn2"),
								Properties.textContent("Child Button 2")
						)
				)
		);

		NodeFactory vboxTarget = SuiVBox.vbox(
				Properties.id("myVBox"),
				Properties.alignment(Pos.TOP_RIGHT),
				Properties.items(
						SuiButton.button(
								Properties.id("btn2"),
								Properties.textContent("Renamed Child Button 2")
						)
				)
		);

		SuiSceneContext context = new SuiSceneContext(state, vbox);
		SuiNode original = context.getRootNode();
		SuiNode target = vboxTarget.create(state);
		SuiNode mutatedNode = context.getMasterNodeHandlers().getMutator().mutate(original, target);

		PropertyTestUtils.assertAlignment(mutatedNode, Pos.TOP_RIGHT);

		final List<SuiNode> children = mutatedNode.getChildrenUnmodifiable();
		assertThat(children).isNotNull();
		assertThat(children).hasSize(1);
		assertThat(children).doesNotContainNull();

		final SuiNode childButton = children.get(0);
		PropertyTestUtils.assertIdProperty(childButton, "btn2");
		PropertyTestUtils.assertTextContentProperty(childButton, "Renamed Child Button 2");
	}


	@Test
	public void testMutateDefault() {

		final TestState state = new TestState();

		NodeFactory vbox = SuiVBox.vbox(
				Properties.id("myVBox"),
				Properties.alignment(Pos.CENTER),
				Properties.mutationBehaviour(MutationBehaviourProperty.MutationBehaviour.DEFAULT),
				Properties.items(
						SuiButton.button(
								Properties.id("btn1"),
								Properties.textContent("Child Button 1")
						),
						SuiButton.button(
								Properties.id("btn2"),
								Properties.textContent("Child Button 2")
						)
				)
		);

		NodeFactory vboxTarget = SuiVBox.vbox(
				Properties.id("myVBox"),
				Properties.alignment(Pos.TOP_RIGHT),
				Properties.mutationBehaviour(MutationBehaviourProperty.MutationBehaviour.DEFAULT),
				Properties.items(
						SuiButton.button(
								Properties.id("btn2"),
								Properties.textContent("Renamed Child Button 2")
						)
				)
		);

		SuiSceneContext context = new SuiSceneContext(state, vbox);
		SuiNode original = context.getRootNode();
		SuiNode target = vboxTarget.create(state);
		SuiNode mutatedNode = context.getMasterNodeHandlers().getMutator().mutate(original, target);

		PropertyTestUtils.assertAlignment(mutatedNode, Pos.TOP_RIGHT);
		PropertyTestUtils.assertMutationBehaviour(mutatedNode, MutationBehaviourProperty.MutationBehaviour.DEFAULT);

		final List<SuiNode> children = mutatedNode.getChildrenUnmodifiable();
		assertThat(children).isNotNull();
		assertThat(children).hasSize(1);
		assertThat(children).doesNotContainNull();

		final SuiNode childButton = children.get(0);
		PropertyTestUtils.assertIdProperty(childButton, "btn2");
		PropertyTestUtils.assertTextContentProperty(childButton, "Renamed Child Button 2");
	}


	@Test
	public void testMutateStaticNode() {

		final TestState state = new TestState();

		NodeFactory vbox = SuiVBox.vbox(
				Properties.id("myVBox"),
				Properties.alignment(Pos.CENTER),
				Properties.mutationBehaviour(MutationBehaviourProperty.MutationBehaviour.STATIC_NODE),
				Properties.items(
						SuiButton.button(
								Properties.id("btn1"),
								Properties.textContent("Child Button 1")
						),
						SuiButton.button(
								Properties.id("btn2"),
								Properties.textContent("Child Button 2")
						)
				)
		);

		NodeFactory vboxTarget = SuiVBox.vbox(
				Properties.id("myVBox"),
				Properties.alignment(Pos.TOP_RIGHT),
				Properties.mutationBehaviour(MutationBehaviourProperty.MutationBehaviour.STATIC_NODE),
				Properties.items(
						SuiButton.button(
								Properties.id("btn2"),
								Properties.textContent("Renamed Child Button 2")
						)
				)
		);

		SuiSceneContext context = new SuiSceneContext(state, vbox);
		SuiNode original = context.getRootNode();
		SuiNode target = vboxTarget.create(state);
		SuiNode mutatedNode = context.getMasterNodeHandlers().getMutator().mutate(original, target);

		PropertyTestUtils.assertAlignment(mutatedNode, Pos.CENTER);
		PropertyTestUtils.assertMutationBehaviour(mutatedNode, MutationBehaviourProperty.MutationBehaviour.STATIC_NODE);

		final List<SuiNode> children = mutatedNode.getChildrenUnmodifiable();
		assertThat(children).isNotNull();
		assertThat(children).hasSize(1);
		assertThat(children).doesNotContainNull();

		final SuiNode childButton = children.get(0);
		PropertyTestUtils.assertIdProperty(childButton, "btn2");
		PropertyTestUtils.assertTextContentProperty(childButton, "Renamed Child Button 2");
	}




	@Test
	public void testMutateStaticSubtree() {

		final TestState state = new TestState();

		NodeFactory vbox = SuiVBox.vbox(
				Properties.id("myVBox"),
				Properties.alignment(Pos.CENTER),
				Properties.mutationBehaviour(MutationBehaviourProperty.MutationBehaviour.STATIC_SUBTREE),
				Properties.items(
						SuiButton.button(
								Properties.id("btn1"),
								Properties.textContent("Child Button 1")
						),
						SuiButton.button(
								Properties.id("btn2"),
								Properties.textContent("Child Button 2")
						)
				)
		);

		NodeFactory vboxTarget = SuiVBox.vbox(
				Properties.id("myVBox"),
				Properties.alignment(Pos.TOP_RIGHT),
				Properties.mutationBehaviour(MutationBehaviourProperty.MutationBehaviour.STATIC_SUBTREE),
				Properties.items(
						SuiButton.button(
								Properties.id("btn2"),
								Properties.textContent("Renamed Child Button 2")
						)
				)
		);

		SuiSceneContext context = new SuiSceneContext(state, vbox);
		SuiNode original = context.getRootNode();
		SuiNode target = vboxTarget.create(state);
		SuiNode mutatedNode = context.getMasterNodeHandlers().getMutator().mutate(original, target);

		PropertyTestUtils.assertAlignment(mutatedNode, Pos.CENTER);
		PropertyTestUtils.assertMutationBehaviour(mutatedNode, MutationBehaviourProperty.MutationBehaviour.STATIC_SUBTREE);

		final List<SuiNode> children = mutatedNode.getChildrenUnmodifiable();
		assertThat(children).isNotNull();
		assertThat(children).hasSize(2);
		assertThat(children).doesNotContainNull();

		final SuiNode childButton1 = children.get(0);
		PropertyTestUtils.assertIdProperty(childButton1, "btn1");
		PropertyTestUtils.assertTextContentProperty(childButton1, "Child Button 1");

		final SuiNode childButton2 = children.get(1);
		PropertyTestUtils.assertIdProperty(childButton2, "btn2");
		PropertyTestUtils.assertTextContentProperty(childButton2, "Child Button 2");
	}



}
