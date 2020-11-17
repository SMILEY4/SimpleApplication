package de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.common.utils.NumberUtils;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.SuiLabeledSlider;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.builders.FactoryExtension;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import lombok.Getter;

import java.util.function.BiFunction;

public class TickMarkProperty extends SuiProperty {


	/**
	 * The default value for the tick marks style.
	 */
	public static final TickMarkStyle DEFAULT_STYLE = TickMarkStyle.ONLY_TICKS;

	/**
	 * The default value for the major tick unit.
	 */
	public static final double DEFAULT_MAJOR_TICK_UNIT = 100;

	/**
	 * The default value for the minor tick count.
	 */
	public static final int DEFAULT_MINOR_TICK_COUNT = 0;
	/**
	 * The default value for snap to ticks.
	 */
	public static final boolean DEFAULT_SNAP_TO_TICKS = false;






	public enum TickMarkStyle {

		/**
		 * No tick marks, no labels.
		 */
		NOTHING(false, false),

		/**
		 * Only tick marks, no labels.
		 */
		ONLY_TICKS(true, false),

		/**
		 * No tick marks, only labels.
		 */
		ONLY_LABELS(false, true),

		/**
		 * Tick marks with labels.
		 */
		LABELED_TICKS(true, true);

		/**
		 * Whether the tick marks are shown.
		 */
		@Getter
		private final boolean showTickMarks;
		/**
		 * Whether the labels are shown.
		 */
		@Getter
		private final boolean showLabels;




		/**
		 * @param showTickMarks whether the tick marks are shown
		 * @param showLabels    whether the labels are shown
		 */
		TickMarkStyle(final boolean showTickMarks, final boolean showLabels) {
			this.showTickMarks = showTickMarks;
			this.showLabels = showLabels;
		}
	}






	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<TickMarkProperty, TickMarkProperty, Boolean> COMPARATOR =
			(a, b) -> a.getTickMarkStyle().equals(b.getTickMarkStyle())
					&& NumberUtils.isEqual(a.getMajorTickUnit(), b.getMajorTickUnit())
					&& a.getMinorTickCount() == b.getMinorTickCount()
					&& a.isSnapToTicks() == b.isSnapToTicks();


	/**
	 * The style of the tick marks
	 */
	@Getter
	private final TickMarkStyle tickMarkStyle;


	/**
	 * The unit of the major tick marks.
	 */
	@Getter
	private final Number majorTickUnit;


	/**
	 * How many minor tick marks for each major mark.
	 */
	@Getter
	private final int minorTickCount;


	/**
	 * Whether to snap to the tick marks.
	 */
	@Getter
	private final boolean snapToTicks;




	/**
	 * @param tickMarkStyle the style of the tick marks
	 */
	public TickMarkProperty(final TickMarkStyle tickMarkStyle) {
		this(tickMarkStyle, DEFAULT_MAJOR_TICK_UNIT, DEFAULT_MINOR_TICK_COUNT, DEFAULT_SNAP_TO_TICKS);
	}




	/**
	 * @param tickMarkStyle  the style of the tick marks
	 * @param majorTickUnit  the unit of the major tick marks
	 * @param minorTickCount how many minor tick marks for each major mark
	 */
	public TickMarkProperty(final TickMarkStyle tickMarkStyle,
							final Number majorTickUnit,
							final int minorTickCount) {
		this(tickMarkStyle, majorTickUnit, minorTickCount, DEFAULT_SNAP_TO_TICKS);
	}




