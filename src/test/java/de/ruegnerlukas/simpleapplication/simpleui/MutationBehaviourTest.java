package de.ruegnerlukas.simpleapplication.simpleui;

import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.elements.ElementTestState;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SUIButton;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SUIVBox;
import de.ruegnerlukas.simpleapplication.simpleui.properties.MutationBehaviourProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.registry.SUIRegistry;
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
		SUIRegistry.initialize();
	}




	@Test
	public void testMutateMissing() {

		final ElementTestState state = new ElementTestState();

		NodeFactory vbox = SUIVBox.vbox(
				Properties.id("myVBox"),
				Properties.alignment(Pos.CENTER),
				Properties.items(
						SUIButton.button(
								Properties.id("btn1"),
								Properties.textContent("Child Button 1")
						),
						SUIButton.button(
								Properties.id("btn2"),
								Properties.textContent("Child Button 2")
						)
				)
		);

		NodeFactory vboxTarget = SUIVBox.vbox(
				Properties.id("myVBox"),
				Properties.alignment(Pos.TOP_RIGHT),
				Properties.items(
						SUIButton.button(
								Properties.id("btn2"),
								Properties.textContent("Renamed Child Button 2")
						)
				)
		);

		SUISceneContext context = new SUISceneContext(state, vbox);
		SUINode original = context.getRootNode();
		SUINode target = vboxTarget.create(state);
		SUINode mutatedNode = context.getMasterNodeHandlers().getMutator().mutate(original, target);

		PropertyTestUtils.assertAlignment(mutatedNode, Pos.TOP_RIGHT);

		final List<SUINode> children = mutatedNode.getChildrenUnmodifiable();
		assertThat(children).isNotNull();
		assertThat(children).hasSize(1);
		assertThat(children).doesNotContainNull();

		final SUINode childButton = children.get(0);
		PropertyTestUtils.assertIdProperty(childButton, "btn2");
		PropertyTestUtils.assertTextContentProperty(childButton, "Renamed Child Button 2");
	}


	@Test
	public void testMutateDefault() {

		final ElementTestState state = new ElementTestState();

		NodeFactory vbox = SUIVBox.vbox(
				Properties.id("myVBox"),
				Properties.alignment(Pos.CENTER),
				Properties.mutationBehaviour(MutationBehaviourProperty.MutationBehaviour.DEFAULT),
				Properties.items(
						SUIButton.button(
								Properties.id("btn1"),
								Properties.textContent("Child Button 1")
						),
						SUIButton.button(
								Properties.id("btn2"),
								Properties.textContent("Child Button 2")
						)
				)
		);

		NodeFactory vboxTarget = SUIVBox.vbox(
				Properties.id("myVBox"),
				Properties.alignment(Pos.TOP_RIGHT),
				Properties.mutationBehaviour(MutationBehaviourProperty.MutationBehaviour.DEFAULT),
				Properties.items(
						SUIButton.button(
								Properties.id("btn2"),
								Properties.textContent("Renamed Child Button 2")
						)
				)
		);

		SUISceneContext context = new SUISceneContext(state, vbox);
		SUINode original = context.getRootNode();
		SUINode target = vboxTarget.create(state);
		SUINode mutatedNode = context.getMasterNodeHandlers().getMutator().mutate(original, target);

		PropertyTestUtils.assertAlignment(mutatedNode, Pos.TOP_RIGHT);
		PropertyTestUtils.assertMutationBehaviour(mutatedNode, MutationBehaviourProperty.MutationBehaviour.DEFAULT);

		final List<SUINode> children = mutatedNode.getChildrenUnmodifiable();
		assertThat(children).isNotNull();
		assertThat(children).hasSize(1);
		assertThat(children).doesNotContainNull();

		final SUINode childButton = children.get(0);
		PropertyTestUtils.assertIdProperty(childButton, "btn2");
		PropertyTestUtils.assertTextContentProperty(childButton, "Renamed Child Button 2");
	}


	@Test
	public void testMutateStaticNode() {

		final ElementTestState state = new ElementTestState();

		NodeFactory vbox = SUIVBox.vbox(
				Properties.id("myVBox"),
				Properties.alignment(Pos.CENTER),
				Properties.mutationBehaviour(MutationBehaviourProperty.MutationBehaviour.STATIC_NODE),
				Properties.items(
						SUIButton.button(
								Properties.id("btn1"),
								Properties.textContent("Child Button 1")
						),
						SUIButton.button(
								Properties.id("btn2"),
								Properties.textContent("Child Button 2")
						)
				)
		);

		NodeFactory vboxTarget = SUIVBox.vbox(
				Properties.id("myVBox"),
				Properties.alignment(Pos.TOP_RIGHT),
				Properties.mutationBehaviour(MutationBehaviourProperty.MutationBehaviour.STATIC_NODE),
				Properties.items(
						SUIButton.button(
								Properties.id("btn2"),
								Properties.textContent("Renamed Child Button 2")
						)
				)
		);

		SUISceneContext context = new SUISceneContext(state, vbox);
		SUINode original = context.getRootNode();
		SUINode target = vboxTarget.create(state);
		SUINode mutatedNode = context.getMasterNodeHandlers().getMutator().mutate(original, target);

		PropertyTestUtils.assertAlignment(mutatedNode, Pos.CENTER);
		PropertyTestUtils.assertMutationBehaviour(mutatedNode, MutationBehaviourProperty.MutationBehaviour.STATIC_NODE);

		final List<SUINode> children = mutatedNode.getChildrenUnmodifiable();
		assertThat(children).isNotNull();
		assertThat(children).hasSize(1);
		assertThat(children).doesNotContainNull();

		final SUINode childButton = children.get(0);
		PropertyTestUtils.assertIdProperty(childButton, "btn2");
		PropertyTestUtils.assertTextContentProperty(childButton, "Renamed Child Button 2");
	}




	@Test
	public void testMutateStaticSubtree() {

		final ElementTestState state = new ElementTestState();

		NodeFactory vbox = SUIVBox.vbox(
				Properties.id("myVBox"),
				Properties.alignment(Pos.CENTER),
				Properties.mutationBehaviour(MutationBehaviourProperty.MutationBehaviour.STATIC_SUBTREE),
				Properties.items(
						SUIButton.button(
								Properties.id("btn1"),
								Properties.textContent("Child Button 1")
						),
						SUIButton.button(
								Properties.id("btn2"),
								Properties.textContent("Child Button 2")
						)
				)
		);

		NodeFactory vboxTarget = SUIVBox.vbox(
				Properties.id("myVBox"),
				Properties.alignment(Pos.TOP_RIGHT),
				Properties.mutationBehaviour(MutationBehaviourProperty.MutationBehaviour.STATIC_SUBTREE),
				Properties.items(
						SUIButton.button(
								Properties.id("btn2"),
								Properties.textContent("Renamed Child Button 2")
						)
				)
		);

		SUISceneContext context = new SUISceneContext(state, vbox);
		SUINode original = context.getRootNode();
		SUINode target = vboxTarget.create(state);
		SUINode mutatedNode = context.getMasterNodeHandlers().getMutator().mutate(original, target);

		PropertyTestUtils.assertAlignment(mutatedNode, Pos.CENTER);
		PropertyTestUtils.assertMutationBehaviour(mutatedNode, MutationBehaviourProperty.MutationBehaviour.STATIC_SUBTREE);

		final List<SUINode> children = mutatedNode.getChildrenUnmodifiable();
		assertThat(children).isNotNull();
		assertThat(children).hasSize(2);
		assertThat(children).doesNotContainNull();

		final SUINode childButton1 = children.get(0);
		PropertyTestUtils.assertIdProperty(childButton1, "btn1");
		PropertyTestUtils.assertTextContentProperty(childButton1, "Child Button 1");

		final SUINode childButton2 = children.get(1);
		PropertyTestUtils.assertIdProperty(childButton2, "btn2");
		PropertyTestUtils.assertTextContentProperty(childButton2, "Child Button 2");
	}



}
