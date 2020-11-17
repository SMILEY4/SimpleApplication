package de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.suimenu;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuItem;
import lombok.Getter;

import java.util.function.Consumer;

@Getter
public class SuiCheckMenuItem extends SuiAbstractMenuItem {


	/**
	 * The text to display.
	 */
	private final String text;


	/**
	 * The action to execute when this menu was selected/deselected. The state (whether the item is selected) is passed to the consumer.
	 */
	private final Consumer<Boolean> action;


	/**
	 * Whether the item is selected by default after creation.
	 */
	private final boolean initialState;




	/**
	 * @param text the text to display.
	 */
	public SuiCheckMenuItem(final String text) {
		this(text, null);
	}




	/**
	 * @param text         the text to display.
	 * @param initialState whether the item is selected by default after creation.
	 */
	public SuiCheckMenuItem(final String text, final boolean initialState) {
		this(text, initialState, null);
	}




	/**
	 * @param text   the text to display.
	 * @param action the action to execute when this menu was selected/deselected.
	 *               The state (whether the item is selected) is passed to the consumer.
	 */
	public SuiCheckMenuItem(final String text, final Consumer<Boolean> action) {
		this(text, false, action);
	}




	/**
	 * @param text         the text to display.
	 * @param initialState whether the item is selected by default after creation.
	 * @param action       the action to execute when this menu was selected/deselected.
	 *                     The state (whether the item is selected) is passed to the consumer.
	 */
	public SuiCheckMenuItem(final String text, final boolean initialState, final Consumer<Boolean> action) {
		Validations.INPUT.notEmpty(text).exception("The text can not be null or empty.");
		this.text = text;
		this.action = action;
		this.initialState = initialState;
	}




	@Override
	public MenuItem toFxMenuItem() {
		CheckMenuItem menuItem = new CheckMenuItem(getText());
		menuItem.setSelected(initialState);
		menuItem.selectedProperty().addListener((v, p, n) -> {
			if (action != null) {
				action.accept(n);
			}
		});
		return menuItem;
	}




	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		SuiCheckMenuItem that = (SuiCheckMenuItem) o;
		return this.getText().equals(that.getText()) && this.isInitialState() == that.isInitialState();
	}




	@Override
	public int hashCode() {
		return super.hashCode();
	}


}
