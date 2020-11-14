package de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.jfxelements;

import de.ruegnerlukas.simpleapplication.core.simpleui.core.events.SuiListChangeListener;
import de.ruegnerlukas.simpleapplication.core.simpleui.utils.MutableConsumer;
import javafx.scene.control.ListView;

import java.util.List;
import java.util.function.Consumer;

public class ExtendedListView<T> extends ListView<T> {


	/**
	 * The listener for selected items or null.
	 */
	private final MutableConsumer<List<T>> selectionListener = new MutableConsumer<>();




	/**
	 * Default constructor
	 */
	public ExtendedListView() {
		this.getSelectionModel().getSelectedItems().addListener(new SuiListChangeListener<>(
				() -> selectionListener.accept(getSelectionModel().getSelectedItems()))
		);
	}




	/**
	 * Sets the listener for selected items to this list view.
	 *
	 * @param listener the listener for selected items or null
	 */
	public void setSelectionListener(final Consumer<List<T>> listener) {
		this.selectionListener.setConsumer(listener);
	}




	/**
	 * Sets the items of this list without notifying the listener.
	 *
	 * @param items the new items
	 */
	public void setItems(final List<T> items) {
		selectionListener.runMuted(() -> {
			getItems().setAll(items);
		});
	}




	/**
	 * Removes all items fo this list without notifying the listener.
	 */
	public void clearItems() {
		selectionListener.runMuted(() -> {
			getItems().clear();
		});
	}

}
