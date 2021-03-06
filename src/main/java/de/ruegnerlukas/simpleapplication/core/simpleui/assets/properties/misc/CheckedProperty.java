package de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.jfxelements.ExtendedCheckbox;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.jfxelements.ExtendedRadioButton;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.jfxelements.ExtendedToggleButton;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.builders.FactoryExtension;
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




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param checked whether the element is checked or unchecked.
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T checked(final boolean checked) {
			getBuilderProperties().add(new CheckedProperty(checked));
			return (T) this;
		}

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






	public static class RadioButtonUpdatingBuilder implements PropFxNodeUpdatingBuilder<CheckedProperty, ExtendedRadioButton> {


		@Override
		public void build(final SuiNode node, final CheckedProperty property, final ExtendedRadioButton fxNode) {
			fxNode.select(property.isChecked());
		}




		@Override
		public MutationResult update(final CheckedProperty property, final SuiNode node, final ExtendedRadioButton fxNode) {
			fxNode.select(property.isChecked());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final CheckedProperty property, final SuiNode node, final ExtendedRadioButton fxNode) {
			return MutationResult.MUTATED;
		}

	}






	public static class ToggleButtonUpdatingBuilder implements PropFxNodeUpdatingBuilder<CheckedProperty, ExtendedToggleButton> {


		@Override
		public void build(final SuiNode node, final CheckedProperty property, final ExtendedToggleButton fxNode) {
			fxNode.select(property.isChecked());
		}




		@Override
		public MutationResult update(final CheckedProperty property, final SuiNode node, final ExtendedToggleButton fxNode) {
			fxNode.select(property.isChecked());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final CheckedProperty property, final SuiNode node, final ExtendedToggleButton fxNode) {
			return MutationResult.MUTATED;
		}

	}


}
