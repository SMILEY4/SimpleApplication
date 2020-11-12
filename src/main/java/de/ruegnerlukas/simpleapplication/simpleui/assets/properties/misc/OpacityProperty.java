package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.common.utils.NumberUtils;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.builders.FactoryExtension;
import javafx.scene.Node;
import lombok.Getter;

import java.util.function.BiFunction;

public class OpacityProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<OpacityProperty, OpacityProperty, Boolean> COMPARATOR =
			(a, b) -> NumberUtils.isEqual(a.getOpacity(), b.getOpacity());

	/**
	 * The opacity. Values between 0.0 and 1.0. Values out of this range will be clamped by javafx.
	 */
	@Getter
	private final double opacity;




	/**
	 * @param opacity the opacity. Values between 0.0 and 1.0. Values out of this range will be clamped by javafx.
	 */
	public OpacityProperty(final double opacity) {
		super(OpacityProperty.class, COMPARATOR);
		this.opacity = opacity;
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param opacity the opacity. Values between 0.0 and 1.0. Values out of this range will be clamped by javafx.
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T opacity(final double opacity) {
			getBuilderProperties().add(new OpacityProperty(opacity));
			return (T) this;
		}

	}






	public static class UpdatingBuilder implements PropFxNodeUpdatingBuilder<OpacityProperty, Node> {


		@Override
		public void build(final SuiNode node, final OpacityProperty property, final Node fxNode) {
			fxNode.setOpacity(property.getOpacity());
		}




		@Override
		public MutationResult update(final OpacityProperty property, final SuiNode node, final Node fxNode) {
			fxNode.setOpacity(property.getOpacity());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final OpacityProperty property, final SuiNode node, final Node fxNode) {
			fxNode.setOpacity(1);
			return MutationResult.MUTATED;
		}

	}


}
