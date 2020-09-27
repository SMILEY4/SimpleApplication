package de.ruegnerlukas.simpleapplication.simpleui.assets.elements.jfxelements;

import de.ruegnerlukas.simpleapplication.simpleui.assets.events.SuiListChangeListener;
import javafx.scene.control.ListView;

import java.util.List;
import java.util.function.Consumer;

public class ExtendedListView<T> extends ListView<T> {


	/**
	 * The listener for selected items or null.
	 */
	private Consumer<List<T>> selectionListener;




	/**
	 * Default constructor
	 */
	public ExtendedListView() {
		this.getSelectionModel().getSelectedItems().addListener(new SuiListChangeListener<>(this::onSelectionChanged));
	}




	/**
	 * Sets the listener for selected items to this list view
	 *
	 * @param listener the listener for selected items or null
	 */
	public void setSelectionListener(final Consumer<List<T>> listener) {
		this.selectionListener = listener;
	}




	/**
	 * Called when the selected items change.
	 */
	private void onSelectionChanged() {
		if (selectionListener != null) {
			selectionListener.accept(getSelectionModel().getSelectedItems());
		}
	}


}
