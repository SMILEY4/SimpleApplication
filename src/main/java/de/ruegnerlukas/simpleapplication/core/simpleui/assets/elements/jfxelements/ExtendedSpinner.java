package de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.jfxelements;

import de.ruegnerlukas.simpleapplication.core.simpleui.utils.MutableBiConsumer;
import javafx.collections.FXCollections;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;

import java.util.List;
import java.util.function.BiConsumer;

import static javafx.scene.control.SpinnerValueFactory.DoubleSpinnerValueFactory;
import static javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import static javafx.scene.control.SpinnerValueFactory.ListSpinnerValueFactory;

public class ExtendedSpinner<T> extends Spinner<T> {


	/**
	 * The mutable listener.
	 */
	private final MutableBiConsumer<T, T> listener = new MutableBiConsumer<>();




	/**
	 * Default constructor.
	 */
	public ExtendedSpinner() {
		addTextInputSanitizers();
		valueProperty().addListener((value, prev, next) -> {
			if (isValidChange(prev, next)) {
				listener.accept(prev, next);
			}
		});
	}




	/**
	 * Checks if the given change is valid for the current value factory
	 *
	 * @param prev the previous value
	 * @param next the new value
	 */
	private boolean isValidChange(final T prev, final T next) {
		if (getValueFactory() instanceof IntegerSpinnerValueFactory) {
			final IntegerSpinnerValueFactory intFactory = (IntegerSpinnerValueFactory) getValueFactory();
			return next != null && (int) next >= intFactory.getMin() && (int) next <= intFactory.getMax();
		}
		if (getValueFactory() instanceof DoubleSpinnerValueFactory) {
			final DoubleSpinnerValueFactory doubleFactory = (DoubleSpinnerValueFactory) getValueFactory();
			return next != null && (double) next >= doubleFactory.getMin() && (double) next <= doubleFactory.getMax();
		}
		if (getValueFactory() instanceof ListSpinnerValueFactory) {
			final ListSpinnerValueFactory<String> listFactory = (ListSpinnerValueFactory<String>) getValueFactory();
			return next != null && listFactory.getItems().contains(next);
		}
		return false;
	}




	/**
	 * Sets the listener for value changes.
	 *
	 * @param listener the listener
	 */
	public void setListener(final BiConsumer<T, T> listener) {
		this.listener.setConsumer(listener);
	}




	/**
	 * Updates or creates the integer value factory.
	 *
	 * @param min     the lower bound (included)
	 * @param max     the upper bound (included)
	 * @param step    the step size
	 * @param initial the initial value
	 */
	public void setIntegerFactory(final int min, final int max, final int step, final int initial) {
		listener.runMuted(() -> {
			if (getValueFactory() instanceof IntegerSpinnerValueFactory) {
				updateIntegerFactory(min, max, step, initial);
			} else {
				//noinspection unchecked
				setValueFactory((SpinnerValueFactory<T>) createIntegerFactory(min, max, step, initial));
			}
		});
	}




	/**
	 * Updates or creates the floating-point value factory.
	 *
	 * @param min          the lower bound (included)
	 * @param max          the upper bound (included)
	 * @param stepSize     the step size
	 * @param initialValue the initial value
	 */
	public void setDoubleFactory(final double min, final double max, final double stepSize, final double initialValue) {
		listener.runMuted(() -> {
			if (getValueFactory() instanceof DoubleSpinnerValueFactory) {
				updateDoubleFactory(min, max, stepSize, initialValue);
			} else {
				//noinspection unchecked
				setValueFactory((SpinnerValueFactory<T>) createDoubleFactory(min, max, stepSize, initialValue));
			}
		});
	}




	/**
	 * Updates or creates the list value factory.
	 *
	 * @param values       the possible values
	 * @param initialValue the initial value
	 * @param wrapAround   whether to wrap around or stop at the end/start
	 */
	public void setListFactory(final List<String> values, final String initialValue, final boolean wrapAround) {
		listener.runMuted(() -> {
			if (getValueFactory() instanceof ListSpinnerValueFactory) {
				updateListFactory(values, initialValue, wrapAround);
			} else {
				//noinspection unchecked
				setValueFactory((SpinnerValueFactory<T>) createListFactory(values, initialValue, wrapAround));
			}
		});
	}




