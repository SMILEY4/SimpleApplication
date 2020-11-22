package de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.builders.FactoryExtension;
import javafx.scene.Node;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;

public class StyleClassProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<StyleClassProperty, StyleClassProperty, Boolean> COMPARATOR = (a, b) -> {
		final Set<String> setA = new HashSet<>(a.getStyleClasses());
		final Set<String> setB = new HashSet<>(b.getStyleClasses());
		return setA.equals(setB);
	};


	/**
	 * The css style classes.
	 */
	@Getter
	private final Collection<String> styleClasses;




	/**
	 * @param styleClasses the css style classes
	 */
	public StyleClassProperty(final Collection<String> styleClasses) {
		super(StyleClassProperty.class, COMPARATOR);
		Validations.INPUT.notNull(styleClasses).exception("The style classes may not be null.");
		Validations.INPUT.containsNoNull(styleClasses).exception("The style classes may not contain null-elements.");
		this.styleClasses = styleClasses;
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param styleClasses the css style classes
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T styleClass(final Collection<String> styleClasses) {
			getBuilderProperties().add(new StyleClassProperty(styleClasses));
			return (T) this;
		}

		/**
		 * @param styleClasses the css style classes
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T styleClass(final String... styleClasses) {
			getBuilderProperties().add(new StyleClassProperty(Arrays.asList(styleClasses)));
			return (T) this;
		}

	}






	public static class UpdatingBuilder implements PropFxNodeUpdatingBuilder<StyleClassProperty, Node> {


		@Override
		public void build(final SuiNode node, final StyleClassProperty property, final Node fxNode) {
			fxNode.getStyleClass().addAll(property.getStyleClasses());
		}




		@Override
		public MutationResult update(final StyleClassProperty property, final SuiNode node, final Node fxNode) {
			node.getPropertyStore().getSafe(StyleClassProperty.class).ifPresent(prevProp -> {
				fxNode.getStyleClass().removeAll(prevProp.getStyleClasses());
			});
			fxNode.getStyleClass().addAll(property.getStyleClasses());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final StyleClassProperty property, final SuiNode node, final Node fxNode) {
			fxNode.getStyleClass().removeAll(property.getStyleClasses());
			return MutationResult.MUTATED;
		}

	}


}



