package de.ruegnerlukas.simpleapplication.core.simpleui.core.mutation.strategies;

import de.ruegnerlukas.simpleapplication.common.utils.Pair;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.mutation.stategies.AddAllStrategy;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.mutation.stategies.StrategyDecisionResult;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.NodeFactory;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.registry.SuiRegistry;
import de.ruegnerlukas.simpleapplication.common.tags.Tags;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static de.ruegnerlukas.simpleapplication.core.simpleui.testutils.StrategyTestUtils.assertChildren;
import static de.ruegnerlukas.simpleapplication.core.simpleui.testutils.StrategyTestUtils.buildTest;
import static de.ruegnerlukas.simpleapplication.core.simpleui.testutils.StrategyTestUtils.buildVBox;
import static de.ruegnerlukas.simpleapplication.core.simpleui.testutils.StrategyTestUtils.printChildButtons;
import static de.ruegnerlukas.simpleapplication.core.simpleui.testutils.StrategyTestUtils.removeChildNodes;
import static de.ruegnerlukas.simpleapplication.core.simpleui.testutils.StrategyTestUtils.shuffleChildNodes;
import static org.assertj.core.api.Assertions.assertThat;

public class AddAllStrategyTest extends ApplicationTest {


	@Override
	public void start(Stage stage) {
		SuiRegistry.initialize();
	}




	@Test
	public void test_identical_children_expect_not_applicable() {

		final NodeFactory factoryOriginal = buildVBox(20, "Btn Orgnal");
		final NodeFactory factoryTarget = buildVBox(20, "Btn Target");

		final Pair<SuiNode, SuiNode> testData = buildTest(factoryOriginal, factoryTarget);
		final SuiNode nodeOriginal = testData.getLeft();
		final SuiNode nodeTarget = testData.getRight();

		printChildButtons("Original", nodeOriginal);
		printChildButtons("  Target", nodeTarget);

		final AddAllStrategy strategy = new AddAllStrategy();

		final StrategyDecisionResult decisionResult = strategy.canBeAppliedTo(nodeOriginal, nodeTarget, false);
		assertThat(decisionResult.isApplicable()).isFalse();
	}




	@Test
	public void test_remove_some_children_expect_not_applicable() {

		final NodeFactory factoryOriginal = buildVBox(20, "Btn Orgnal");
		final NodeFactory factoryTarget = buildVBox(20, "Btn Target");

		final Pair<SuiNode, SuiNode> testData = buildTest(factoryOriginal, factoryTarget);
		final SuiNode nodeOriginal = testData.getLeft();
		final SuiNode nodeTarget = testData.getRight();

		removeChildNodes(nodeTarget, 5);

		printChildButtons("Original", nodeOriginal);
		printChildButtons("  Target", nodeTarget);

		final AddAllStrategy strategy = new AddAllStrategy();

		final StrategyDecisionResult decisionResult = strategy.canBeAppliedTo(nodeOriginal, nodeTarget, false);
		assertThat(decisionResult.isApplicable()).isFalse();


	}




	@Test
	public void test_remove_most_children_expect_not_applicable() {

		final NodeFactory factoryOriginal = buildVBox(20, "Btn Orgnal");
		final NodeFactory factoryTarget = buildVBox(20, "Btn Target");

		final Pair<SuiNode, SuiNode> testData = buildTest(factoryOriginal, factoryTarget);
		final SuiNode nodeOriginal = testData.getLeft();
		final SuiNode nodeTarget = testData.getRight();

		removeChildNodes(nodeTarget, 15);

		printChildButtons("Original", nodeOriginal);
		printChildButtons("  Target", nodeTarget);

		final AddAllStrategy strategy = new AddAllStrategy();

		final StrategyDecisionResult decisionResult = strategy.canBeAppliedTo(nodeOriginal, nodeTarget, false);
		assertThat(decisionResult.isApplicable()).isFalse();

	}




	@Test
	public void test_remove_all_children_expect_not_applicable() {

		final NodeFactory factoryOriginal = buildVBox(20, "Btn Orgnal");
		final NodeFactory factoryTarget = buildVBox(0, "Btn Target");

		final Pair<SuiNode, SuiNode> testData = buildTest(factoryOriginal, factoryTarget);
		final SuiNode nodeOriginal = testData.getLeft();
		final SuiNode nodeTarget = testData.getRight();

		printChildButtons("Original", nodeOriginal);
		printChildButtons("  Target", nodeTarget);

		final AddAllStrategy strategy = new AddAllStrategy();

		final StrategyDecisionResult decisionResult = strategy.canBeAppliedTo(nodeOriginal, nodeTarget, false);
		assertThat(decisionResult.isApplicable()).isFalse();

	}




