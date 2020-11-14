package de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.jfxelements;

import de.ruegnerlukas.simpleapplication.core.simpleui.utils.MutableConsumer;
import javafx.scene.control.CheckBox;

import java.util.function.Consumer;

public class ExtendedCheckbox extends CheckBox {


	/**
	 * The listener of this checkbox or null
	 */
	private final MutableConsumer<Boolean> listener = new MutableConsumer<>();




	/**
	 * Default constructor
	 */
	public ExtendedCheckbox() {
		selectedProperty().addListener((value, prev, next) -> {
			listener.accept(next);
		});
	}




	/**
	 * Sets the listener to the given listener
	 *
	 * @param listener the new listener or null
	 */
	public void setListener(final Consumer<Boolean> listener) {
		this.listener.setConsumer(listener);
	}




	/**
	 * Checks/Unchecks this checkbox without triggering the added listener
	 *
	 * @param selected whether to check/uncheck the box
	 */
	public void select(final boolean selected) {
		listener.runMuted(() -> setSelected(selected));
	}


}
