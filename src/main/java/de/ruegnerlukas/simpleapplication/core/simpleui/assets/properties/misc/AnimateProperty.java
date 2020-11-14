package de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.jfxelements.ExtendedAccordion;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.builders.FactoryExtension;
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




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param animated whether to animate the element
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T animated(final boolean animated) {
			getBuilderProperties().add(new AnimateProperty(animated));
			return (T) this;
		}

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
