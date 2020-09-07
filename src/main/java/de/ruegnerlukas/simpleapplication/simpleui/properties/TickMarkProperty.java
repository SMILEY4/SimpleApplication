package de.ruegnerlukas.simpleapplication.simpleui.properties;


import de.ruegnerlukas.simpleapplication.common.utils.NumberUtils;
import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.elements.basenode.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;
import javafx.scene.control.Slider;
import lombok.Getter;

public class TickMarkProperty extends Property {

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
	 * @param tickMarkStyle  the style of the tick marks
	 * @param majorTickUnit  the unit of the major tick marks
	 * @param minorTickCount how many minor tick marks for each major mark
	 * @param snapToTicks    whether to snap to the tick marks
	 */
	public TickMarkProperty(final TickMarkStyle tickMarkStyle,
							final Number majorTickUnit,
							final int minorTickCount,
							final boolean snapToTicks) {
		super(TickMarkProperty.class);
		this.tickMarkStyle = tickMarkStyle;
		this.majorTickUnit = majorTickUnit;
		this.minorTickCount = minorTickCount;
		this.snapToTicks = snapToTicks;
	}




	@Override
	protected boolean isPropertyEqual(final Property other) {
		return getTickMarkStyle().equals(((TickMarkProperty) other).getTickMarkStyle())
				&& NumberUtils.isEqual(getMajorTickUnit(), ((TickMarkProperty) other).getMajorTickUnit())
				&& getMinorTickCount() == ((TickMarkProperty) other).getMinorTickCount()
				&& isSnapToTicks() == ((TickMarkProperty) other).isSnapToTicks();
	}




	@Override
	public String printValue() {
		return getTickMarkStyle().toString() + "/" + getMajorTickUnit() + "/" + getMinorTickCount() + "/" + isSnapToTicks();
	}




	public static class SliderUpdatingBuilder implements PropFxNodeUpdatingBuilder<TickMarkProperty, Slider> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers,
						  final SuiNode node,
						  final TickMarkProperty property,
						  final Slider fxNode) {
			fxNode.setShowTickMarks(property.getTickMarkStyle().isShowTickMarks());
			fxNode.setShowTickLabels(property.getTickMarkStyle().isShowLabels());
			fxNode.setMajorTickUnit(property.getMajorTickUnit().doubleValue());
			fxNode.setMinorTickCount(property.getMinorTickCount());
			fxNode.setSnapToTicks(property.isSnapToTicks());
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers,
									 final TickMarkProperty property,
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
		public MutationResult remove(final MasterNodeHandlers nodeHandlers,
									 final TickMarkProperty property,
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


}



