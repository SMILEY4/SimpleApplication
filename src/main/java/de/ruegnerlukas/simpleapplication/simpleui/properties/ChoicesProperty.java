package de.ruegnerlukas.simpleapplication.simpleui.properties;


import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.events.SuiEvent;
import de.ruegnerlukas.simpleapplication.simpleui.events.SelectedIndexEventData;
import de.ruegnerlukas.simpleapplication.simpleui.events.SelectedItemEventData;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.properties.events.OnSelectedIndexEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.events.OnSelectedItemEventProperty;
import javafx.scene.control.ChoiceBox;
import lombok.Getter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
		public void build(final MasterNodeHandlers nodeHandlers, final SuiNode node, final ChoicesProperty<T> property,
						  final ChoiceBox<T> fxNode) {
			setItems(node, property, fxNode);
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers, final ChoicesProperty<T> property,
									 final SuiNode node, final ChoiceBox<T> fxNode) {
			setItems(node, property, fxNode);
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers, final ChoicesProperty<T> property,
									 final SuiNode node, final ChoiceBox<T> fxNode) {
			final Object prevValue = fxNode.getSelectionModel().getSelectedItem();
			final int prevIndex = fxNode.getSelectionModel().getSelectedIndex();
			removeListeners(node, fxNode);
			fxNode.getItems().clear();
			addListener(node, fxNode);
			if (prevValue != null) {
				callListeners(node, prevIndex, -1, prevValue, null);
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
		private void setItems(final SuiNode node, final ChoicesProperty<T> property, final ChoiceBox<T> fxNode) {

			final T prevValue = fxNode.getSelectionModel().getSelectedItem();
			final int prevIndex = fxNode.getSelectionModel().getSelectedIndex();

			removeListeners(node, fxNode);
			fxNode.getItems().setAll(property.getChoices());
			if (fxNode.getItems().contains(prevValue)) {
				fxNode.setValue(prevValue);
			}
			addListener(node, fxNode);

			final int nextIndex = fxNode.getSelectionModel().getSelectedIndex();
			final Object nextValue = nextIndex != -1 ? fxNode.getSelectionModel().getSelectedItem() : null;
			if (prevIndex != nextIndex || !Objects.equals(prevValue, nextValue)) {
				callListeners(node, prevIndex, nextIndex, prevValue, nextValue);
			}

		}




		/**
		 * Removes the listeners from the choicebox if any exist
		 *
		 * @param node   the simpleui node
		 * @param fxNode the javafx choicebox
		 */
		private void removeListeners(final SuiNode node, final ChoiceBox<T> fxNode) {
			node.getPropertySafe(OnSelectedIndexEventProperty.class).ifPresent(property -> {
				fxNode.getSelectionModel().selectedIndexProperty().removeListener(property.getChangeListener());
			});
			node.getPropertySafe(OnSelectedItemEventProperty.class).ifPresent(property -> {
				fxNode.getSelectionModel().selectedItemProperty().removeListener(property.getChangeListener());
			});
		}




		/**
		 * Adds the listener back to the choicebox if any existed
		 *
		 * @param node   the simpleui node
		 * @param fxNode the javafx choicebox
		 */
		private void addListener(final SuiNode node, final ChoiceBox<T> fxNode) {
			node.getPropertySafe(OnSelectedIndexEventProperty.class).ifPresent(property -> {
				fxNode.getSelectionModel().selectedIndexProperty().addListener(property.getChangeListener());
			});
			node.getPropertySafe(OnSelectedItemEventProperty.class).ifPresent(property -> {
				fxNode.getSelectionModel().selectedItemProperty().addListener(property.getChangeListener());
			});
		}




		/**
		 * Manually calls the listeners of the choicebox if any exist
		 *
		 * @param node      the simpleui node
		 * @param prevIndex the previous index
		 * @param nextIndex the new index
		 * @param prevItem  the previous selected item
		 * @param nextItem  the new selected item
		 */
		private void callListeners(final SuiNode node,
								   final int prevIndex, final int nextIndex,
								   final Object prevItem, final Object nextItem) {
			node.getPropertySafe(OnSelectedIndexEventProperty.class).ifPresent(property -> {
				property.getListener().onEvent(new SuiEvent<>(
						OnSelectedIndexEventProperty.EVENT_ID,
						new SelectedIndexEventData(nextIndex, prevIndex)
				));
			});
			node.getPropertySafe(OnSelectedItemEventProperty.class).ifPresent(property -> {
				property.getListener().onEvent(new SuiEvent<>(
						OnSelectedItemEventProperty.EVENT_ID,
						new SelectedItemEventData<>(nextItem, prevItem)
				));
			});
		}


	}


}
