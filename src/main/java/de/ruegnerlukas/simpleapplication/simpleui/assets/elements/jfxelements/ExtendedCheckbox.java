package de.ruegnerlukas.simpleapplication.simpleui.assets.elements.jfxelements;

import javafx.scene.control.CheckBox;

import java.util.function.Consumer;

public class ExtendedCheckbox extends CheckBox {


	/**
	 * The listener of this checkbox or null
	 */
	private Consumer<Boolean> listener;

	/**
	 * Whether the listener should receive events
	 */
	private boolean muted = false;




	/**
	 * Default constructor
	 */
	public ExtendedCheckbox() {
		selectedProperty().addListener((value, prev, next) -> {
			if (!muted && listener != null) {
				listener.accept(next);
			}
		});
	}




	/**
	 * Sets the listener to the given listener
	 *
	 * @param listener the new listener or null
	 */
	public void setListener(final Consumer<Boolean> listener) {
		this.listener = listener;
	}




	/**
	 * Checks/Unchecks this checkbox without triggering the added listener
	 *
	 * @param selected whether to check/uncheck the box
	 */
	public void select(final boolean selected) {
		muted = true;
		this.setSelected(selected);
		muted = false;
	}


}
