package de.ruegnerlukas.simpleapplication.simpleui.strategies;

import de.ruegnerlukas.simpleapplication.common.utils.Triplet;
import de.ruegnerlukas.simpleapplication.simpleui.core.SuiSceneController;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.stategies.StandardMutationStrategy;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.stategies.StrategyDecisionResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiBaseNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static de.ruegnerlukas.simpleapplication.simpleui.testutils.StrategyTestUtils.assertChildren;
import static de.ruegnerlukas.simpleapplication.simpleui.testutils.StrategyTestUtils.buildTest;
import static de.ruegnerlukas.simpleapplication.simpleui.testutils.StrategyTestUtils.buildVBox;
import static de.ruegnerlukas.simpleapplication.simpleui.testutils.StrategyTestUtils.printChildButtons;
import static de.ruegnerlukas.simpleapplication.simpleui.testutils.StrategyTestUtils.removeChildNodes;
import static de.ruegnerlukas.simpleapplication.simpleui.testutils.StrategyTestUtils.shuffleChildNodes;
import static org.assertj.core.api.Assertions.assertThat;

public class StandardMutationStrategyTest extends ApplicationTest {


	@Override
	public void start(Stage stage) {
		SuiRegistry.initialize();
	}




	@Test
	public void test_identical_children_expect_mutation_but_no_change() {

		final NodeFactory factoryOriginal = buildVBox(20, "Btn Orgnal");
		final NodeFactory factoryTarget = buildVBox(20, "Btn Target");

		final Triplet<SuiSceneController, SuiBaseNode, SuiBaseNode> testData = buildTest(factoryOriginal, factoryTarget);
		final SuiSceneController context = testData.getLeft();
		final SuiBaseNode nodeOriginal = testData.getMiddle();
		final SuiBaseNode nodeTarget = testData.getRight();

		printChildButtons("Original", nodeOriginal);
		printChildButtons("  Target", nodeTarget);

		final StandardMutationStrategy strategy = new StandardMutationStrategy();

		final StrategyDecisionResult decisionResult = strategy.canBeAppliedTo(nodeOriginal, nodeTarget, false);
		assertThat(decisionResult.isApplicable()).isTrue();

		final MutationResult mutationResult = strategy.mutate(
				nodeOriginal, nodeTarget,
				StrategyDecisionResult.APPLICABLE_NO_EXTRA_DATA);
		printChildButtons("  Result", nodeOriginal);

		assertThat(mutationResult).isEqualTo(MutationResult.MUTATED);
		assertChildren(nodeTarget, nodeOriginal);

	}




	@Test
	public void test_remove_some_children_expect_mutation_and_children_removed() {

		final NodeFactory factoryOriginal = buildVBox(20, "Btn Orgnal");
		final NodeFactory factoryTarget = buildVBox(20, "Btn Target");

		final Triplet<SuiSceneController, SuiBaseNode, SuiBaseNode> testData = buildTest(factoryOriginal, factoryTarget);
		final SuiSceneController context = testData.getLeft();
		final SuiBaseNode nodeOriginal = testData.getMiddle();
		final SuiBaseNode nodeTarget = testData.getRight();

		removeChildNodes(nodeTarget, 5);

		printChildButtons("Original", nodeOriginal);
		printChildButtons("  Target", nodeTarget);

		final StandardMutationStrategy strategy = new StandardMutationStrategy();

		final StrategyDecisionResult decisionResult = strategy.canBeAppliedTo(nodeOriginal, nodeTarget, false);
		assertThat(decisionResult.isApplicable()).isTrue();

		final MutationResult mutationResult = strategy.mutate(
				nodeOriginal, nodeTarget,
				StrategyDecisionResult.APPLICABLE_NO_EXTRA_DATA);
		printChildButtons("  Result", nodeOriginal);

		assertThat(mutationResult).isEqualTo(MutationResult.MUTATED);
		assertChildren(nodeTarget, nodeOriginal);

	}




