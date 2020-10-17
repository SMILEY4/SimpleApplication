package de.ruegnerlukas.simpleapplication.simpleui.core;

import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiButton;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiVBox;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.MutationBehaviourProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.tags.TagConditionExpression;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.tags.Tags;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
import de.ruegnerlukas.simpleapplication.simpleui.testutils.TestState;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class EarlyOutBuildTest extends ApplicationTest {


	@Before
	public void setup() {
		SuiRegistry.initialize();
	}




	@Test
	public void test_default_behaviour_expect_no_early_out() {

		final TestState testState = new TestState();
		final NodeFactory factory = buildFactory(MutationBehaviourProperty.MutationBehaviour.DEFAULT, null);

		final SuiNode node = factory.create(testState, Tags.empty());
		assertTreeIsComplete(node);
	}




	@Test
	public void test_static_node_behaviour_expect_no_early_out() {

		final TestState testState = new TestState();
		final NodeFactory factory = buildFactory(MutationBehaviourProperty.MutationBehaviour.STATIC_NODE, null);

		final SuiNode node = factory.create(testState, Tags.empty());
		assertTreeIsComplete(node);
	}




	@Test
	public void test_static_subtree_behaviour_expect_no_early_out() {

		final TestState testState = new TestState();
		final NodeFactory factory = buildFactory(MutationBehaviourProperty.MutationBehaviour.STATIC_SUBTREE, null);

		final SuiNode node = factory.create(testState, Tags.empty());
		assertTreeIsComplete(node);
	}




	@Test
	public void test_static_behaviour_with_null_tags_expect_no_early_out() {

		final TestState testState = new TestState();
		final NodeFactory factory = buildFactory(MutationBehaviourProperty.MutationBehaviour.STATIC, null);

		final SuiNode node = factory.create(testState, null);
		assertTreeIsComplete(node);
	}




	@Test
	public void test_static_behaviour_with_matching_condition_expect_no_early_out() {

		final TestState testState = new TestState();
		final NodeFactory factory = buildFactory(MutationBehaviourProperty.MutationBehaviour.STATIC, Tags.contains("tag2"));

		final SuiNode node = factory.create(testState, Tags.from("tag1", "tag2"));
		assertTreeIsComplete(node);
	}




	@Test
	public void test_static_behaviour_with_failing_condition_expect_early_out() {

		final TestState testState = new TestState();
		final NodeFactory factory = buildFactory(MutationBehaviourProperty.MutationBehaviour.STATIC, Tags.contains("nonexistent"));

		final SuiNode node = factory.create(testState, Tags.from("tag1", "tag2"));
		assertTreeIsEarlyOut(node);
	}




	private void assertTreeIsComplete(final SuiNode root) {
		assertThat(root).isNotNull();
		final List<SuiNode> childBoxes = root.getChildNodeStore().getUnmodifiable();
		assertThat(childBoxes).hasSize(2);
		assertThat(childBoxes.get(0).getChildNodeStore().count()).isEqualTo(2);
		assertThat(childBoxes.get(1).getChildNodeStore().count()).isEqualTo(3);
	}




	private void assertTreeIsEarlyOut(final SuiNode root) {
		assertThat(root).isNotNull();
		final List<SuiNode> childBoxes = root.getChildNodeStore().getUnmodifiable();
		assertThat(childBoxes).hasSize(2);
		assertThat(childBoxes.get(0).getChildNodeStore().count()).isEqualTo(2);
		assertThat(childBoxes.get(1).getChildNodeStore().count()).isEqualTo(0);
	}




	private NodeFactory buildFactory(final MutationBehaviourProperty.MutationBehaviour behaviour, final TagConditionExpression condition) {
		return SuiVBox.vbox(
				Properties.id("root"),
				Properties.items(
						SuiVBox.vbox(
								Properties.id("box-left"),
								Properties.items(
										SuiButton.button(
												Properties.id("btn-left-1"),
												Properties.textContent("Button Left 1")
										),
										SuiButton.button(
												Properties.id("btn-left-2"),
												Properties.textContent("Button Left 2")
										)
								)
						),
						SuiVBox.vbox(
								Properties.id("box-right"),
								Properties.mutationBehaviour(behaviour, condition), // <- this node has the mutation behaviour
								Properties.items(
										SuiButton.button(
												Properties.id("btn-right-1"),
												Properties.textContent("Button Right 1")
										),
										SuiButton.button(
												Properties.id("btn-right-2"),
												Properties.textContent("Button Right 2")
										),
										SuiButton.button(
												Properties.id("btn-right-3"),
												Properties.textContent("Button Right 3")
										)
								)
						)
				)
		);
	}


}


