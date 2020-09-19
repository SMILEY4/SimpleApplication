package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiLabeledSlider;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import lombok.Getter;

import java.util.function.BiFunction;

public class OrientationProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<OrientationProperty, OrientationProperty, Boolean> COMPARATOR =
			(a, b) -> a.getOrientation() == b.getOrientation();


	/**
	 * The orientation value
	 */
	@Getter
	private final Orientation orientation;




	/**
	 * @param orientation the orientation value
	 */
	public OrientationProperty(final Orientation orientation) {
		super(OrientationProperty.class, COMPARATOR);
		this.orientation = orientation;
	}




	public static class SeparatorUpdatingBuilder implements PropFxNodeUpdatingBuilder<OrientationProperty, Separator> {


		@Override
		public void build(final SuiNode node,
						  final OrientationProperty property,
						  final Separator fxNode) {
			fxNode.setOrientation(property.getOrientation());
		}




		@Override
		public MutationResult update(final OrientationProperty property,
									 final SuiNode node,
									 final Separator fxNode) {
			fxNode.setOrientation(property.getOrientation());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final OrientationProperty property,
									 final SuiNode node,
									 final Separator fxNode) {
			fxNode.setOrientation(Orientation.HORIZONTAL);
			return MutationResult.MUTATED;
		}

	}






	public static class SliderUpdatingBuilder implements PropFxNodeUpdatingBuilder<OrientationProperty, Slider> {


		@Override
		public void build(final SuiNode node,
						  final OrientationProperty property,
						  final Slider fxNode) {
			fxNode.setOrientation(property.getOrientation());
		}




		@Override
		public MutationResult update(final OrientationProperty property,
									 final SuiNode node,
									 final Slider fxNode) {
			fxNode.setOrientation(property.getOrientation());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final OrientationProperty property,
									 final SuiNode node,
									 final Slider fxNode) {
			fxNode.setOrientation(Orientation.HORIZONTAL);
			return MutationResult.MUTATED;
		}

	}






	public static class LabeledSliderUpdatingBuilder implements PropFxNodeUpdatingBuilder<OrientationProperty, Pane> {


		@Override
		public void build(final SuiNode node,
						  final OrientationProperty property,
						  final Pane fxNode) {
			SuiLabeledSlider.getSlider(fxNode).setOrientation(property.getOrientation());
		}




		@Override
		public MutationResult update(final OrientationProperty property,
									 final SuiNode node,
									 final Pane fxNode) {
			SuiLabeledSlider.getSlider(fxNode).setOrientation(property.getOrientation());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final OrientationProperty property,
									 final SuiNode node,
									 final Pane fxNode) {
			SuiLabeledSlider.getSlider(fxNode).setOrientation(Orientation.HORIZONTAL);
			return MutationResult.MUTATED;
		}

	}

}



