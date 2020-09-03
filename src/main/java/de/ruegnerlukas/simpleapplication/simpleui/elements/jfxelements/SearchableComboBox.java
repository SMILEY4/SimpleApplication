package de.ruegnerlukas.simpleapplication.simpleui.elements.jfxelements;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ComboBox;
import lombok.Getter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SearchableComboBox<T> extends ComboBox<T> {


	/**
	 * The selected value before opening the drop-down
	 */
	private T lastSelectedValue = null;

	/**
	 * The filterable list of items.
	 */
	private FilteredList<T> filteredList = new FilteredList<>(FXCollections.observableArrayList());

	/**
	 * Whether the change listeners are muted.
	 */
	private final BooleanProperty muted = new SimpleBooleanProperty(false);

	/**
	 * The change listeners and their mutable proxies.
	 */
	private Map<ChangeListener<T>, MutableChangeListenerProxy<T>> listeners = new HashMap<>();

	/**
	 * The selected value property independent of any changes happening during the search operation.
	 */
	@Getter
	private ObjectProperty<T> selectedValueProperty = new SimpleObjectProperty<>(null);




	/**
	 * Create a searchable combobox
	 */
	public SearchableComboBox() {
		this.setEditable(false);
		this.showingProperty().addListener((value, prev, next) -> onShowing(next));
		this.getEditor().textProperty().addListener((value, prev, next) -> onSearching(next));
		this.setItems(filteredList);
		addSelectedItemListener((value, prev, next) -> getSelectedValueProperty().setValue(next));
	}




	/**
	 * Adds the given change listener to this combobox.
	 * The listener will be notified of any real changes to the selected item.
	 *
	 * @param changeListener the listener to add
	 */
	public void addSelectedItemListener(final ChangeListener<T> changeListener) {
		final MutableChangeListenerProxy<T> mutableProxy = new MutableChangeListenerProxy<>(muted, changeListener);
		listeners.put(changeListener, mutableProxy);
		getSelectionModel().selectedItemProperty().addListener(mutableProxy);
	}




	/**
	 * Removes the given change listener from this combobox.
	 * The listener will no longer be notified of any real changes to the selected item.
	 *
	 * @param changeListener the listener to remove
	 */
	public void removeSelectedItemListener(final ChangeListener<T> changeListener) {
		final MutableChangeListenerProxy<T> mutableProxy = listeners.get(changeListener);
		listeners.remove(changeListener);
		getSelectionModel().selectedItemProperty().removeListener(mutableProxy);
	}




	/**
	 * Sets the items of this combobox to the given items
	 *
	 * @param items the new items
	 */
	public void setAllItems(final Collection<T> items) {
		filteredList.getSource().setAll((Collection) items);
	}




	/**
	 * @param isShowing whether the contents of the combobox is now showing
	 */
	private void onShowing(final boolean isShowing) {
		this.muted.set(isShowing);
		this.setEditable(isShowing);
		if (isShowing) {
			lastSelectedValue = getValue();
			getEditor().setText("");
		} else {
			if (filteredList.size() == 0) {
				onSearching(asString(lastSelectedValue, ""));
				getSelectionModel().select(lastSelectedValue);
			} else {
				if (lastSelectedValue != null && getValue() == null) {
					getSelectionModel().select(lastSelectedValue);
				}
				if (!Objects.equals(lastSelectedValue, getValue())) {
					listeners.forEach((listener, proxy) -> {
						listener.changed(valueProperty(), lastSelectedValue, getValue());
					});
				}
			}
		}
	}




	/**
	 * @param text the current text in the text/search-field
	 */
	private void onSearching(final String text) {
		if (asString(getValue(), "").equals(text)) {
			return;
		}
		filteredList.setPredicate(item -> asString(item, "").toLowerCase().contains(text.toLowerCase()));
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
			string = item == null ? "" : item.toString();
		}
		if (string == null) {
			string = strDefault;
		}
		return string;
	}




	/**
	 * A proxy value change listener that can be enabled and disabled.
	 *
	 * @param <T> the generic type of the items
	 */
	private static class MutableChangeListenerProxy<T> implements ChangeListener<T> {


		/**
		 * Property indicating whether the listener is muted.
		 */
		private final BooleanProperty mute;

		/**
		 * The real change listener.
		 */
		private final ChangeListener<T> listener;




		/**
		 * @param mute     property indicating whether the listener is muted
		 * @param listener the real change listener
		 */
		MutableChangeListenerProxy(final BooleanProperty mute, final ChangeListener<T> listener) {
			this.mute = mute;
			this.listener = listener;
		}




		@Override
		public void changed(final ObservableValue<? extends T> value, final T prev, final T next) {
			if (!mute.get()) {
				listener.changed(value, prev, next);
			}
		}

	}

}