	/**
	 * Updates the current integer value factory.
	 *
	 * @param min          the lower bound (included)
	 * @param max          the upper bound (included)
	 * @param stepSize     the step size
	 * @param initialValue the initial value
	 */
	private void updateIntegerFactory(final int min, final int max, final int stepSize, final int initialValue) {
		final SpinnerValueFactory<T> valueFactory = getValueFactory();
		if (valueFactory instanceof IntegerSpinnerValueFactory) {
			final IntegerSpinnerValueFactory intValueFactory = (IntegerSpinnerValueFactory) valueFactory;
			intValueFactory.setMin(min);
			intValueFactory.setMax(max);
			intValueFactory.setAmountToStepBy(stepSize);
			intValueFactory.setValue(initialValue);
		}
	}




	/**
	 * Creates a new spinner value factory for integer values
	 *
	 * @param min          the lower bound (included)
	 * @param max          the upper bound (included)
	 * @param stepSize     the step size
	 * @param initialValue the initial value
	 * @return the created factory
	 */
	private IntegerSpinnerValueFactory createIntegerFactory(final int min, final int max, final int stepSize, final int initialValue) {
		return new IntegerSpinnerValueFactory(min, max, initialValue, stepSize);
	}




	/**
	 * Updates the current floating-point value factory.
	 *
	 * @param min          the lower bound (included)
	 * @param max          the upper bound (included)
	 * @param stepSize     the step size
	 * @param initialValue the initial value
	 */
	private void updateDoubleFactory(final double min, final double max, final double stepSize, final double initialValue) {
		final SpinnerValueFactory<T> valueFactory = getValueFactory();
		if (valueFactory instanceof DoubleSpinnerValueFactory) {
			final DoubleSpinnerValueFactory doubleValueFactory = (DoubleSpinnerValueFactory) valueFactory;
			doubleValueFactory.setMin(min);
			doubleValueFactory.setMax(max);
			doubleValueFactory.setAmountToStepBy(stepSize);
			doubleValueFactory.setValue(initialValue);
		}
	}




	/**
	 * Creates a new spinner value factory for floating-point values.
	 *
	 * @param min          the lower bound (included)
	 * @param max          the upper bound (included)
	 * @param stepSize     the step size
	 * @param initialValue the initial value
	 * @return the created factory
	 */
	public DoubleSpinnerValueFactory createDoubleFactory(final double min,
														 final double max,
														 final double stepSize,
														 final double initialValue) {
		return new DoubleSpinnerValueFactory(min, max, initialValue, stepSize);
	}




	/**
	 * Updates the current list value factory.
	 *
	 * @param values       the possible values
	 * @param initialValue the initial value
	 * @param wrapAround   whether to wrap around or stop at the end/start
	 */
	private void updateListFactory(final List<String> values, final String initialValue, final boolean wrapAround) {
		final SpinnerValueFactory<T> valueFactory = getValueFactory();
		if (valueFactory instanceof ListSpinnerValueFactory) {
			final ListSpinnerValueFactory<String> listValueFactory = (ListSpinnerValueFactory<String>) valueFactory;
			listValueFactory.setItems(FXCollections.observableArrayList(values));
			listValueFactory.setValue(initialValue);
			listValueFactory.setWrapAround(wrapAround);
		}
	}




	/**
	 * Creates a new spinner value factory for lists.
	 *
	 * @param values       the possible values
	 * @param initialValue the initial value
	 * @param wrapAround   whether to wrap around or stop at the end/start
	 * @return the created factory
	 */
	public ListSpinnerValueFactory<String> createListFactory(final List<String> values,
															 final String initialValue,
															 final boolean wrapAround) {
		final ListSpinnerValueFactory<String> factory = new ListSpinnerValueFactory<>(FXCollections.observableArrayList(values));
		factory.setValue(initialValue);
		factory.setWrapAround(wrapAround);
		return factory;
	}




	/**
	 * Prevent errors when the user enters invalid text.
	 * This does not prevent exceptions when the editor looses focus with an invalid content, only when the enter-key was pressed.
	 */
	private void addTextInputSanitizers() {
		getEditor().addEventHandler(KeyEvent.KEY_PRESSED, event -> {
			if (event.getCode() == KeyCode.ENTER) {
				sanitizeTextInput();
			}
		});
	}




	/**
	 * Cleans the text input to prevent errors with invalid input.
	 */
	private void sanitizeTextInput() {
		final StringConverter<T> converter = getValueFactory().getConverter();
		if (converter != null) {
			try {
				converter.fromString(getEditor().getText());
			} catch (Exception e) {
				listener.runMuted(() -> getEditor().setText(String.valueOf(converter.toString(getValue()))));
			}
		}
	}


}
