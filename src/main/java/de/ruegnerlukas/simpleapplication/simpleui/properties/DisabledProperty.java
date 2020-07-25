package de.ruegnerlukas.simpleapplication.simpleui.properties;


import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import javafx.scene.Node;
import lombok.Getter;

import static de.ruegnerlukas.simpleapplication.simpleui.mutation.BaseNodeMutator.MutationResult;

public class DisabledProperty extends Property {


	/**
	 * Whether the element is disabled.
	 */
	@Getter
	private final boolean disabled;




	/**
	 * @param disabled whether the element is disabled
	 */
	public DisabledProperty(final boolean disabled) {
		super(DisabledProperty.class);
		this.disabled = disabled;
	}




	@Override
	protected boolean isPropertyEqual(final Property other) {
		return disabled == ((DisabledProperty) other).isDisabled();
	}




	@Override
	public String printValue() {
		return isDisabled() ? "disabled" : "notDisabled";
	}




	public static class DisabledUpdatingBuilder implements PropFxNodeUpdatingBuilder<DisabledProperty, Node> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers, final SUINode node, final DisabledProperty property,
						  final Node fxNode) {
			fxNode.setDisable(property.isDisabled());
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers, final DisabledProperty property,
									 final SUINode node, final Node fxNode) {
			fxNode.setDisable(property.isDisabled());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers, final DisabledProperty property,
									 final SUINode node, final Node fxNode) {
			fxNode.setDisable(false);
			return MutationResult.MUTATED;
		}

	}


}


