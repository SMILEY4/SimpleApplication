package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiTabPane;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.builders.FactoryExtension;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import lombok.Getter;

import java.util.function.BiFunction;

public class TitleProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<TitleProperty, TitleProperty, Boolean> COMPARATOR =
			(a, b) -> a.getTitle().equals(b.getTitle());


	/**
	 * The title of the element (used for tabs, titled-panes, ...).
	 */
	@Getter
	private final String title;




	/**
	 * @param title the title of the tab (used for tabs, titled-panes, ...).
	 */
	public TitleProperty(final String title) {
		super(TitleProperty.class, COMPARATOR);
		this.title = title;
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param title the title of this element
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T title(final String title) {
			getBuilderProperties().add(new TitleProperty(title));
			return (T) this;
		}

	}






	public static class UpdatingBuilder implements PropFxNodeUpdatingBuilder<TitleProperty, Node> {


		@Override
		public void build(final SuiNode node,
						  final TitleProperty property,
						  final Node fxNode) {
			setTabTitle(fxNode, property.getTitle());

		}




		@Override
		public MutationResult update(final TitleProperty property,
									 final SuiNode node,
									 final Node fxNode) {
			setTabTitle(fxNode, property.getTitle());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final TitleProperty property,
									 final SuiNode node,
									 final Node fxNode) {
			setTabTitle(fxNode, "no-title");
			return MutationResult.MUTATED;
		}




		/**
		 * Set the title of the parent tab of the given node to the given title
		 *
		 * @param contentNode the content of the tab
		 * @param title       the new title of the tab
		 */
		private void setTabTitle(final Node contentNode, final String title) {
			final Tab tab = SuiTabPane.findParentTab(contentNode);
			if (tab != null) {
				tab.setText(title);
			}
		}


	}


}