	@Test
	public void test_remove_most_children_expect_mutation_and_children_removed() {

		final NodeFactory factoryOriginal = buildVBox(20, "Btn Orgnal");
		final NodeFactory factoryTarget = buildVBox(20, "Btn Target");

		final Triplet<SuiSceneController, SuiBaseNode, SuiBaseNode> testData = buildTest(factoryOriginal, factoryTarget);
		final SuiSceneController context = testData.getLeft();
		final SuiBaseNode nodeOriginal = testData.getMiddle();
		final SuiBaseNode nodeTarget = testData.getRight();

		removeChildNodes(nodeTarget, 15);

		printChildButtons("Original", nodeOriginal);
		printChildButtons("  Target", nodeTarget);

		final StandardMutationStrategy strategy = new StandardMutationStrategy();

		final StrategyDecisionResult decisionResult = strategy.canBeAppliedTo(nodeOriginal, nodeTarget, false);
		assertThat(decisionResult.isApplicable()).isTrue();

		final MutationResult mutationResult = strategy.mutate(
				nodeOriginal, nodeTarget,
				StrategyDecisionResult.APPLICABLE_NO_EXTRA_DATA);
		printChildButtons("  Result", nodeOriginal);

		assertThat(mutationResult).isEqualTo(MutationResult.MUTATED);
		assertChildren(nodeTarget, nodeOriginal);

	}




	@Test
	public void test_remove_all_children_expect_mutation_and_original_has_no_more_children() {

		final NodeFactory factoryOriginal = buildVBox(20, "Btn Orgnal");
		final NodeFactory factoryTarget = buildVBox(0, "Btn Target");

		final Triplet<SuiSceneController, SuiBaseNode, SuiBaseNode> testData = buildTest(factoryOriginal, factoryTarget);
		final SuiSceneController context = testData.getLeft();
		final SuiBaseNode nodeOriginal = testData.getMiddle();
		final SuiBaseNode nodeTarget = testData.getRight();

		printChildButtons("Original", nodeOriginal);
		printChildButtons("  Target", nodeTarget);

		final StandardMutationStrategy strategy = new StandardMutationStrategy();

		final StrategyDecisionResult decisionResult = strategy.canBeAppliedTo(nodeOriginal, nodeTarget, false);
		assertThat(decisionResult.isApplicable()).isTrue();

		final MutationResult mutationResult = strategy.mutate(
				nodeOriginal, nodeTarget,
				StrategyDecisionResult.APPLICABLE_NO_EXTRA_DATA);
		printChildButtons("  Result", nodeOriginal);

		assertThat(mutationResult).isEqualTo(MutationResult.MUTATED);
		assertChildren(nodeTarget, nodeOriginal);

	}




	@Test
	public void test_add_some_to_existing_children_expect_mutation_and_original_has_nodes() {

		final NodeFactory factoryOriginal = buildVBox(20, "Btn Orgnal");
		final NodeFactory factoryTarget = buildVBox(20, "Btn Target");

		final Triplet<SuiSceneController, SuiBaseNode, SuiBaseNode> testData = buildTest(factoryOriginal, factoryTarget);
		final SuiSceneController context = testData.getLeft();
		final SuiBaseNode nodeOriginal = testData.getMiddle();
		final SuiBaseNode nodeTarget = testData.getRight();

		removeChildNodes(nodeOriginal, 5);

		printChildButtons("Original", nodeOriginal);
		printChildButtons("  Target", nodeTarget);

		final StandardMutationStrategy strategy = new StandardMutationStrategy();

		final StrategyDecisionResult decisionResult = strategy.canBeAppliedTo(nodeOriginal, nodeTarget, false);
		assertThat(decisionResult.isApplicable()).isTrue();

		final MutationResult mutationResult = strategy.mutate(
				nodeOriginal, nodeTarget,
				StrategyDecisionResult.APPLICABLE_NO_EXTRA_DATA);
		printChildButtons("  Result", nodeOriginal);

		assertThat(mutationResult).isEqualTo(MutationResult.MUTATED);
		assertChildren(nodeTarget, nodeOriginal);

	}




	@Test
	public void test_add_many_to_existing_children_expect_mutation_and_original_has_nodes() {

		final NodeFactory factoryOriginal = buildVBox(20, "Btn Orgnal");
		final NodeFactory factoryTarget = buildVBox(20, "Btn Target");

		final Triplet<SuiSceneController, SuiBaseNode, SuiBaseNode> testData = buildTest(factoryOriginal, factoryTarget);
		final SuiSceneController context = testData.getLeft();
		final SuiBaseNode nodeOriginal = testData.getMiddle();
		final SuiBaseNode nodeTarget = testData.getRight();

		removeChildNodes(nodeOriginal, 15);

		printChildButtons("Original", nodeOriginal);
		printChildButtons("  Target", nodeTarget);

		final StandardMutationStrategy strategy = new StandardMutationStrategy();

		final StrategyDecisionResult decisionResult = strategy.canBeAppliedTo(nodeOriginal, nodeTarget, false);
		assertThat(decisionResult.isApplicable()).isTrue();

		final MutationResult mutationResult = strategy.mutate(
				nodeOriginal, nodeTarget,
				StrategyDecisionResult.APPLICABLE_NO_EXTRA_DATA);
		printChildButtons("  Result", nodeOriginal);

		assertThat(mutationResult).isEqualTo(MutationResult.MUTATED);
		assertChildren(nodeTarget, nodeOriginal);

	}




