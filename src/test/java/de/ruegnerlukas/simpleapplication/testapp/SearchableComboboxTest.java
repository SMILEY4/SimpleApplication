package de.ruegnerlukas.simpleapplication.testapp;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.ListView;
import javafx.scene.control.skin.ComboBoxListViewSkin;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings ("ALL")
public class SearchableComboboxTest extends Application {


	/*
	SOURCE:
	https://stackoverflow.com/questions/19924852/autocomplete-combobox-in-javafx
	 */

	public static class HideableItem<T> {


		private final ObjectProperty<T> object = new SimpleObjectProperty<>();
		private final BooleanProperty hidden = new SimpleBooleanProperty();




		private HideableItem(T object) {
			setObject(object);
		}




		private ObjectProperty<T> objectProperty() {
			return this.object;
		}




		private T getObject() {
			return this.objectProperty().get();
		}




		private void setObject(T object) {
			this.objectProperty().set(object);
		}




		private BooleanProperty hiddenProperty() {
			return this.hidden;
		}




		private boolean isHidden() {
			return this.hiddenProperty().get();
		}




		private void setHidden(boolean hidden) {
			this.hiddenProperty().set(hidden);
		}




		@Override
		public String toString() {
			return getObject() == null ? null : getObject().toString();
		}

	}




	public void start(Stage stage) {
		List<String> countries = new ArrayList<>();

		countries.add("Afghanistan");
		countries.add("Albania");
		countries.add("Algeria");
		countries.add("Andorra");
		countries.add("Angola");
		countries.add("Antigua and Barbuda");
		countries.add("Argentina");
		countries.add("Armenia");
		countries.add("Australia");
		countries.add("Austria");
		countries.add("Azerbaijan");
		countries.add("Bahamas");
		countries.add("Bahrain");
		countries.add("Bangladesh");
		countries.add("Barbados");
		countries.add("Belarus");
		countries.add("Belgium");
		countries.add("Belize");
		countries.add("Benin");
		countries.add("Bhutan");
		countries.add("Bolivia");
		countries.add("Bosnia and Herzegovina");
		countries.add("Botswana");
		countries.add("Brazil");
		countries.add("Brunei");
		countries.add("Bulgaria");
		countries.add("Burkina Faso");
		countries.add("Burundi");
		countries.add("Cabo Verde");
		countries.add("Cambodia");
		countries.add("Cameroon");
		countries.add("Canada");
		countries.add("Central African Republic (CAR)");
		countries.add("Chad");
		countries.add("Chile");
		countries.add("China");
		countries.add("Colombia");
		countries.add("Comoros");
		countries.add("Democratic Republic of the Congo");
		countries.add("Republic of the Congo");
		countries.add("Costa Rica");
		countries.add("Cote d'Ivoire");
		countries.add("Croatia");
		countries.add("Cuba");
		countries.add("Cyprus");
		countries.add("Czech Republic");
		countries.add("Denmark");
		countries.add("Djibouti");
		countries.add("Dominica");
		countries.add("Dominican Republic");
		countries.add("Ecuador");
		countries.add("Egypt");
		countries.add("El Salvador");
		countries.add("Equatorial Guinea");
		countries.add("Eritrea");
		countries.add("Estonia");
		countries.add("Ethiopia");
		countries.add("Fiji");
		countries.add("Finland");
		countries.add("France");
		countries.add("Gabon");
		countries.add("Gambia");
		countries.add("Georgia");
		countries.add("Germany");
		countries.add("Ghana");
		countries.add("Greece");
		countries.add("Grenada");
		countries.add("Guatemala");
		countries.add("Guinea");
		countries.add("Guinea-Bissau");
		countries.add("Guyana");
		countries.add("Haiti");
		countries.add("Honduras");
		countries.add("Hungary");
		countries.add("Iceland");
		countries.add("India");
		countries.add("Indonesia");
		countries.add("Iran");
		countries.add("Iraq");
		countries.add("Ireland");
		countries.add("Israel");
		countries.add("Italy");
		countries.add("Jamaica");
		countries.add("Japan");
		countries.add("Jordan");
		countries.add("Kazakhstan");
		countries.add("Kenya");
		countries.add("Kiribati");
		countries.add("Kosovo");
		countries.add("Kuwait");
		countries.add("Kyrgyzstan");
		countries.add("Laos");
		countries.add("Latvia");
		countries.add("Lebanon");
		countries.add("Lesotho");
		countries.add("Liberia");
		countries.add("Libya");
		countries.add("Liechtenstein");
		countries.add("Lithuania");
		countries.add("Luxembourg");
		countries.add("Macedonia (FYROM)");
		countries.add("Madagascar");
		countries.add("Malawi");
		countries.add("Malaysia");
		countries.add("Maldives");
		countries.add("Mali");
		countries.add("Malta");
		countries.add("Marshall Islands");
		countries.add("Mauritania");
		countries.add("Mauritius");
		countries.add("Mexico");
		countries.add("Micronesia");
		countries.add("Moldova");
		countries.add("Monaco");
		countries.add("Mongolia");
		countries.add("Montenegro");
		countries.add("Morocco");
		countries.add("Mozambique");
		countries.add("Myanmar (Burma)");
		countries.add("Namibia");
		countries.add("Nauru");
		countries.add("Nepal");
		countries.add("Netherlands");
		countries.add("New Zealand");
		countries.add("Nicaragua");
		countries.add("Niger");
		countries.add("Nigeria");
		countries.add("North Korea");
		countries.add("Norway");
		countries.add("Oman");
		countries.add("Pakistan");
		countries.add("Palau");
		countries.add("Palestine");
		countries.add("Panama");
		countries.add("Papua New Guinea");
		countries.add("Paraguay");
		countries.add("Peru");
		countries.add("Philippines");
		countries.add("Poland");
		countries.add("Portugal");
		countries.add("Qatar");
		countries.add("Romania");
		countries.add("Russia");
		countries.add("Rwanda");
		countries.add("Saint Kitts and Nevis");
		countries.add("Saint Lucia");
		countries.add("Saint Vincent and the Grenadines");
		countries.add("Samoa");
		countries.add("San Marino");
		countries.add("Sao Tome and Principe");
		countries.add("Saudi Arabia");
		countries.add("Senegal");
		countries.add("Serbia");
		countries.add("Seychelles");
		countries.add("Sierra Leone");
		countries.add("Singapore");
		countries.add("Slovakia");
		countries.add("Slovenia");
		countries.add("Solomon Islands");
		countries.add("Somalia");
		countries.add("South Africa");
		countries.add("South Korea");
		countries.add("South Sudan");
		countries.add("Spain");
		countries.add("Sri Lanka");
		countries.add("Sudan");
		countries.add("Suriname");
		countries.add("Swaziland");
		countries.add("Sweden");
		countries.add("Switzerland");
		countries.add("Syria");
		countries.add("Taiwan");
		countries.add("Tajikistan");
		countries.add("Tanzania");
		countries.add("Thailand");
		countries.add("Timor-Leste");
		countries.add("Togo");
		countries.add("Tonga");
		countries.add("Trinidad and Tobago");
		countries.add("Tunisia");
		countries.add("Turkey");
		countries.add("Turkmenistan");
		countries.add("Tuvalu");
		countries.add("Uganda");
		countries.add("Ukraine");
		countries.add("United Arab Emirates (UAE)");
		countries.add("United Kingdom (UK)");
		countries.add("United States of America (USA)");
		countries.add("Uruguay");
		countries.add("Uzbekistan");
		countries.add("Vanuatu");
		countries.add("Vatican City (Holy See)");
		countries.add("Venezuela");
		countries.add("Vietnam");
		countries.add("Yemen");
		countries.add("Zambia");
		countries.add("Zimbabwe");

		ComboBox<HideableItem<String>> comboBox = createComboBoxWithAutoCompletionSupport(countries);
		comboBox.setMaxWidth(Double.MAX_VALUE);

		HBox root = new HBox();
		root.getChildren().add(comboBox);

		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

		comboBox.setMinWidth(comboBox.getWidth());
		comboBox.setPrefWidth(comboBox.getWidth());
	}




