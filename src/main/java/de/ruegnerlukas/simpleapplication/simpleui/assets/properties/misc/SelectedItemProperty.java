package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.jfxelements.ExtendedChoiceBox;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import lombok.Getter;

import java.util.Objects;
import java.util.function.BiFunction;

public class SelectedItemProperty<T> extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<SelectedItemProperty, SelectedItemProperty, Boolean> COMPARATOR =
			(a, b) -> Objects.equals(a.getSelected(), b.getSelected());

	/**
	 * The selected item.
	 */
	@Getter
	private final T selected;




	/**
	 * @param selected the selected item
	 */
	public SelectedItemProperty(final T selected) {
		super(SelectedItemProperty.class, COMPARATOR);
		this.selected = selected;
	}




	public static class ChoiceBoxUpdatingBuilder<T> implements PropFxNodeUpdatingBuilder<SelectedItemProperty<T>, ExtendedChoiceBox<T>> {


		@Override
		public void build(final SuiNode node, final SelectedItemProperty<T> property, final ExtendedChoiceBox<T> fxNode) {
			fxNode.selectItem(property.getSelected());
		}




		@Override
		public MutationResult update(final SelectedItemProperty<T> property, final SuiNode node, final ExtendedChoiceBox<T> fxNode) {
			fxNode.selectItem(property.getSelected());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final SelectedItemProperty<T> property, final SuiNode node, final ExtendedChoiceBox<T> fxNode) {
			return MutationResult.MUTATED;
		}

	}


}
