package de.ruegnerlukas.simpleapplication.simpleui.properties;


import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import javafx.scene.control.ChoiceBox;
import lombok.Getter;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;

public class ChoicesProperty<T> extends Property {


	/**
	 * The list of choices
	 */
	@Getter
	private final List<T> choices;




	/**
	 * @param choices the list of choices
	 */
	public ChoicesProperty(final List<T> choices) {
		super(ChoicesProperty.class);
		this.choices = choices;
	}




	@Override
	protected boolean isPropertyEqual(final Property other) {
		final ChoicesProperty otherProp = (ChoicesProperty) other;
		if (getChoices().size() != otherProp.getChoices().size()) {
			return false;
		} else {
			for (int i = 0; i < getChoices().size(); i++) {
				if (!Objects.equals(getChoices().get(i), otherProp.getChoices().get(i))) {
					return false;
				}
			}
			return true;
		}
	}




	@Override
	public String printValue() {
		return "{" + getChoices().stream().map(Object::toString).collect(Collectors.joining(", ")) + "}";
	}




	public static class ChoicesPropertyUpdatingBuilder<T> implements PropFxNodeUpdatingBuilder<ChoicesProperty<T>, ChoiceBox<T>> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers, final SUINode node, final ChoicesProperty<T> property,
						  final ChoiceBox<T> fxNode) {
			setItems(node, property, fxNode);
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers, final ChoicesProperty<T> property,
									 final SUINode node, final ChoiceBox<T> fxNode) {
			setItems(node, property, fxNode);
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers, final ChoicesProperty<T> property,
									 final SUINode node, final ChoiceBox<T> fxNode) {
			final Object prevValue = fxNode.getSelectionModel().getSelectedItem();
			final int prevIndex = fxNode.getSelectionModel().getSelectedIndex();
			removeListener(node, fxNode);
			fxNode.getItems().clear();
			addListener(node, fxNode);
			if (prevValue != null) {
				callListener(node, prevIndex, -1, prevValue, null);
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
		private void setItems(final SUINode node, final ChoicesProperty<T> property, final ChoiceBox<T> fxNode) {

			final T prevValue = fxNode.getSelectionModel().getSelectedItem();
			final int prevIndex = fxNode.getSelectionModel().getSelectedIndex();

			removeListener(node, fxNode);
			fxNode.getItems().setAll(property.getChoices());
			if (fxNode.getItems().contains(prevValue)) {
				fxNode.setValue(prevValue);
			}
			addListener(node, fxNode);

			final int nextIndex = fxNode.getSelectionModel().getSelectedIndex();
			final Object nextValue = nextIndex != -1 ? fxNode.getSelectionModel().getSelectedItem() : null;
			if (prevIndex != nextIndex || !Objects.equals(prevValue, nextValue)) {
				callListener(node, prevIndex, nextIndex, prevValue, nextValue);
			}

		}




		/**
		 * Removes the listener from the choicebox if one exists
		 *
		 * @param node   the simpleui node
		 * @param fxNode the javafx choicebox
		 */
		private void removeListener(final SUINode node, final ChoiceBox<T> fxNode) {
			final Optional<ChoiceBoxListenerProperty> listenerProperty = node.getPropertySafe(ChoiceBoxListenerProperty.class);
			listenerProperty.ifPresent(listenerProp ->
					fxNode.getSelectionModel().selectedIndexProperty().removeListener(listenerProp.getIndexListener()));
		}




		/**
		 * Adds the listener back to the choicebox if one existed
		 *
		 * @param node   the simpleui node
		 * @param fxNode the javafx choicebox
		 */
		private void addListener(final SUINode node, final ChoiceBox<T> fxNode) {
			final Optional<ChoiceBoxListenerProperty> listenerProperty = node.getPropertySafe(ChoiceBoxListenerProperty.class);
			listenerProperty.ifPresent(listenerProp ->
					fxNode.getSelectionModel().selectedIndexProperty().addListener(listenerProp.getIndexListener()));
		}




		/**
		 * Manually call the listener of the choicebox if one exists
		 *
		 * @param node      the simpleui node
		 * @param prevIndex the previous index
		 * @param nextIndex the new index
		 * @param prevItem  the previous selected item
		 * @param nextItem  the new selected item
		 */
		private void callListener(final SUINode node,
								  final int prevIndex, final int nextIndex,
								  final Object prevItem, final Object nextItem) {
			final Optional<ChoiceBoxListenerProperty> listenerProperty = node.getPropertySafe(ChoiceBoxListenerProperty.class);
			listenerProperty.ifPresent(listenerProp ->
					listenerProp.getListener().onSelection(prevIndex, nextIndex, prevItem, nextItem));
		}


	}


}



