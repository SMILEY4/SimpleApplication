package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.common.utils.NumberUtils;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.jfxelements.ExtendedSpinner;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.FactoryExtension;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.function.BiFunction;

@Slf4j
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
	 * The initial value.
	 */
	@Getter
	private final String initialItem;

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
	 * @param min          the min value.
	 * @param max          the max value.
	 * @param stepSize     the amount to step by.
	 * @param initialValue the initial value
	 */
	public SpinnerFactoryProperty(final int min, final int max, final int stepSize, final int initialValue) {
		super(SpinnerFactoryProperty.class, COMPARATOR);
		this.max = max;
		this.min = min;
		this.stepSize = stepSize;
		this.initialValue = initialValue;
		this.initialItem = null;
		this.items = null;
		this.wrapAround = false;
		this.dataType = DataType.INTEGER;
	}




	/**
	 * @param min          the min value.
	 * @param max          the max value.
	 * @param stepSize     the amount to step by.
	 * @param initialValue the initial value
	 */
	public SpinnerFactoryProperty(final double min, final double max, final double stepSize, final double initialValue) {
		super(SpinnerFactoryProperty.class, COMPARATOR);
		this.max = max;
		this.min = min;
		this.stepSize = stepSize;
		this.initialValue = initialValue;
		this.initialItem = null;
		this.items = null;
		this.wrapAround = false;
		this.dataType = DataType.FLOATING_POINT;
	}




	/**
	 * @param items        the items
	 * @param initialValue the initial value
	 * @param wrapAround   whether to wrap around or stop at the end/start
	 */
	public SpinnerFactoryProperty(final List<String> items, final String initialValue, final boolean wrapAround) {
		super(SpinnerFactoryProperty.class, COMPARATOR);
		this.max = null;
		this.min = null;
		this.stepSize = null;
		this.initialValue = null;
		this.initialItem = initialValue;
		this.items = items;
		this.wrapAround = wrapAround;
		this.dataType = DataType.STRINGS;
	}




	@SuppressWarnings ("unchecked")
	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		default T integerSpinnerValues(final int min,
									   final int max,
									   final int stepSize,
									   final int value) {
			getFactoryInternalProperties().add(Properties.integerSpinnerValues(min, max, stepSize, value));
			return (T) this;
		}

		default T floatingPointSpinnerValues(final double min,
											 final double max,
											 final double stepSize,
											 final double value) {
			getFactoryInternalProperties().add(Properties.floatingPointSpinnerValues(min, max, stepSize, value));
			return (T) this;
		}

		default T listSpinnerValues(final List<String> items, final String initialValue, final boolean wrapAround) {
			getFactoryInternalProperties().add(Properties.listSpinnerValues(items, initialValue, wrapAround));
			return (T) this;
		}

	}






	public static class SpinnerUpdatingBuilder implements PropFxNodeUpdatingBuilder<SpinnerFactoryProperty, ExtendedSpinner<?>> {


		@Override
		public void build(final SuiNode node, final SpinnerFactoryProperty property, final ExtendedSpinner<?> fxNode) {
			setFactory(property, fxNode);
		}




		@Override
		public MutationResult update(final SpinnerFactoryProperty property, final SuiNode node, final ExtendedSpinner<?> fxNode) {
			setFactory(property, fxNode);
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final SpinnerFactoryProperty property, final SuiNode node, final ExtendedSpinner<?> fxNode) {
			Validations.STATE.fail().exception("Can't remove {}. This property is required.", this.getClass().getSimpleName());
			return MutationResult.MUTATED;
		}




		/**
		 * Sets the factory of the given spinner
		 *
		 * @param property the property
		 * @param spinner  the spinner
		 */
		private void setFactory(final SpinnerFactoryProperty property, final ExtendedSpinner<?> spinner) {
			switch (property.getDataType()) {
				case INTEGER:
					spinner.setIntegerFactory(
							property.getMin().intValue(),
							property.getMax().intValue(),
							property.getStepSize().intValue(),
							property.getInitialValue().intValue()
					);
					break;
				case FLOATING_POINT:
					spinner.setDoubleFactory(
							property.getMin().doubleValue(),
							property.getMax().doubleValue(),
							property.getStepSize().doubleValue(),
							property.getInitialValue().doubleValue()
					);
					break;
				case STRINGS:
					spinner.setListFactory(property.getItems(), property.getInitialItem(), property.wrapAround);
					break;
				default:
					log.warn("Invalid spinner factory data type: {}.", property.getDataType());
			}
		}

	}


}



