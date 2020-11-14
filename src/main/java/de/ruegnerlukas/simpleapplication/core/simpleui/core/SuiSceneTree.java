package de.ruegnerlukas.simpleapplication.core.simpleui.core;

import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.NodeFactory;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.tags.Tags;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.profiler.SuiProfiler;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.state.SuiState;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor (access = AccessLevel.PRIVATE)
public class SuiSceneTree {


	/**
	 * The root node of this tree.
	 */
	@Getter
	private SuiNode root;




	/**
	 * @param nodeFactory the node factory (of the root node)
	 * @return the created scene tree
	 */
	public static SuiSceneTree build(final NodeFactory nodeFactory, final SuiState state, final Tags tags) {
		long timeStart = System.currentTimeMillis();
		final SuiNode rootNode = nodeFactory.create(state, tags);
		SuiProfiler.get().getBuildDuration().count(System.currentTimeMillis() - timeStart);
		return new SuiSceneTree(rootNode);
	}




	/**
	 * Builds the javafx-nodes for the whole tree.
	 */
	public void buildFxNodes() {
		SuiServices.get().enrichWithFxNodes(root);
	}




	/**
	 * Mutate this tree to match the given target tree. This operation may replace the root node of this tree.
	 *
	 * @param targetTree the target tree to match
	 * @param tags       tags associated with the state update triggering this mutation
	 * @return true, when the root node was rebuild
	 */
	public boolean mutate(final SuiSceneTree targetTree, final Tags tags) {
		root = SuiServices.get().mutateTree(this, targetTree, tags).getRoot();
		return root.equals(targetTree.getRoot());
	}


}
