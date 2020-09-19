package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnValueChangedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.AlignmentProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.BlockIncrementProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.LabelFormatterProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.LabelSizeProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.MinMaxProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.SpacingProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TickMarkProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.AbstractFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Set;

import static de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry.PropertyEntry;

public final class SuiLabeledSlider {


	/**
	 * Hidden constructor for utility classes
	 */
	private SuiLabeledSlider() {
		// do nothing
	}




	/**
	 * Creates a new slider with a label next to it.
	 *
	 * @param properties the properties
	 * @return the factory for a labeled slider
	 */
	public static NodeFactory labeledSlider(final SuiProperty... properties) {
		Validations.INPUT.notNull(properties).exception("The properties may not be null.");
		Validations.INPUT.containsNoNull(properties).exception("The properties may not contain null-entries");
		Properties.validate(SuiLabeledSlider.class, properties);
		return (state, tags) -> SuiNode.create(
				SuiLabeledSlider.class,
				List.of(properties),
				state,
				tags
		);
	}




	/**
	 * Register this node type at the given registry.
	 *
	 * @param registry the registry
	 */
	public static void register(final SuiRegistry registry) {
		registry.registerBaseFxNodeBuilder(SuiLabeledSlider.class, new FxNodeBuilder());
		registry.registerProperties(SuiLabeledSlider.class, PropertyGroups.commonProperties());
		registry.registerProperties(SuiLabeledSlider.class, PropertyGroups.commonRegionProperties());
		registry.registerProperties(SuiLabeledSlider.class, PropertyGroups.commonEventProperties());
		registry.registerProperties(SuiLabeledSlider.class, List.of(
				PropertyEntry.of(MinMaxProperty.class, new MinMaxProperty.LabeledSliderUpdatingBuilder()),
				PropertyEntry.of(BlockIncrementProperty.class, new BlockIncrementProperty.LabeledSliderUpdatingBuilder()),
				PropertyEntry.of(LabelFormatterProperty.class, new LabelFormatterProperty.LabeledSliderUpdatingBuilder()),
				PropertyEntry.of(OnValueChangedEventProperty.class, new OnValueChangedEventProperty.LabeledSliderUpdatingBuilder()),
				PropertyEntry.of(TickMarkProperty.class, new TickMarkProperty.LabeledSliderUpdatingBuilder()),
				PropertyEntry.of(AlignmentProperty.class, new AlignmentProperty.LabeledSliderUpdatingBuilder()),
				PropertyEntry.of(LabelSizeProperty.class, new LabelSizeProperty.LabeledSliderUpdatingBuilder()),
				PropertyEntry.of(SpacingProperty.class, new SpacingProperty.LabeledSliderUpdatingBuilder())
		));
	}




	/**
	 * The alignments resulting in a horizontal layout.
	 */
	private static final Set<Pos> HORZ_ALIGNMENTS =
			Set.of(Pos.BASELINE_LEFT, Pos.BASELINE_CENTER, Pos.BASELINE_RIGHT, Pos.CENTER_LEFT, Pos.CENTER_RIGHT, Pos.CENTER);




	/**
	 * @param alignment the alignment
	 * @return whether the slider and label are laid out next to each other horizontally.
	 */
	public static boolean isHorizontalLayout(final Pos alignment) {
		return HORZ_ALIGNMENTS.contains(alignment);
	}




	/**
	 * @param node the node to check
	 * @return whether the slider and label are laid out next to each other horizontally.
	 */
	public static boolean isHorizontalLayout(final SuiNode node) {
		return HORZ_ALIGNMENTS.contains(getAlignment(node));
	}




	/**
	 * @param node the node
	 * @return the alignment from the property or a default alignment.
	 */
	public static Pos getAlignment(final SuiNode node) {
		return node.getPropertyStore().getSafe(AlignmentProperty.class)
				.map(AlignmentProperty::getAlignment)
				.orElse(Pos.BASELINE_RIGHT);
	}




	/**
	 * @param pane the pane
	 * @return the child slider-node from the given pane
	 */
	public static Slider getSlider(final Pane pane) {
		for (Node node : pane.getChildren()) {
			if (node instanceof Slider) {
				return (Slider) node;
			}
		}
		return null;
	}




	/**
	 * @param pane the pane
	 * @return the child label-node from the given pane
	 */
	public static Label getLabel(final Pane pane) {
		for (Node node : pane.getChildren()) {
			if (node instanceof Label) {
				return (Label) node;
			}
		}
		return null;
	}




	private static class FxNodeBuilder implements AbstractFxNodeBuilder<Pane> {


		@Override
		public Pane build(final SuiNode node) {

			final Slider slider = buildSlider();
			final Label label = buildLabel();
			final Pane box = buildBox(slider, label, isHorizontalLayout(getAlignment(node)));

			slider.valueProperty().addListener((value, prev, next) -> label.setText(valueToString(node, next.doubleValue())));
			label.setText(valueToString(node, slider.getValue()));

			return box;
		}




		/**
		 * Formats the given number by either using a provided label-formatter-property or by simply converting to a string.
		 *
		 * @param node  the node
		 * @param value the value to format
		 * @return the formatted value
		 */
		private String valueToString(final SuiNode node, final double value) {
			final LabelFormatterProperty propFormatter = node.getPropertyStore().get(LabelFormatterProperty.class);
			if (propFormatter == null) {
				return String.valueOf(value);
			} else {
				return propFormatter.getFormatter().apply(value);
			}
		}




		/**
		 * @return the slider
		 */
		private Slider buildSlider() {
			final Slider slider = new Slider();
			slider.setShowTickMarks(TickMarkProperty.DEFAULT_STYLE.isShowTickMarks());
			slider.setShowTickLabels(TickMarkProperty.DEFAULT_STYLE.isShowLabels());
			slider.setMajorTickUnit(TickMarkProperty.DEFAULT_MAJOR_TICK_UNIT);
			slider.setMinorTickCount(TickMarkProperty.DEFAULT_MINOR_TICK_COUNT);
			slider.setSnapToTicks(TickMarkProperty.DEFAULT_SNAP_TO_TICKS);
			slider.setMinSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
			slider.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
			slider.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
			HBox.setHgrow(slider, Priority.ALWAYS);
			return slider;
		}




		/**
		 * @return the label
		 */
		private Label buildLabel() {
			final Label label = new Label();
			label.setMinSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
			label.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
			label.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
			return label;
		}




		/**
		 * @param slider     the slider
		 * @param label      the label
		 * @param horizontal whether the label is to the left or right of the slider
		 * @return the hbox or vbox (depending on value of "horizontal")
		 */
		private Pane buildBox(final Slider slider, final Label label, final boolean horizontal) {
			final Pane box = horizontal ? new HBox() : new VBox();
			box.getChildren().addAll(label, slider);
			box.setMinSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
			box.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
			box.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_PREF_SIZE);
			return box;
		}

	}


}
