package de.ruegnerlukas.simpleapplication.core.simpleui.core.mutation.strategies;

import de.ruegnerlukas.simpleapplication.common.tags.Tags;
import de.ruegnerlukas.simpleapplication.common.utils.Pair;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.SuiElements;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.TextContentProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.mutation.stategies.IdShuffleMutationStrategy;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.mutation.stategies.StrategyDecisionResult;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.NodeFactory;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.testutils.TestUtils;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.List;

import static de.ruegnerlukas.simpleapplication.core.simpleui.testutils.StrategyTestUtils.assertChildren;
import static de.ruegnerlukas.simpleapplication.core.simpleui.testutils.StrategyTestUtils.buildTest;
import static de.ruegnerlukas.simpleapplication.core.simpleui.testutils.StrategyTestUtils.buildVBox;
import static de.ruegnerlukas.simpleapplication.core.simpleui.testutils.StrategyTestUtils.printChildButtons;
import static de.ruegnerlukas.simpleapplication.core.simpleui.testutils.StrategyTestUtils.removeChildNodes;
import static de.ruegnerlukas.simpleapplication.core.simpleui.testutils.StrategyTestUtils.shuffleChildNodes;
import static org.assertj.core.api.Assertions.assertThat;

public class IdShuffleMutationStrategyTest extends ApplicationTest {


	@Override
	public void start(Stage stage) {
		TestUtils.registerSuiRegistryFactory();
	}




	@Test
	public void test_identical_children_expect_mutation_but_no_change() {

		final NodeFactory factoryOriginal = buildVBox(20, "Btn Orgnal", true);
		final NodeFactory factoryTarget = buildVBox(20, "Btn Target", true);

		final Pair<SuiNode, SuiNode> testData = buildTest(factoryOriginal, factoryTarget);
		final SuiNode nodeOriginal = testData.getLeft();
		final SuiNode nodeTarget = testData.getRight();

		printChildButtons("Original", nodeOriginal);
		printChildButtons("  Target", nodeTarget);

		final IdShuffleMutationStrategy strategy = new IdShuffleMutationStrategy();

		final StrategyDecisionResult decisionResult = strategy.canBeAppliedTo(nodeOriginal, nodeTarget, true);
		assertThat(decisionResult.isApplicable()).isTrue();

		final MutationResult mutationResult = strategy.mutate(
				nodeOriginal, nodeTarget, Tags.empty(),
				decisionResult);
		printChildButtons("  Result", nodeOriginal);

		assertThat(mutationResult).isEqualTo(MutationResult.MUTATED);
		assertChildren(nodeTarget, nodeOriginal);

	}




	@Test
	public void test_remove_some_children_expect_not_applicable() {

		final NodeFactory factoryOriginal = buildVBox(20, "Btn Orgnal", true);
		final NodeFactory factoryTarget = buildVBox(20, "Btn Target", true);

		final Pair<SuiNode, SuiNode> testData = buildTest(factoryOriginal, factoryTarget);
		final SuiNode nodeOriginal = testData.getLeft();
		final SuiNode nodeTarget = testData.getRight();

		removeChildNodes(nodeTarget, 5);

		printChildButtons("Original", nodeOriginal);
		printChildButtons("  Target", nodeTarget);

		final IdShuffleMutationStrategy strategy = new IdShuffleMutationStrategy();

		final StrategyDecisionResult decisionResult = strategy.canBeAppliedTo(nodeOriginal, nodeTarget, true);
		assertThat(decisionResult.isApplicable()).isFalse();

	}




	@Test
	public void test_remove_most_children_expect_not_applicable() {

		final NodeFactory factoryOriginal = buildVBox(20, "Btn Orgnal", true);
		final NodeFactory factoryTarget = buildVBox(20, "Btn Target", true);

		final Pair<SuiNode, SuiNode> testData = buildTest(factoryOriginal, factoryTarget);
		final SuiNode nodeOriginal = testData.getLeft();
		final SuiNode nodeTarget = testData.getRight();

		removeChildNodes(nodeTarget, 15);

		printChildButtons("Original", nodeOriginal);
		printChildButtons("  Target", nodeTarget);

		final IdShuffleMutationStrategy strategy = new IdShuffleMutationStrategy();

		final StrategyDecisionResult decisionResult = strategy.canBeAppliedTo(nodeOriginal, nodeTarget, true);
		assertThat(decisionResult.isApplicable()).isFalse();

	}




