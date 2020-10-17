package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;

import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.jfxelements.ExtendedSlider;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.BaseBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.CommonEventBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnValueChangedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.AlignmentProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.BlockIncrementProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.EditableProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.LabelFormatterProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.LabelSizeProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.MinMaxProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.OrientationProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.SpacingProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TickMarkProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TooltipProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.AbstractFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.tags.Tags;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.builders.BuilderExtensionContainer;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.builders.RegionBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

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
	 * Build a new element
	 *
	 * @return the builder for the element
	 */
	public static SuiLabeledSliderBuilder create() {
		return new SuiLabeledSliderBuilder();
	}




	public static class SuiLabeledSliderBuilder extends BuilderExtensionContainer implements
			BaseBuilderExtension<SuiLabeledSliderBuilder>,
			RegionBuilderExtension<SuiLabeledSliderBuilder>,
			CommonEventBuilderExtension<SuiLabeledSliderBuilder>,
			AlignmentProperty.PropertyBuilderExtension<SuiLabeledSliderBuilder>,
			TooltipProperty.PropertyBuilderExtension<SuiLabeledSliderBuilder>,
			MinMaxProperty.PropertyBuilderExtension<SuiLabeledSliderBuilder>,
			BlockIncrementProperty.PropertyBuilderExtension<SuiLabeledSliderBuilder>,
			TickMarkProperty.PropertyBuilderExtension<SuiLabeledSliderBuilder>,
			OrientationProperty.PropertyBuilderExtension<SuiLabeledSliderBuilder>,
			SpacingProperty.PropertyBuilderExtension<SuiLabeledSliderBuilder>,
			EditableProperty.PropertyBuilderExtension<SuiLabeledSliderBuilder>,
			LabelFormatterProperty.PropertyBuilderExtension<SuiLabeledSliderBuilder>,
			LabelSizeProperty.PropertyBuilderExtension<SuiLabeledSliderBuilder>,
			OnValueChangedEventProperty.PropertyBuilderExtension<SuiLabeledSliderBuilder> {


		@Override
		public SuiNode create(final SuiState state, final Tags tags) {
			return create(
					SuiLabeledSlider.class,
					state,
					tags
			);
		}

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
				PropertyEntry.of(TickMarkProperty.class, new TickMarkProperty.LabeledSliderUpdatingBuilder()),
				PropertyEntry.of(AlignmentProperty.class, new AlignmentProperty.LabeledSliderUpdatingBuilder()),
				PropertyEntry.of(OrientationProperty.class, new OrientationProperty.LabeledSliderUpdatingBuilder()),
				PropertyEntry.of(SpacingProperty.class, new SpacingProperty.LabeledSliderUpdatingBuilder()),
				PropertyEntry.of(EditableProperty.class, new EditableProperty.LabeledSliderUpdatingBuilder()),
				PropertyEntry.of(LabelFormatterProperty.class, new LabelFormatterProperty.LabeledSliderUpdatingBuilder()),
				PropertyEntry.of(LabelSizeProperty.class, new LabelSizeProperty.LabeledSliderUpdatingBuilder()),
				PropertyEntry.of(OnValueChangedEventProperty.class, new OnValueChangedEventProperty.LabeledSliderUpdatingBuilder()),
				PropertyEntry.of(TooltipProperty.class, new TooltipProperty.LabeledSliderUpdatingBuilder())
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
	public static ExtendedSlider getSlider(final Pane pane) {
		for (Node node : pane.getChildren()) {
			if (node instanceof ExtendedSlider) {
				return (ExtendedSlider) node;
			}
		}
		return null;
	}




	/**
	 * @param pane the pane
	 * @return the child label-node from the given pane
	 */
	public static TextField getLabel(final Pane pane) {
		for (Node node : pane.getChildren()) {
			if (node instanceof TextField) {
				return (TextField) node;
			}
		}
		return null;
	}




	private static class FxNodeBuilder implements AbstractFxNodeBuilder<Pane> {


		@Override
		public Pane build(final SuiNode node) {
			final ExtendedSlider slider = buildSlider();
			final TextField label = buildLabel();
			final Pane box = buildBox(slider, label, isHorizontalLayout(getAlignment(node)));
			setupLogic(node, slider, label);
			return box;
		}




		/**
		 * @return the slider
		 */
		private ExtendedSlider buildSlider() {
			final ExtendedSlider slider = new ExtendedSlider();
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
		private TextField buildLabel() {
			final TextField label = new TextField();
			label.setMinSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
			label.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
			label.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
			label.setEditable(true);
			return label;
		}




		/**
		 * @param slider     the slider
		 * @param label      the label
		 * @param horizontal whether the label is to the left or right of the slider
		 * @return the hbox or vbox (depending on value of "horizontal")
		 */
		private Pane buildBox(final Node slider, final Node label, final boolean horizontal) {
			final Pane box = horizontal ? buildHBox() : buildVBox();
			box.getChildren().addAll(slider, label);
			box.setMinSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
			box.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
			box.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_PREF_SIZE);
			return box;
		}




		/**
		 * @return the hbox
		 */
		private HBox buildHBox() {
			HBox box = new HBox();
			box.setFillHeight(true);
			return box;
		}




		/**
		 * @return the vbox
		 */
		private VBox buildVBox() {
			VBox box = new VBox();
			box.setFillWidth(false);
			return box;
		}




		/**
		 * Setup the listeners and logic
		 *
		 * @param node   the simpleui-node
		 * @param slider the slider
		 * @param label  the label
		 */
		private void setupLogic(final SuiNode node, final ExtendedSlider slider, final TextField label) {
			slider.valueProperty().addListener((value, prev, next) -> label.setText(valueToString(node, next.doubleValue())));
			label.setText(valueToString(node, slider.getValue()));
			label.setOnAction(e -> {
				final Double value = valueFromString(label.getText().trim(), slider.getValue(), slider.getMin(), slider.getMax());
				if (value != null) {
					slider.setValue(value, true);
				}
				label.setText(valueToString(node, slider.getValue()));
			});
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
		 * @param string the string to convert to a value
		 * @return the value or null, if the string could not be converted.
		 */
		private Double valueFromString(final String string, final double current, final double min, final double max) {
			try {
				final Expression expression = new ExpressionBuilder(string)
						.variable("x")
						.build()
						.setVariable("x", current);
				final double result = expression.evaluate();
				return Math.max(min, Math.min(result, max));
			} catch (Exception e) {
				return null;
			}
//			try {
//				return Double.parseDouble(string);
//			} catch (Exception e) {
//				return null;
//			}
		}


	}


}
