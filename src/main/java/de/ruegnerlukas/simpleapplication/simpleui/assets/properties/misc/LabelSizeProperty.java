package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.common.utils.NumberUtils;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiLabeledSlider;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import lombok.Getter;

import java.util.function.BiFunction;

public class LabelSizeProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<LabelSizeProperty, LabelSizeProperty, Boolean> COMPARATOR =
			(a, b) -> NumberUtils.isEqual(a.getMinSize(), b.getMinSize())
					&& NumberUtils.isEqual(a.getPrefSize(), b.getPrefSize())
					&& NumberUtils.isEqual(a.getMaxSize(), b.getMaxSize());

	/**
	 * The min width or height of the label.
	 */
	@Getter
	private final Number minSize;

	/**
	 * The preferred width or height of the label.
	 */
	@Getter
	private final Number prefSize;

	/**
	 * The max width or height of the label.
	 */
	@Getter
	private final Number maxSize;




	/**
	 * @param minSize  the min width or height of the label.
	 * @param prefSize the preferred width or height of the label.
	 * @param maxSize  the max width or height of the label.
	 */
	public LabelSizeProperty(final Number minSize, final Number prefSize, final Number maxSize) {
		super(LabelSizeProperty.class, COMPARATOR);
		this.minSize = minSize;
		this.prefSize = prefSize;
		this.maxSize = maxSize;
	}




	public static class LabeledSliderUpdatingBuilder implements PropFxNodeUpdatingBuilder<LabelSizeProperty, Pane> {


		@Override
		public void build(final SuiNode node,
						  final LabelSizeProperty property,
						  final Pane fxNode) {
			setSize(node, fxNode, property);
		}




		@Override
		public MutationResult update(final LabelSizeProperty property,
									 final SuiNode node,
									 final Pane fxNode) {
			setSize(node, fxNode, property);
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final LabelSizeProperty property,
									 final SuiNode node,
									 final Pane fxNode) {
			setSize(node, fxNode, property);
			return MutationResult.MUTATED;
		}




		/**
		 * @param node     the node
		 * @param pane     the hbox/vbox depending on the layout
		 * @param property the property
		 */
		private void setSize(final SuiNode node, final Pane pane, final LabelSizeProperty property) {
			final Label label = SuiLabeledSlider.getLabel(pane);
			if (SuiLabeledSlider.isHorizontalLayout(SuiLabeledSlider.getAlignment(node))) {
				label.setMinWidth(property.getMinSize().doubleValue());
				label.setPrefWidth(property.getPrefSize().doubleValue());
				label.setMaxWidth(property.getMaxSize().doubleValue());
			} else {
				label.setMinHeight(property.getMinSize().doubleValue());
				label.setPrefHeight(property.getPrefSize().doubleValue());
				label.setMaxHeight(property.getMaxSize().doubleValue());
			}
		}

	}


}
