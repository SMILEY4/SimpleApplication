package de.ruegnerlukas.simpleapplication.simpleui.assets.elements.jfxelements;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.ChoiceBox;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

@Slf4j
public class ExtendedChoiceBox<T> extends ChoiceBox<T> {


	/**
	 * The listener for selected items
	 */
	private BiConsumer<T, T> listener;

	/**
	 * Whether the listener should be triggered.
	 */
	private boolean muted = false;




	/**
	 * Default constructor
	 */
	public ExtendedChoiceBox() {
		this.getSelectionModel().selectedItemProperty().addListener((value, prev, next) -> notifyListener(prev, next));
	}




	/**
	 * Set the listener for selected items
	 *
	 * @param listener the listener or null
	 */
	public void setSelectedItemListener(final BiConsumer<T, T> listener) {
		this.listener = listener;
	}




	/**
	 * Sets/Replaces the available items without unnecessarily triggering the listener or changing the currently selected item if possible.
	 * If the selected item was removed this way, the listener will receive an event.
	 *
	 * @param items the new available items to select from.
	 */
	public void setItems(final List<T> items, final T selectedItem) {
		withMutedListeners(() -> {
			getItems().setAll(items);
			if (getItems().contains(selectedItem)) {
				setValue(selectedItem);
			} else {
				setValue(null);
			}
		});
	}




	/**
	 * Removes all currently available items and selected item. Only removing the selected item will trigger the listener.
	 */
	public void clearItems() {
		final T prevSelected = getValue();
		boolean selectionCleared = withMutedListeners(() -> {
			boolean deselected = false;
			getItems().clear();
			if (prevSelected != null) {
				deselected = true;
			}
			return deselected;
		});
		if (selectionCleared) {
			notifyListener(prevSelected, null);
		}
	}




	/**
	 * Select the given item without triggering the listener.
	 * If the item is not in the list of available items, the selected item will not change
	 *
	 * @param item the item to select
	 */
	public void selectItem(final T item) {
		withMutedListeners(() -> {
			if (item == null || getItems().stream().anyMatch(i -> Objects.equals(i, item))) {
				if (!Objects.equals(getValue(), item)) {
					getSelectionModel().select(item);
				}
			} else {
				log.warn("Could not select item {}: item not in list.", item);
			}
		});
	}




	/**
	 * Notifies the listener when a new item was selected. The listener is only triggered when one exists and is not muted
	 *
	 * @param prev the previously selected item
	 * @param next the now selected item
	 */
	private void notifyListener(final T prev, final T next) {
		if (!muted && listener != null) {
			listener.accept(prev, next);
		}
	}




	/**
	 * Run the given action with a return value without triggering any listeners while doing so
	 *
	 * @param action the action to run
	 * @param <R>    the type of the return value
	 * @return the value returned by the given action
	 */
	private <R> R withMutedListeners(final Supplier<R> action) {
		SimpleObjectProperty<R> result = new SimpleObjectProperty<>();
		withMutedListeners(() -> result.set(action.get()));
		return result.get();
	}




	/**
	 * Run the given action with without triggering any listeners while doing so.
	 *
	 * @param action the action to run
	 */
	private void withMutedListeners(final Runnable action) {
		muted = true;
		action.run();
		muted = false;
	}


}
