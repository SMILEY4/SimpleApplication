package de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.jfxelements;

import de.ruegnerlukas.simpleapplication.core.simpleui.utils.MutableConsumer;
import javafx.scene.control.RadioButton;

import java.util.function.Consumer;

public class ExtendedRadioButton extends RadioButton {


	/**
	 * The listener of this radiobutton or null
	 */
	private final MutableConsumer<Boolean> listener = new MutableConsumer<>();




	/**
	 * Default constructor
	 */
	public ExtendedRadioButton() {
		sceneProperty().addListener((v, p, n) -> {
			if (n == null) {
				this.setToggleGroup(null);
			}
		});
		selectedProperty().addListener((value, prev, next) -> {
			listener.accept(next);
		});
	}




	/**
	 * Saves the id of the node to the user data of this toggle
	 *
	 * @param suiNodeId the id of the node
	 */
	public void setSuiNodeId(final String suiNodeId) {
		this.setUserData(suiNodeId);
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
	 * Selects/deselects this radiobutton without triggering the added listener
	 *
	 * @param selected whether to check/uncheck the box
	 */
	public void select(final boolean selected) {
		listener.runMuted(() -> setSelected(selected));
	}


}
