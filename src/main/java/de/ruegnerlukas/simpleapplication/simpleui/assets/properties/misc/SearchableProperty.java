package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.jfxelements.ExtendedComboBox;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import lombok.Getter;

import java.util.function.BiFunction;

public class SearchableProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<SearchableProperty, SearchableProperty, Boolean> COMPARATOR =
			(a, b) -> a.isSearchable() == b.isSearchable();

	/**
	 * Whether the element is searchable.
	 */
	@Getter
	private final boolean searchable;




	/**
	 * @param searchable whether the element is searchable
	 */
	public SearchableProperty(final boolean searchable) {
		super(SearchableProperty.class, COMPARATOR);
		this.searchable = searchable;
	}




	/**
	 * Utility method to check if the given node is searchable
	 *
	 * @param node the node to check
	 * @return whether the node is searchable
	 */
	public static boolean isSearchable(final SuiNode node) {
		return node.getPropertyStore().getSafe(SearchableProperty.class)
				.map(SearchableProperty::isSearchable)
				.orElse(false);
	}




	public static class ComboBoxUpdatingBuilder implements PropFxNodeUpdatingBuilder<SearchableProperty, ExtendedComboBox<?>> {


		@Override
		public void build(final SuiNode node, final SearchableProperty property, final ExtendedComboBox<?> fxNode) {
			fxNode.setSearchable(property.isSearchable());
		}




		@Override
		public MutationResult update(final SearchableProperty property, final SuiNode node, final ExtendedComboBox<?> fxNode) {
			fxNode.setSearchable(property.isSearchable());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final SearchableProperty property, final SuiNode node, final ExtendedComboBox<?> fxNode) {
			fxNode.setSearchable(false);
			return MutationResult.MUTATED;
		}

	}


}



