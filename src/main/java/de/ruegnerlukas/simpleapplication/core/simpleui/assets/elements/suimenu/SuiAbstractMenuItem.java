package de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.suimenu;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import javafx.scene.control.MenuItem;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

public abstract class SuiAbstractMenuItem {


	/**
	 * The child menu items.
	 */
	@Getter
	private final List<SuiAbstractMenuItem> childItems;




	/**
	 * Constructor without any child items.
	 */
	public SuiAbstractMenuItem() {
		this(List.of());
	}




	/**
	 * @param childItems the child menu items.
	 */
	public SuiAbstractMenuItem(final SuiAbstractMenuItem... childItems) {
		this(List.of(childItems));
	}




	/**
	 * @param childItems the child menu items.
	 */
	public SuiAbstractMenuItem(final List<SuiAbstractMenuItem> childItems) {
		Validations.INPUT.notNull(childItems).exception("The child items can not be null.");
		Validations.INPUT.containsNoNull(childItems).exception("The child items can not contain null-elements.");
		this.childItems = Collections.unmodifiableList(childItems);
	}




	/**
	 * Creates a javafx {@link javafx.scene.control.MenuItem} from from this menu item including all child items.
	 */
	public abstract MenuItem toFxMenuItem();


	@Override
	public abstract boolean equals(Object obj);





}
