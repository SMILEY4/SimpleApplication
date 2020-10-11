package de.ruegnerlukas.simpleapplication.simpleui.assets.elements.jfxelements;

import de.ruegnerlukas.simpleapplication.simpleui.utils.MutableBiConsumer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ComboBox;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;

public class ExtendedComboBox<T> extends ComboBox<T> {


	/**
	 * The possible  types of combo-boxes
	 */
	public enum ComboBoxType {

		/**
		 * items can only be selected via the list of all items
		 */
		DEFAULT,

		/**
		 * items can be selected via the text input field
		 */
		EDITABLE,

		/**
		 * The list of items can be filtered with the text input field
		 */
		SEARCHABLE;
	}






	/**
	 * The listener for selected items
	 */
	private final MutableBiConsumer<T, T> listener = new MutableBiConsumer<>();


	/**
	 * The selected item before opening the dropdown
	 */
	private T lastSelectedValue = null;


	/**
	 * The behaviour type of this combobox
	 */
	private ComboBoxType type = ComboBoxType.DEFAULT;

	/**
	 * The filterable list of items.
	 */
	private final FilteredList<T> filteredList = new FilteredList<>(FXCollections.observableArrayList());




	/**
	 * Default constructor
	 */
	public ExtendedComboBox() {

		this.setItems(filteredList);

		this.getSelectionModel().selectedItemProperty().addListener((value, prev, next) -> {
			if (type == ComboBoxType.EDITABLE && !isShowing()) {
				onSelectedItemOutsideMenu(prev, next);
			}
		});

		this.showingProperty().addListener((value, prev, next) -> {
			if (next) {
				onOpenMenu();
			} else {
				onCloseMenu();
			}
		});

		this.getEditor().textProperty().addListener((value, prev, next) -> {
			if (type == ComboBoxType.SEARCHABLE) {
				onSearching(next);
			}
		});

	}




	/**
	 * Called when the dropdown menu opens
	 */
	private void onOpenMenu() {
		lastSelectedValue = getValue();
		if (type == ComboBoxType.SEARCHABLE) {
			Platform.runLater(() -> listener.runMuted(() -> {
				setEditable(true);
				// clear text twice, otherwise it will not be set the first time the user opens the dropdown. WHYYY ?!?!
				getEditor().setText("");
				getEditor().setText("");
			}));
		}
	}




	/**
	 * Called when the dropdown menu closes
	 */
	private void onCloseMenu() {
		final T selectedValue = getValue();
		final int filteredSize = filteredList.size();
		boolean shouldTriggerListener = true;
		if (type == ComboBoxType.SEARCHABLE) {
			setEditable(false);
			listener.runMuted(() -> {
				// reset predicate or otherwise the dropdown will be very small next time depending on last predicate.
				filteredList.setPredicate(null);
				getSelectionModel().select(selectedValue);
			});
			if (filteredSize == 0) {
				listener.runMuted(() -> {
					onSearching(asString(lastSelectedValue, ""));
					getSelectionModel().select(lastSelectedValue);
				});
			} else {
				if (lastSelectedValue != null && selectedValue == null) {
					shouldTriggerListener = false;
					listener.runMuted(() -> getSelectionModel().select(lastSelectedValue));
				}
			}
		}
		if (shouldTriggerListener && !Objects.equals(lastSelectedValue, selectedValue)) {
			notifyListener(lastSelectedValue, selectedValue);
		}
	}




	/**
	 * Called when an item was selected without opening the dropdown menu
	 *
	 * @param prev the previous selected item
	 * @param next the new selected item
	 */
	private void onSelectedItemOutsideMenu(final T prev, final T next) {
		if (!listener.isMuted()) {
			return;
		}
		if (!Objects.equals(prev, next)) {
			if (isValidItem(next)) {
				notifyListener(lastSelectedValue, getValue());
			} else {
				listener.runMuted(() -> setValue(prev));
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
	 * Checks whether the item is a valid item (i.e. is in the list of available items or is null)
	 *
	 * @param item the item to check
	 * @return whether the given item is valid for this dropdown
	 */
	private boolean isValidItem(final T item) {
		return item == null || getItems().stream().anyMatch(item::equals);
	}




	/**
	 * Calls the listener with the given values
	 *
	 * @param prev the previously selected value
	 * @param next the new selected value
	 */
	private void notifyListener(final T prev, final T next) {
		listener.accept(prev, next);
	}




	/**
	 * Sets the listener to the given one
	 *
	 * @param listener the listener or null
	 */
	public void setListener(final BiConsumer<T, T> listener) {
		this.listener.setConsumer(listener);
	}




	/**
	 * Sets the behaviour type of this combobox
	 *
	 * @param type the new type
	 */
	public void setType(final ComboBoxType type) {
		this.type = type;
		this.setEditable(type == ComboBoxType.EDITABLE);
	}


	/**
	 * Removes all items  without triggering the listener.
	 */
	public void clearItems() {
		setItems(List.of(), null);
	}


	/**
	 * Sets the available items of this combobox without triggering the listener.
	 *
	 * @param items        the items
	 * @param selectedItem the item from the list to select
	 */
	public void setItems(final List<T> items, final T selectedItem) {
		listener.runMuted(() -> {
			filteredList.getSource().setAll((Collection) items);
			getSelectionModel().select(selectedItem);
		});
	}




}