	@Test
	public void test_add_some_to_existing_children_expect_not_applicable() {

		final NodeFactory factoryOriginal = buildVBox(20, "Btn Orgnal");
		final NodeFactory factoryTarget = buildVBox(20, "Btn Target");

		final Pair<SuiNode, SuiNode> testData = buildTest(factoryOriginal, factoryTarget);
		final SuiNode nodeOriginal = testData.getLeft();
		final SuiNode nodeTarget = testData.getRight();

		removeChildNodes(nodeOriginal, 5);

		printChildButtons("Original", nodeOriginal);
		printChildButtons("  Target", nodeTarget);

		final AddAllStrategy strategy = new AddAllStrategy();

		final StrategyDecisionResult decisionResult = strategy.canBeAppliedTo(nodeOriginal, nodeTarget, false);
		assertThat(decisionResult.isApplicable()).isFalse();


	}




	@Test
	public void test_add_many_to_existing_children_expect_not_applicable() {

		final NodeFactory factoryOriginal = buildVBox(20, "Btn Orgnal");
		final NodeFactory factoryTarget = buildVBox(20, "Btn Target");

		final Pair<SuiNode, SuiNode> testData = buildTest(factoryOriginal, factoryTarget);
		final SuiNode nodeOriginal = testData.getLeft();
		final SuiNode nodeTarget = testData.getRight();

		removeChildNodes(nodeOriginal, 15);

		printChildButtons("Original", nodeOriginal);
		printChildButtons("  Target", nodeTarget);

		final AddAllStrategy strategy = new AddAllStrategy();

		final StrategyDecisionResult decisionResult = strategy.canBeAppliedTo(nodeOriginal, nodeTarget, false);
		assertThat(decisionResult.isApplicable()).isFalse();


	}




	@Test
	public void test_add_children_to_empty_parent_expect_mutated_and_parent_now_has_children() {

		final NodeFactory factoryOriginal = buildVBox(0, "Btn Orgnal");
		final NodeFactory factoryTarget = buildVBox(20, "Btn Target");

		final Pair<SuiNode, SuiNode> testData = buildTest(factoryOriginal, factoryTarget);
		final SuiNode nodeOriginal = testData.getLeft();
		final SuiNode nodeTarget = testData.getRight();

		printChildButtons("Original", nodeOriginal);
		printChildButtons("  Target", nodeTarget);

		final AddAllStrategy strategy = new AddAllStrategy();

		final StrategyDecisionResult decisionResult = strategy.canBeAppliedTo(nodeOriginal, nodeTarget, false);
		assertThat(decisionResult.isApplicable()).isTrue();

		final MutationResult mutationResult = strategy.mutate(
				nodeOriginal, nodeTarget, Tags.empty(),
				StrategyDecisionResult.APPLICABLE_NO_EXTRA_DATA);
		printChildButtons("  Result", nodeOriginal);

		assertThat(mutationResult).isEqualTo(MutationResult.MUTATED);
		assertThat(nodeOriginal.getChildNodeStore().count()).isEqualTo(20);
		assertChildren(nodeTarget, nodeOriginal);

	}




	@Test
	public void test_shuffle_some_existing_children_expect_not_applicable() {

		final NodeFactory factoryOriginal = buildVBox(20, "Btn Orgnal");
		final NodeFactory factoryTarget = buildVBox(20, "Btn Target");

		final Pair<SuiNode, SuiNode> testData = buildTest(factoryOriginal, factoryTarget);
		final SuiNode nodeOriginal = testData.getLeft();
		final SuiNode nodeTarget = testData.getRight();

		shuffleChildNodes(nodeTarget, 3);

		printChildButtons("Original", nodeOriginal);
		printChildButtons("  Target", nodeTarget);

		final AddAllStrategy strategy = new AddAllStrategy();

		final StrategyDecisionResult decisionResult = strategy.canBeAppliedTo(nodeOriginal, nodeTarget, false);
		assertThat(decisionResult.isApplicable()).isFalse();


	}




	@Test
	public void test_shuffle_most_existing_children_expect_not_applicable() {

		final NodeFactory factoryOriginal = buildVBox(20, "Btn Orgnal");
		final NodeFactory factoryTarget = buildVBox(20, "Btn Target");

		final Pair<SuiNode, SuiNode> testData = buildTest(factoryOriginal, factoryTarget);
		final SuiNode nodeOriginal = testData.getLeft();
		final SuiNode nodeTarget = testData.getRight();

		shuffleChildNodes(nodeTarget, 20);

		printChildButtons("Original", nodeOriginal);
		printChildButtons("  Target", nodeTarget);

		final AddAllStrategy strategy = new AddAllStrategy();

		final StrategyDecisionResult decisionResult = strategy.canBeAppliedTo(nodeOriginal, nodeTarget, false);
		assertThat(decisionResult.isApplicable()).isFalse();

	}


}
