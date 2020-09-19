package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.common.utils.NumberUtils;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.DoubleSpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.ListSpinnerValueFactory;
import lombok.Getter;

import java.util.List;
import java.util.function.BiFunction;

import static javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;

public class SpinnerFactoryProperty extends SuiProperty {


	/**
	 * The data type of the spinner factory
	 */
	private enum DataType {
		/**
		 * Integer values
		 */
		INTEGER,

		/**
		 * Floating point values
		 */
		FLOATING_POINT,

		/**
		 * String list
		 */
		STRINGS
	}






	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<SpinnerFactoryProperty, SpinnerFactoryProperty, Boolean> COMPARATOR = (a, b) -> {
		if (a.getDataType() != b.getDataType()) {
			return false;
		}
		if (a.getDataType() == DataType.INTEGER || a.getDataType() == DataType.FLOATING_POINT) {
			return NumberUtils.isEqual(a.getMin(), b.getMin())
					&& NumberUtils.isEqual(a.getMax(), b.getMax())
					&& NumberUtils.isEqual(a.getStepSize(), b.getStepSize())
					&& NumberUtils.isEqual(a.getInitialValue(), b.getInitialValue());
		} else {
			return a.isWrapAround() == b.isWrapAround() && a.getItems().equals(b.getItems());
		}
	};

	/**
	 * The data type.
	 */
	@Getter
	private final DataType dataType;

	/**
	 * The min value.
	 */
	@Getter
	private final Number min;

	/**
	 * The max value.
	 */
	@Getter
	private final Number max;

	/**
	 * The amount to step by.
	 */
	@Getter
	private final Number stepSize;

	/**
	 * The initial value.
	 */
	@Getter
	private final Number initialValue;

	/**
	 * The list of values
	 */
	@Getter
	private List<String> items;

	/**
	 * Whether to wrap around or stop at the end/start
	 */
	@Getter
	private boolean wrapAround;




	/**
	 * @param propertyId   see {@link de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty#getPropertyId()}.
	 * @param min          the min value.
	 * @param max          the max value.
	 * @param stepSize     the amount to step by.
	 * @param initialValue the initial value
	 */
	public SpinnerFactoryProperty(final String propertyId,
								  final int min,
								  final int max,
								  final int stepSize,
								  final int initialValue) {
		super(SpinnerFactoryProperty.class, COMPARATOR, propertyId);
		this.max = max;
		this.min = min;
		this.stepSize = stepSize;
		this.initialValue = initialValue;
		this.items = null;
		this.wrapAround = false;
		this.dataType = DataType.INTEGER;
	}




	/**
	 * @param propertyId   see {@link de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty#getPropertyId()}.
	 * @param min          the min value.
	 * @param max          the max value.
	 * @param stepSize     the amount to step by.
	 * @param initialValue the initial value
	 */
	public SpinnerFactoryProperty(final String propertyId,
								  final double min,
								  final double max,
								  final double stepSize,
								  final double initialValue) {
		super(SpinnerFactoryProperty.class, COMPARATOR, propertyId);
		this.max = max;
		this.min = min;
		this.stepSize = stepSize;
		this.initialValue = initialValue;
		this.items = null;
		this.wrapAround = false;
		this.dataType = DataType.FLOATING_POINT;
	}




	/**
	 * @param propertyId see {@link de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty#getPropertyId()}.
	 * @param items      the items
	 * @param wrapAround whether to wrap around or stop at the end/start
	 */
	public SpinnerFactoryProperty(final String propertyId, final List<String> items, final boolean wrapAround) {
		super(SpinnerFactoryProperty.class, COMPARATOR, propertyId);
		this.max = null;
		this.min = null;
		this.stepSize = null;
		this.initialValue = null;
		this.items = items;
		this.wrapAround = wrapAround;
		this.dataType = DataType.STRINGS;
	}




	public static class SpinnerUpdatingBuilder implements PropFxNodeUpdatingBuilder<SpinnerFactoryProperty, Spinner<?>> {


		@Override
		public void build(final SuiNode node,
						  final SpinnerFactoryProperty property,
						  final Spinner fxNode) {
			setFactory(property, fxNode);
		}




		@Override
		public MutationResult update(final SpinnerFactoryProperty property,
									 final SuiNode node,
									 final Spinner fxNode) {
			final DataType prevDataType = node.getPropertyStore()
					.getSafe(SpinnerFactoryProperty.class)
					.map(SpinnerFactoryProperty::getDataType)
					.orElse(null);

			if (prevDataType == property.getDataType()) {
				updateFactory(property, fxNode);
			} else {
				setFactory(property, fxNode);
			}
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final SpinnerFactoryProperty property,
									 final SuiNode node,
									 final Spinner<?> fxNode) {
			Validations.STATE.fail().exception("Can't remove {}. This property is required.", this.getClass().getSimpleName());
			return MutationResult.MUTATED;
		}




