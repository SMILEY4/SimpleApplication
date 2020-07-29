package de.ruegnerlukas.simpleapplication.simpleui.properties;


import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.ChoiceBox;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import static de.ruegnerlukas.simpleapplication.simpleui.mutation.BaseNodeMutator.MutationResult;

public class ChoiceBoxListenerProperty<T> extends Property {


	/**
	 * The listener.
	 */
	@Getter
	private final ChoiceBoxListener<T> listener;

	/**
	 * The change listener listening to the index.
	 */
	@Getter
	@Setter (value = AccessLevel.PRIVATE)
	private ChangeListener<Number> indexListener;




	/**
	 * @param listener the listener.
	 */
	public ChoiceBoxListenerProperty(final ChoiceBoxListener<T> listener) {
		super(ChoiceBoxListenerProperty.class);
		this.listener = listener;
	}




	@Override
	protected boolean isPropertyEqual(final Property other) {
		return listener.equals(((ChoiceBoxListenerProperty) other).getListener());
	}




	@Override
	public String printValue() {
		return getListener() != null ? getListener().toString() : "null";
	}




	public static class CBListenerUpdatingBuilder<T> implements PropFxNodeUpdatingBuilder<ChoiceBoxListenerProperty<T>, ChoiceBox<T>> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers, final SUINode node, final ChoiceBoxListenerProperty<T> property,
						  final ChoiceBox<T> fxNode) {
			setListener(property, fxNode);
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers, final ChoiceBoxListenerProperty<T> property,
									 final SUINode node, final ChoiceBox<T> fxNode) {
			node.getPropertySafe(ChoiceBoxListenerProperty.class).ifPresent(prevProperty -> {
				fxNode.getSelectionModel().selectedIndexProperty().removeListener(prevProperty.getIndexListener());
			});
			setListener(property, fxNode);
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers, final ChoiceBoxListenerProperty<T> property,
									 final SUINode node, final ChoiceBox<T> fxNode) {
			fxNode.getSelectionModel().selectedIndexProperty().removeListener(property.getIndexListener());
			return MutationResult.MUTATED;
		}




		/**
		 * Sets the listener of the given property and the given choicebox.
		 *
		 * @param property  the property
		 * @param choiceBox the choiceBox
		 */
		private void setListener(final ChoiceBoxListenerProperty<T> property, final ChoiceBox<T> choiceBox) {
			property.setIndexListener(((observable, prev, next) -> {
				final Object prevItem = prev.intValue() < 0 ? null : choiceBox.getItems().get(prev.intValue());
				final Object nextItem = next.intValue() < 0 ? null : choiceBox.getItems().get(next.intValue());
				property.getListener().onSelection(prev.intValue(), next.intValue(), (T) prevItem, (T) nextItem);
			}));
			choiceBox.getSelectionModel().selectedIndexProperty().addListener(property.getIndexListener());
		}

	}






	public interface ChoiceBoxListener<T> {


		/**
		 * Called when the selection of the choicebox changed
		 *
		 * @param index the index of the selected item
		 * @param item  the selected item
		 */
		void onSelection(int index, T item);

		/**
		 * Called when the selection of the choicebox changed
		 *
		 * @param prevIndex the previous index of the selected item
		 * @param nextIndex the next index of the selected item
		 * @param prevItem  the previous item
		 * @param nextItem  the next index item
		 */
		default void onSelection(final int prevIndex, final int nextIndex, final T prevItem, final T nextItem) {
			onSelection(nextIndex, nextItem);
		}

	}


}



