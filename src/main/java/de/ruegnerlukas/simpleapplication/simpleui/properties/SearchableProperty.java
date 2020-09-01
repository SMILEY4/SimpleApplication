package de.ruegnerlukas.simpleapplication.simpleui.properties;


import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;
import javafx.scene.control.ComboBoxBase;
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




	public static class ComboBoxBaseUpdatingBuilder implements PropFxNodeUpdatingBuilder<SearchableProperty, ComboBoxBase<?>> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers, final SuiNode node, final SearchableProperty property,
						  final ComboBoxBase<?> fxNode) {
			// do nothing, decision is made when building javafx node, see SuiTextComboBox.FxNodeBuilder
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers, final SearchableProperty property,
									 final SuiNode node, final ComboBoxBase<?> fxNode) {
			return MutationResult.REQUIRES_REBUILD;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers, final SearchableProperty property,
									 final SuiNode node, final ComboBoxBase<?> fxNode) {
			return MutationResult.REQUIRES_REBUILD;
		}

	}


}