	public static void main(String[] args) {
		launch();
	}




	private static <T> ComboBox<HideableItem<T>> createComboBoxWithAutoCompletionSupport(List<T> items) {
		ObservableList<HideableItem<T>> hideableHideableItems = FXCollections.observableArrayList(hideableItem -> new Observable[]{hideableItem.hiddenProperty()});

		items.forEach(item ->
		{
			HideableItem<T> hideableItem = new HideableItem<>(item);
			hideableHideableItems.add(hideableItem);
		});

		FilteredList<HideableItem<T>> filteredHideableItems = new FilteredList<>(hideableHideableItems, t -> !t.isHidden());

		ComboBox<HideableItem<T>> comboBox = new ComboBox<>();
		comboBox.setItems(filteredHideableItems);

		@SuppressWarnings ("unchecked")
		HideableItem<T>[] selectedItem = (HideableItem<T>[]) new HideableItem[1];

		comboBox.addEventHandler(KeyEvent.KEY_PRESSED, event ->
		{
			if (!comboBox.isShowing()) return;

			comboBox.setEditable(true);
			comboBox.getEditor().clear();
		});

		comboBox.showingProperty().addListener((observable, oldValue, newValue) ->
		{
			if (newValue) {
				@SuppressWarnings ("unchecked")
				ListView<HideableItem> lv = (ListView<HideableItem>) ((ComboBoxListViewSkin<?>) comboBox.getSkin()).getPopupContent();

				Platform.runLater(() ->
				{
					if (selectedItem[0] == null) // first use
					{
						double cellHeight = ((Control) lv.lookup(".list-cell")).getHeight();
						lv.setFixedCellSize(cellHeight);
					}
				});

				lv.scrollTo(comboBox.getValue());
			} else {
				HideableItem<T> value = comboBox.getValue();
				if (value != null) selectedItem[0] = value;

				comboBox.setEditable(false);

				Platform.runLater(() ->
				{
					comboBox.getSelectionModel().select(selectedItem[0]);
					comboBox.setValue(selectedItem[0]);
				});
			}
		});

		comboBox.setOnHidden(event -> hideableHideableItems.forEach(item -> item.setHidden(false)));

		comboBox.getEditor().textProperty().addListener((obs, oldValue, newValue) ->
		{
			if (!comboBox.isShowing()) return;

			Platform.runLater(() ->
			{
				if (comboBox.getSelectionModel().getSelectedItem() == null) {
					hideableHideableItems.forEach(item -> item.setHidden(!item.getObject().toString().toLowerCase().contains(newValue.toLowerCase())));
				} else {
					boolean validText = false;

					for (HideableItem hideableItem : hideableHideableItems) {
						if (hideableItem.getObject().toString().equals(newValue)) {
							validText = true;
							break;
						}
					}

					if (!validText) comboBox.getSelectionModel().select(null);
				}
			});
		});

		return comboBox;
	}

}