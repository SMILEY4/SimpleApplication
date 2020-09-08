package de.ruegnerlukas.simpleapplication.simpleui;

import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiButton;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiVBox;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.MutationBehaviourProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.CoreServices;
import de.ruegnerlukas.simpleapplication.simpleui.core.SuiSceneController;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiBaseNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
import de.ruegnerlukas.simpleapplication.simpleui.testutils.PropertyTestUtils;
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
	public void test_mutate_without_behaviour_property_expect_everything_changed() {

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

		SuiSceneController context = new SuiSceneController(state, vbox);
		SuiBaseNode original = context.getRootNode();
		SuiBaseNode target = vboxTarget.create(state);
		SuiBaseNode mutatedNode = CoreServices.mutateNode(original, target);

		PropertyTestUtils.assertAlignment(mutatedNode, Pos.TOP_RIGHT);

		final List<SuiBaseNode> children = mutatedNode.getChildNodeStore().getUnmodifiable();
		assertThat(children).isNotNull();
		assertThat(children).hasSize(1);
		assertThat(children).doesNotContainNull();

		final SuiBaseNode childButton = children.get(0);
		PropertyTestUtils.assertIdProperty(childButton, "btn2");
		PropertyTestUtils.assertTextContentProperty(childButton, "Renamed Child Button 2");
	}


	@Test
	public void test_mutate_with_default_behaviour_expect_everything_changed() {

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

		SuiSceneController context = new SuiSceneController(state, vbox);
		SuiBaseNode original = context.getRootNode();
		SuiBaseNode target = vboxTarget.create(state);
		SuiBaseNode mutatedNode = CoreServices.mutateNode(original, target);

		PropertyTestUtils.assertAlignment(mutatedNode, Pos.TOP_RIGHT);
		PropertyTestUtils.assertMutationBehaviour(mutatedNode, MutationBehaviourProperty.MutationBehaviour.DEFAULT);

		final List<SuiBaseNode> children = mutatedNode.getChildNodeStore().getUnmodifiable();
		assertThat(children).isNotNull();
		assertThat(children).hasSize(1);
		assertThat(children).doesNotContainNull();

		final SuiBaseNode childButton = children.get(0);
		PropertyTestUtils.assertIdProperty(childButton, "btn2");
		PropertyTestUtils.assertTextContentProperty(childButton, "Renamed Child Button 2");
	}


	@Test
	public void test_mutate_with_behaviour_is_static_expect_only_children_changed() {

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

		SuiSceneController context = new SuiSceneController(state, vbox);
		SuiBaseNode original = context.getRootNode();
		SuiBaseNode target = vboxTarget.create(state);
		SuiBaseNode mutatedNode = CoreServices.mutateNode(original, target);

		PropertyTestUtils.assertAlignment(mutatedNode, Pos.CENTER);
		PropertyTestUtils.assertMutationBehaviour(mutatedNode, MutationBehaviourProperty.MutationBehaviour.STATIC_NODE);

		final List<SuiBaseNode> children = mutatedNode.getChildNodeStore().getUnmodifiable();
		assertThat(children).isNotNull();
		assertThat(children).hasSize(1);
		assertThat(children).doesNotContainNull();

		final SuiBaseNode childButton = children.get(0);
		PropertyTestUtils.assertIdProperty(childButton, "btn2");
		PropertyTestUtils.assertTextContentProperty(childButton, "Renamed Child Button 2");
	}




	@Test
	public void test_mutate_with_behaviour_is_static_expect_nothing_changed() {

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

		SuiSceneController context = new SuiSceneController(state, vbox);
		SuiBaseNode original = context.getRootNode();
		SuiBaseNode target = vboxTarget.create(state);
		SuiBaseNode mutatedNode = CoreServices.mutateNode(original, target);

		PropertyTestUtils.assertAlignment(mutatedNode, Pos.CENTER);
		PropertyTestUtils.assertMutationBehaviour(mutatedNode, MutationBehaviourProperty.MutationBehaviour.STATIC_SUBTREE);

		final List<SuiBaseNode> children = mutatedNode.getChildNodeStore().getUnmodifiable();
		assertThat(children).isNotNull();
		assertThat(children).hasSize(2);
		assertThat(children).doesNotContainNull();

		final SuiBaseNode childButton1 = children.get(0);
		PropertyTestUtils.assertIdProperty(childButton1, "btn1");
		PropertyTestUtils.assertTextContentProperty(childButton1, "Child Button 1");

		final SuiBaseNode childButton2 = children.get(1);
		PropertyTestUtils.assertIdProperty(childButton2, "btn2");
		PropertyTestUtils.assertTextContentProperty(childButton2, "Child Button 2");
	}



}