	@Test
	public void test_remove_all_children_expect_not_applicable() {

		final NodeFactory factoryOriginal = buildVBox(20, "Btn Orgnal", true);
		final NodeFactory factoryTarget = buildVBox(0, "Btn Target", true);

		final Pair<SuiNode, SuiNode> testData = buildTest(factoryOriginal, factoryTarget);
		final SuiNode nodeOriginal = testData.getLeft();
		final SuiNode nodeTarget = testData.getRight();

		printChildButtons("Original", nodeOriginal);
		printChildButtons("  Target", nodeTarget);

		final IdShuffleMutationStrategy strategy = new IdShuffleMutationStrategy();

		final StrategyDecisionResult decisionResult = strategy.canBeAppliedTo(nodeOriginal, nodeTarget, true);
		assertThat(decisionResult.isApplicable()).isFalse();

	}




	@Test
	public void test_add_some_to_existing_children_expect_not_applicable() {

		final NodeFactory factoryOriginal = buildVBox(20, "Btn Orgnal", true);
		final NodeFactory factoryTarget = buildVBox(20, "Btn Target", true);

		final Pair<SuiNode, SuiNode> testData = buildTest(factoryOriginal, factoryTarget);
		final SuiNode nodeOriginal = testData.getLeft();
		final SuiNode nodeTarget = testData.getRight();

		removeChildNodes(nodeOriginal, 5);

		printChildButtons("Original", nodeOriginal);
		printChildButtons("  Target", nodeTarget);

		final IdShuffleMutationStrategy strategy = new IdShuffleMutationStrategy();

		final StrategyDecisionResult decisionResult = strategy.canBeAppliedTo(nodeOriginal, nodeTarget, true);
		assertThat(decisionResult.isApplicable()).isFalse();

	}




	@Test
	public void test_add_many_to_existing_children_expect_not_applicable() {

		final NodeFactory factoryOriginal = buildVBox(20, "Btn Orgnal", true);
		final NodeFactory factoryTarget = buildVBox(20, "Btn Target", true);

		final Pair<SuiNode, SuiNode> testData = buildTest(factoryOriginal, factoryTarget);
		final SuiNode nodeOriginal = testData.getLeft();
		final SuiNode nodeTarget = testData.getRight();

		removeChildNodes(nodeOriginal, 15);

		printChildButtons("Original", nodeOriginal);
		printChildButtons("  Target", nodeTarget);

		final IdShuffleMutationStrategy strategy = new IdShuffleMutationStrategy();

		final StrategyDecisionResult decisionResult = strategy.canBeAppliedTo(nodeOriginal, nodeTarget, true);
		assertThat(decisionResult.isApplicable()).isFalse();

	}




	@Test
	public void test_add_children_to_empty_parent_expect_not_applicable() {

		final NodeFactory factoryOriginal = buildVBox(0, "Btn Orgnal", true);
		final NodeFactory factoryTarget = buildVBox(20, "Btn Target", true);

		final Pair<SuiNode, SuiNode> testData = buildTest(factoryOriginal, factoryTarget);
		final SuiNode nodeOriginal = testData.getLeft();
		final SuiNode nodeTarget = testData.getRight();

		printChildButtons("Original", nodeOriginal);
		printChildButtons("  Target", nodeTarget);

		final IdShuffleMutationStrategy strategy = new IdShuffleMutationStrategy();

		final StrategyDecisionResult decisionResult = strategy.canBeAppliedTo(nodeOriginal, nodeTarget, true);
		assertThat(decisionResult.isApplicable()).isFalse();

	}




	@Test
	public void test_shuffle_some_existing_children_expect_not_applicable_due_to_not_enough_changes() {

		final NodeFactory factoryOriginal = buildVBox(20, "Btn Orgnal", true);
		final NodeFactory factoryTarget = buildVBox(20, "Btn Target", true);

		final Pair<SuiNode, SuiNode> testData = buildTest(factoryOriginal, factoryTarget);
		final SuiNode nodeOriginal = testData.getLeft();
		final SuiNode nodeTarget = testData.getRight();

		shuffleChildNodes(nodeTarget, 3);

		printChildButtons("Original", nodeOriginal);
		printChildButtons("  Target", nodeTarget);

		final IdShuffleMutationStrategy strategy = new IdShuffleMutationStrategy();

		final StrategyDecisionResult decisionResult = strategy.canBeAppliedTo(nodeOriginal, nodeTarget, true);
		assertThat(decisionResult.isApplicable()).isFalse();

	}




