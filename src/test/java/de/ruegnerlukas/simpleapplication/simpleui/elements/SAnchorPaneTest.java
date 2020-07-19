package de.ruegnerlukas.simpleapplication.simpleui.elements;


import de.ruegnerlukas.simpleapplication.simpleui.FxTestUtils;
import de.ruegnerlukas.simpleapplication.simpleui.PropertyTestUtils;
import de.ruegnerlukas.simpleapplication.simpleui.SNode;
import de.ruegnerlukas.simpleapplication.simpleui.SceneContext;
import de.ruegnerlukas.simpleapplication.simpleui.SimpleUIRegistry;
import de.ruegnerlukas.simpleapplication.simpleui.TestUtils;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Properties;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SAnchorPaneTest extends ApplicationTest {


	@Override
	public void start(Stage stage) {
		SimpleUIRegistry.initialize();
	}




	@Test
	public void testAnchorPaneSNode() {

		final TestState state = new TestState();

		NodeFactory anchorPane = SAnchorPane.anchorPane(
				Properties.id("myAnchorPane"),
				Properties.minSize(1.0, 2.0),
				Properties.preferredSize(3.0, 4.0),
				Properties.maxSize(5.0, 6.0),
				Properties.size(10.0, 11.0, 12.0, 13.0, 14.0, 15.0),
				Properties.disabled(true),
				Properties.items(
						SAnchorPane.anchorPaneItem(
								SButton.button(
										Properties.id("btn1"),
										Properties.textContent("Child Button 1")
								),
								Properties.anchor(10, null, null, 50)
						),
						SAnchorPane.anchorPaneItem(
								SButton.button(
										Properties.id("btn2"),
										Properties.textContent("Child Button 2")
								),
								Properties.anchor(20, null, null, 50)
						)
				)
		);

		final SNode node = anchorPane.create(state);
		assertThat(node.getProperties().keySet())
				.containsExactlyInAnyOrderElementsOf(SimpleUIRegistry.get().getEntry(SAnchorPane.class).getProperties());

		TestUtils.assertNode(node, SAnchorPane.class);
		PropertyTestUtils.assertIdProperty(node, "myAnchorPane");
		PropertyTestUtils.assertSizeMinProperty(node, 1.0, 2.0);
		PropertyTestUtils.assertSizePreferredProperty(node, 3.0, 4.0);
		PropertyTestUtils.assertSizeMaxProperty(node, 5.0, 6.0);
		PropertyTestUtils.assertSizeProperty(node, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0);
		PropertyTestUtils.assertDisabledProperty(node, true);

		final List<SNode> children = node.getChildren();
		assertThat(children).isNotNull();
		assertThat(children).hasSize(2);
		assertThat(children).doesNotContainNull();

		final SNode child1 = children.get(0);
		TestUtils.assertNode(child1, SAnchorPane.AnchorPaneChildItem.class);
		PropertyTestUtils.assertAnchorProperty(child1, 10, null, null, 50);
		assertThat(child1.getChildren()).isNotNull();
		assertThat(child1.getChildren()).hasSize(1);
		assertThat(child1.getChildren()).doesNotContainNull();

		final SNode child1Button = child1.getChildren().get(0);
		PropertyTestUtils.assertIdProperty(child1Button, "btn1");
		PropertyTestUtils.assertTextContentProperty(child1Button, "Child Button 1");

		final SNode child2 = children.get(0);
		TestUtils.assertNode(child2, SAnchorPane.AnchorPaneChildItem.class);
		PropertyTestUtils.assertAnchorProperty(child2, 10, null, null, 50);
		assertThat(child2.getChildren()).isNotNull();
		assertThat(child2.getChildren()).hasSize(1);
		assertThat(child2.getChildren()).doesNotContainNull();

		final SNode child2Button = child2.getChildren().get(0);
		PropertyTestUtils.assertIdProperty(child2Button, "btn1");
		PropertyTestUtils.assertTextContentProperty(child2Button, "Child Button 1");

	}




	@Test
	public void testMutate() {

		final TestState state = new TestState();

		NodeFactory anchorPane = SAnchorPane.anchorPane(
				Properties.id("myAnchorPane"),
				Properties.minSize(1.0, 2.0),
				Properties.preferredSize(3.0, 4.0),
				Properties.maxSize(5.0, 6.0),
				Properties.size(10.0, 11.0, 12.0, 13.0, 14.0, 15.0),
				Properties.disabled(true),
				Properties.items(
						SAnchorPane.anchorPaneItem(
								SButton.button(
										Properties.id("btn"),
										Properties.textContent("Child Button")
								),
								Properties.anchor(20, null, null, 50)
						)
				)
		);

		NodeFactory anchorPaneTarget = SAnchorPane.anchorPane(
				Properties.id("myAnchorPane"),
				Properties.minSize(1.0, 2.0),
				Properties.preferredSize(3000.0, 4000.0),
				Properties.maxSize(5.0, 6.0),
				Properties.size(10.0, 11.0, 12.0, 13.0, 14.0, 15.0),
				Properties.disabled(false),
				Properties.items(
						SAnchorPane.anchorPaneItem(
								SButton.button(
										Properties.id("btn"),
										Properties.textContent("New Button")
								),
								Properties.anchor(500, 100, null, null)
						)
				)
		);

		SceneContext context = new SceneContext(state, anchorPane, null);
		SNode original = context.getRootNode();
		SNode target = anchorPaneTarget.create(state);

		SNode mutatedNode = context.getMutator().mutate(original, target);
		assertThat(mutatedNode).isEqualTo(original);

		TestUtils.assertNode(mutatedNode, SAnchorPane.class);
		PropertyTestUtils.assertIdProperty(mutatedNode, "myAnchorPane");
		PropertyTestUtils.assertSizeMinProperty(mutatedNode, 1.0, 2.0);
		PropertyTestUtils.assertSizePreferredProperty(mutatedNode, 3000.0, 4000.0);
		PropertyTestUtils.assertSizeMaxProperty(mutatedNode, 5.0, 6.0);
		PropertyTestUtils.assertSizeProperty(mutatedNode, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0);
		PropertyTestUtils.assertDisabledProperty(mutatedNode, false);

		final List<SNode> children = mutatedNode.getChildren();
		assertThat(children).isNotNull();
		assertThat(children).hasSize(1);
		assertThat(children).doesNotContainNull();

		final SNode child1 = children.get(0);
		TestUtils.assertNode(child1, SAnchorPane.AnchorPaneChildItem.class);
		PropertyTestUtils.assertAnchorProperty(child1, 500, 100, null, null);
		assertThat(child1.getChildren()).isNotNull();
		assertThat(child1.getChildren()).hasSize(1);
		assertThat(child1.getChildren()).doesNotContainNull();

		final SNode child1Button = child1.getChildren().get(0);
		PropertyTestUtils.assertIdProperty(child1Button, "btn");
		PropertyTestUtils.assertTextContentProperty(child1Button, "New Button");
	}




	@Test
	public void testMutateAddChild() {

		final TestState state = new TestState();

		NodeFactory anchorPane = SAnchorPane.anchorPane(
				Properties.items(
						SAnchorPane.anchorPaneItem(
								SButton.button(
										Properties.id("btn1"),
										Properties.textContent("Button 1")
								)
						)
				)
		);

		NodeFactory anchorPaneTarget = SAnchorPane.anchorPane(
				Properties.items(
						SAnchorPane.anchorPaneItem(
								SButton.button(
										Properties.id("btn1"),
										Properties.textContent("Button 1")
								)
						),
						SAnchorPane.anchorPaneItem(
								SButton.button(
										Properties.id("btn2"),
										Properties.textContent("Button 2")
								)
						)
				)
		);

		SceneContext context = new SceneContext(state, anchorPane, null);
		SNode original = context.getRootNode();
		SNode target = anchorPaneTarget.create(state);

		SNode mutatedNode = context.getMutator().mutate(original, target);
		assertThat(mutatedNode).isEqualTo(original);

		TestUtils.assertNode(mutatedNode, SAnchorPane.class);

		final List<SNode> children = mutatedNode.getChildren();
		assertThat(children).isNotNull();
		assertThat(children).hasSize(2);
		assertThat(children).doesNotContainNull();
		assertThat(children.get(0)).isEqualTo(original.getChildren().get(0));

		assertChildButton(mutatedNode, 0, "btn1", "Button 1");
		assertChildButton(mutatedNode, 1, "btn2", "Button 2");
	}




	@Test
	public void testMutateCreateChild() {

		final TestState state = new TestState();

		NodeFactory anchorPane = SAnchorPane.anchorPane(
				Properties.items(
				)
		);

		NodeFactory anchorPaneTarget = SAnchorPane.anchorPane(
				Properties.items(
						SAnchorPane.anchorPaneItem(
								SButton.button(
										Properties.id("btn"),
										Properties.textContent("Button")
								)
						)
				)
		);

		SceneContext context = new SceneContext(state, anchorPane, null);
		SNode original = context.getRootNode();
		SNode target = anchorPaneTarget.create(state);

		SNode mutatedNode = context.getMutator().mutate(original, target);
		assertThat(mutatedNode).isEqualTo(original);

		TestUtils.assertNode(mutatedNode, SAnchorPane.class);

		final List<SNode> children = mutatedNode.getChildren();
		assertThat(children).isNotNull();
		assertThat(children).hasSize(1);
		assertThat(children).doesNotContainNull();

		assertChildButton(mutatedNode, 0, "btn", "Button");
	}




	@Test
	public void testMutateClearChildren() {

		final TestState state = new TestState();

		NodeFactory anchorPane = SAnchorPane.anchorPane(
				Properties.items(
						SAnchorPane.anchorPaneItem(
								SButton.button(
										Properties.id("btn1"),
										Properties.textContent("Button 1")
								)
						),
						SAnchorPane.anchorPaneItem(
								SButton.button(
										Properties.id("btn2"),
										Properties.textContent("Button 2")
								)
						)
				)
		);

		NodeFactory anchorPaneTarget = SAnchorPane.anchorPane(
				Properties.items(
				)
		);

		SceneContext context = new SceneContext(state, anchorPane, null);
		SNode original = context.getRootNode();
		SNode target = anchorPaneTarget.create(state);

		SNode mutatedNode = context.getMutator().mutate(original, target);
		assertThat(mutatedNode).isEqualTo(original);

		TestUtils.assertNode(mutatedNode, SAnchorPane.class);

		final List<SNode> children = mutatedNode.getChildren();
		assertThat(children).isNotNull();
		assertThat(children).hasSize(0);

	}




	@Test
	public void testMutateRemoveChild1() {

		final TestState state = new TestState();

		NodeFactory anchorPane = SAnchorPane.anchorPane(
				Properties.items(
						SAnchorPane.anchorPaneItem(
								SButton.button(
										Properties.id("btn1"),
										Properties.textContent("Button 1")
								)
						),
						SAnchorPane.anchorPaneItem(
								SButton.button(
										Properties.id("btn2"),
										Properties.textContent("Button 2")
								)
						)
				)
		);

		NodeFactory anchorPaneTarget = SAnchorPane.anchorPane(
				Properties.items(
						SAnchorPane.anchorPaneItem(
								SButton.button(
										Properties.id("btn2"),
										Properties.textContent("Button 2")
								)
						)
				)
		);

		SceneContext context = new SceneContext(state, anchorPane, null);
		SNode original = context.getRootNode();
		SNode target = anchorPaneTarget.create(state);

		SNode mutatedNode = context.getMutator().mutate(original, target);
		assertThat(mutatedNode).isEqualTo(original);

		TestUtils.assertNode(mutatedNode, SAnchorPane.class);

		final List<SNode> children = mutatedNode.getChildren();
		assertThat(children).isNotNull();
		assertThat(children).hasSize(1);
		assertThat(children).doesNotContainNull();

		assertChildButton(mutatedNode, 0, "btn2", "Button 2");
	}




	@Test
	public void testMutateRemoveChild2() {

		final TestState state = new TestState();

		NodeFactory anchorPane = SAnchorPane.anchorPane(
				Properties.items(
						SAnchorPane.anchorPaneItem(
								SButton.button(
										Properties.id("btn1"),
										Properties.textContent("Button 1")
								)
						),
						SAnchorPane.anchorPaneItem(
								SButton.button(
										Properties.id("btn2"),
										Properties.textContent("Button 2")
								)
						)
				)
		);

		NodeFactory anchorPaneTarget = SAnchorPane.anchorPane(
				Properties.items(
						SAnchorPane.anchorPaneItem(
								SButton.button(
										Properties.id("btn1"),
										Properties.textContent("Button 1")
								)
						)
				)
		);

		SceneContext context = new SceneContext(state, anchorPane, null);
		SNode original = context.getRootNode();
		SNode target = anchorPaneTarget.create(state);

		SNode mutatedNode = context.getMutator().mutate(original, target);
		assertThat(mutatedNode).isEqualTo(original);

		TestUtils.assertNode(mutatedNode, SAnchorPane.class);

		final List<SNode> children = mutatedNode.getChildren();
		assertThat(children).isNotNull();
		assertThat(children).hasSize(1);
		assertThat(children).doesNotContainNull();

		assertChildButton(mutatedNode, 0, "btn1", "Button 1");
	}




	@Test
	public void testMutateSwitchChildren() {

		final TestState state = new TestState();

		NodeFactory anchorPane = SAnchorPane.anchorPane(
				Properties.items(
						SAnchorPane.anchorPaneItem(
								SButton.button(
										Properties.id("btn1"),
										Properties.textContent("Button 1")
								)
						),
						SAnchorPane.anchorPaneItem(
								SButton.button(
										Properties.id("btn2"),
										Properties.textContent("Button 2")
								)
						)
				)
		);

		NodeFactory anchorPaneTarget = SAnchorPane.anchorPane(
				Properties.items(
						SAnchorPane.anchorPaneItem(
								SButton.button(
										Properties.id("btn2"),
										Properties.textContent("Button 2")
								)
						),
						SAnchorPane.anchorPaneItem(
								SButton.button(
										Properties.id("btn1"),
										Properties.textContent("Button 1")
								)
						)
				)
		);

		SceneContext context = new SceneContext(state, anchorPane, null);
		SNode original = context.getRootNode();
		SNode target = anchorPaneTarget.create(state);

		SNode mutatedNode = context.getMutator().mutate(original, target);
		assertThat(mutatedNode).isEqualTo(original);

		TestUtils.assertNode(mutatedNode, SAnchorPane.class);

		final List<SNode> children = mutatedNode.getChildren();
		assertThat(children).isNotNull();
		assertThat(children).hasSize(2);
		assertThat(children).doesNotContainNull();

		assertChildButton(mutatedNode, 0, "btn2", "Button 2");
		assertChildButton(mutatedNode, 1, "btn1", "Button 1");
	}




	private void assertChildButton(SNode anchorPaneNode, int index, String id, String text) {

		final List<SNode> children = anchorPaneNode.getChildren();

		final SNode child = children.get(index);
		TestUtils.assertNode(child, SAnchorPane.AnchorPaneChildItem.class);
		assertThat(child.getChildren()).isNotNull();
		assertThat(child.getChildren()).hasSize(1);
		assertThat(child.getChildren()).doesNotContainNull();

		final SNode childButton = child.getChildren().get(0);
		PropertyTestUtils.assertIdProperty(childButton, id);
		PropertyTestUtils.assertTextContentProperty(childButton, text);
	}




	@Test
	public void testFxNode() {

		final TestState state = new TestState();

		NodeFactory anchorPane = SAnchorPane.anchorPane(
				Properties.id("myAnchorPane"),
				Properties.minSize(1.0, 2.0),
				Properties.preferredSize(3.0, 4.0),
				Properties.maxSize(5.0, 6.0),
				Properties.size(10.0, 11.0, 12.0, 13.0, 14.0, 15.0),
				Properties.disabled(true),
				Properties.items(
						SAnchorPane.anchorPaneItem(
								SButton.button(
										Properties.id("btn"),
										Properties.textContent("Child Button 1")
								),
								Properties.anchor(20, null, null, 50)
						),
						SAnchorPane.anchorPaneItem(
								SButton.button(
										Properties.id("btn"),
										Properties.textContent("Child Button 2")
								),
								Properties.anchor(null, 50, null, 100)
						)
				)
		);

		SceneContext context = new SceneContext(state, anchorPane, null);
		SNode node = context.getRootNode();

		FxTestUtils.assertAnchorPane((AnchorPane) node.getFxNode(), FxTestUtils.AnchorPaneInfo.builder()
				.minWidth(1.0).minHeight(2.0)
				.prefWidth(3.0).prefHeight(4.0)
				.maxWidth(5.0).maxHeight(6.0)
				.disabled(true)
				.children(List.of(
						FxTestUtils.AnchorPaneChildButtonInfo.builder()
								.text("Child Button 1")
								.anchorTop(20)
								.anchorBottom(null)
								.anchorLeft(null)
								.anchorRight(50)
								.build(),
						FxTestUtils.AnchorPaneChildButtonInfo.builder()
								.text("Child Button 2")
								.anchorTop(null)
								.anchorBottom(50)
								.anchorLeft(null)
								.anchorRight(100)
								.build()
				))
				.build());


	}

}
