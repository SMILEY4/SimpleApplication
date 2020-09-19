package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiTabPane;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import lombok.Getter;

import java.util.function.BiFunction;

public class TabDisabledProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<TabDisabledProperty, TabDisabledProperty, Boolean> COMPARATOR =
			(a, b) -> a.isDisabled() == b.isDisabled();

	/**
	 * Whether the parent tab is disabled.
	 */
	@Getter
	private final boolean disabled;




	/**
	 * @param disabled whether the parent tab is disabled
	 */
	public TabDisabledProperty(final boolean disabled) {
		super(TabDisabledProperty.class, COMPARATOR);
		this.disabled = disabled;
	}




	public static class UpdatingBuilder implements PropFxNodeUpdatingBuilder<TabDisabledProperty, Node> {


		@Override
		public void build(final SuiNode node,
						  final TabDisabledProperty property,
						  final Node fxNode) {
			setTabDisabled(fxNode, property.isDisabled());
		}




		@Override
		public MutationResult update(final TabDisabledProperty property,
									 final SuiNode node,
									 final Node fxNode) {
			setTabDisabled(fxNode, property.isDisabled());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final TabDisabledProperty property,
									 final SuiNode node,
									 final Node fxNode) {
			setTabDisabled(fxNode, false);
			return MutationResult.MUTATED;
		}




		/**
		 * Enables or disables the parent tab of the given node.
		 *
		 * @param contentNode the content of the tab
		 * @param disable     whether the tab is enabled or disabled
		 */
		private void setTabDisabled(final Node contentNode, final boolean disable) {
			final Tab tab = SuiTabPane.findParentTab(contentNode);
			if (tab != null) {
				tab.setDisable(disable);
			}
		}

	}


}
