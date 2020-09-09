package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.TextInputControl;
import lombok.Getter;

import java.util.function.BiFunction;

public class EditableProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<EditableProperty, EditableProperty, Boolean> COMPARATOR =
			(a, b) -> a.isEditable() == b.isEditable();

	/**
	 * Whether the element is editable.
	 */
	@Getter
	private final boolean editable;




	/**
	 * @param editable whether the element is editable
	 */
	public EditableProperty(final boolean editable) {
		super(EditableProperty.class, COMPARATOR);
		this.editable = editable;
	}




	public static class TextInputControlUpdatingBuilder implements PropFxNodeUpdatingBuilder<EditableProperty, TextInputControl> {


		@Override
		public void build(final SuiNode node,
						  final EditableProperty property,
						  final TextInputControl fxNode) {
			fxNode.setEditable(property.isEditable());
		}




		@Override
		public MutationResult update(final EditableProperty property,
									 final SuiNode node,
									 final TextInputControl fxNode) {
			fxNode.setEditable(property.isEditable());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final EditableProperty property,
									 final SuiNode node,
									 final TextInputControl fxNode) {
			fxNode.setEditable(true);
			return MutationResult.MUTATED;
		}

	}






	public static class ComboBoxBaseUpdatingBuilder implements PropFxNodeUpdatingBuilder<EditableProperty, ComboBoxBase<?>> {


		@Override
		public void build(final SuiNode node,
						  final EditableProperty property,
						  final ComboBoxBase<?> fxNode) {
			fxNode.setEditable(property.isEditable());
		}




		@Override
		public MutationResult update(final EditableProperty property,
									 final SuiNode node,
									 final ComboBoxBase<?> fxNode) {
			fxNode.setEditable(property.isEditable());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final EditableProperty property,
									 final SuiNode node,
									 final ComboBoxBase<?> fxNode) {
			fxNode.setEditable(false);
			return MutationResult.MUTATED;
		}

	}


}