	/**
	 * @param tickMarkStyle  the style of the tick marks
	 * @param majorTickUnit  the unit of the major tick marks
	 * @param minorTickCount how many minor tick marks for each major mark
	 * @param snapToTicks    whether to snap to the tick marks
	 */
	public TickMarkProperty(final TickMarkStyle tickMarkStyle,
							final Number majorTickUnit,
							final int minorTickCount,
							final boolean snapToTicks) {
		super(TickMarkProperty.class, COMPARATOR);
		Validations.INPUT.notNull(tickMarkStyle).exception("The tick mark style may not be null.");
		this.tickMarkStyle = tickMarkStyle;
		this.majorTickUnit = majorTickUnit;
		this.minorTickCount = minorTickCount;
		this.snapToTicks = snapToTicks;
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param tickMarkStyle the tick mark style
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T tickMarks(final TickMarkProperty.TickMarkStyle tickMarkStyle) {
			getBuilderProperties().add(new TickMarkProperty(tickMarkStyle));
			return (T) this;
		}

		/**
		 * @param tickMarkStyle  the tick mark style
		 * @param majorTickUnit  the units for major ticks
		 * @param minorTickCount the count of minor ticks between major ticks
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T tickMarks(final TickMarkProperty.TickMarkStyle tickMarkStyle,
							final Number majorTickUnit,
							final int minorTickCount) {
			getBuilderProperties().add(new TickMarkProperty(tickMarkStyle, majorTickUnit, minorTickCount));
			return (T) this;
		}

		/**
		 * @param tickMarkStyle  the tick mark style
		 * @param majorTickUnit  the units for major ticks
		 * @param minorTickCount the count of minor ticks between major ticks
		 * @param snapToTicks    whether to snap to ticks
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T tickMarks(final TickMarkProperty.TickMarkStyle tickMarkStyle,
							final Number majorTickUnit,
							final int minorTickCount,
							final boolean snapToTicks) {
			getBuilderProperties().add(new TickMarkProperty(tickMarkStyle, majorTickUnit, minorTickCount, snapToTicks));
			return (T) this;
		}


	}






	public static class SliderUpdatingBuilder implements PropFxNodeUpdatingBuilder<TickMarkProperty, Slider> {


		@Override
		public void build(final SuiNode node,
						  final TickMarkProperty property,
						  final Slider fxNode) {
			fxNode.setShowTickMarks(property.getTickMarkStyle().isShowTickMarks());
			fxNode.setShowTickLabels(property.getTickMarkStyle().isShowLabels());
			fxNode.setMajorTickUnit(property.getMajorTickUnit().doubleValue());
			fxNode.setMinorTickCount(property.getMinorTickCount());
			fxNode.setSnapToTicks(property.isSnapToTicks());
		}




		@Override
		public MutationResult update(final TickMarkProperty property,
									 final SuiNode node,
									 final Slider fxNode) {
			fxNode.setShowTickMarks(property.getTickMarkStyle().isShowTickMarks());
			fxNode.setShowTickLabels(property.getTickMarkStyle().isShowLabels());
			fxNode.setMajorTickUnit(property.getMajorTickUnit().doubleValue());
			fxNode.setMinorTickCount(property.getMinorTickCount());
			fxNode.setSnapToTicks(property.isSnapToTicks());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final TickMarkProperty property,
									 final SuiNode node,
									 final Slider fxNode) {
			fxNode.setShowTickMarks(DEFAULT_STYLE.isShowTickMarks());
			fxNode.setShowTickLabels(DEFAULT_STYLE.isShowLabels());
			fxNode.setMajorTickUnit(DEFAULT_MAJOR_TICK_UNIT);
			fxNode.setMinorTickCount(DEFAULT_MINOR_TICK_COUNT);
			fxNode.setSnapToTicks(DEFAULT_SNAP_TO_TICKS);
			return MutationResult.MUTATED;
		}

	}






	public static class LabeledSliderUpdatingBuilder implements PropFxNodeUpdatingBuilder<TickMarkProperty, Pane> {


		@Override
		public void build(final SuiNode node,
						  final TickMarkProperty property,
						  final Pane fxNode) {
			final Slider slider = SuiLabeledSlider.getSlider(fxNode);
			slider.setShowTickMarks(property.getTickMarkStyle().isShowTickMarks());
			slider.setShowTickLabels(property.getTickMarkStyle().isShowLabels());
			slider.setMajorTickUnit(property.getMajorTickUnit().doubleValue());
			slider.setMinorTickCount(property.getMinorTickCount());
			slider.setSnapToTicks(property.isSnapToTicks());
		}




		@Override
		public MutationResult update(final TickMarkProperty property,
									 final SuiNode node,
									 final Pane fxNode) {
			final Slider slider = SuiLabeledSlider.getSlider(fxNode);
			slider.setShowTickMarks(property.getTickMarkStyle().isShowTickMarks());
			slider.setShowTickLabels(property.getTickMarkStyle().isShowLabels());
			slider.setMajorTickUnit(property.getMajorTickUnit().doubleValue());
			slider.setMinorTickCount(property.getMinorTickCount());
			slider.setSnapToTicks(property.isSnapToTicks());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final TickMarkProperty property,
									 final SuiNode node,
									 final Pane fxNode) {
			final Slider slider = SuiLabeledSlider.getSlider(fxNode);
			slider.setShowTickMarks(DEFAULT_STYLE.isShowTickMarks());
			slider.setShowTickLabels(DEFAULT_STYLE.isShowLabels());
			slider.setMajorTickUnit(DEFAULT_MAJOR_TICK_UNIT);
			slider.setMinorTickCount(DEFAULT_MINOR_TICK_COUNT);
			slider.setSnapToTicks(DEFAULT_SNAP_TO_TICKS);
			return MutationResult.MUTATED;
		}

	}


}



