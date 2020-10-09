package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.jfxelements.ExtendedChoiceBox;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.jfxelements.ExtendedComboBox;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import javafx.scene.control.ListView;
import lombok.Getter;

import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

public class ContentItemsProperty<T> extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<ContentItemsProperty, ContentItemsProperty, Boolean> COMPARATOR = (a, b) -> {
		if (Objects.equals(a.getSelectedChoice(), b.getSelectedChoice()) && a.getChoices().size() == b.getChoices().size()) {
			for (int i = 0; i < a.getChoices().size(); i++) {
				if (!Objects.equals(a.getChoices().get(i), b.getChoices().get(i))) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	};

	/**
	 * The list of choices
	 */
	@Getter
	private final List<T> choices;

	@Getter
	private final T selectedChoice;


	/**
	 * @param choices the list of choices
	 */
	public ContentItemsProperty(final List<T> choices, final T selectedChoice) {
		super(ContentItemsProperty.class, COMPARATOR);
		this.choices = choices;
		this.selectedChoice = selectedChoice;
	}




	public static class ChoiceBoxUpdatingBuilder<T> implements PropFxNodeUpdatingBuilder<ContentItemsProperty<T>, ExtendedChoiceBox<T>> {


		@Override
		public void build(final SuiNode node, final ContentItemsProperty<T> property, final ExtendedChoiceBox<T> fxNode) {
			fxNode.setItems(property.getChoices(), property.getSelectedChoice());
		}




		@Override
		public MutationResult update(final ContentItemsProperty<T> property, final SuiNode node, final ExtendedChoiceBox<T> fxNode) {
			fxNode.setItems(property.getChoices(), property.getSelectedChoice());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final ContentItemsProperty<T> property, final SuiNode node, final ExtendedChoiceBox<T> fxNode) {
			fxNode.clearItems();
			return MutationResult.MUTATED;
		}

	}






	public static class ComboBoxUpdatingBuilder<T> implements PropFxNodeUpdatingBuilder<ContentItemsProperty<T>, ExtendedComboBox<T>> {


		@Override
		public void build(final SuiNode node, final ContentItemsProperty<T> property, final ExtendedComboBox<T> fxNode) {
			fxNode.setItems(property.getChoices(), property.getSelectedChoice());
		}




		@Override
		public MutationResult update(final ContentItemsProperty<T> property, final SuiNode node, final ExtendedComboBox<T> fxNode) {
			fxNode.setItems(property.getChoices(), property.getSelectedChoice());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final ContentItemsProperty<T> property, final SuiNode node, final ExtendedComboBox<T> fxNode) {
			fxNode.clearItems();
			return MutationResult.MUTATED;
		}


	}






	public static class ListViewUpdatingBuilder<T> implements PropFxNodeUpdatingBuilder<ContentItemsProperty<T>, ListView<T>> {


		@Override
		public void build(final SuiNode node,
						  final ContentItemsProperty<T> property,
						  final ListView<T> fxNode) {
			fxNode.getItems().setAll(property.getChoices());
		}




		@Override
		public MutationResult update(final ContentItemsProperty<T> property,
									 final SuiNode node,
									 final ListView<T> fxNode) {
			fxNode.getItems().setAll(property.getChoices());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final ContentItemsProperty<T> property,
									 final SuiNode node,
									 final ListView<T> fxNode) {
			fxNode.getItems().clear();
			return MutationResult.MUTATED;
		}


	}


}
