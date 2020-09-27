package de.ruegnerlukas.simpleapplication.simpleui.assets.elements.suimenu;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import lombok.Getter;

import java.util.Objects;

@Getter
public class SuiMenuItem extends SuiAbstractMenuItem {


	/**
	 * The text to display.
	 */
	private final String text;

	/**
	 * The shortcut key combination for this item or null
	 */
	private final KeyCombination shortcut;

	/**
	 * The action to execute when this menu was selected.
	 */
	private final Runnable action;




	/**
	 * @param text the text to display.
	 */
	public SuiMenuItem(final String text) {
		this(text, null, null);
	}




	/**
	 * @param text     the text to display.
	 * @param shortcut the shortcut key combination for this item or null
	 */
	public SuiMenuItem(final String text, final KeyCombination shortcut) {
		this(text, shortcut, null);
	}




	/**
	 * @param text   the text to display.
	 * @param action the action to execute when this menu was selected.
	 */
	public SuiMenuItem(final String text, final Runnable action) {
		this(text, null, action);
	}




	/**
	 * @param text     the text to display.
	 * @param shortcut the shortcut key combination for this item or null
	 * @param action   the action to execute when this menu was selected.
	 */
	public SuiMenuItem(final String text, final KeyCombination shortcut, final Runnable action) {
		Validations.INPUT.notEmpty(text).exception("The text can not be null or empty.");
		this.text = text;
		this.action = action;
		this.shortcut = shortcut;
	}




	@Override
	public MenuItem toFxMenuItem() {
		MenuItem menuItem = new MenuItem(getText());
		menuItem.setOnAction(e -> {
			if (action != null) {
				action.run();
			}
		});
		if (shortcut != null) {
			menuItem.setAccelerator(shortcut);
		}
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
		SuiMenuItem that = (SuiMenuItem) o;
		return this.getText().equals(that.getText()) && Objects.equals(this.getShortcut(), that.getShortcut());
	}




	@Override
	public int hashCode() {
		return super.hashCode();
	}


}
