package de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements;


import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.Provider;
import de.ruegnerlukas.simpleapplication.common.tags.Tags;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.jfxelements.ExtendedRadioButton;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.builders.AbstractFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.NodeFactory;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.registry.SuiRegistry;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.state.SuiState;
import javafx.scene.Node;
import javafx.scene.control.ToggleGroup;
import lombok.AllArgsConstructor;

import java.util.function.Consumer;

public final class SuiToggleGroup {


	/**
	 * Hidden constructor for utility classes
	 */
	private SuiToggleGroup() {
		// do nothing
	}




	/**
	 * Build a new element
	 *
	 * @param groupId           the id of this toggle group
	 * @param selectionListener the listener listening to the selected item (or null)
	 * @return the builder for the element
	 */
	public static SuiToggleGroupBuilder create(final String groupId, final Consumer<String> selectionListener) {
		return new SuiToggleGroupBuilder(groupId, selectionListener);
	}




	@AllArgsConstructor
	public static class SuiToggleGroupBuilder implements NodeFactory {


		/**
		 * The id of this toggle group
		 */
		private final String groupId;

		/**
		 * The listener listening to the selected item (or null)
		 */
		private final Consumer<String> selectionListener;




		@Override
		public SuiNode create(final SuiState state, final Tags tags) {
			final ToggleGroup group = new ToggleGroup();
			if (selectionListener != null) {
				group.selectedToggleProperty().addListener((value, prev, next) -> {
					if (next != null) {
						final Object suiNodeId = next.getUserData();
						if (suiNodeId != null) {
							selectionListener.accept((String) suiNodeId);
						} else {
							selectionListener.accept(((ExtendedRadioButton) next).getText());
						}
					}
				});
			}
			final SuiRegistry suiRegistry = new Provider<>(SuiRegistry.class).get();
			suiRegistry.registerToggleGroup(groupId, group);
			return NodeFactory.EMPTY_NODE;
		}

	}




	/**
	 * Register this node type at the given registry.
	 *
	 * @param registry the registry
	 */
	public static void register(final SuiRegistry registry) {
		registry.registerBaseFxNodeBuilder(SuiToggleGroup.class, new FxNodeBuilder());
	}




	private static class FxNodeBuilder implements AbstractFxNodeBuilder<Node> {


		@Override
		public Node build(final SuiNode node) {
			return null;
		}

	}

}
