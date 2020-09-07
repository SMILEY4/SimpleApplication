package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Property;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiBaseNode;
import javafx.scene.Node;
import lombok.Getter;

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




	public static class UpdatingBuilder implements PropFxNodeUpdatingBuilder<DisabledProperty, Node> {


		@Override
		public void build(final SuiBaseNode node,
						  final DisabledProperty property,
						  final Node fxNode) {
			fxNode.setDisable(property.isDisabled());
		}




		@Override
		public MutationResult update(final DisabledProperty property,
									 final SuiBaseNode node,
									 final Node fxNode) {
			fxNode.setDisable(property.isDisabled());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final DisabledProperty property,
									 final SuiBaseNode node,
									 final Node fxNode) {
			fxNode.setDisable(false);
			return MutationResult.MUTATED;
		}

	}


}