	@Test
	public void test_shuffle_most_existing_children_expect_mutation_and_original_matching_target() {

		final NodeFactory factoryOriginal = buildVBox(20, "Btn Orgnal", true);
		final NodeFactory factoryTarget = buildVBox(20, "Btn Target", true);

		final Pair<SuiNode, SuiNode> testData = buildTest(factoryOriginal, factoryTarget);
		final SuiNode nodeOriginal = testData.getLeft();
		final SuiNode nodeTarget = testData.getRight();

		shuffleChildNodes(nodeTarget, 20);

		printChildButtons("Original", nodeOriginal);
		printChildButtons("  Target", nodeTarget);

		final IdShuffleMutationStrategy strategy = new IdShuffleMutationStrategy();

		final StrategyDecisionResult decisionResult = strategy.canBeAppliedTo(nodeOriginal, nodeTarget, true);
		assertThat(decisionResult.isApplicable()).isTrue();

		final MutationResult mutationResult = strategy.mutate(
				nodeOriginal, nodeTarget, Tags.empty(),
				decisionResult);
		printChildButtons("  Result", nodeOriginal);

		assertThat(mutationResult).isEqualTo(MutationResult.MUTATED);
		assertChildren(nodeTarget, nodeOriginal);
	}




	@Test
	public void test_with_children_missing_ids_expect_not_applicable() {

		final NodeFactory factoryOriginal = buildVBox(20, "Btn Orgnal", false);
		final NodeFactory factoryTarget = buildVBox(20, "Btn Target", false);

		final Pair<SuiNode, SuiNode> testData = buildTest(factoryOriginal, factoryTarget);
		final SuiNode nodeOriginal = testData.getLeft();
		final SuiNode nodeTarget = testData.getRight();

		final IdShuffleMutationStrategy strategy = new IdShuffleMutationStrategy();

		final StrategyDecisionResult decisionResult = strategy.canBeAppliedTo(nodeOriginal, nodeTarget, false);
		assertThat(decisionResult.isApplicable()).isFalse();
	}




	@Test
	public void test_same_ids_and_order_but_rebuild_child_expect_mutated_and_child_replaced() {

		final NodeFactory factoryOriginal = SuiElements.vBox()
				.id("box")
				.items(
						SuiElements.button()
								.id("child1")
								.textContent("Button 1"),
						SuiElements.button()
								.id("child2")
								.textContent("Button 2"),
						SuiElements.button()
								.id("child3")
								.textContent("Button 2")
				);

		final NodeFactory factoryTarget = SuiElements.vBox()
				.id("box")
				.items(
						SuiElements.button()
								.id("child1")
								.textContent("Mutated Button 1"),
						SuiElements.label()
								.id("child2")
								.textContent("Label"),
						SuiElements.button()
								.id("child3")
								.textContent("Mutated Button 2")
				);

		final Pair<SuiNode, SuiNode> testData = buildTest(factoryOriginal, factoryTarget);
		final SuiNode nodeOriginal = testData.getLeft();
		final SuiNode nodeTarget = testData.getRight();

		final IdShuffleMutationStrategy strategy = new IdShuffleMutationStrategy();

		final StrategyDecisionResult decisionResult = strategy.canBeAppliedTo(nodeOriginal, nodeTarget, true);
		assertThat(decisionResult.isApplicable()).isTrue();

		final MutationResult mutationResult = strategy.mutate(nodeOriginal, nodeTarget, Tags.empty(), decisionResult);
		assertThat(mutationResult).isEqualTo(MutationResult.MUTATED);
		assertThat(nodeOriginal.getChildNodeStore().count()).isEqualTo(3);

		final List<SuiNode> childrenMutated = nodeOriginal.getChildNodeStore().getUnmodifiable();
		assertThat(childrenMutated.get(0).getFxNodeStore().get()).isInstanceOf(Button.class);
		assertThat(childrenMutated.get(1).getFxNodeStore().get()).isInstanceOf(Label.class);
		assertThat(childrenMutated.get(2).getFxNodeStore().get()).isInstanceOf(Button.class);

		assertThat(childrenMutated.get(0).getPropertyStore().getIdUnsafe()).isEqualTo("child1");
		assertThat(childrenMutated.get(1).getPropertyStore().getIdUnsafe()).isEqualTo("child2");
		assertThat(childrenMutated.get(2).getPropertyStore().getIdUnsafe()).isEqualTo("child3");

		assertThat(childrenMutated.get(0).getPropertyStore().get(TextContentProperty.class).getText()).isEqualTo("Mutated Button 1");
		assertThat(childrenMutated.get(1).getPropertyStore().get(TextContentProperty.class).getText()).isEqualTo("Label");
		assertThat(childrenMutated.get(2).getPropertyStore().get(TextContentProperty.class).getText()).isEqualTo("Mutated Button 2");

	}




