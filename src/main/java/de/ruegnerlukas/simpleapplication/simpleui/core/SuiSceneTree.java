package de.ruegnerlukas.simpleapplication.simpleui.core;

import de.ruegnerlukas.simpleapplication.simpleui.core.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiBaseNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor (access = AccessLevel.PRIVATE)
public class SuiSceneTree {


	/**
	 * The root node of this tree.
	 */
	@Getter
	private final SuiBaseNode root;




	/**
	 * @param nodeFactory the node factory (of the root node)
	 * @return the created scene tree
	 */
	public static SuiSceneTree build(final NodeFactory nodeFactory, final SuiState state) {
		final SuiBaseNode rootNode = nodeFactory.create(state);
		return new SuiSceneTree(rootNode);
	}




	/**
	 * Mutate this tree to match the given target tree
	 *
	 * @param targetTree the target tree to match
	 * @return true, when the root node was rebuild
	 */
	public boolean mutate(final SuiSceneTree targetTree) {
		// todo
		return false;
	}


}