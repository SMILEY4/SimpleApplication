package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.common.utils.NumberUtils;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiLabeledSlider;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.builders.FactoryExtension;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lombok.Getter;

import java.util.function.BiFunction;

public class SpacingProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<SpacingProperty, SpacingProperty, Boolean> COMPARATOR =
			(a, b) -> NumberUtils.isEqual(a.getSpacing(), b.getSpacing());

	/**
	 * The spacing value between the elements.
	 */
	@Getter
	private final Number spacing;




	/**
	 * @param spacing the spacing value between the elements.
	 */
	public SpacingProperty(final Number spacing) {
		super(SpacingProperty.class, COMPARATOR);
		Validations.INPUT.notNull(spacing).exception("The spacing may not be null.");
		this.spacing = spacing;
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param spacing the spacing between elements
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T spacing(final Number spacing) {
			getBuilderProperties().add(new SpacingProperty(spacing));
			return (T) this;
		}

	}






	public static class VBoxUpdatingBuilder implements PropFxNodeUpdatingBuilder<SpacingProperty, VBox> {


		@Override
		public void build(final SuiNode node,
						  final SpacingProperty property,
						  final VBox fxNode) {
			fxNode.setSpacing(property.getSpacing().doubleValue());
		}




		@Override
		public MutationResult update(final SpacingProperty property,
									 final SuiNode node,
									 final VBox fxNode) {
			fxNode.setSpacing(property.getSpacing().doubleValue());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final SpacingProperty property,
									 final SuiNode node,
									 final VBox fxNode) {
			fxNode.setSpacing(0);
			return MutationResult.MUTATED;
		}

	}






	public static class HBoxUpdatingBuilder implements PropFxNodeUpdatingBuilder<SpacingProperty, HBox> {


		@Override
		public void build(final SuiNode node,
						  final SpacingProperty property,
						  final HBox fxNode) {
			fxNode.setSpacing(property.getSpacing().doubleValue());
		}




		@Override
		public MutationResult update(final SpacingProperty property,
									 final SuiNode node,
									 final HBox fxNode) {
			fxNode.setSpacing(property.getSpacing().doubleValue());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final SpacingProperty property,
									 final SuiNode node,
									 final HBox fxNode) {
			fxNode.setSpacing(0);
			return MutationResult.MUTATED;
		}

	}






	public static class LabeledSliderUpdatingBuilder implements PropFxNodeUpdatingBuilder<SpacingProperty, Pane> {


		@Override
		public void build(final SuiNode node,
						  final SpacingProperty property,
						  final Pane fxNode) {
			if (SuiLabeledSlider.isHorizontalLayout(node)) {
				((HBox) fxNode).setSpacing(property.getSpacing().doubleValue());
			} else {
				((VBox) fxNode).setSpacing(property.getSpacing().doubleValue());
			}
		}




		@Override
		public MutationResult update(final SpacingProperty property,
									 final SuiNode node,
									 final Pane fxNode) {
			if (SuiLabeledSlider.isHorizontalLayout(node)) {
				((HBox) fxNode).setSpacing(property.getSpacing().doubleValue());
			} else {
				((VBox) fxNode).setSpacing(property.getSpacing().doubleValue());
			}
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final SpacingProperty property,
									 final SuiNode node,
									 final Pane fxNode) {
			if (SuiLabeledSlider.isHorizontalLayout(node)) {
				((HBox) fxNode).setSpacing(0);
			} else {
				((VBox) fxNode).setSpacing(0);
			}
			return MutationResult.MUTATED;
		}

	}

}
