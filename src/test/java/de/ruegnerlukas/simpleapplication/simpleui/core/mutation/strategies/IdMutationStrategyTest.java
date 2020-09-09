package de.ruegnerlukas.simpleapplication.simpleui.core.mutation.strategies;

import de.ruegnerlukas.simpleapplication.common.utils.Triplet;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiButton;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiLabel;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiVBox;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TextContentProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.SuiSceneController;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.stategies.IdMutationStrategy;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.stategies.StrategyDecisionResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiBaseNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.List;

import static de.ruegnerlukas.simpleapplication.simpleui.testutils.StrategyTestUtils.assertChildren;
import static de.ruegnerlukas.simpleapplication.simpleui.testutils.StrategyTestUtils.buildTest;
import static de.ruegnerlukas.simpleapplication.simpleui.testutils.StrategyTestUtils.buildVBox;
import static de.ruegnerlukas.simpleapplication.simpleui.testutils.StrategyTestUtils.printChildButtons;
import static de.ruegnerlukas.simpleapplication.simpleui.testutils.StrategyTestUtils.removeChildNodes;
import static de.ruegnerlukas.simpleapplication.simpleui.testutils.StrategyTestUtils.shuffleChildNodes;
import static org.assertj.core.api.Assertions.assertThat;

public class IdMutationStrategyTest extends ApplicationTest {


	@Override
	public void start(Stage stage) {
		SuiRegistry.initialize();
	}




	@Test
	public void test_identical_children_expect_mutation_but_no_change() {

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
	public void test_remove_some_children_expect_mutated_and_original_matching_target() {

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
	public void test_remove_most_children_expect_mutated_and_original_matching_target() {

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
	public void test_remove_all_children_expect_mutated_and_all_children_removed() {

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
		assertThat(nodeOriginal.getChildNodeStore().count()).isEqualTo(0);

	}




	@Test
	public void test_add_some_to_existing_children_expect_mutation_and_original_matching_target() {

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
	public void test_add_many_to_existing_children_expect_mutation_and_original_matching_target() {

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
	public void test_add_children_to_empty_parent_mutated_and_parent_now_has_children() {

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
		assertThat(nodeOriginal.getChildNodeStore().count()).isEqualTo(20);
		assertChildren(nodeTarget, nodeOriginal);

	}




	@Test
	public void test_shuffle_some_existing_children_expect_mutation_and_original_matching_target() {

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
	public void test_shuffle_most_existing_children_expect_mutation_and_original_matching_target() {

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
	public void test_with_rebuild_child_expect_mutated_and_child_replaced() {

		final NodeFactory factoryOriginal = SuiVBox.vbox(
				Properties.id("box"),
				Properties.items(
						SuiButton.button(
								Properties.id("child1"),
								Properties.textContent("Button 1")
						),
						SuiButton.button(
								Properties.id("child2"),
								Properties.textContent("Button 2")
						),
						SuiButton.button(
								Properties.id("child3"),
								Properties.textContent("Button 2")
						)
				)
		);

		final NodeFactory factoryTarget = SuiVBox.vbox(
				Properties.id("box"),
				Properties.items(
						SuiButton.button(
								Properties.id("child1"),
								Properties.textContent("Mutated Button 1")
						),
						SuiLabel.label(
								Properties.id("child2"),
								Properties.textContent("Label")
						),
						SuiButton.button(
								Properties.id("child3"),
								Properties.textContent("Mutated Button 2")
						)
				)
		);

		final Triplet<SuiSceneController, SuiBaseNode, SuiBaseNode> testData = buildTest(factoryOriginal, factoryTarget);
		final SuiSceneController context = testData.getLeft();
		final SuiBaseNode nodeOriginal = testData.getMiddle();
		final SuiBaseNode nodeTarget = testData.getRight();

		final IdMutationStrategy strategy = new IdMutationStrategy();

		final StrategyDecisionResult decisionResult = strategy.canBeAppliedTo(nodeOriginal, nodeTarget, true);
		assertThat(decisionResult.isApplicable()).isTrue();

		final MutationResult mutationResult = strategy.mutate(nodeOriginal, nodeTarget, StrategyDecisionResult.APPLICABLE_NO_EXTRA_DATA);
		assertThat(mutationResult).isEqualTo(MutationResult.MUTATED);
		assertThat(nodeOriginal.getChildNodeStore().count()).isEqualTo(3);

		final List<SuiBaseNode> childrenMutated = nodeOriginal.getChildNodeStore().getUnmodifiable();
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
	public void test_with_children_missing_ids_expect_not_applicable() {

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
