package de.ruegnerlukas.simpleapplication.simpleui.assets.elements.suimenu;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import lombok.Getter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
public class SuiMenu extends SuiAbstractMenuItem {


	/**
	 * The text to display.
	 */
	private final String text;




	/**
	 * @param text     the text to display.
	 * @param subMenus the child menu items
	 */
	public SuiMenu(final String text, final SuiAbstractMenuItem... subMenus) {
		this(text, List.of(subMenus));
	}




	/**
	 * @param text     the text to display.
	 * @param subMenus the child menu items
	 */
	public SuiMenu(final String text, final List<SuiAbstractMenuItem> subMenus) {
		super(subMenus);
		Validations.INPUT.notEmpty(text).exception("The text can not be null or empty.");
		this.text = text;
	}




	@Override
	public MenuItem toFxMenuItem() {
		final Menu menu = new Menu(getText());
		menu.getItems().setAll(this.getChildItems().stream()
				.map(SuiAbstractMenuItem::toFxMenuItem)
				.collect(Collectors.toList()));
		return menu;
	}




	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		SuiMenu that = (SuiMenu) o;
		if (!Objects.equals(this.getText(), that.getText())) {
			return false;
		}
		return getChildItems() != null ? getChildItems().equals(that.getChildItems()) : that.getChildItems() == null;
	}




	@Override
	public int hashCode() {
		return super.hashCode();
	}


}
