package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.jfxelements.ExtendedAccordion;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import lombok.Getter;

import java.util.function.BiFunction;

public class AnimateProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<AnimateProperty, AnimateProperty, Boolean> COMPARATOR =
			(a, b) -> a.isAnimate() == b.isAnimate();

	/**
	 * Whether the element is animated.
	 */
	@Getter
	private final boolean animate;




	/**
	 * @param animate whether the element is animated
	 */
	public AnimateProperty(final boolean animate) {
		super(AnimateProperty.class, COMPARATOR);
		this.animate = animate;
	}




	public static class AccordionUpdatingBuilder implements PropFxNodeUpdatingBuilder<AnimateProperty, ExtendedAccordion> {


		@Override
		public void build(final SuiNode node, final AnimateProperty property, final ExtendedAccordion fxNode) {
			fxNode.setAnimated(property.isAnimate());
		}




		@Override
		public MutationResult update(final AnimateProperty property, final SuiNode node, final ExtendedAccordion fxNode) {
			fxNode.setAnimated(property.isAnimate());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final AnimateProperty property, final SuiNode node, final ExtendedAccordion fxNode) {
			fxNode.setAnimated(true);
			return MutationResult.MUTATED;
		}

	}


}
