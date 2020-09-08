package de.ruegnerlukas.simpleapplication.simpleui.strategies;

import de.ruegnerlukas.simpleapplication.common.utils.Triplet;
import de.ruegnerlukas.simpleapplication.simpleui.core.SuiSceneController;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.stategies.IdMutationStrategy;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.stategies.StrategyDecisionResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiBaseNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static de.ruegnerlukas.simpleapplication.simpleui.strategies.StrategyTestUtils.assertChildren;
import static de.ruegnerlukas.simpleapplication.simpleui.strategies.StrategyTestUtils.buildTest;
import static de.ruegnerlukas.simpleapplication.simpleui.strategies.StrategyTestUtils.buildVBox;
import static de.ruegnerlukas.simpleapplication.simpleui.strategies.StrategyTestUtils.printChildButtons;
import static de.ruegnerlukas.simpleapplication.simpleui.strategies.StrategyTestUtils.removeChildNodes;
import static de.ruegnerlukas.simpleapplication.simpleui.strategies.StrategyTestUtils.shuffleChildNodes;
import static org.assertj.core.api.Assertions.assertThat;

public class IdMutationStrategyTest extends ApplicationTest {


	@Override
	public void start(Stage stage) {
		SuiRegistry.initialize();
	}




	@Test
	public void testNoMutation() {

		final NodeFactory factoryOriginal = buildVBox(20, "Btn Orgnal", true);
		final NodeFactory factoryTarget = buildVBox(20, "Btn Target", true);

		final Triplet<SuiSceneController, SuiBaseNode, SuiBaseNode> testData = buildTest(factoryOriginal, factoryTarget);
		final SuiSceneController context = testData.getLeft();
		final SuiBaseNode nodeOriginal = testData.getMiddle();
		final SuiBaseNode nodeTarget = testData.getRight();

		printChildButtons("Original", nodeOriginal);
		printChildButtons("  Target", nodeTarget);

		final IdMutationStrategy strategy = new IdMutationStrategy();

		final StrategyDecisionResult decisionResult = strategy.canBeAppliedTo(nodeOriginal, nodeTarget, true);
		assertThat(decisionResult.isApplicable()).isTrue();

		final MutationResult mutationResult = strategy.mutate(
				nodeOriginal, nodeTarget,
				StrategyDecisionResult.APPLICABLE_NO_EXTRA_DATA);
		printChildButtons("  Result", nodeOriginal);

		assertThat(mutationResult).isEqualTo(MutationResult.MUTATED);
		assertChildren(nodeTarget, nodeOriginal);

	}




	@Test
	public void testRemoveSome() {

		final NodeFactory factoryOriginal = buildVBox(20, "Btn Orgnal", true);
		final NodeFactory factoryTarget = buildVBox(20, "Btn Target", true);

		final Triplet<SuiSceneController, SuiBaseNode, SuiBaseNode> testData = buildTest(factoryOriginal, factoryTarget);
		final SuiSceneController context = testData.getLeft();
		final SuiBaseNode nodeOriginal = testData.getMiddle();
		final SuiBaseNode nodeTarget = testData.getRight();

		removeChildNodes(nodeTarget, 5);

		printChildButtons("Original", nodeOriginal);
		printChildButtons("  Target", nodeTarget);

		final IdMutationStrategy strategy = new IdMutationStrategy();

		final StrategyDecisionResult decisionResult = strategy.canBeAppliedTo(nodeOriginal, nodeTarget, true);
		assertThat(decisionResult.isApplicable()).isTrue();

		final MutationResult mutationResult = strategy.mutate(
				nodeOriginal, nodeTarget,
				StrategyDecisionResult.APPLICABLE_NO_EXTRA_DATA);
		printChildButtons("  Result", nodeOriginal);

		assertThat(mutationResult).isEqualTo(MutationResult.MUTATED);
		assertChildren(nodeTarget, nodeOriginal);

	}




	@Test
	public void testRemoveMost() {

		final NodeFactory factoryOriginal = buildVBox(20, "Btn Orgnal", true);
		final NodeFactory factoryTarget = buildVBox(20, "Btn Target", true);

		final Triplet<SuiSceneController, SuiBaseNode, SuiBaseNode> testData = buildTest(factoryOriginal, factoryTarget);
		final SuiSceneController context = testData.getLeft();
		final SuiBaseNode nodeOriginal = testData.getMiddle();
		final SuiBaseNode nodeTarget = testData.getRight();

		removeChildNodes(nodeTarget, 15);

		printChildButtons("Original", nodeOriginal);
		printChildButtons("  Target", nodeTarget);

		final IdMutationStrategy strategy = new IdMutationStrategy();

		final StrategyDecisionResult decisionResult = strategy.canBeAppliedTo(nodeOriginal, nodeTarget, true);
		assertThat(decisionResult.isApplicable()).isTrue();

		final MutationResult mutationResult = strategy.mutate(
				nodeOriginal, nodeTarget,
				StrategyDecisionResult.APPLICABLE_NO_EXTRA_DATA);
		printChildButtons("  Result", nodeOriginal);

		assertThat(mutationResult).isEqualTo(MutationResult.MUTATED);
		assertChildren(nodeTarget, nodeOriginal);

	}




