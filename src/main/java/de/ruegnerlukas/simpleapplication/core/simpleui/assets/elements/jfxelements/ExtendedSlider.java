package de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.jfxelements;

import de.ruegnerlukas.simpleapplication.core.simpleui.utils.MutableBiConsumer;
import javafx.scene.control.Slider;

import java.util.function.BiConsumer;

public class ExtendedSlider extends Slider {


	/**
	 * The mutable listener
	 */
	private final MutableBiConsumer<Number, Number> listener = new MutableBiConsumer<>();




	/**
	 * Default constructor
	 */
	public ExtendedSlider() {
		this.valueProperty().addListener((value, prev, next) -> listener.accept(prev, next));
	}




	/**
	 * Sets the listener
	 *
	 * @param listener the listener or null
	 */
	public void setListener(final BiConsumer<Number, Number> listener) {
		this.listener.setConsumer(listener);
	}




	/**
	 * Sets the min and max value without triggering the listener.
	 *
	 * @param min the min value
	 * @param max the max value
	 */
	public void setMinMax(final double min, final double max) {
		listener.runMuted(() -> {
			setMin(min);
			setMax(max);
		});
	}




	/**
	 * Sets the value of this slider to the given value
	 *
	 * @param value          the new value
	 * @param notifyListener whether to notify the listener of this change
	 */
	public void setValue(final double value, final boolean notifyListener) {
		final Runnable action = () -> this.setValue(value);
		if (notifyListener) {
			action.run();
		} else {
			listener.runMuted(action);
		}
	}


}