	@Test
	public void test_same_ids_but_different_order_and_rebuild_child_expect_mutated_and_child_replaced() {

		final NodeFactory factoryOriginal = SuiElements.vBox()
				.id("box")
				.items(
						SuiElements.button()
								.id("child1")
								.textContent("Button 1"),
						SuiElements.button()
								.id("child2")
								.textContent("Button 2"),
						SuiElements.button()
								.id("child3")
								.textContent("Button 2")
				);

		final NodeFactory factoryTarget = SuiElements.vBox()
				.id("box")
				.items(
						SuiElements.button()
								.id("child2")
								.textContent("Mutated Button 1"),
						SuiElements.label()
								.id("child1")
								.textContent("Label"),
						SuiElements.button()
								.id("child3")
								.textContent("Mutated Button 2")
				);

		final Pair<SuiNode, SuiNode> testData = buildTest(factoryOriginal, factoryTarget);
		final SuiNode nodeOriginal = testData.getLeft();
		final SuiNode nodeTarget = testData.getRight();

		final IdShuffleMutationStrategy strategy = new IdShuffleMutationStrategy();

		final StrategyDecisionResult decisionResult = strategy.canBeAppliedTo(nodeOriginal, nodeTarget, true);
		assertThat(decisionResult.isApplicable()).isTrue();

		final MutationResult mutationResult = strategy.mutate(nodeOriginal, nodeTarget, Tags.empty(), decisionResult);
		assertThat(mutationResult).isEqualTo(MutationResult.MUTATED);
		assertThat(nodeOriginal.getChildNodeStore().count()).isEqualTo(3);

		final List<SuiNode> childrenMutated = nodeOriginal.getChildNodeStore().getUnmodifiable();
		assertThat(childrenMutated.get(0).getFxNodeStore().get()).isInstanceOf(Button.class);
		assertThat(childrenMutated.get(1).getFxNodeStore().get()).isInstanceOf(Label.class);
		assertThat(childrenMutated.get(2).getFxNodeStore().get()).isInstanceOf(Button.class);

		assertThat(childrenMutated.get(0).getPropertyStore().getIdUnsafe()).isEqualTo("child2");
		assertThat(childrenMutated.get(1).getPropertyStore().getIdUnsafe()).isEqualTo("child1");
		assertThat(childrenMutated.get(2).getPropertyStore().getIdUnsafe()).isEqualTo("child3");

		assertThat(childrenMutated.get(0).getPropertyStore().get(TextContentProperty.class).getText()).isEqualTo("Mutated Button 1");
		assertThat(childrenMutated.get(1).getPropertyStore().get(TextContentProperty.class).getText()).isEqualTo("Label");
		assertThat(childrenMutated.get(2).getPropertyStore().get(TextContentProperty.class).getText()).isEqualTo("Mutated Button 2");

	}




	@Test
	public void test_with_children_different_ids_expect_not_applicable() {

		final NodeFactory factoryOriginal = buildVBox(20, "Btn Orgnal", false);
		final NodeFactory factoryTarget = buildVBox(20, "Btn Target", false);

		final Pair<SuiNode, SuiNode> testData = buildTest(factoryOriginal, factoryTarget);
		final SuiNode nodeOriginal = testData.getLeft();
		final SuiNode nodeTarget = testData.getRight();

		shuffleChildNodes(nodeOriginal, 20);
		shuffleChildNodes(nodeTarget, 20);

		removeChildNodes(nodeOriginal, 10);
		removeChildNodes(nodeTarget, 10);

		final IdShuffleMutationStrategy strategy = new IdShuffleMutationStrategy();

		final StrategyDecisionResult decisionResult = strategy.canBeAppliedTo(nodeOriginal, nodeTarget, false);
		assertThat(decisionResult.isApplicable()).isFalse();
	}


}