	@Test
	public void testRemoveAll() {

		final NodeFactory factoryOriginal = buildVBox(20, "Btn Orgnal", true);
		final NodeFactory factoryTarget = buildVBox(0, "Btn Target", true);

		final Triplet<SuiSceneController, SuiBaseNode, SuiBaseNode> testData = buildTest(factoryOriginal, factoryTarget);
		final SuiSceneController context = testData.getLeft();
		final SuiBaseNode nodeOriginal = testData.getMiddle();
		final SuiBaseNode nodeTarget = testData.getRight();

		printChildButtons("Original", nodeOriginal);
		printChildButtons("  Target", nodeTarget);

		final IdMutationStrategy strategy = new IdMutationStrategy();

		final StrategyDecisionResult decisionResult = strategy.canBeAppliedTo(nodeOriginal, nodeTarget, true);
		assertThat(decisionResult.isApplicable()).isTrue();

		final MutationResult mutationResult = strategy.mutate(
				nodeOriginal, nodeTarget,
				StrategyDecisionResult.APPLICABLE_NO_EXTRA_DATA);
		printChildButtons("  Result", nodeOriginal);

		assertThat(mutationResult).isEqualTo(MutationResult.MUTATED);
		assertChildren(nodeTarget, nodeOriginal);

	}




	@Test
	public void testAddSome() {

		final NodeFactory factoryOriginal = buildVBox(20, "Btn Orgnal", true);
		final NodeFactory factoryTarget = buildVBox(20, "Btn Target", true);

		final Triplet<SuiSceneController, SuiBaseNode, SuiBaseNode> testData = buildTest(factoryOriginal, factoryTarget);
		final SuiSceneController context = testData.getLeft();
		final SuiBaseNode nodeOriginal = testData.getMiddle();
		final SuiBaseNode nodeTarget = testData.getRight();

		removeChildNodes(nodeOriginal, 5);

		printChildButtons("Original", nodeOriginal);
		printChildButtons("  Target", nodeTarget);

		final IdMutationStrategy strategy = new IdMutationStrategy();

		final StrategyDecisionResult decisionResult = strategy.canBeAppliedTo(nodeOriginal, nodeTarget, true);
		assertThat(decisionResult.isApplicable()).isTrue();

		final MutationResult mutationResult = strategy.mutate(
				nodeOriginal, nodeTarget,
				StrategyDecisionResult.APPLICABLE_NO_EXTRA_DATA);
		printChildButtons("  Result", nodeOriginal);

		assertThat(mutationResult).isEqualTo(MutationResult.MUTATED);
		assertChildren(nodeTarget, nodeOriginal);

	}




	@Test
	public void testAddMost() {

		final NodeFactory factoryOriginal = buildVBox(20, "Btn Orgnal", true);
		final NodeFactory factoryTarget = buildVBox(20, "Btn Target", true);

		final Triplet<SuiSceneController, SuiBaseNode, SuiBaseNode> testData = buildTest(factoryOriginal, factoryTarget);
		final SuiSceneController context = testData.getLeft();
		final SuiBaseNode nodeOriginal = testData.getMiddle();
		final SuiBaseNode nodeTarget = testData.getRight();

		removeChildNodes(nodeOriginal, 15);

		printChildButtons("Original", nodeOriginal);
		printChildButtons("  Target", nodeTarget);

		final IdMutationStrategy strategy = new IdMutationStrategy();

		final StrategyDecisionResult decisionResult = strategy.canBeAppliedTo(nodeOriginal, nodeTarget, true);
		assertThat(decisionResult.isApplicable()).isTrue();

		final MutationResult mutationResult = strategy.mutate(
				nodeOriginal, nodeTarget,
				StrategyDecisionResult.APPLICABLE_NO_EXTRA_DATA);
		printChildButtons("  Result", nodeOriginal);

		assertThat(mutationResult).isEqualTo(MutationResult.MUTATED);
		assertChildren(nodeTarget, nodeOriginal);

	}




