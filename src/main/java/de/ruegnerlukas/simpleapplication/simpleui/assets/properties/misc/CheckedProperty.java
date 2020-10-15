package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.jfxelements.ExtendedCheckbox;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import lombok.Getter;

import java.util.function.BiFunction;

public class CheckedProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<CheckedProperty, CheckedProperty, Boolean> COMPARATOR =
			(a, b) -> a.isChecked() == b.isChecked();

	/**
	 * Whether the checkbox is selected.
	 */
	@Getter
	private final boolean checked;




	/**
	 * @param checked whether the checkbox is selected.
	 */
	public CheckedProperty(final boolean checked) {
		super(CheckedProperty.class, COMPARATOR);
		this.checked = checked;
	}




	public static class CheckBoxUpdatingBuilder implements PropFxNodeUpdatingBuilder<CheckedProperty, ExtendedCheckbox> {


		@Override
		public void build(final SuiNode node, final CheckedProperty property, final ExtendedCheckbox fxNode) {
			fxNode.select(property.isChecked());
		}




		@Override
		public MutationResult update(final CheckedProperty property, final SuiNode node, final ExtendedCheckbox fxNode) {
			fxNode.select(property.isChecked());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final CheckedProperty property, final SuiNode node, final ExtendedCheckbox fxNode) {
			return MutationResult.MUTATED;
		}

	}


}