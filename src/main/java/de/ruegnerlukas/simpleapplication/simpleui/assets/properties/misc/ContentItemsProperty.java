package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.jfxelements.ExtendedChoiceBox;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.jfxelements.SearchableComboBox;
import de.ruegnerlukas.simpleapplication.simpleui.assets.events.ValueChangedEventData;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnValueChangedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;
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
		if (a.getChoices().size() == b.getChoices().size()) {
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




	/**
	 * @param choices the list of choices
	 */
	public ContentItemsProperty(final List<T> choices) {
		super(ContentItemsProperty.class, COMPARATOR);
		this.choices = choices;
	}




	public static class ChoiceBoxUpdatingBuilder<T> implements PropFxNodeUpdatingBuilder<ContentItemsProperty<T>, ExtendedChoiceBox<T>> {


		@Override
		public void build(final SuiNode node, final ContentItemsProperty<T> property, final ExtendedChoiceBox<T> fxNode) {
			fxNode.setItems(property.getChoices());
		}




		@Override
		public MutationResult update(final ContentItemsProperty<T> property, final SuiNode node, final ExtendedChoiceBox<T> fxNode) {
			fxNode.setItems(property.getChoices());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final ContentItemsProperty<T> property, final SuiNode node, final ExtendedChoiceBox<T> fxNode) {
			fxNode.clearItems();
			return MutationResult.MUTATED;
		}

	}






	public static class ComboBoxUpdatingBuilder<T> implements PropFxNodeUpdatingBuilder<ContentItemsProperty<T>, ComboBox<T>> {


		@Override
		public void build(final SuiNode node,
						  final ContentItemsProperty<T> property,
						  final ComboBox<T> fxNode) {
			setItems(node, property, fxNode);
		}




		@Override
		public MutationResult update(final ContentItemsProperty<T> property,
									 final SuiNode node,
									 final ComboBox<T> fxNode) {
			setItems(node, property, fxNode);
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final ContentItemsProperty<T> property,
									 final SuiNode node,
									 final ComboBox<T> fxNode) {
			final T prevValue = fxNode.getSelectionModel().getSelectedItem();
			removeListeners(node, fxNode);
			fxNode.getItems().clear();
			addListener(node, fxNode);
			if (prevValue != null) {
				callListeners(node, prevValue, null);
			}
			return MutationResult.MUTATED;
		}




		/**
		 * Set the items without triggering the listeners or changing the currently selected value.
		 *
		 * @param node     the simpleui node
		 * @param property the choices-property
		 * @param fxNode   the javafx choicebox
		 */
		private void setItems(final SuiNode node, final ContentItemsProperty<T> property, final ComboBox<T> fxNode) {

			final T prevValue = fxNode.getValue();

			removeListeners(node, fxNode);
			setItems(node, fxNode, property.getChoices());
			if (fxNode.getItems().contains(prevValue)) {
				fxNode.setValue(prevValue);
			}
			addListener(node, fxNode);

			final T nextValue = fxNode.getSelectionModel().getSelectedIndex() != -1 ? fxNode.getSelectionModel().getSelectedItem() : null;
			if (!Objects.equals(prevValue, nextValue)) {
				callListeners(node, prevValue, nextValue);
			}
		}




		/**
		 * Sets the items of the combobox to the given items
		 *
		 * @param node   the sui node
		 * @param fxNode the combobox
		 * @param items  the items to set
		 */
		private void setItems(final SuiNode node, final ComboBox<T> fxNode, final List<T> items) {
			if (SearchableProperty.isSearchable(node)) {
				final SearchableComboBox<T> searchableComboBox = (SearchableComboBox<T>) fxNode;
				searchableComboBox.setAllItems(items);
			} else {
				fxNode.getItems().setAll(items);
			}
		}




		/**
		 * Removes the listeners from the combobox if any exist
		 *
		 * @param node   the simpleui node
		 * @param fxNode the javafx combobox
		 */
		private void removeListeners(final SuiNode node, final ComboBox<T> fxNode) {
			node.getPropertyStore().getSafe(OnValueChangedEventProperty.class)
					.map(prop -> (OnValueChangedEventProperty<T>) prop)
					.map(OnValueChangedEventProperty::getChangeListenerProxy)
					.ifPresent(listener -> listener.removeFrom(getObservableValue(fxNode)));
		}




		/**
		 * Adds the listener back to the choicebox if any existed
		 *
		 * @param node   the simpleui node
		 * @param fxNode the javafx choicebox
		 */
		private void addListener(final SuiNode node, final ComboBox<T> fxNode) {
			node.getPropertyStore().getSafe(OnValueChangedEventProperty.class)
					.map(prop -> (OnValueChangedEventProperty<T>) prop)
					.map(OnValueChangedEventProperty::getChangeListenerProxy)
					.ifPresent(listener -> listener.addTo(getObservableValue(fxNode)));
		}




		/**
		 * Get the observable value from the given combo box
		 *
		 * @param fxNode the combobox (or a searchable combo box)
		 * @param <O>    the generic type of the values
		 * @return the observable value
		 */
		private <O> ObservableValue<O> getObservableValue(final ComboBox<O> fxNode) {
			ObservableValue<O> observableValue;
			if (fxNode instanceof SearchableComboBox) {
				final SearchableComboBox<O> searchableComboBox = (SearchableComboBox<O>) fxNode;
				observableValue = searchableComboBox.getSelectedValueProperty();
			} else {
				observableValue = fxNode.getSelectionModel().selectedItemProperty();
			}
			return observableValue;
		}




		/**
		 * Manually calls the listeners of the choicebox if any exist
		 *
		 * @param node     the simpleui node
		 * @param prevItem the previous selected item
		 * @param nextItem the new selected item
		 */
		private void callListeners(final SuiNode node, final T prevItem, final T nextItem) {
			node.getPropertyStore().getSafe(OnValueChangedEventProperty.class)
					.map(prop -> (OnValueChangedEventProperty<T>) prop)
					.ifPresent(property -> property.getListener().onEvent(new ValueChangedEventData<>(nextItem, prevItem)));
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
