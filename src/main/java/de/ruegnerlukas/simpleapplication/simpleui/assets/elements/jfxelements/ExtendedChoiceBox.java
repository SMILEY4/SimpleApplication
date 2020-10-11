package de.ruegnerlukas.simpleapplication.simpleui.assets.elements.jfxelements;

import de.ruegnerlukas.simpleapplication.simpleui.utils.MutableBiConsumer;
import javafx.scene.control.ChoiceBox;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

@Slf4j
public class ExtendedChoiceBox<T> extends ChoiceBox<T> {


	/**
	 * The listener for selected items
	 */
	private final MutableBiConsumer<T, T> listener = new MutableBiConsumer<>();




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
		this.listener.setConsumer(listener);
	}




	/**
	 * Sets/Replaces the available items without unnecessarily triggering the listener or changing the currently selected item if possible.
	 * If the selected item was removed this way, the listener will receive an event.
	 *
	 * @param items the new available items to select from.
	 */
	public void setItems(final List<T> items, final T selectedItem) {
		listener.runMuted(() -> {
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
		boolean selectionCleared = listener.runMuted(() -> {
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
		listener.runMuted(() -> {
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
	 * Notifies the listener when a new item was selected.
	 *
	 * @param prev the previously selected item
	 * @param next the now selected item
	 */
	private void notifyListener(final T prev, final T next) {
		listener.accept(prev, next);
	}


}
