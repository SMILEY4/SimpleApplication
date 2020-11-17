package de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.suimenu;

import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import lombok.Getter;

@Getter
public class SuiSeparatorMenuItem extends SuiAbstractMenuItem {


	@Override
	public MenuItem toFxMenuItem() {
		return new SeparatorMenuItem();
	}




	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		return o != null && getClass() == o.getClass();
	}




	@Override
	public int hashCode() {
		return super.hashCode();
	}


}
