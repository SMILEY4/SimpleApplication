package de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.jfxelements.ExtendedHBox;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.jfxelements.ExtendedVBox;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.builders.FactoryExtension;
import lombok.Getter;

import java.util.function.BiFunction;

public class OptimizedProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<OptimizedProperty, OptimizedProperty, Boolean> COMPARATOR =
			(a, b) -> a.isOptimized() == b.isOptimized();

	/**
	 * Whether the element is is using an optimized version.
	 */
	@Getter
	private final boolean optimized;




	/**
	 * @param optimized whether the element is is using an optimized version.
	 */
	public OptimizedProperty(final boolean optimized) {
		super(OptimizedProperty.class, COMPARATOR);
		this.optimized = optimized;
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param optimized whether the element is is using an optimized version.
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T optimized(final boolean optimized) {
			getBuilderProperties().add(new OptimizedProperty(optimized));
			return (T) this;
		}

	}






	public static class HBoxUpdatingBuilder implements PropFxNodeUpdatingBuilder<OptimizedProperty, ExtendedHBox> {


		@Override
		public void build(final SuiNode node, final OptimizedProperty property, final ExtendedHBox fxNode) {
			fxNode.setEnableOptimisation(property.isOptimized());
		}




		@Override
		public MutationResult update(final OptimizedProperty property, final SuiNode node, final ExtendedHBox fxNode) {
			fxNode.setEnableOptimisation(property.isOptimized());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final OptimizedProperty property, final SuiNode node, final ExtendedHBox fxNode) {
			fxNode.setEnableOptimisation(false);
			return MutationResult.MUTATED;
		}

	}






	public static class VBoxUpdatingBuilder implements PropFxNodeUpdatingBuilder<OptimizedProperty, ExtendedVBox> {


		@Override
		public void build(final SuiNode node, final OptimizedProperty property, final ExtendedVBox fxNode) {
			fxNode.setEnableOptimisation(property.isOptimized());
		}




		@Override
		public MutationResult update(final OptimizedProperty property, final SuiNode node, final ExtendedVBox fxNode) {
			fxNode.setEnableOptimisation(property.isOptimized());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final OptimizedProperty property, final SuiNode node, final ExtendedVBox fxNode) {
			fxNode.setEnableOptimisation(false);
			return MutationResult.MUTATED;
		}

	}


}