	@Test
	public void test_add_children_to_empty_parent_expect_mutation_and_original_now_has_children() {

		final NodeFactory factoryOriginal = buildVBox(0, "Btn Orgnal");
		final NodeFactory factoryTarget = buildVBox(20, "Btn Target");

		final Triplet<SuiSceneController, SuiBaseNode, SuiBaseNode> testData = buildTest(factoryOriginal, factoryTarget);
		final SuiSceneController context = testData.getLeft();
		final SuiBaseNode nodeOriginal = testData.getMiddle();
		final SuiBaseNode nodeTarget = testData.getRight();

		printChildButtons("Original", nodeOriginal);
		printChildButtons("  Target", nodeTarget);

		final StandardMutationStrategy strategy = new StandardMutationStrategy();

		final StrategyDecisionResult decisionResult = strategy.canBeAppliedTo(nodeOriginal, nodeTarget, false);
		assertThat(decisionResult.isApplicable()).isTrue();

		final MutationResult mutationResult = strategy.mutate(
				nodeOriginal, nodeTarget,
				StrategyDecisionResult.APPLICABLE_NO_EXTRA_DATA);
		printChildButtons("  Result", nodeOriginal);

		assertThat(mutationResult).isEqualTo(MutationResult.MUTATED);
		assertChildren(nodeTarget, nodeOriginal);

	}




	@Test
	public void test_shuffle_some_existing_children_expect_mutation_and_original_matching_target() {

		final NodeFactory factoryOriginal = buildVBox(20, "Btn Orgnal");
		final NodeFactory factoryTarget = buildVBox(20, "Btn Target");

		final Triplet<SuiSceneController, SuiBaseNode, SuiBaseNode> testData = buildTest(factoryOriginal, factoryTarget);
		final SuiSceneController context = testData.getLeft();
		final SuiBaseNode nodeOriginal = testData.getMiddle();
		final SuiBaseNode nodeTarget = testData.getRight();

		shuffleChildNodes(nodeTarget, 3);

		printChildButtons("Original", nodeOriginal);
		printChildButtons("  Target", nodeTarget);

		final StandardMutationStrategy strategy = new StandardMutationStrategy();

		final StrategyDecisionResult decisionResult = strategy.canBeAppliedTo(nodeOriginal, nodeTarget, false);
		assertThat(decisionResult.isApplicable()).isTrue();

		final MutationResult mutationResult = strategy.mutate(
				nodeOriginal, nodeTarget,
				StrategyDecisionResult.APPLICABLE_NO_EXTRA_DATA);
		printChildButtons("  Result", nodeOriginal);

		assertThat(mutationResult).isEqualTo(MutationResult.MUTATED);
		assertChildren(nodeTarget, nodeOriginal);

	}




	@Test
	public void test_shuffle_most_existing_children_expect_mutation_and_original_matching_target() {

		final NodeFactory factoryOriginal = buildVBox(20, "Btn Orgnal");
		final NodeFactory factoryTarget = buildVBox(20, "Btn Target");

		final Triplet<SuiSceneController, SuiBaseNode, SuiBaseNode> testData = buildTest(factoryOriginal, factoryTarget);
		final SuiSceneController context = testData.getLeft();
		final SuiBaseNode nodeOriginal = testData.getMiddle();
		final SuiBaseNode nodeTarget = testData.getRight();

		shuffleChildNodes(nodeTarget, 20);

		printChildButtons("Original", nodeOriginal);
		printChildButtons("  Target", nodeTarget);

		final StandardMutationStrategy strategy = new StandardMutationStrategy();

		final StrategyDecisionResult decisionResult = strategy.canBeAppliedTo(nodeOriginal, nodeTarget, false);
		assertThat(decisionResult.isApplicable()).isTrue();

		final MutationResult mutationResult = strategy.mutate(
				nodeOriginal, nodeTarget,
				StrategyDecisionResult.APPLICABLE_NO_EXTRA_DATA);
		printChildButtons("  Result", nodeOriginal);

		assertThat(mutationResult).isEqualTo(MutationResult.MUTATED);
		assertChildren(nodeTarget, nodeOriginal);
	}


}
