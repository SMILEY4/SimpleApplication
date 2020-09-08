//package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;
//
//
//import de.ruegnerlukas.simpleapplication.simpleui.TestState;
//import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Properties;
//import de.ruegnerlukas.simpleapplication.simpleui.core.SuiSceneController;
//import de.ruegnerlukas.simpleapplication.simpleui.core.builders.NodeFactory;
//import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiBaseNode;
//import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
//import de.ruegnerlukas.simpleapplication.simpleui.utils.FxTestUtils;
//import de.ruegnerlukas.simpleapplication.simpleui.utils.PropertyTestUtils;
//import de.ruegnerlukas.simpleapplication.simpleui.utils.TestUtils;
//import javafx.scene.layout.AnchorPane;
//import javafx.stage.Stage;
//import org.junit.Test;
//import org.testfx.framework.junit.ApplicationTest;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//public class SuiAnchorPaneTest extends ApplicationTest {
//
//
//	@Override
//	public void start(Stage stage) {
//		SuiRegistry.initialize();
//	}
//
//
//
//
//	@Test
//	public void testAnchorPaneSNode() {
//
//		final TestState state = new TestState();
//
//		NodeFactory anchorPane = SuiAnchorPane.anchorPane(
//				Properties.id("myAnchorPane"),
//				Properties.minSize(1.0, 2.0),
//				Properties.preferredSize(3.0, 4.0),
//				Properties.maxSize(5.0, 6.0),
//				Properties.size(10.0, 11.0, 12.0, 13.0, 14.0, 15.0),
//				Properties.disabled(true),
//				Properties.style("-fx-background-color: red"),
//				Properties.items(
//						SuiAnchorPaneItem.anchorPaneItem(
//								SuiButton.button(
//										Properties.id("btn1"),
//										Properties.textContent("Child Button 1")
//								),
//								Properties.anchor(10, null, null, 50)
//						),
//						SuiAnchorPaneItem.anchorPaneItem(
//								SuiButton.button(
//										Properties.id("btn2"),
//										Properties.textContent("Child Button 2")
//								),
//								Properties.anchor(20, null, null, 50)
//						)
//				)
//		);
//
//		final SuiBaseNode node = anchorPane.create(state);
//
//		TestUtils.assertNode(node, SuiAnchorPane.class);
//		PropertyTestUtils.assertIdProperty(node, "myAnchorPane");
//		PropertyTestUtils.assertSizeMinProperty(node, 1.0, 2.0);
//		PropertyTestUtils.assertSizePreferredProperty(node, 3.0, 4.0);
//		PropertyTestUtils.assertSizeMaxProperty(node, 5.0, 6.0);
//		PropertyTestUtils.assertSizeProperty(node, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0);
//		PropertyTestUtils.assertDisabledProperty(node, true);
//		PropertyTestUtils.assertStyle(node, "-fx-background-color: red");
//
//		final List<SuiBaseNode> children = node.getChildrenUnmodifiable();
//		assertThat(children).isNotNull();
//		assertThat(children).hasSize(2);
//		assertThat(children).doesNotContainNull();
//
//		final SuiBaseNode child1 = children.get(0);
//		TestUtils.assertNode(child1, SuiAnchorPaneItem.class);
//		PropertyTestUtils.assertAnchorProperty(child1, 10, null, null, 50);
//		assertThat(child1.getChildrenUnmodifiable()).isNotNull();
//		assertThat(child1.getChildrenUnmodifiable()).hasSize(1);
//		assertThat(child1.getChildrenUnmodifiable()).doesNotContainNull();
//
//		final SuiBaseNode child1Button = child1.getChildrenUnmodifiable().get(0);
//		PropertyTestUtils.assertIdProperty(child1Button, "btn1");
//		PropertyTestUtils.assertTextContentProperty(child1Button, "Child Button 1");
//
//		final SuiBaseNode child2 = children.get(0);
//		TestUtils.assertNode(child2, SuiAnchorPaneItem.class);
//		PropertyTestUtils.assertAnchorProperty(child2, 10, null, null, 50);
//		assertThat(child2.getChildrenUnmodifiable()).isNotNull();
//		assertThat(child2.getChildrenUnmodifiable()).hasSize(1);
//		assertThat(child2.getChildrenUnmodifiable()).doesNotContainNull();
//
//		final SuiBaseNode child2Button = child2.getChildrenUnmodifiable().get(0);
//		PropertyTestUtils.assertIdProperty(child2Button, "btn1");
//		PropertyTestUtils.assertTextContentProperty(child2Button, "Child Button 1");
//
//	}
//
//
//
//
//	@Test
//	public void testMutate() {
//
//		final TestState state = new TestState();
//
//		NodeFactory anchorPane = SuiAnchorPane.anchorPane(
//				Properties.id("myAnchorPane"),
//				Properties.minSize(1.0, 2.0),
//				Properties.preferredSize(3.0, 4.0),
//				Properties.maxSize(5.0, 6.0),
//				Properties.size(10.0, 11.0, 12.0, 13.0, 14.0, 15.0),
//				Properties.disabled(true),
//				Properties.style("-fx-background-color: red"),
//				Properties.items(
//						SuiAnchorPaneItem.anchorPaneItem(
//								SuiButton.button(
//										Properties.id("btn"),
//										Properties.textContent("Child Button")
//								),
//								Properties.anchor(20, null, null, 50)
//						)
//				)
//		);
//
//		NodeFactory anchorPaneTarget = SuiAnchorPane.anchorPane(
//				Properties.id("myAnchorPane"),
//				Properties.minSize(1.0, 2.0),
//				Properties.preferredSize(3000.0, 4000.0),
//				Properties.maxSize(5.0, 6.0),
//				Properties.size(10.0, 11.0, 12.0, 13.0, 14.0, 15.0),
//				Properties.disabled(false),
//				Properties.style("-fx-background-color: blue"),
//				Properties.items(
//						SuiAnchorPaneItem.anchorPaneItem(
//								SuiButton.button(
//										Properties.id("btn"),
//										Properties.textContent("New Button")
//								),
//								Properties.anchor(500, 100, null, null)
//						)
//				)
//		);
//
//		SuiSceneController context = new SuiSceneController(state, anchorPane);
//		SuiBaseNode original = context.getRootNode();
//		SuiBaseNode target = anchorPaneTarget.create(state);
//
//		SuiBaseNode mutatedNode = context.getMasterNodeHandlers().getMutator().mutate(original, target);
//		assertThat(mutatedNode).isEqualTo(original);
//
//		TestUtils.assertNode(mutatedNode, SuiAnchorPane.class);
//		PropertyTestUtils.assertIdProperty(mutatedNode, "myAnchorPane");
//		PropertyTestUtils.assertSizeMinProperty(mutatedNode, 1.0, 2.0);
//		PropertyTestUtils.assertSizePreferredProperty(mutatedNode, 3000.0, 4000.0);
//		PropertyTestUtils.assertSizeMaxProperty(mutatedNode, 5.0, 6.0);
//		PropertyTestUtils.assertSizeProperty(mutatedNode, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0);
//		PropertyTestUtils.assertDisabledProperty(mutatedNode, false);
//		PropertyTestUtils.assertStyle(mutatedNode, "-fx-background-color: blue");
//
//		final List<SuiBaseNode> children = mutatedNode.getChildrenUnmodifiable();
//		assertThat(children).isNotNull();
//		assertThat(children).hasSize(1);
//		assertThat(children).doesNotContainNull();
//
//		final SuiBaseNode child1 = children.get(0);
//		TestUtils.assertNode(child1, SuiAnchorPaneItem.class);
//		PropertyTestUtils.assertAnchorProperty(child1, 500, 100, null, null);
//		assertThat(child1.getChildrenUnmodifiable()).isNotNull();
//		assertThat(child1.getChildrenUnmodifiable()).hasSize(1);
//		assertThat(child1.getChildrenUnmodifiable()).doesNotContainNull();
//
//		final SuiBaseNode child1Button = child1.getChildrenUnmodifiable().get(0);
//		PropertyTestUtils.assertIdProperty(child1Button, "btn");
//		PropertyTestUtils.assertTextContentProperty(child1Button, "New Button");
//	}
//
//
//
//
//	@Test
//	public void testMutateAddChild() {
//
//		final TestState state = new TestState();
//
//		NodeFactory anchorPane = SuiAnchorPane.anchorPane(
//				Properties.items(
//						SuiAnchorPaneItem.anchorPaneItem(
//								SuiButton.button(
//										Properties.id("btn1"),
//										Properties.textContent("Button 1")
//								)
//						)
//				)
//		);
//
//		NodeFactory anchorPaneTarget = SuiAnchorPane.anchorPane(
//				Properties.items(
//						SuiAnchorPaneItem.anchorPaneItem(
//								SuiButton.button(
//										Properties.id("btn1"),
//										Properties.textContent("Button 1")
//								)
//						),
//						SuiAnchorPaneItem.anchorPaneItem(
//								SuiButton.button(
//										Properties.id("btn2"),
//										Properties.textContent("Button 2")
//								)
//						)
//				)
//		);
//
//		SuiSceneController context = new SuiSceneController(state, anchorPane);
//		SuiBaseNode original = context.getRootNode();
//		SuiBaseNode target = anchorPaneTarget.create(state);
//
//		SuiBaseNode mutatedNode = context.getMasterNodeHandlers().getMutator().mutate(original, target);
//		assertThat(mutatedNode).isEqualTo(original);
//
//		TestUtils.assertNode(mutatedNode, SuiAnchorPane.class);
//
//		final List<SuiBaseNode> children = mutatedNode.getChildrenUnmodifiable();
//		assertThat(children).isNotNull();
//		assertThat(children).hasSize(2);
//		assertThat(children).doesNotContainNull();
//		assertThat(children.get(0)).isEqualTo(original.getChildrenUnmodifiable().get(0));
//
//		assertChildButton(mutatedNode, 0, "btn1", "Button 1");
//		assertChildButton(mutatedNode, 1, "btn2", "Button 2");
//	}
//
//
//
//
//	@Test
//	public void testMutateCreateChild() {
//
//		final TestState state = new TestState();
//
//		NodeFactory anchorPane = SuiAnchorPane.anchorPane(
//				Properties.items(
//				)
//		);
//
//		NodeFactory anchorPaneTarget = SuiAnchorPane.anchorPane(
//				Properties.items(
//						SuiAnchorPaneItem.anchorPaneItem(
//								SuiButton.button(
//										Properties.id("btn"),
//										Properties.textContent("Button")
//								)
//						)
//				)
//		);
//
//		SuiSceneController context = new SuiSceneController(state, anchorPane);
//		SuiBaseNode original = context.getRootNode();
//		SuiBaseNode target = anchorPaneTarget.create(state);
//
//		SuiBaseNode mutatedNode = context.getMasterNodeHandlers().getMutator().mutate(original, target);
//		assertThat(mutatedNode).isEqualTo(original);
//
//		TestUtils.assertNode(mutatedNode, SuiAnchorPane.class);
//
//		final List<SuiBaseNode> children = mutatedNode.getChildrenUnmodifiable();
//		assertThat(children).isNotNull();
//		assertThat(children).hasSize(1);
//		assertThat(children).doesNotContainNull();
//
//		assertChildButton(mutatedNode, 0, "btn", "Button");
//	}
//
//
//
//
//	@Test
//	public void testMutateClearChildren() {
//
//		final TestState state = new TestState();
//
//		NodeFactory anchorPane = SuiAnchorPane.anchorPane(
//				Properties.items(
//						SuiAnchorPaneItem.anchorPaneItem(
//								SuiButton.button(
//										Properties.id("btn1"),
//										Properties.textContent("Button 1")
//								)
//						),
//						SuiAnchorPaneItem.anchorPaneItem(
//								SuiButton.button(
//										Properties.id("btn2"),
//										Properties.textContent("Button 2")
//								)
//						)
//				)
//		);
//
//		NodeFactory anchorPaneTarget = SuiAnchorPane.anchorPane(
//				Properties.items(
//				)
//		);
//
//		SuiSceneController context = new SuiSceneController(state, anchorPane);
//		SuiBaseNode original = context.getRootNode();
//		SuiBaseNode target = anchorPaneTarget.create(state);
//
//		SuiBaseNode mutatedNode = context.getMasterNodeHandlers().getMutator().mutate(original, target);
//		assertThat(mutatedNode).isEqualTo(original);
//
//		TestUtils.assertNode(mutatedNode, SuiAnchorPane.class);
//
//		final List<SuiBaseNode> children = mutatedNode.getChildrenUnmodifiable();
//		assertThat(children).isNotNull();
//		assertThat(children).hasSize(0);
//
//	}
//
//
//
//
//	@Test
//	public void testMutateRemoveChild1() {
//
//		final TestState state = new TestState();
//
//		NodeFactory anchorPane = SuiAnchorPane.anchorPane(
//				Properties.items(
//						SuiAnchorPaneItem.anchorPaneItem(
//								SuiButton.button(
//										Properties.id("btn1"),
//										Properties.textContent("Button 1")
//								)
//						),
//						SuiAnchorPaneItem.anchorPaneItem(
//								SuiButton.button(
//										Properties.id("btn2"),
//										Properties.textContent("Button 2")
//								)
//						)
//				)
//		);
//
//		NodeFactory anchorPaneTarget = SuiAnchorPane.anchorPane(
//				Properties.items(
//						SuiAnchorPaneItem.anchorPaneItem(
//								SuiButton.button(
//										Properties.id("btn2"),
//										Properties.textContent("Button 2")
//								)
//						)
//				)
//		);
//
//		SuiSceneController context = new SuiSceneController(state, anchorPane);
//		SuiBaseNode original = context.getRootNode();
//		SuiBaseNode target = anchorPaneTarget.create(state);
//
//		SuiBaseNode mutatedNode = context.getMasterNodeHandlers().getMutator().mutate(original, target);
//		assertThat(mutatedNode).isEqualTo(original);
//
//		TestUtils.assertNode(mutatedNode, SuiAnchorPane.class);
//
//		final List<SuiBaseNode> children = mutatedNode.getChildrenUnmodifiable();
//		assertThat(children).isNotNull();
//		assertThat(children).hasSize(1);
//		assertThat(children).doesNotContainNull();
//
//		assertChildButton(mutatedNode, 0, "btn2", "Button 2");
//	}
//
//
//
//
//	@Test
//	public void testMutateRemoveChild2() {
//
//		final TestState state = new TestState();
//
//		NodeFactory anchorPane = SuiAnchorPane.anchorPane(
//				Properties.items(
//						SuiAnchorPaneItem.anchorPaneItem(
//								SuiButton.button(
//										Properties.id("btn1"),
//										Properties.textContent("Button 1")
//								)
//						),
//						SuiAnchorPaneItem.anchorPaneItem(
//								SuiButton.button(
//										Properties.id("btn2"),
//										Properties.textContent("Button 2")
//								)
//						)
//				)
//		);
//
//		NodeFactory anchorPaneTarget = SuiAnchorPane.anchorPane(
//				Properties.items(
//						SuiAnchorPaneItem.anchorPaneItem(
//								SuiButton.button(
//										Properties.id("btn1"),
//										Properties.textContent("Button 1")
//								)
//						)
//				)
//		);
//
//		SuiSceneController context = new SuiSceneController(state, anchorPane);
//		SuiBaseNode original = context.getRootNode();
//		SuiBaseNode target = anchorPaneTarget.create(state);
//
//		SuiBaseNode mutatedNode = context.getMasterNodeHandlers().getMutator().mutate(original, target);
//		assertThat(mutatedNode).isEqualTo(original);
//
//		TestUtils.assertNode(mutatedNode, SuiAnchorPane.class);
//
//		final List<SuiBaseNode> children = mutatedNode.getChildrenUnmodifiable();
//		assertThat(children).isNotNull();
//		assertThat(children).hasSize(1);
//		assertThat(children).doesNotContainNull();
//
//		assertChildButton(mutatedNode, 0, "btn1", "Button 1");
//	}
//
//
//
//
//	@Test
//	public void testMutateSwitchChildren() {
//
//		final TestState state = new TestState();
//
//		NodeFactory anchorPane = SuiAnchorPane.anchorPane(
//				Properties.items(
//						SuiAnchorPaneItem.anchorPaneItem(
//								SuiButton.button(
//										Properties.id("btn1"),
//										Properties.textContent("Button 1")
//								)
//						),
//						SuiAnchorPaneItem.anchorPaneItem(
//								SuiButton.button(
//										Properties.id("btn2"),
//										Properties.textContent("Button 2")
//								)
//						)
//				)
//		);
//
//		NodeFactory anchorPaneTarget = SuiAnchorPane.anchorPane(
//				Properties.items(
//						SuiAnchorPaneItem.anchorPaneItem(
//								SuiButton.button(
//										Properties.id("btn2"),
//										Properties.textContent("Button 2")
//								)
//						),
//						SuiAnchorPaneItem.anchorPaneItem(
//								SuiButton.button(
//										Properties.id("btn1"),
//										Properties.textContent("Button 1")
//								)
//						)
//				)
//		);
//
//		SuiSceneController context = new SuiSceneController(state, anchorPane);
//		SuiBaseNode original = context.getRootNode();
//		SuiBaseNode target = anchorPaneTarget.create(state);
//
//		SuiBaseNode mutatedNode = context.getMasterNodeHandlers().getMutator().mutate(original, target);
//		assertThat(mutatedNode).isEqualTo(original);
//
//		TestUtils.assertNode(mutatedNode, SuiAnchorPane.class);
//
//		final List<SuiBaseNode> children = mutatedNode.getChildrenUnmodifiable();
//		assertThat(children).isNotNull();
//		assertThat(children).hasSize(2);
//		assertThat(children).doesNotContainNull();
//
//		assertChildButton(mutatedNode, 0, "btn2", "Button 2");
//		assertChildButton(mutatedNode, 1, "btn1", "Button 1");
//	}
//
//
//
//
//	private void assertChildButton(SuiBaseNode anchorPaneNode, int index, String id, String text) {
//
//		final List<SuiBaseNode> children = anchorPaneNode.getChildrenUnmodifiable();
//
//		final SuiBaseNode child = children.get(index);
//		TestUtils.assertNode(child, SuiAnchorPaneItem.class);
//		assertThat(child.getChildrenUnmodifiable()).isNotNull();
//		assertThat(child.getChildrenUnmodifiable()).hasSize(1);
//		assertThat(child.getChildrenUnmodifiable()).doesNotContainNull();
//
//		final SuiBaseNode childButton = child.getChildrenUnmodifiable().get(0);
//		PropertyTestUtils.assertIdProperty(childButton, id);
//		PropertyTestUtils.assertTextContentProperty(childButton, text);
//	}
//
//
//
//
//	@Test
//	public void testFxNode() {
//
//		final TestState state = new TestState();
//
//		NodeFactory anchorPane = SuiAnchorPane.anchorPane(
//				Properties.id("myAnchorPane"),
//				Properties.minSize(1.0, 2.0),
//				Properties.preferredSize(3.0, 4.0),
//				Properties.maxSize(5.0, 6.0),
//				Properties.size(10.0, 11.0, 12.0, 13.0, 14.0, 15.0),
//				Properties.disabled(true),
//				Properties.style("-fx-background-color: red"),
//				Properties.items(
//						SuiAnchorPaneItem.anchorPaneItem(
//								SuiButton.button(
//										Properties.id("btn"),
//										Properties.textContent("Child Button 1")
//								),
//								Properties.anchor(20, null, null, 50)
//						),
//						SuiAnchorPaneItem.anchorPaneItem(
//								SuiButton.button(
//										Properties.id("btn"),
//										Properties.textContent("Child Button 2")
//								),
//								Properties.anchor(null, 50, null, 100)
//						)
//				)
//		);
//
//		SuiSceneController context = new SuiSceneController(state, anchorPane);
//		SuiBaseNode node = context.getRootNode();
//
//		FxTestUtils.assertAnchorPane((AnchorPane) node.getFxNode(), FxTestUtils.AnchorPaneInfo.builder()
//				.minWidth(1.0).minHeight(2.0)
//				.prefWidth(3.0).prefHeight(4.0)
//				.maxWidth(5.0).maxHeight(6.0)
//				.disabled(true)
//				.style("-fx-background-color: red")
//				.children(List.of(
//						FxTestUtils.AnchorPaneChildButtonInfo.builder()
//								.text("Child Button 1")
//								.anchorTop(20)
//								.anchorBottom(null)
//								.anchorLeft(null)
//								.anchorRight(50)
//								.build(),
//						FxTestUtils.AnchorPaneChildButtonInfo.builder()
//								.text("Child Button 2")
//								.anchorTop(null)
//								.anchorBottom(50)
//								.anchorLeft(null)
//								.anchorRight(100)
//								.build()
//				))
//				.build());
//
//
//	}
//
//}