		/**
		 * Updates the values of the existing factory of the given spinner
		 *
		 * @param property the property
		 * @param spinner  the spinner
		 */
		private void updateFactory(final SpinnerFactoryProperty property, final Spinner spinner) {
			switch (property.getDataType()) {
				case INTEGER:
					updateIntegerFactory(property, spinner);
					break;
				case FLOATING_POINT:
					updateFloatingPointFactory(property, spinner);
					break;
				case STRINGS:
					break;
				default:
					Validations.STATE.fail().exception("Unknown factory data type: {}", property.getDataType());
			}
		}




		/**
		 * Sets the factory of the given spinner
		 *
		 * @param property the property
		 * @param spinner  the spinner
		 */
		private void setFactory(final SpinnerFactoryProperty property, final Spinner spinner) {
			switch (property.getDataType()) {
				case INTEGER:
					setIntegerFactory(property, spinner);
					break;
				case FLOATING_POINT:
					setFloatingPointFactory(property, spinner);
					break;
				case STRINGS:
					setListFactory(property, spinner);
					break;
				default:
					Validations.STATE.fail().exception("Unknown factory data type: {}", property.getDataType());
			}
		}




		/**
		 * Updates the values of the existing factory of the given spinner
		 *
		 * @param property the property
		 * @param spinner  the spinner
		 */
		private void updateIntegerFactory(final SpinnerFactoryProperty property, final Spinner spinner) {
			final IntegerSpinnerValueFactory currentFactory = (IntegerSpinnerValueFactory) spinner.getValueFactory();
			currentFactory.setMin(property.getMin().intValue());
			currentFactory.setMax(property.getMax().intValue());
			currentFactory.setAmountToStepBy(property.getStepSize().intValue());
			currentFactory.setValue(property.getInitialValue().intValue());
		}




		/**
		 * Updates the values of the existing factory of the given spinner
		 *
		 * @param property the property
		 * @param spinner  the spinner
		 */
		private void updateFloatingPointFactory(final SpinnerFactoryProperty property, final Spinner spinner) {
			final DoubleSpinnerValueFactory currentFactory = (DoubleSpinnerValueFactory) spinner.getValueFactory();
			currentFactory.setMin(property.getMin().doubleValue());
			currentFactory.setMax(property.getMax().doubleValue());
			currentFactory.setAmountToStepBy(property.getStepSize().doubleValue());
			currentFactory.setValue(property.getInitialValue().doubleValue());
		}




		/**
		 * Updates the values of the existing factory of the given spinner
		 *
		 * @param property the property
		 * @param spinner  the spinner
		 */
		private void updateListFactory(final SpinnerFactoryProperty property, final Spinner spinner) {
			final ListSpinnerValueFactory<String> currentFactory = (ListSpinnerValueFactory<String>) spinner.getValueFactory();
			currentFactory.getItems().setAll(property.getItems());
			currentFactory.setWrapAround(property.isWrapAround());
		}




		/**
		 * Sets the factory of the given spinner
		 *
		 * @param property the property
		 * @param spinner  the spinner
		 */
		private void setIntegerFactory(final SpinnerFactoryProperty property, final Spinner spinner) {
			spinner.setValueFactory(new IntegerSpinnerValueFactory(
					property.getMin().intValue(),
					property.getMax().intValue(),
					property.getInitialValue().intValue(),
					property.getStepSize().intValue()));
		}




		/**
		 * Sets the factory of the given spinner
		 *
		 * @param property the property
		 * @param spinner  the spinner
		 */
		private void setFloatingPointFactory(final SpinnerFactoryProperty property, final Spinner spinner) {
			spinner.setValueFactory(new DoubleSpinnerValueFactory(
					property.getMin().doubleValue(),
					property.getMax().doubleValue(),
					property.getInitialValue().doubleValue(),
					property.getStepSize().doubleValue()));
		}




		/**
		 * Sets the factory of the given spinner
		 *
		 * @param property the property
		 * @param spinner  the spinner
		 */
		private void setListFactory(final SpinnerFactoryProperty property, final Spinner spinner) { // todo
			ListSpinnerValueFactory<String> factory = new ListSpinnerValueFactory<>(FXCollections.observableArrayList(property.getItems()));
			factory.setWrapAround(property.isWrapAround());
			spinner.setValueFactory(factory);
		}


	}


}



