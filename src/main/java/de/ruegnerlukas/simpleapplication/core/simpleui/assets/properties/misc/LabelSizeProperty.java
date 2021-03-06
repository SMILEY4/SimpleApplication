package de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.common.utils.NumberUtils;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.SuiLabeledSlider;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.builders.FactoryExtension;
import javafx.scene.control.TextField;
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
		Validations.INPUT.notNull(minSize).exception("The min size may not be null.");
		Validations.INPUT.notNull(prefSize).exception("The preferred size may not be null.");
		Validations.INPUT.notNull(maxSize).exception("The max size may not be null.");
		this.minSize = minSize;
		this.prefSize = prefSize;
		this.maxSize = maxSize;
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param size the exact size of the label
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T labelSize(final Number size) {
			getBuilderProperties().add(new LabelSizeProperty(size, size, size));
			return (T) this;
		}

		/**
		 * @param minSize  the min width or height of the label.
		 * @param prefSize the preferred width or height of the label.
		 * @param maxSize  the max width or height of the label.
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T labelSize(final Number minSize, final Number prefSize, final Number maxSize) {
			getBuilderProperties().add(new LabelSizeProperty(minSize, prefSize, maxSize));
			return (T) this;
		}

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
			final TextField label = SuiLabeledSlider.getLabel(pane);
			label.setMinWidth(property.getMinSize().doubleValue());
			label.setPrefWidth(property.getPrefSize().doubleValue());
			label.setMaxWidth(property.getMaxSize().doubleValue());
//			if (SuiLabeledSlider.isHorizontalLayout(SuiLabeledSlider.getAlignment(node))) {
//				label.setMinWidth(property.getMinSize().doubleValue());
//				label.setPrefWidth(property.getPrefSize().doubleValue());
//				label.setMaxWidth(property.getMaxSize().doubleValue());
//			} else {
//				label.setMinHeight(property.getMinSize().doubleValue());
//				label.setPrefHeight(property.getPrefSize().doubleValue());
//				label.setMaxHeight(property.getMaxSize().doubleValue());
//			}
		}

	}


}
