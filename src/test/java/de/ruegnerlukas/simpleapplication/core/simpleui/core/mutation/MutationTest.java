package de.ruegnerlukas.simpleapplication.core.simpleui.core.mutation;

import de.ruegnerlukas.simpleapplication.common.utils.Pair;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.SuiElements;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.SuiButton;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.SuiVBox;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.events.OnActionEventProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.MutationBehaviourProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.SizeProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.TextContentProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.SuiServices;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.NodeFactory;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.registry.SuiRegistry;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.tags.Tags;
import de.ruegnerlukas.simpleapplication.core.simpleui.testutils.PropertyTestUtils;
import de.ruegnerlukas.simpleapplication.core.simpleui.testutils.TestState;
import de.ruegnerlukas.simpleapplication.core.simpleui.testutils.TestUtils;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.Test;
import org.mockito.Mockito;
import org.testfx.framework.junit.ApplicationTest;

import java.util.ArrayList;
import java.util.List;

import static de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.MutationBehaviourProperty.MutationBehaviour.DEFAULT;
import static de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.MutationBehaviourProperty.MutationBehaviour.STATIC;
import static de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.MutationBehaviourProperty.MutationBehaviour.STATIC_NODE;
import static de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.MutationBehaviourProperty.MutationBehaviour.STATIC_SUBTREE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class MutationTest extends ApplicationTest {


	@Override
	public void start(Stage stage) {
		SuiRegistry.initialize();
	}




	@Test
	public void test_mutate_expect_alignment_changed_and_child_removed_and_child_text_changed() {

		final TestState state = new TestState();

		NodeFactory vbox = SuiElements.vBox()
				.id("myVBox")
				.alignment(Pos.CENTER)
				.items(
						SuiElements.button()
								.id("btn1")
								.textContent("Child Button 1"),
						SuiElements.button()
								.id("btn2")
								.textContent("Child Button 2")
				);

		NodeFactory vboxTarget = SuiElements.vBox()
				.id("myVBox")
				.alignment(Pos.TOP_RIGHT)
				.items(
						SuiElements.button()
								.id("btn2")
								.textContent("Renamed Child Button 2")
				);

		final SuiNode original = vbox.create(state, Tags.empty());
		SuiServices.get().enrichWithFxNodes(original);
		final SuiNode target = vboxTarget.create(state, null);
		final SuiNode mutatedNode = SuiServices.get().mutateNode(original, target, Tags.empty());

		PropertyTestUtils.assertAlignment(mutatedNode, Pos.TOP_RIGHT);

		final List<SuiNode> children = mutatedNode.getChildNodeStore().getUnmodifiable();
		assertThat(children).isNotNull();
		assertThat(children).hasSize(1);
		assertThat(children).doesNotContainNull();

		final SuiNode childButton = children.get(0);
		PropertyTestUtils.assertIdProperty(childButton, "btn2");
		PropertyTestUtils.assertTextContentProperty(childButton, "Renamed Child Button 2");
	}




	@Test
	public void test_mutate_node_type_expect_rebuild() {

		final TestState state = new TestState();

		NodeFactory factoryOriginal = SuiElements.vBox()
				.items(SuiElements.button().textContent("Child Button"));

		NodeFactory factoryTarget = SuiElements.button().textContent("My Button");

		final SuiNode original = factoryOriginal.create(state, Tags.empty());
		SuiServices.get().enrichWithFxNodes(original);
		final SuiNode target = factoryTarget.create(state, null);
		final SuiNode mutatedNode = SuiServices.get().mutateNode(original, target, Tags.empty());

		TestUtils.assertNode(mutatedNode, SuiButton.class);
		PropertyTestUtils.assertTextContentProperty(mutatedNode, "My Button");
		assertThat(mutatedNode).isNotEqualTo(original);
		assertThat(mutatedNode).isEqualTo(target);
		assertThat(mutatedNode.getFxNodeStore().get()).isNotNull();

	}




	@Test
	public void test_remove_size_property_expect_rebuild() {

		final TestState state = new TestState();

		NodeFactory factoryOriginal = SuiElements.vBox()
				.size(0, 0, 5, 5, 10, 10)
				.items(SuiElements.button().textContent("Child Button"));

		NodeFactory factoryTarget = SuiElements.vBox()
				.items(SuiElements.button().textContent("Child Button"));

		final SuiNode original = factoryOriginal.create(state, Tags.empty());
		SuiServices.get().enrichWithFxNodes(original);
		final SuiNode target = factoryTarget.create(state, null);
		final SuiNode mutatedNode = SuiServices.get().mutateNode(original, target, Tags.empty());

		TestUtils.assertNode(mutatedNode, SuiVBox.class);
		assertThat(mutatedNode.getPropertyStore().has(SizeProperty.class)).isFalse();
		assertThat(mutatedNode).isNotEqualTo(original);
		assertThat(mutatedNode).isEqualTo(target);
		assertThat(mutatedNode.getFxNodeStore().get()).isNotNull();
	}




	@Test
	public void test_remove_property_expect_mutation_and_property_removed() {

		final TestState state = new TestState();

		NodeFactory factoryOriginal = SuiElements.button()
				.id("myButton")
				.textContent("Button");

		NodeFactory factoryTarget = SuiElements.button()
				.id("myButton");

		final SuiNode original = factoryOriginal.create(state, Tags.empty());
		SuiServices.get().enrichWithFxNodes(original);
		final SuiNode target = factoryTarget.create(state, null);
		final SuiNode mutatedNode = SuiServices.get().mutateNode(original, target, Tags.empty());

		TestUtils.assertNode(mutatedNode, SuiButton.class);
		assertThat(mutatedNode.getPropertyStore().has(TextContentProperty.class)).isFalse();
		assertThat(mutatedNode).isEqualTo(original);
		assertThat(mutatedNode).isNotEqualTo(target);
		assertThat(mutatedNode.getFxNodeStore().get()).isNotNull();
		assertThat(((Button) mutatedNode.getFxNodeStore().get()).getText()).isEqualTo("");
	}




	@Test
	public void test_add_property_expect_mutation_and_property_added() {

		final TestState state = new TestState();

		NodeFactory factoryOriginal = SuiElements.button()
				.id("myButton");

		NodeFactory factoryTarget = SuiElements.button()
				.id("myButton")
				.textContent("Button");

		final SuiNode original = factoryOriginal.create(state, Tags.empty());
		SuiServices.get().enrichWithFxNodes(original);
		final SuiNode target = factoryTarget.create(state, null);
		final SuiNode mutatedNode = SuiServices.get().mutateNode(original, target, Tags.empty());

		TestUtils.assertNode(mutatedNode, SuiButton.class);
		assertThat(mutatedNode).isEqualTo(original);
		assertThat(mutatedNode).isNotEqualTo(target);
		PropertyTestUtils.assertTextContentProperty(mutatedNode, "Button");
		assertThat(mutatedNode.getFxNodeStore().get()).isNotNull();
		assertThat(((Button) mutatedNode.getFxNodeStore().get()).getText()).isEqualTo("Button");
	}




	@Test
	public void test_mutate_with_default_behaviour_expect_everything_changed() {

		final TestState state = new TestState();

		NodeFactory vbox = SuiElements.vBox()
				.id("myVBox")
				.alignment(Pos.CENTER)
				.mutationBehaviour(DEFAULT)
				.items(
						SuiElements.button()
								.id("btn1")
								.textContent("Child Button 1"),
						SuiElements.button()
								.id("btn2")
								.textContent("Child Button 2")
				);

		NodeFactory vboxTarget = SuiElements.vBox()
				.id("myVBox")
				.alignment(Pos.TOP_RIGHT)
				.mutationBehaviour(DEFAULT)
				.items(
						SuiElements.button()
								.id("btn2")
								.textContent("Renamed Child Button 2")
				);

		final SuiNode original = vbox.create(state, Tags.empty());
		SuiServices.get().enrichWithFxNodes(original);
		final SuiNode target = vboxTarget.create(state, null);
		final SuiNode mutatedNode = SuiServices.get().mutateNode(original, target, Tags.empty());

		PropertyTestUtils.assertAlignment(mutatedNode, Pos.TOP_RIGHT);
		PropertyTestUtils.assertMutationBehaviour(mutatedNode, MutationBehaviourProperty.MutationBehaviour.DEFAULT);

		final List<SuiNode> children = mutatedNode.getChildNodeStore().getUnmodifiable();
		assertThat(children).isNotNull();
		assertThat(children).hasSize(1);
		assertThat(children).doesNotContainNull();

		final SuiNode childButton = children.get(0);
		PropertyTestUtils.assertIdProperty(childButton, "btn2");
		PropertyTestUtils.assertTextContentProperty(childButton, "Renamed Child Button 2");
	}




	@Test
	public void test_mutate_with_behaviour_is_static_expect_only_children_changed() {

		final TestState state = new TestState();

		NodeFactory vbox = SuiElements.vBox()
				.id("myVBox")
				.alignment(Pos.CENTER)
				.mutationBehaviour(STATIC_NODE)
				.items(
						SuiElements.button().id("btn1").textContent("Child Button 1"),
						SuiElements.button().id("btn2").textContent("Child Button 2")
				);

		NodeFactory vboxTarget = SuiElements.vBox()
				.id("myVBox")
				.alignment(Pos.TOP_RIGHT)
				.mutationBehaviour(STATIC_NODE)
				.items(
						SuiElements.button().id("btn2").textContent("Renamed Child Button 2")
				);

		final SuiNode original = vbox.create(state, Tags.empty());
		SuiServices.get().enrichWithFxNodes(original);
		final SuiNode target = vboxTarget.create(state, null);
		final SuiNode mutatedNode = SuiServices.get().mutateNode(original, target, Tags.empty());

		PropertyTestUtils.assertAlignment(mutatedNode, Pos.CENTER);
		PropertyTestUtils.assertMutationBehaviour(mutatedNode, MutationBehaviourProperty.MutationBehaviour.STATIC_NODE);

		final List<SuiNode> children = mutatedNode.getChildNodeStore().getUnmodifiable();
		assertThat(children).isNotNull();
		assertThat(children).hasSize(1);
		assertThat(children).doesNotContainNull();

		final SuiNode childButton = children.get(0);
		PropertyTestUtils.assertIdProperty(childButton, "btn2");
		PropertyTestUtils.assertTextContentProperty(childButton, "Renamed Child Button 2");
	}




	@Test
	public void test_mutate_with_behaviour_is_static_subtree_expect_only_parent_changed() {

		final TestState state = new TestState();

		NodeFactory vbox = SuiElements.vBox()
				.id("myVBox")
				.alignment(Pos.CENTER)
				.mutationBehaviour(STATIC_SUBTREE)
				.items(
						SuiElements.button().id("btn1").textContent("Child Button 1"),
						SuiElements.button().id("btn2").textContent("Child Button 2")
				);

		NodeFactory vboxTarget = SuiElements.vBox()
				.id("myVBox")
				.alignment(Pos.TOP_RIGHT)
				.mutationBehaviour(STATIC_SUBTREE)
				.items(
						SuiElements.button().id("btn2").textContent("Renamed Child Button 2")
				);

		final SuiNode original = vbox.create(state, Tags.empty());
		SuiServices.get().enrichWithFxNodes(original);
		final SuiNode target = vboxTarget.create(state, null);
		final SuiNode mutatedNode = SuiServices.get().mutateNode(original, target, Tags.empty());

		PropertyTestUtils.assertAlignment(mutatedNode, Pos.TOP_RIGHT);
		PropertyTestUtils.assertMutationBehaviour(mutatedNode, STATIC_SUBTREE);

		final List<SuiNode> children = mutatedNode.getChildNodeStore().getUnmodifiable();
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




	@Test
	public void test_mutate_with_behaviour_is_static_expect_nothing_changed() {

		final TestState state = new TestState();

		NodeFactory vbox = SuiElements.vBox()
				.id("myVBox")
				.alignment(Pos.CENTER)
				.mutationBehaviour(STATIC)
				.items(
						SuiElements.button().id("btn1").textContent("Child Button 1"),
						SuiElements.button().id("btn2").textContent("Child Button 2")
				);

		NodeFactory vboxTarget = SuiElements.vBox()
				.id("myVBox")
				.alignment(Pos.TOP_RIGHT)
				.mutationBehaviour(STATIC)
				.items(
						SuiElements.button().id("btn2").textContent("Renamed Child Button 2")
				);

		final SuiNode original = vbox.create(state, null);
		SuiServices.get().enrichWithFxNodes(original);
		final SuiNode target = vboxTarget.create(state, null);
		final SuiNode mutatedNode = SuiServices.get().mutateNode(original, target, Tags.empty());

		PropertyTestUtils.assertAlignment(mutatedNode, Pos.CENTER);
		PropertyTestUtils.assertMutationBehaviour(mutatedNode, STATIC);

		final List<SuiNode> children = mutatedNode.getChildNodeStore().getUnmodifiable();
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




	@Test
	public void test_with_not_comparable_property_and_no_property_id_expect_update_property() {

		final TestState state = new TestState();
		final List<String> capturedEvents = new ArrayList<>();

		final OnActionEventProperty.ButtonBaseUpdatingBuilder spyOnActionUpdatingBuilder = Mockito.spy(new OnActionEventProperty.ButtonBaseUpdatingBuilder());
		SuiRegistry.get().registerProperty(SuiButton.class, OnActionEventProperty.class, spyOnActionUpdatingBuilder);

		NodeFactory factoryOriginal = SuiElements.button()
				.id("btn")
				.textContent("Button Original")
				.eventAction("a", e -> capturedEvents.add("from original"));

		NodeFactory factoryTarget = SuiElements.button()
				.id("btn")
				.textContent("Button Target")
				.eventAction("b", e -> capturedEvents.add("from target"));

		final SuiNode original = factoryOriginal.create(state, Tags.empty());
		SuiServices.get().enrichWithFxNodes(original);
		final SuiNode target = factoryTarget.create(state, null);
		final SuiNode mutatedNode = SuiServices.get().mutateNode(original, target, Tags.empty());

		assertThat(mutatedNode).isNotEqualTo(target);
		assertThat(mutatedNode).isEqualTo(original);

		((Button) mutatedNode.getFxNodeStore().get()).fire();
		delay(100);
		assertThat(capturedEvents).containsExactly("from target");

		assertThat(mutatedNode.getPropertyStore().get(OnActionEventProperty.class))
				.isEqualTo(target.getPropertyStore().get(OnActionEventProperty.class));

		verify(spyOnActionUpdatingBuilder).update(
				Mockito.eq(target.getPropertyStore().get(OnActionEventProperty.class)),
				Mockito.eq(original),
				Mockito.any());

	}




	@Test
	public void test_with_not_comparable_property_and_property_id_expect_no_change() {

		final TestState state = new TestState();
		final List<String> capturedEvents = new ArrayList<>();

		final OnActionEventProperty.ButtonBaseUpdatingBuilder spyOnActionUpdatingBuilder = Mockito.spy(new OnActionEventProperty.ButtonBaseUpdatingBuilder());
		SuiRegistry.get().registerProperty(SuiButton.class, OnActionEventProperty.class, spyOnActionUpdatingBuilder);

		NodeFactory factoryOriginal = SuiElements.button()
				.id("btn")
				.textContent("Button Original")
				.eventAction("myPropertyId", e -> capturedEvents.add("from original"));

		NodeFactory factoryTarget = SuiElements.button()
				.id("btn")
				.textContent("Button Target")
				.eventAction("myPropertyId", e -> capturedEvents.add("from target"));

		final SuiNode original = factoryOriginal.create(state, Tags.empty());
		SuiServices.get().enrichWithFxNodes(original);
		final SuiNode target = factoryTarget.create(state, null);
		final SuiNode mutatedNode = SuiServices.get().mutateNode(original, target, Tags.empty());

		assertThat(mutatedNode).isNotEqualTo(target);
		assertThat(mutatedNode).isEqualTo(original);

		((Button) mutatedNode.getFxNodeStore().get()).fire();
		delay(100);
		assertThat(capturedEvents).containsExactly("from original");

		assertThat(mutatedNode.getPropertyStore().get(OnActionEventProperty.class))
				.isNotEqualTo(target.getPropertyStore().get(OnActionEventProperty.class));

		verify(spyOnActionUpdatingBuilder, never()).update(
				Mockito.eq(target.getPropertyStore().get(OnActionEventProperty.class)),
				Mockito.eq(original),
				Mockito.any());

	}




	@Test
	public void test_state_update_with_tags_expect_mutate_only_tag_1_parts_of_tree() {

		final TestState state = new TestState();

		final Pair<NodeFactory, NodeFactory> nodeFactories = buildNodeFactoriesTagMutationTest();
		final NodeFactory factoryOriginal = nodeFactories.getLeft();
		final NodeFactory factoryTarget = nodeFactories.getRight();

		final SuiNode original = factoryOriginal.create(state, Tags.empty());
		SuiServices.get().enrichWithFxNodes(original);
		final SuiNode target = factoryTarget.create(state, null);
		final SuiNode mutatedNode = SuiServices.get().mutateNode(original, target, Tags.from("tag1"));

		assertThat(mutatedNode).isNotEqualTo(target);
		assertThat(mutatedNode).isEqualTo(original);

		final List<SuiNode> children = mutatedNode.getChildNodeStore().getUnmodifiable();
		assertThat(children).isNotNull();
		assertThat(children).hasSize(2);
		assertThat(children).doesNotContainNull();

		final VBox vbox = (VBox) mutatedNode.getFxNodeStore().get();
		assertThat(vbox).isNotNull();
		assertThat(vbox.getChildren()).hasSize(2);

		assertThat(((Button) vbox.getChildren().get(0)).getText()).isEqualTo("Mutated Button 1");
		assertThat(((Button) vbox.getChildren().get(1)).getText()).isEqualTo("Button 2");
	}




	@Test
	public void test_state_update_with_tags_expect_mutate_only_tag2_parts_of_tree() {

		final TestState state = new TestState();

		final Pair<NodeFactory, NodeFactory> nodeFactories = buildNodeFactoriesTagMutationTest();
		final NodeFactory factoryOriginal = nodeFactories.getLeft();
		final NodeFactory factoryTarget = nodeFactories.getRight();

		final SuiNode original = factoryOriginal.create(state, Tags.empty());
		SuiServices.get().enrichWithFxNodes(original);
		final SuiNode target = factoryTarget.create(state, null);
		final SuiNode mutatedNode = SuiServices.get().mutateNode(original, target, Tags.from("tag2"));

		assertThat(mutatedNode).isNotEqualTo(target);
		assertThat(mutatedNode).isEqualTo(original);

		final List<SuiNode> children = mutatedNode.getChildNodeStore().getUnmodifiable();
		assertThat(children).isNotNull();
		assertThat(children).hasSize(2);
		assertThat(children).doesNotContainNull();

		final VBox vbox = (VBox) mutatedNode.getFxNodeStore().get();
		assertThat(vbox).isNotNull();
		assertThat(vbox.getChildren()).hasSize(2);

		assertThat(((Button) vbox.getChildren().get(0)).getText()).isEqualTo("Button 1");
		assertThat(((Button) vbox.getChildren().get(1)).getText()).isEqualTo("Mutated Button 2");
	}




	private Pair<NodeFactory, NodeFactory> buildNodeFactoriesTagMutationTest() {
		NodeFactory factoryOriginal = SuiElements.vBox()
				.id("box")
				.items(
						SuiElements.button()
								.id("btn1")
								.mutationBehaviour(STATIC, Tags.contains("tag1"))
								.textContent("Button 1"),
						SuiElements.button()
								.id("btn2")
								.mutationBehaviour(STATIC, Tags.contains("tag2"))
								.textContent("Button 2")
				);
		NodeFactory factoryTarget = SuiElements.vBox()
				.id("box")
				.items(
						SuiElements.button()
								.id("btn1")
								.mutationBehaviour(STATIC, Tags.contains("tag1"))
								.textContent("Mutated Button 1"),
						SuiElements.button()
								.id("btn2")
								.mutationBehaviour(STATIC, Tags.contains("tag2"))
								.textContent("Mutated Button 2")
				);
		return Pair.of(factoryOriginal, factoryTarget);
	}




	private void delay(final long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
