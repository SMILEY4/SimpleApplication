package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Property;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiBaseNode;
import javafx.scene.Node;
import lombok.Getter;

public class SearchableProperty extends Property {


	/**
	 * Whether the element is searchable.
	 */
	@Getter
	private final boolean searchable;




	/**
	 * @param searchable whether the element is searchable
	 */
	public SearchableProperty(final boolean searchable) {
		super(SearchableProperty.class);
		this.searchable = searchable;
	}




	@Override
	protected boolean isPropertyEqual(final Property other) {
		return searchable == ((SearchableProperty) other).isSearchable();
	}




	@Override
	public String printValue() {
		return isSearchable() ? "searchable" : "notSearchable";
	}




	/**
	 * Utility method to check if the given node is searchable
	 *
	 * @param node the node to check
	 * @return whether the node is searchable
	 */
	public static boolean isSearchable(final SuiBaseNode node) {
		return node.getPropertyStore().getSafe(SearchableProperty.class)
				.map(SearchableProperty::isSearchable)
				.orElse(false);
	}




	public static class UpdatingBuilder implements PropFxNodeUpdatingBuilder<SearchableProperty, Node> {


		@Override
		public void build(final SuiBaseNode node,
						  final SearchableProperty property,
						  final Node fxNode) {
			// do nothing, decision is made when building javafx node. For an example, see SuiComboBox.FxNodeBuilder
		}




		@Override
		public MutationResult update(final SearchableProperty property,
									 final SuiBaseNode node,
									 final Node fxNode) {
			return MutationResult.REQUIRES_REBUILD;
		}




		@Override
		public MutationResult remove(final SearchableProperty property,
									 final SuiBaseNode node,
									 final Node fxNode) {
			return MutationResult.REQUIRES_REBUILD;
		}

	}


}