	@Test
	public void testAddAll() {

		final NodeFactory factoryOriginal = buildVBox(0, "Btn Orgnal", true);
		final NodeFactory factoryTarget = buildVBox(20, "Btn Target", true);

		final Triplet<SuiSceneController, SuiBaseNode, SuiBaseNode> testData = buildTest(factoryOriginal, factoryTarget);
		final SuiSceneController context = testData.getLeft();
		final SuiBaseNode nodeOriginal = testData.getMiddle();
		final SuiBaseNode nodeTarget = testData.getRight();

		printChildButtons("Original", nodeOriginal);
		printChildButtons("  Target", nodeTarget);

		final IdMutationStrategy strategy = new IdMutationStrategy();

		final StrategyDecisionResult decisionResult = strategy.canBeAppliedTo(nodeOriginal, nodeTarget, true);
		assertThat(decisionResult.isApplicable()).isTrue();

		final MutationResult mutationResult = strategy.mutate(
				nodeOriginal, nodeTarget,
				StrategyDecisionResult.APPLICABLE_NO_EXTRA_DATA);
		printChildButtons("  Result", nodeOriginal);

		assertThat(mutationResult).isEqualTo(MutationResult.MUTATED);
		assertChildren(nodeTarget, nodeOriginal);

	}




	@Test
	public void shuffleSome() {

		final NodeFactory factoryOriginal = buildVBox(20, "Btn Orgnal", true);
		final NodeFactory factoryTarget = buildVBox(20, "Btn Target", true);

		final Triplet<SuiSceneController, SuiBaseNode, SuiBaseNode> testData = buildTest(factoryOriginal, factoryTarget);
		final SuiSceneController context = testData.getLeft();
		final SuiBaseNode nodeOriginal = testData.getMiddle();
		final SuiBaseNode nodeTarget = testData.getRight();

		shuffleChildNodes(nodeTarget, 3);

		printChildButtons("Original", nodeOriginal);
		printChildButtons("  Target", nodeTarget);

		final IdMutationStrategy strategy = new IdMutationStrategy();

		final StrategyDecisionResult decisionResult = strategy.canBeAppliedTo(nodeOriginal, nodeTarget, true);
		assertThat(decisionResult.isApplicable()).isTrue();

		final MutationResult mutationResult = strategy.mutate(
				nodeOriginal, nodeTarget,
				StrategyDecisionResult.APPLICABLE_NO_EXTRA_DATA);
		printChildButtons("  Result", nodeOriginal);

		assertThat(mutationResult).isEqualTo(MutationResult.MUTATED);
		assertChildren(nodeTarget, nodeOriginal);

	}




	@Test
	public void shuffleMost() {

		final NodeFactory factoryOriginal = buildVBox(20, "Btn Orgnal", true);
		final NodeFactory factoryTarget = buildVBox(20, "Btn Target", true);

		final Triplet<SuiSceneController, SuiBaseNode, SuiBaseNode> testData = buildTest(factoryOriginal, factoryTarget);
		final SuiSceneController context = testData.getLeft();
		final SuiBaseNode nodeOriginal = testData.getMiddle();
		final SuiBaseNode nodeTarget = testData.getRight();

		shuffleChildNodes(nodeTarget, 20);

		printChildButtons("Original", nodeOriginal);
		printChildButtons("  Target", nodeTarget);

		final IdMutationStrategy strategy = new IdMutationStrategy();

		final StrategyDecisionResult decisionResult = strategy.canBeAppliedTo(nodeOriginal, nodeTarget, true);
		assertThat(decisionResult.isApplicable()).isTrue();

		final MutationResult mutationResult = strategy.mutate(
				nodeOriginal, nodeTarget,
				StrategyDecisionResult.APPLICABLE_NO_EXTRA_DATA);
		printChildButtons("  Result", nodeOriginal);

		assertThat(mutationResult).isEqualTo(MutationResult.MUTATED);
		assertChildren(nodeTarget, nodeOriginal);
	}




	@Test
	public void testMissingIds() {

		final NodeFactory factoryOriginal = buildVBox(20, "Btn Orgnal", false);
		final NodeFactory factoryTarget = buildVBox(20, "Btn Target", false);

		final Triplet<SuiSceneController, SuiBaseNode, SuiBaseNode> testData = buildTest(factoryOriginal, factoryTarget);
		final SuiBaseNode nodeOriginal = testData.getMiddle();
		final SuiBaseNode nodeTarget = testData.getRight();

		final IdMutationStrategy strategy = new IdMutationStrategy();

		final StrategyDecisionResult decisionResult = strategy.canBeAppliedTo(nodeOriginal, nodeTarget, false);
		assertThat(decisionResult.isApplicable()).isFalse();
	}



}
