package de.ruegnerlukas.simpleapplication.simpleui.assets.elements.jfxelements;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ComboBox;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

@Slf4j
public class ExtendedComboBox<T> extends ComboBox<T> {


	/**
	 * The listener for selected items
	 */
	private BiConsumer<T, T> listener;

	/**
	 * Whether the listener should be triggered.
	 */
	private boolean muted = false;

	/**
	 * The filterable list of items.
	 */
	private FilteredList<T> filteredList = new FilteredList<>(FXCollections.observableArrayList());

	/**
	 * The selected value before opening the drop-down
	 */
	private T lastSelectedValue = null;

	private boolean searchable = false;




	/**
	 * Default constructor
	 */
	public ExtendedComboBox() {
		this.setItems(filteredList);
		this.getSelectionModel().selectedItemProperty().addListener((value, prev, next) -> {
			if (!searchable) {
				if (next != null && !filteredList.getSource().contains(next)) {
					withMutedListeners(() -> selectItem(prev));
				} else {
					notifyListener(prev, next);
				}
			} else {
				notifyListener(prev, next);
			}
		});
		this.getEditor().textProperty().addListener((value, prev, next) -> {
			if (searchable) {
				onSearching(next);
			}
		});
		this.showingProperty().addListener((value, prev, next) -> {
			if (searchable) {
				onShowing(next);
			}
		});
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
	 * Whether this is a searchable combo-box
	 *
	 * @param searchable whether this is a searchable combo-box
	 */
	public void setSearchable(final boolean searchable) {
		this.searchable = searchable;
	}




	/**
	 * Sets/Replaces the available items without unnecessarily triggering the listener or changing the currently selected item if possible.
	 * If the selected item was removed this way, the listener will receive an event.
	 *
	 * @param items the new available items to select from.
	 */
	public void setItems(final List<T> items, final T selectedItem) {
		withMutedListeners(() -> {
			filteredList.getSource().setAll((Collection) items);
			setValue(selectedItem);
		});
	}




	/**
	 * Removes all currently available items and selected item. Only removing the selected item will trigger the listener.
	 */
	public void clearItems() {
		withMutedListeners(() -> {
			filteredList.getSource().clear();
			getSelectionModel().select(null);
		});
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
	 * @param isShowing whether the contents of the combobox is now showing
	 */
	private void onShowing(final boolean isShowing) {
		this.muted = isShowing;
		this.setEditable(isShowing);
		if (isShowing) {
			lastSelectedValue = getValue();
			Platform.runLater(() -> withMutedListeners(() -> getEditor().setText("")));
		} else {
			if (filteredList.size() == 0) {
				withMutedListeners(() -> {
					onSearching(asString(lastSelectedValue, ""));
					getSelectionModel().select(lastSelectedValue);
				});
			} else {
				withMutedListeners(() -> {
					if (lastSelectedValue != null && getValue() == null) {
						getSelectionModel().select(lastSelectedValue);
					}
				});
				if (!Objects.equals(lastSelectedValue, getValue())) {
					notifyListener(lastSelectedValue, getValue());
				}
			}
		}
	}




	/**
	 * @param text the current text in the text/search-field
	 */
	private void onSearching(final String text) {
		if (!asString(getValue(), "").equals(text)) {
			filteredList.setPredicate(item -> asString(item, "").toLowerCase().contains(text.toLowerCase()));
		}
	}




	/**
	 * @param item       the item to convert to a string
	 * @param strDefault the default string if the conversion result of the given item is null
	 * @return the string representation of the item
	 */
	private String asString(final T item, final String strDefault) {
		String string;
		if (getConverter() != null) {
			string = getConverter().toString(item);
		} else {
			string = Optional.of(item).map(Object::toString).orElse(null);
		}
		if (string == null) {
			string = strDefault;
		}
		return string;
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
