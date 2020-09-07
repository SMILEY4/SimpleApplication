package de.ruegnerlukas.simpleapplication.simpleui.elements;


import de.ruegnerlukas.simpleapplication.simpleui.TestState;
import de.ruegnerlukas.simpleapplication.simpleui.utils.FxTestUtils;
import de.ruegnerlukas.simpleapplication.simpleui.utils.PropertyTestUtils;
import de.ruegnerlukas.simpleapplication.simpleui.SuiSceneContext;
import de.ruegnerlukas.simpleapplication.simpleui.utils.TestUtils;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SuiVBoxTest extends ApplicationTest {


	@Override
	public void start(Stage stage) {
		SuiRegistry.initialize();
	}




	@Test
	public void testVBoxSNode() {

		final TestState state = new TestState();

		NodeFactory vbox = SuiVBox.vbox(
				Properties.id("myVBox"),
				Properties.minSize(1.0, 2.0),
				Properties.preferredSize(3.0, 4.0),
				Properties.maxSize(5.0, 6.0),
				Properties.size(10.0, 11.0, 12.0, 13.0, 14.0, 15.0),
				Properties.disabled(true),
				Properties.spacing(5.0),
				Properties.fitToWidth(true),
				Properties.style("-fx-background-color: red"),
				Properties.alignment(Pos.CENTER),
				Properties.defaultMutationBehaviour(),
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

		final SuiNode node = vbox.create(state);

		TestUtils.assertNode(node, SuiVBox.class);
		PropertyTestUtils.assertIdProperty(node, "myVBox");
		PropertyTestUtils.assertSizeMinProperty(node, 1.0, 2.0);
		PropertyTestUtils.assertSizePreferredProperty(node, 3.0, 4.0);
		PropertyTestUtils.assertSizeMaxProperty(node, 5.0, 6.0);
		PropertyTestUtils.assertSizeProperty(node, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0);
		PropertyTestUtils.assertDisabledProperty(node, true);
		PropertyTestUtils.assertSpacingProperty(node, 5.0);
		PropertyTestUtils.assertFitToWidthProperty(node, true);
		PropertyTestUtils.assertAlignment(node, Pos.CENTER);
		PropertyTestUtils.assertStyle(node, "-fx-background-color: red");

		final List<SuiNode> children = node.getChildrenUnmodifiable();
		assertThat(children).isNotNull();
		assertThat(children).hasSize(2);
		assertThat(children).doesNotContainNull();

		final SuiNode child1Button = children.get(0);
		PropertyTestUtils.assertIdProperty(child1Button, "btn1");
		PropertyTestUtils.assertTextContentProperty(child1Button, "Child Button 1");


		final SuiNode child2Button = children.get(1);
		PropertyTestUtils.assertIdProperty(child2Button, "btn2");
		PropertyTestUtils.assertTextContentProperty(child2Button, "Child Button 2");

	}




	@Test
	public void testMutate() {

		final TestState state = new TestState();

		NodeFactory vbox = SuiVBox.vbox(
				Properties.id("myVBox"),
				Properties.minSize(1.0, 2.0),
				Properties.preferredSize(3.0, 4.0),
				Properties.maxSize(5.0, 6.0),
				Properties.size(10.0, 11.0, 12.0, 13.0, 14.0, 15.0),
				Properties.disabled(true),
				Properties.spacing(5.0),
				Properties.fitToWidth(true),
				Properties.alignment(Pos.CENTER),
				Properties.style("-fx-background-color: red"),
				Properties.items(
						SuiButton.button(
								Properties.id("btn"),
								Properties.textContent("Child Button 1")
						)
				)
		);

		NodeFactory vboxTarget = SuiVBox.vbox(
				Properties.id("myVBox"),
				Properties.minSize(1.0, 2.0),
				Properties.preferredSize(33.0, 44.0),
				Properties.maxSize(5.0, 6.0),
				Properties.size(10.0, 11.0, 12.0, 13.0, 14.0, 15.0),
				Properties.disabled(false),
				Properties.spacing(15.0),
				Properties.fitToWidth(false),
				Properties.alignment(Pos.TOP_RIGHT),
				Properties.style("-fx-background-color: blue"),
				Properties.items(
						SuiButton.button(
								Properties.id("btn"),
								Properties.textContent("New Button")
						)
				)
		);

		SuiSceneContext context = new SuiSceneContext(state, vbox);
		SuiNode original = context.getRootNode();
		SuiNode target = vboxTarget.create(state);

		SuiNode mutatedNode = context.getMasterNodeHandlers().getMutator().mutate(original, target);
		assertThat(mutatedNode).isEqualTo(original);

		TestUtils.assertNode(mutatedNode, SuiVBox.class);
		PropertyTestUtils.assertIdProperty(mutatedNode, "myVBox");
		PropertyTestUtils.assertSizeMinProperty(mutatedNode, 1.0, 2.0);
		PropertyTestUtils.assertSizePreferredProperty(mutatedNode, 33.0, 44.0);
		PropertyTestUtils.assertSizeMaxProperty(mutatedNode, 5.0, 6.0);
		PropertyTestUtils.assertSizeProperty(mutatedNode, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0);
		PropertyTestUtils.assertDisabledProperty(mutatedNode, false);
		PropertyTestUtils.assertSpacingProperty(mutatedNode, 15.0);
		PropertyTestUtils.assertFitToWidthProperty(mutatedNode, false);
		PropertyTestUtils.assertAlignment(mutatedNode, Pos.TOP_RIGHT);
		PropertyTestUtils.assertStyle(mutatedNode, "-fx-background-color: blue");

		final List<SuiNode> children = mutatedNode.getChildrenUnmodifiable();
		assertThat(children).isNotNull();
		assertThat(children).hasSize(1);
		assertThat(children).doesNotContainNull();

		final SuiNode childButton = children.get(0);
		PropertyTestUtils.assertIdProperty(childButton, "btn");
		PropertyTestUtils.assertTextContentProperty(childButton, "New Button");
	}




	@Test
	public void testMutateAddChild() {

		final TestState state = new TestState();

		NodeFactory vbox = SuiVBox.vbox(
				Properties.id("myVBox"),
				Properties.items(
						SuiButton.button(
								Properties.id("btn1"),
								Properties.textContent("Button 1")
						)
				)
		);

		NodeFactory vboxTarget = SuiVBox.vbox(
				Properties.id("myVBox"),
				Properties.items(
						SuiButton.button(
								Properties.id("btn1"),
								Properties.textContent("Button 1")
						),
						SuiButton.button(
								Properties.id("btn2"),
								Properties.textContent("Button 2")
						)
				)
		);

		SuiSceneContext context = new SuiSceneContext(state, vbox);
		SuiNode original = context.getRootNode();
		SuiNode target = vboxTarget.create(state);

		SuiNode mutatedNode = context.getMasterNodeHandlers().getMutator().mutate(original, target);
		assertThat(mutatedNode).isEqualTo(original);

		TestUtils.assertNode(mutatedNode, SuiVBox.class);
		PropertyTestUtils.assertIdProperty(mutatedNode, "myVBox");

		final List<SuiNode> children = mutatedNode.getChildrenUnmodifiable();
		assertThat(children).isNotNull();
		assertThat(children).hasSize(2);
		assertThat(children).doesNotContainNull();

		final SuiNode child1Button = children.get(0);
		PropertyTestUtils.assertIdProperty(child1Button, "btn1");
		PropertyTestUtils.assertTextContentProperty(child1Button, "Button 1");

		final SuiNode child2Button = children.get(1);
		PropertyTestUtils.assertIdProperty(child2Button, "btn2");
		PropertyTestUtils.assertTextContentProperty(child2Button, "Button 2");
	}




	@Test
	public void testMutateCreateChild() {

		final TestState state = new TestState();

		NodeFactory vbox = SuiVBox.vbox(
				Properties.id("myVBox")
		);

		NodeFactory vboxTarget = SuiVBox.vbox(
				Properties.id("myVBox"),
				Properties.items(
						SuiButton.button(
								Properties.id("btn"),
								Properties.textContent("Button")
						)
				)
		);

		SuiSceneContext context = new SuiSceneContext(state, vbox);
		SuiNode original = context.getRootNode();
		SuiNode target = vboxTarget.create(state);

		SuiNode mutatedNode = context.getMasterNodeHandlers().getMutator().mutate(original, target);
		assertThat(mutatedNode).isEqualTo(original);

		TestUtils.assertNode(mutatedNode, SuiVBox.class);
		PropertyTestUtils.assertIdProperty(mutatedNode, "myVBox");

		final List<SuiNode> children = mutatedNode.getChildrenUnmodifiable();
		assertThat(children).isNotNull();
		assertThat(children).hasSize(1);
		assertThat(children).doesNotContainNull();

		final SuiNode child1Button = children.get(0);
		PropertyTestUtils.assertIdProperty(child1Button, "btn");
		PropertyTestUtils.assertTextContentProperty(child1Button, "Button");
	}




	@Test
	public void testMutateClearChildren() {

		final TestState state = new TestState();

		NodeFactory vbox = SuiVBox.vbox(
				Properties.id("myVBox"),
				Properties.items(
						SuiButton.button(
								Properties.id("btn1"),
								Properties.textContent("Button 1")
						),
						SuiButton.button(
								Properties.id("btn2"),
								Properties.textContent("Button 2")
						)
				)
		);

		NodeFactory vboxTarget = SuiVBox.vbox(
				Properties.id("myVBox"),
				Properties.items(
				)
		);

		SuiSceneContext context = new SuiSceneContext(state, vbox);
		SuiNode original = context.getRootNode();
		SuiNode target = vboxTarget.create(state);

		SuiNode mutatedNode = context.getMasterNodeHandlers().getMutator().mutate(original, target);
		assertThat(mutatedNode).isEqualTo(original);

		TestUtils.assertNode(mutatedNode, SuiVBox.class);
		PropertyTestUtils.assertIdProperty(mutatedNode, "myVBox");

		final List<SuiNode> children = mutatedNode.getChildrenUnmodifiable();
		assertThat(children).isNotNull();
		assertThat(children).hasSize(0);

	}




	@Test
	public void testMutateRemoveChild1() {

		final TestState state = new TestState();

		NodeFactory vbox = SuiVBox.vbox(
				Properties.id("myVBox"),
				Properties.items(
						SuiButton.button(
								Properties.id("btn1"),
								Properties.textContent("Button 1")
						),
						SuiButton.button(
								Properties.id("btn2"),
								Properties.textContent("Button 2")
						)
				)
		);

		NodeFactory vboxTarget = SuiVBox.vbox(
				Properties.id("myVBox"),
				Properties.items(
						SuiButton.button(
								Properties.id("btn2"),
								Properties.textContent("Button 2")
						)
				)
		);

		SuiSceneContext context = new SuiSceneContext(state, vbox);
		SuiNode original = context.getRootNode();
		SuiNode target = vboxTarget.create(state);

		SuiNode mutatedNode = context.getMasterNodeHandlers().getMutator().mutate(original, target);
		assertThat(mutatedNode).isEqualTo(original);

		TestUtils.assertNode(mutatedNode, SuiVBox.class);
		PropertyTestUtils.assertIdProperty(mutatedNode, "myVBox");

		final List<SuiNode> children = mutatedNode.getChildrenUnmodifiable();
		assertThat(children).isNotNull();
		assertThat(children).hasSize(1);
		assertThat(children).doesNotContainNull();

		final SuiNode childButton = children.get(0);
		PropertyTestUtils.assertIdProperty(childButton, "btn2");
		PropertyTestUtils.assertTextContentProperty(childButton, "Button 2");
	}




	@Test
	public void testMutateRemoveChild2() {

		final TestState state = new TestState();

		NodeFactory vbox = SuiVBox.vbox(
				Properties.id("myVBox"),
				Properties.items(
						SuiButton.button(
								Properties.id("btn1"),
								Properties.textContent("Button 1")
						),
						SuiButton.button(
								Properties.id("btn2"),
								Properties.textContent("Button 2")
						)
				)
		);

		NodeFactory vboxTarget = SuiVBox.vbox(
				Properties.id("myVBox"),
				Properties.items(
						SuiButton.button(
								Properties.id("btn1"),
								Properties.textContent("Button 1")
						)
				)
		);

		SuiSceneContext context = new SuiSceneContext(state, vbox);
		SuiNode original = context.getRootNode();
		SuiNode target = vboxTarget.create(state);

		SuiNode mutatedNode = context.getMasterNodeHandlers().getMutator().mutate(original, target);
		assertThat(mutatedNode).isEqualTo(original);

		TestUtils.assertNode(mutatedNode, SuiVBox.class);
		PropertyTestUtils.assertIdProperty(mutatedNode, "myVBox");

		final List<SuiNode> children = mutatedNode.getChildrenUnmodifiable();
		assertThat(children).isNotNull();
		assertThat(children).hasSize(1);
		assertThat(children).doesNotContainNull();

		final SuiNode childButton = children.get(0);
		PropertyTestUtils.assertIdProperty(childButton, "btn1");
		PropertyTestUtils.assertTextContentProperty(childButton, "Button 1");
	}




	@Test
	public void testMutateSwitchChildren() {

		final TestState state = new TestState();

		NodeFactory vbox = SuiVBox.vbox(
				Properties.id("myVBox"),
				Properties.items(
						SuiButton.button(
								Properties.id("btn1"),
								Properties.textContent("Button 1")
						),
						SuiButton.button(
								Properties.id("btn2"),
								Properties.textContent("Button 2")
						)
				)
		);

		NodeFactory vboxTarget = SuiVBox.vbox(
				Properties.id("myVBox"),
				Properties.items(
						SuiButton.button(
								Properties.id("btn2"),
								Properties.textContent("Button 2")
						),
						SuiButton.button(
								Properties.id("btn1"),
								Properties.textContent("Button 1")
						)
				)
		);

		SuiSceneContext context = new SuiSceneContext(state, vbox);
		SuiNode original = context.getRootNode();
		SuiNode target = vboxTarget.create(state);

		SuiNode mutatedNode = context.getMasterNodeHandlers().getMutator().mutate(original, target);
		assertThat(mutatedNode).isEqualTo(original);

		TestUtils.assertNode(mutatedNode, SuiVBox.class);
		PropertyTestUtils.assertIdProperty(mutatedNode, "myVBox");

		final List<SuiNode> children = mutatedNode.getChildrenUnmodifiable();
		assertThat(children).isNotNull();
		assertThat(children).hasSize(2);
		assertThat(children).doesNotContainNull();

		final SuiNode child1Button = children.get(0);
		PropertyTestUtils.assertIdProperty(child1Button, "btn2");
		PropertyTestUtils.assertTextContentProperty(child1Button, "Button 2");

		final SuiNode child2Button = children.get(1);
		PropertyTestUtils.assertIdProperty(child2Button, "btn1");
		PropertyTestUtils.assertTextContentProperty(child2Button, "Button 1");
	}




	@Test
	public void testFxNode() {

		final TestState state = new TestState();

		NodeFactory vbox = SuiVBox.vbox(
				Properties.id("myVBox"),
				Properties.minSize(1.0, 2.0),
				Properties.preferredSize(3.0, 4.0),
				Properties.maxSize(5.0, 6.0),
				Properties.size(10.0, 11.0, 12.0, 13.0, 14.0, 15.0),
				Properties.disabled(true),
				Properties.spacing(5.0),
				Properties.fitToWidth(true),
				Properties.alignment(Pos.CENTER),
				Properties.style("-fx-background-color: red"),
				Properties.items(
						SuiButton.button(
								Properties.id("btn1"),
								Properties.textContent("Button 1")
						),
						SuiButton.button(
								Properties.id("btn2"),
								Properties.textContent("Button 2")
						)
				)
		);

		SuiSceneContext context = new SuiSceneContext(state, vbox);
		SuiNode node = context.getRootNode();

		FxTestUtils.assertVBox((VBox) node.getFxNode(), FxTestUtils.VBoxInfo.builder()
				.minWidth(1.0).minHeight(2.0)
				.prefWidth(3.0).prefHeight(4.0)
				.maxWidth(5.0).maxHeight(6.0)
				.disabled(true)
				.spacing(5.0)
				.fitToWidth(true)
				.alignment(Pos.CENTER)
				.contentButtonTexts(List.of("Button 1", "Button 2"))
				.style("-fx-background-color: red")
				.build());
	}

}
