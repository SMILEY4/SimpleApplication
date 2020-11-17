package de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements;


import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.Provider;
import de.ruegnerlukas.simpleapplication.common.tags.Tags;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.jfxelements.ExtendedRadioButton;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.jfxelements.ExtendedToggleButton;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.builders.AbstractFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.NodeFactory;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.registry.SuiRegistry;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.state.SuiState;
import javafx.scene.Node;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@Slf4j
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
						onToggleSelected(next);
					}
				});
			}
			final SuiRegistry suiRegistry = new Provider<>(SuiRegistry.class).get();
			suiRegistry.registerToggleGroup(groupId, group);
			return NodeFactory.EMPTY_NODE;
		}




		/**
		 * Called when a new toggle of the group was selected
		 *
		 * @param selected the new selected toggle
		 */
		private void onToggleSelected(final Toggle selected) {
			final Object suiNodeId = selected.getUserData();
			if (suiNodeId != null) {
				selectionListener.accept((String) suiNodeId);
			} else {
				if (selected instanceof ExtendedRadioButton) {
					selectionListener.accept(((ExtendedRadioButton) selected).getText());
				} else if (selected instanceof ExtendedToggleButton) {
					selectionListener.accept(((ExtendedToggleButton) selected).getText());
				} else {
					log.warn("Unknown toggle type: " + selected);
				}
			}
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
