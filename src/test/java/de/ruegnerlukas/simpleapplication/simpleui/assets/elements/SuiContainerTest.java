//package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;
//
//
//import de.ruegnerlukas.simpleapplication.simpleui.testutils.TestState;
//import de.ruegnerlukas.simpleapplication.simpleui.core.SuiSceneController;
//import de.ruegnerlukas.simpleapplication.simpleui.core.builders.NodeFactory;
//import de.ruegnerlukas.simpleapplication.simpleui.core.SuiNode;
//import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Properties;
//import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
//import de.ruegnerlukas.simpleapplication.simpleui.utils.PropertyTestUtils;
//import de.ruegnerlukas.simpleapplication.simpleui.utils.TestUtils;
//import javafx.scene.Node;
//import javafx.scene.layout.Pane;
//import javafx.stage.Stage;
//import org.junit.Test;
//import org.testfx.framework.junit.ApplicationTest;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//public class SuiContainerTest extends ApplicationTest {
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
//		NodeFactory container = SuiContainer.container(
//				Properties.id("myContainer"),
//				Properties.minSize(100.0, 200.0),
//				Properties.preferredSize(300.0, 400.0),
//				Properties.maxSize(500.0, 600.0),
//				Properties.layout("myLayout", ((parent, nodes) -> {
//					final double width = parent.getWidth();
//					final double height = parent.getHeight();
//					final Node node0 = nodes.get(0);
//					final Node node1 = nodes.get(1);
//					node0.resizeRelocate(0, 0, width, height / 2);
//					node1.resizeRelocate(0, height / 2, width, height / 2);
//				})),
//				Properties.items(
//						SuiButton.button(
//								Properties.id("btn1"),
//								Properties.textContent("Child Button 1")
//						),
//						SuiButton.button(
//								Properties.id("btn2"),
//								Properties.textContent("Child Button 2")
//						)
//				)
//		);
//
//		final SuiNode node = container.create(state);
//
//		TestUtils.assertNode(node, SuiContainer.class);
//		PropertyTestUtils.assertIdProperty(node, "myContainer");
//		PropertyTestUtils.assertSizeMinProperty(node, 100.0, 200.0);
//		PropertyTestUtils.assertSizePreferredProperty(node, 300.0, 400.0);
//		PropertyTestUtils.assertSizeMaxProperty(node, 500.0, 600.0);
//
//		final List<SuiNode> children = node.getChildrenUnmodifiable();
//		assertThat(children).isNotNull();
//		assertThat(children).hasSize(2);
//		assertThat(children).doesNotContainNull();
//
//		final SuiNode child1Button = children.get(0);
//		PropertyTestUtils.assertIdProperty(child1Button, "btn1");
//		PropertyTestUtils.assertTextContentProperty(child1Button, "Child Button 1");
//
//		final SuiNode child2Button = children.get(1);
//		PropertyTestUtils.assertIdProperty(child2Button, "btn2");
//		PropertyTestUtils.assertTextContentProperty(child2Button, "Child Button 2");
//
//	}
//
////
////
////
////	@Test
////	public void testMutate() {
////
////		final ElementTestState state = new ElementTestState();
////
////		NodeFactory anchorPane = SUIAnchorPane.anchorPane(
////				Properties.id("myAnchorPane"),
////				Properties.minSize(1.0, 2.0),
////				Properties.preferredSize(3.0, 4.0),
////				Properties.maxSize(5.0, 6.0),
////				Properties.size(10.0, 11.0, 12.0, 13.0, 14.0, 15.0),
////				Properties.disabled(true),
////				Properties.style("-fx-background-color: red"),
////				Properties.items(
////						SUIAnchorPane.anchorPaneItem(
////								SUIButton.button(
////										Properties.id("btn"),
////										Properties.textContent("Child Button")
////								),
////								Properties.anchor(20, null, null, 50)
////						)
////				)
////		);
////
////		NodeFactory anchorPaneTarget = SUIAnchorPane.anchorPane(
////				Properties.id("myAnchorPane"),
////				Properties.minSize(1.0, 2.0),
////				Properties.preferredSize(3000.0, 4000.0),
////				Properties.maxSize(5.0, 6.0),
////				Properties.size(10.0, 11.0, 12.0, 13.0, 14.0, 15.0),
////				Properties.disabled(false),
////				Properties.style("-fx-background-color: blue"),
////				Properties.items(
////						SUIAnchorPane.anchorPaneItem(
////								SUIButton.button(
////										Properties.id("btn"),
////										Properties.textContent("New Button")
////								),
////								Properties.anchor(500, 100, null, null)
////						)
////				)
////		);
////
////		SUISceneContext context = new SUISceneContext(state, anchorPane);
////		SUINode original = context.getRootNode();
////		SUINode target = anchorPaneTarget.create(state);
////
////		SUINode mutatedNode = context.getMasterNodeHandlers().getMutator().mutate(original, target);
////		assertThat(mutatedNode).isEqualTo(original);
////
////		TestUtils.assertNode(mutatedNode, SUIAnchorPane.class);
////		PropertyTestUtils.assertIdProperty(mutatedNode, "myAnchorPane");
////		PropertyTestUtils.assertSizeMinProperty(mutatedNode, 1.0, 2.0);
////		PropertyTestUtils.assertSizePreferredProperty(mutatedNode, 3000.0, 4000.0);
////		PropertyTestUtils.assertSizeMaxProperty(mutatedNode, 5.0, 6.0);
////		PropertyTestUtils.assertSizeProperty(mutatedNode, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0);
////		PropertyTestUtils.assertDisabledProperty(mutatedNode, false);
////		PropertyTestUtils.assertStyle(mutatedNode, "-fx-background-color: blue");
////
////		final List<SUINode> children = mutatedNode.getChildrenUnmodifiable();
////		assertThat(children).isNotNull();
////		assertThat(children).hasSize(1);
////		assertThat(children).doesNotContainNull();
////
////		final SUINode child1 = children.get(0);
////		TestUtils.assertNode(child1, SUIAnchorPane.AnchorPaneChildItem.class);
////		PropertyTestUtils.assertAnchorProperty(child1, 500, 100, null, null);
////		assertThat(child1.getChildrenUnmodifiable()).isNotNull();
////		assertThat(child1.getChildrenUnmodifiable()).hasSize(1);
////		assertThat(child1.getChildrenUnmodifiable()).doesNotContainNull();
////
////		final SUINode child1Button = child1.getChildrenUnmodifiable().get(0);
////		PropertyTestUtils.assertIdProperty(child1Button, "btn");
////		PropertyTestUtils.assertTextContentProperty(child1Button, "New Button");
////	}
//
//
//
//	@Test
//	public void testFxNode() {
//
//		final TestState state = new TestState();
//
//		NodeFactory container = SuiContainer.container(
//				Properties.id("myContainer"),
//				Properties.minSize(100.0, 200.0),
//				Properties.preferredSize(300.0, 400.0),
//				Properties.maxSize(500.0, 600.0),
//				Properties.layout("myLayout", ((parent, nodes) -> {
//					final double width = parent.getWidth();
//					final double height = parent.getHeight();
//					final Node node0 = nodes.get(0);
//					final Node node1 = nodes.get(1);
//					node0.resizeRelocate(0, 0, width, height / 2);
//					node1.resizeRelocate(0, height / 2, width, height / 2);
//				})),
//				Properties.items(
//						SuiButton.button(
//								Properties.id("btn1"),
//								Properties.textContent("Child Button 1")
//						),
//						SuiButton.button(
//								Properties.id("btn2"),
//								Properties.textContent("Child Button 2")
//						)
//				)
//		);
//
//		SuiSceneController context = new SuiSceneController(state, container);
//		SuiNode node = context.getRootNode();
//
//		Node fxNode = node.getFxNode();
//		assertThat(fxNode instanceof Pane).isTrue();
//
//		Pane fxPane = (Pane)fxNode;
//		assertThat(fxPane.getChildren()).hasSize(2);
//
//	}
//
//}
