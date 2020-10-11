package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiLabeledSlider;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.jfxelements.ExtendedComboBox;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.Pane;
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






	public static class ComboBoxUpdatingBuilder implements PropFxNodeUpdatingBuilder<EditableProperty, ExtendedComboBox<?>> {


		@Override
		public void build(final SuiNode node, final EditableProperty property, final ExtendedComboBox<?> fxNode) {
			fxNode.setType(property.isEditable()
					? ExtendedComboBox.ComboBoxType.EDITABLE
					: (isSearchable(node) ? ExtendedComboBox.ComboBoxType.SEARCHABLE : ExtendedComboBox.ComboBoxType.DEFAULT));
		}




		@Override
		public MutationResult update(final EditableProperty property, final SuiNode node, final ExtendedComboBox<?> fxNode) {
			fxNode.setType(property.isEditable()
					? ExtendedComboBox.ComboBoxType.EDITABLE
					: (isSearchable(node) ? ExtendedComboBox.ComboBoxType.SEARCHABLE : ExtendedComboBox.ComboBoxType.DEFAULT));
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final EditableProperty property, final SuiNode node, final ExtendedComboBox<?> fxNode) {
			fxNode.setType(isSearchable(node) ? ExtendedComboBox.ComboBoxType.SEARCHABLE : ExtendedComboBox.ComboBoxType.DEFAULT);
			return MutationResult.MUTATED;
		}




		/**
		 * Checks if the given node has the searchable-property and returns its the value
		 *
		 * @param node the node to check
		 * @return the value of the searchable-property
		 */
		private boolean isSearchable(final SuiNode node) {
			return node.getPropertyStore().getSafe(SearchableProperty.class)
					.map(SearchableProperty::isSearchable)
					.orElse(false);
		}

	}






	public static class DatePickerUpdatingBuilder implements PropFxNodeUpdatingBuilder<EditableProperty, DatePicker> {


		@Override
		public void build(final SuiNode node, final EditableProperty property, final DatePicker fxNode) {
			fxNode.setEditable(property.isEditable());
		}




		@Override
		public MutationResult update(final EditableProperty property, final SuiNode node, final DatePicker fxNode) {
			fxNode.setEditable(property.isEditable());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final EditableProperty property, final SuiNode node, final DatePicker fxNode) {
			fxNode.setEditable(false);
			return MutationResult.MUTATED;
		}


	}






	public static class LabeledSliderUpdatingBuilder implements PropFxNodeUpdatingBuilder<EditableProperty, Pane> {


		@Override
		public void build(final SuiNode node,
						  final EditableProperty property,
						  final Pane fxNode) {
			SuiLabeledSlider.getLabel(fxNode).setEditable(property.isEditable());
		}




		@Override
		public MutationResult update(final EditableProperty property,
									 final SuiNode node,
									 final Pane fxNode) {
			SuiLabeledSlider.getLabel(fxNode).setEditable(property.isEditable());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final EditableProperty property,
									 final SuiNode node,
									 final Pane fxNode) {
			SuiLabeledSlider.getLabel(fxNode).setEditable(false);
			return MutationResult.MUTATED;
		}

	}






	public static class SpinnerUpdatingBuilder implements PropFxNodeUpdatingBuilder<EditableProperty, Spinner> {


		@Override
		public void build(final SuiNode node,
						  final EditableProperty property,
						  final Spinner fxNode) {
			fxNode.setEditable(property.isEditable());
		}




		@Override
		public MutationResult update(final EditableProperty property,
									 final SuiNode node,
									 final Spinner fxNode) {
			fxNode.setEditable(property.isEditable());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final EditableProperty property,
									 final SuiNode node,
									 final Spinner fxNode) {
			fxNode.setEditable(false);
			return MutationResult.MUTATED;
		}

	}


}



