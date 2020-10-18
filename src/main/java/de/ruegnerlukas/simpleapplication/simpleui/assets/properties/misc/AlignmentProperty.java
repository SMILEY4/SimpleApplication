package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiLabeledSlider;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.builders.FactoryExtension;
import javafx.geometry.Pos;
import javafx.scene.control.Labeled;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lombok.Getter;

import java.util.function.BiFunction;

public class AlignmentProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<AlignmentProperty, AlignmentProperty, Boolean> COMPARATOR =
			(a, b) -> a.getAlignment() == b.getAlignment();


	/**
	 * The alignment.
	 */
	@Getter
	private final Pos alignment;




	/**
	 * @param alignment the alignment
	 */
	public AlignmentProperty(final Pos alignment) {
		super(AlignmentProperty.class, COMPARATOR);
		Validations.INPUT.notNull(alignment).exception("The alignment may not be null,");
		this.alignment = alignment;
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param alignment the alignment
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T alignment(final Pos alignment) {
			getBuilderProperties().add(new AlignmentProperty(alignment));
			return (T) this;
		}

	}






	public static class VBoxUpdatingBuilder implements PropFxNodeUpdatingBuilder<AlignmentProperty, VBox> {


		@Override
		public void build(final SuiNode node, final AlignmentProperty property, final VBox fxNode) {
			fxNode.setAlignment(property.getAlignment());
		}




		@Override
		public MutationResult update(final AlignmentProperty property, final SuiNode node, final VBox fxNode) {
			fxNode.setAlignment(property.getAlignment());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final AlignmentProperty property, final SuiNode node, final VBox fxNode) {
			fxNode.setAlignment(Pos.TOP_LEFT);
			return MutationResult.MUTATED;
		}

	}






	public static class HBoxUpdatingBuilder implements PropFxNodeUpdatingBuilder<AlignmentProperty, HBox> {


		@Override
		public void build(final SuiNode node, final AlignmentProperty property, final HBox fxNode) {
			fxNode.setAlignment(property.getAlignment());
		}




		@Override
		public MutationResult update(final AlignmentProperty property, final SuiNode node, final HBox fxNode) {
			fxNode.setAlignment(property.getAlignment());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final AlignmentProperty property, final SuiNode node, final HBox fxNode) {
			fxNode.setAlignment(Pos.TOP_LEFT);
			return MutationResult.MUTATED;
		}

	}






	public static class LabeledUpdatingBuilder implements PropFxNodeUpdatingBuilder<AlignmentProperty, Labeled> {


		@Override
		public void build(final SuiNode node, final AlignmentProperty property, final Labeled fxNode) {
			fxNode.setAlignment(property.getAlignment());
		}




		@Override
		public MutationResult update(final AlignmentProperty property, final SuiNode node, final Labeled fxNode) {
			fxNode.setAlignment(property.getAlignment());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final AlignmentProperty property, final SuiNode node, final Labeled fxNode) {
			fxNode.setAlignment(Pos.TOP_LEFT);
			return MutationResult.MUTATED;
		}

	}






	public static class TextFieldUpdatingBuilder implements PropFxNodeUpdatingBuilder<AlignmentProperty, TextField> {


		@Override
		public void build(final SuiNode node, final AlignmentProperty property, final TextField fxNode) {
			fxNode.setAlignment(property.getAlignment());
		}




		@Override
		public MutationResult update(final AlignmentProperty property, final SuiNode node, final TextField fxNode) {
			fxNode.setAlignment(property.getAlignment());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final AlignmentProperty property, final SuiNode node, final TextField fxNode) {
			fxNode.setAlignment(Pos.TOP_LEFT);
			return MutationResult.MUTATED;
		}

	}






	public static class LabeledSliderUpdatingBuilder implements PropFxNodeUpdatingBuilder<AlignmentProperty, Pane> {


		@Override
		public void build(final SuiNode node, final AlignmentProperty property, final Pane fxNode) {
			final Pos alignment = property.getAlignment();
			Validations.STATE.typeOf(fxNode, SuiLabeledSlider.isHorizontalLayout(alignment) ? HBox.class : VBox.class)
					.exception("Alignment does not match pane-type: {} with {}.", alignment, fxNode);
			updateChildNodeOrder(alignment, fxNode);
			updateBoxAlignment(alignment, fxNode);
		}




		@Override
		public MutationResult update(final AlignmentProperty property, final SuiNode node, final Pane fxNode) {
			if (requiresRebuild(node, property)) {
				return MutationResult.REQUIRES_REBUILD;
			} else {
				updateChildNodeOrder(property.getAlignment(), fxNode);
				updateBoxAlignment(property.getAlignment(), fxNode);
				return MutationResult.MUTATED;
			}
		}




		@Override
		public MutationResult remove(final AlignmentProperty property, final SuiNode node, final Pane fxNode) {
			if (requiresRebuild(node, property)) {
				return MutationResult.REQUIRES_REBUILD;
			} else {
				updateChildNodeOrder(property.getAlignment(), fxNode);
				updateBoxAlignment(property.getAlignment(), fxNode);
				return MutationResult.MUTATED;
			}
		}




		/**
		 * Checks if the modification requires a rebuild.
		 *
		 * @param node     the node with the previous property
		 * @param property the new property
		 * @return whether a rebuild is required
		 */
		private boolean requiresRebuild(final SuiNode node, final AlignmentProperty property) {
			final boolean directionPrev = SuiLabeledSlider.isHorizontalLayout(SuiLabeledSlider.getAlignment(node));
			final boolean directionNext = SuiLabeledSlider.isHorizontalLayout(property.getAlignment());
			return directionPrev != directionNext;
		}




		/**
		 * Brings the child nodes of the hbox/vbox in the correct order depending on the alignment.
		 *
		 * @param alignment the alignment
		 * @param pane      the hbox or vbox (depending on alignment)
		 */
		private void updateChildNodeOrder(final Pos alignment, final Pane pane) {
			final Slider slider = SuiLabeledSlider.getSlider(pane);
			final TextField label = SuiLabeledSlider.getLabel(pane);

			if (SuiLabeledSlider.isHorizontalLayout(alignment)) {
				final HBox hbox = (HBox) pane;
				if (alignment == Pos.BASELINE_LEFT || alignment == Pos.CENTER_LEFT) {
					hbox.getChildren().setAll(label, slider);
				}
				if (alignment == Pos.BASELINE_RIGHT || alignment == Pos.CENTER_RIGHT) {
					hbox.getChildren().setAll(slider, label);
				}

			} else {
				final VBox vbox = (VBox) pane;
				if (alignment == Pos.TOP_RIGHT || alignment == Pos.TOP_CENTER || alignment == Pos.TOP_LEFT) {
					vbox.getChildren().setAll(label, slider);
				}
				if (alignment == Pos.BOTTOM_RIGHT || alignment == Pos.BOTTOM_CENTER || alignment == Pos.BOTTOM_LEFT) {
					vbox.getChildren().setAll(slider, label);
				}
			}
		}




		/**
		 * Updates the alignment of the vbox/hox
		 *
		 * @param alignment the alignment
		 * @param pane      the hbox or vbox (depending on alignment)
		 */
		private void updateBoxAlignment(final Pos alignment, final Pane pane) {
			if (SuiLabeledSlider.isHorizontalLayout(alignment)) {
				((HBox) pane).setAlignment(Pos.CENTER);
			} else {
				((VBox) pane).setAlignment(alignment);
			}
		}


	}


}



