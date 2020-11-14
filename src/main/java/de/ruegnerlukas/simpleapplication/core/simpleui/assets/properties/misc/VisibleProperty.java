package de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.core.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.builders.FactoryExtension;
import javafx.scene.Node;
import lombok.Getter;

import java.util.function.BiFunction;

public class VisibleProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<VisibleProperty, VisibleProperty, Boolean> COMPARATOR =
			(a, b) -> a.isVisible() == b.isVisible();

	/**
	 * Whether the element is visible.
	 */
	@Getter
	private final boolean visible;




	/**
	 * @param visible whether the element is visible
	 */
	public VisibleProperty(final boolean visible) {
		super(VisibleProperty.class, COMPARATOR);
		this.visible = visible;
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param visible whether the element is visible
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T visible(final boolean visible) {
			getBuilderProperties().add(new VisibleProperty(visible));
			return (T) this;
		}

	}






	public static class UpdatingBuilder implements PropFxNodeUpdatingBuilder<VisibleProperty, Node> {


		@Override
		public void build(final SuiNode node,
						  final VisibleProperty property,
						  final Node fxNode) {
			fxNode.setVisible(property.isVisible());
		}




		@Override
		public MutationResult update(final VisibleProperty property,
									 final SuiNode node,
									 final Node fxNode) {
			fxNode.setVisible(property.isVisible());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final VisibleProperty property,
									 final SuiNode node,
									 final Node fxNode) {
			fxNode.setVisible(true);
			return MutationResult.MUTATED;
		}

	}


}
