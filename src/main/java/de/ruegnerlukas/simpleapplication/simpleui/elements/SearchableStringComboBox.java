package de.ruegnerlukas.simpleapplication.simpleui.elements;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ComboBox;

public class SearchableStringComboBox {


	/**
	 * @return the searchable combo box
	 */
	public static ComboBox<String> create() {

		final ComboBox<String> comboBox = new ComboBox<>();
		comboBox.setEditable(false);

		comboBox.showingProperty().addListener((observable, prev, next) -> {
			comboBox.setEditable(next);
			if (next) {
				comboBox.getEditor().clear();
			}
		});

		final FilteredList<String> filteredItems = new FilteredList<>(FXCollections.observableArrayList());
		comboBox.setItems(filteredItems);
		comboBox.getEditor().textProperty().addListener(new InputFilter(comboBox, filteredItems));

		return comboBox;
	}




	private static class InputFilter implements ChangeListener<String> {


		/**
		 * The combobox
		 */
		private final ComboBox<String> comboBox;

		/**
		 * The available items
		 */
		private final FilteredList<String> items;




		/**
		 * @param comboBox The combo box to whose textProperty this listener is added.
		 * @param items    The {@link FilteredList} containing the items in the list.
		 */
		public InputFilter(final ComboBox<String> comboBox,
						   final FilteredList<String> items) {
			this.comboBox = comboBox;
			this.items = items;
		}




		@Override
		public void changed(final ObservableValue<? extends String> observable, final String prevText, final String newText) {
			if (!comboBox.isShowing()) {
				return;
			}

			final String selected = comboBox.getSelectionModel().getSelectedItem();
			final StringProperty value = new SimpleStringProperty(newText);
			if (selected != null && selected.equals(value.get())) {
				Platform.runLater(() -> comboBox.getEditor().end());
			} else {
				items.setPredicate(item -> item.toLowerCase().contains(value.get().toLowerCase()));
			}

			comboBox.getEditor().setText(value.get());
		}

	}

}
