package de.ruegnerlukas.simpleapplication.simpleui.core.mutation;

import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiButton;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiVBox;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.EventProperties;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnActionEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.MutationBehaviourProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.SizeProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TextContentProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.SuiSceneController;
import de.ruegnerlukas.simpleapplication.simpleui.core.SuiServices;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.tags.Tags;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.TaggedSuiStateUpdate;
import de.ruegnerlukas.simpleapplication.simpleui.testutils.PropertyTestUtils;
import de.ruegnerlukas.simpleapplication.simpleui.testutils.TestState;
import de.ruegnerlukas.simpleapplication.simpleui.testutils.TestUtils;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.Test;
import org.mockito.Mockito;
import org.testfx.framework.junit.ApplicationTest;

import java.util.ArrayList;
import java.util.List;

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

		final SuiSceneController context = new SuiSceneController(state, vbox);
		final SuiNode original = context.getRootNode();
		final SuiNode target = vboxTarget.create(state);
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

		NodeFactory factoryOriginal = SuiVBox.vbox(
				Properties.items(
						SuiButton.button(
								Properties.textContent("Child Button")
						)
				)
		);

		NodeFactory factoryTarget = SuiButton.button(
				Properties.textContent("My Button")
		);

		final SuiSceneController context = new SuiSceneController(state, factoryOriginal);
		final SuiNode original = context.getRootNode();
		final SuiNode target = factoryTarget.create(state);
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

		NodeFactory factoryOriginal = SuiVBox.vbox(
				Properties.size(0, 0, 5, 5, 10, 10),
				Properties.items(
						SuiButton.button(
								Properties.textContent("Child Button")
						)
				)
		);

		NodeFactory factoryTarget = SuiVBox.vbox(
				Properties.items(
						SuiButton.button(
								Properties.textContent("Child Button")
						)
				)
		);

		final SuiSceneController context = new SuiSceneController(state, factoryOriginal);
		final SuiNode original = context.getRootNode();
		final SuiNode target = factoryTarget.create(state);
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

		NodeFactory factoryOriginal = SuiButton.button(
				Properties.id("myButton"),
				Properties.textContent("Button")
		);

		NodeFactory factoryTarget = SuiButton.button(
				Properties.id("myButton")
		);

		final SuiSceneController context = new SuiSceneController(state, factoryOriginal);
		final SuiNode original = context.getRootNode();
		final SuiNode target = factoryTarget.create(state);
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

		NodeFactory factoryOriginal = SuiButton.button(
				Properties.id("myButton")
		);

		NodeFactory factoryTarget = SuiButton.button(
				Properties.id("myButton"),
				Properties.textContent("Button")
		);

		final SuiSceneController context = new SuiSceneController(state, factoryOriginal);
		final SuiNode original = context.getRootNode();
		final SuiNode target = factoryTarget.create(state);
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

		final SuiSceneController context = new SuiSceneController(state, vbox);
		final SuiNode original = context.getRootNode();
		final SuiNode target = vboxTarget.create(state);
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

		final SuiSceneController context = new SuiSceneController(state, vbox);
		final SuiNode original = context.getRootNode();
		final SuiNode target = vboxTarget.create(state);
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

		final SuiSceneController context = new SuiSceneController(state, vbox);
		final SuiNode original = context.getRootNode();
		final SuiNode target = vboxTarget.create(state);
		final SuiNode mutatedNode = SuiServices.get().mutateNode(original, target, Tags.empty());

		PropertyTestUtils.assertAlignment(mutatedNode, Pos.CENTER);
		PropertyTestUtils.assertMutationBehaviour(mutatedNode, MutationBehaviourProperty.MutationBehaviour.STATIC_SUBTREE);

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

		NodeFactory factoryOriginal = SuiButton.button(
				Properties.id("btn"),
				Properties.textContent("Button Original"),
				EventProperties.eventAction(e -> capturedEvents.add("from original"))
		);

		NodeFactory factoryTarget = SuiButton.button(
				Properties.id("btn"),
				Properties.textContent("Button Target"),
				EventProperties.eventAction(e -> capturedEvents.add("from target"))
		);

		final SuiSceneController context = new SuiSceneController(state, factoryOriginal);
		final SuiNode original = context.getRootNode();
		final SuiNode target = factoryTarget.create(state);
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

		NodeFactory factoryOriginal = SuiButton.button(
				Properties.id("btn"),
				Properties.textContent("Button Original"),
				EventProperties.eventAction("myPropertyId", e -> capturedEvents.add("from original"))
		);

		NodeFactory factoryTarget = SuiButton.button(
				Properties.id("btn"),
				Properties.textContent("Button Target"),
				EventProperties.eventAction("myPropertyId", e -> capturedEvents.add("from target"))
		);

		final SuiSceneController context = new SuiSceneController(state, factoryOriginal);
		final SuiNode original = context.getRootNode();
		final SuiNode target = factoryTarget.create(state);
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
	public void test_state_update_with_tags_expect_mutate_only_parts_of_tree() {

		final TestState state = new TestState();

		NodeFactory factoryOriginal = SuiVBox.vbox(
				Properties.id("box"),
				Properties.items(
						SuiButton.button(
								Properties.id("btn1"),
								// todo: add behaviour: dont mutate on tested state update
								Properties.textContent("Button 1")
						),
						SuiButton.button(
								Properties.id("btn2"),
								Properties.textContent("Button 2")
						)
				)
		);

		NodeFactory factoryTarget = SuiVBox.vbox(
				Properties.id("box"),
				Properties.items(
						SuiButton.button(
								Properties.id("btn1"),
								// todo: add behaviour: dont mutate on tested state update
								Properties.textContent("Mutated Button 1")
						),
						SuiButton.button(
								Properties.id("btn2"),
								Properties.textContent("Mutated Button 2")
						)
				)
		);

		final SuiSceneController context = new SuiSceneController(state, factoryOriginal);
		final SuiNode original = context.getRootNode();

		final SuiNode target = factoryTarget.create(state);
		final SuiNode mutatedNode = SuiServices.get().mutateNode(original, target, Tags.empty());

		state.update(TestState.class, s -> s.text = "somethingElse");

		state.update(TestState.class, (TaggedSuiStateUpdate<TestState>) s -> Tags.from("tag1", "tag2"));

		assertThat(mutatedNode).isNotEqualTo(target);
		assertThat(mutatedNode).isEqualTo(original);

		final List<SuiNode> children = mutatedNode.getChildNodeStore().getUnmodifiable();
		assertThat(children).isNotNull();
		assertThat(children).hasSize(2);
		assertThat(children).doesNotContainNull();

		final VBox vbox = (VBox) mutatedNode.getFxNodeStore().get();
		assertThat(vbox).isNotNull();
		assertThat(vbox.getChildren()).hasSize(2);

		assertThat(((Button)vbox.getChildren().get(0)).getText()).isEqualTo("Button 1");
		assertThat(((Button)vbox.getChildren().get(1)).getText()).isEqualTo("Mutated Button 2");
	}




	private void delay(final long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
