package de.ruegnerlukas.simpleapplication.simpleui.properties;


import de.ruegnerlukas.simpleapplication.simpleui.SNode;
import de.ruegnerlukas.simpleapplication.simpleui.SceneContext;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.BaseNodeMutator;
import javafx.scene.Node;
import lombok.Getter;

public class DisabledProperty extends Property {


	@Getter
	private final boolean disabled;




	public DisabledProperty(boolean disabled) {
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




	public static boolean isDisabled(SNode node) {
		return node.getPropertySafe(DisabledProperty.class)
				.map(DisabledProperty::isDisabled)
				.orElse(false);
	}




	public static class DisabledUpdatingBuilder implements PropFxNodeUpdatingBuilder<DisabledProperty, Node> {


		@Override
		public void build(final SceneContext context, final SNode node, final DisabledProperty property, final Node fxNode) {
			fxNode.setDisable(property.isDisabled());
		}




		@Override
		public BaseNodeMutator.MutationResult update(final SceneContext context, final DisabledProperty property, final SNode node, final Node fxNode) {
			fxNode.setDisable(property.isDisabled());
			return BaseNodeMutator.MutationResult.MUTATED;
		}




		@Override
		public BaseNodeMutator.MutationResult remove(final SceneContext context, final DisabledProperty property, final SNode node, final Node fxNode) {
			fxNode.setDisable(false);
			return BaseNodeMutator.MutationResult.MUTATED;
		}

	}


}



