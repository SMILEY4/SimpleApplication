package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import javafx.scene.Node;
import lombok.Getter;

import java.util.function.BiFunction;

public class DisabledProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<DisabledProperty, DisabledProperty, Boolean> COMPARATOR =
			(a, b) -> a.isDisabled() == b.isDisabled();

	/**
	 * Whether the element is disabled.
	 */
	@Getter
	private final boolean disabled;




	/**
	 * @param disabled whether the element is disabled
	 */
	public DisabledProperty(final boolean disabled) {
		super(DisabledProperty.class, COMPARATOR);
		this.disabled = disabled;
	}




	public static class UpdatingBuilder implements PropFxNodeUpdatingBuilder<DisabledProperty, Node> {


		@Override
		public void build(final SuiNode node,
						  final DisabledProperty property,
						  final Node fxNode) {
			fxNode.setDisable(property.isDisabled());
		}




		@Override
		public MutationResult update(final DisabledProperty property,
									 final SuiNode node,
									 final Node fxNode) {
			fxNode.setDisable(property.isDisabled());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final DisabledProperty property,
									 final SuiNode node,
									 final Node fxNode) {
			fxNode.setDisable(false);
			return MutationResult.MUTATED;
		}

	}


}
