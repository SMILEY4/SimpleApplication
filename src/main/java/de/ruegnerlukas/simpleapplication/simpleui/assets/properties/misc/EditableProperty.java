package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Property;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiBaseNode;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.TextInputControl;
import lombok.Getter;

public class EditableProperty extends Property {


	/**
	 * Whether the element is editable.
	 */
	@Getter
	private final boolean editable;




	/**
	 * @param editable whether the element is editable
	 */
	public EditableProperty(final boolean editable) {
		super(EditableProperty.class);
		this.editable = editable;
	}




	@Override
	protected boolean isPropertyEqual(final Property other) {
		return editable == ((EditableProperty) other).isEditable();
	}




	@Override
	public String printValue() {
		return isEditable() ? "editable" : "notEditable";
	}




	public static class TextInputControlUpdatingBuilder implements PropFxNodeUpdatingBuilder<EditableProperty, TextInputControl> {


		@Override
		public void build(final SuiBaseNode node,
						  final EditableProperty property,
						  final TextInputControl fxNode) {
			fxNode.setEditable(property.isEditable());
		}




		@Override
		public MutationResult update(final EditableProperty property,
									 final SuiBaseNode node,
									 final TextInputControl fxNode) {
			fxNode.setEditable(property.isEditable());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final EditableProperty property,
									 final SuiBaseNode node,
									 final TextInputControl fxNode) {
			fxNode.setEditable(true);
			return MutationResult.MUTATED;
		}

	}






	public static class ComboBoxBaseUpdatingBuilder implements PropFxNodeUpdatingBuilder<EditableProperty, ComboBoxBase<?>> {


		@Override
		public void build(final SuiBaseNode node,
						  final EditableProperty property,
						  final ComboBoxBase<?> fxNode) {
			fxNode.setEditable(property.isEditable());
		}




		@Override
		public MutationResult update(final EditableProperty property,
									 final SuiBaseNode node,
									 final ComboBoxBase<?> fxNode) {
			fxNode.setEditable(property.isEditable());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final EditableProperty property,
									 final SuiBaseNode node,
									 final ComboBoxBase<?> fxNode) {
			fxNode.setEditable(false);
			return MutationResult.MUTATED;
		}

	}


}



