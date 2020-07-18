package de.ruegnerlukas.simpleapplication.simpleui.properties;


import de.ruegnerlukas.simpleapplication.simpleui.SNode;
import de.ruegnerlukas.simpleapplication.simpleui.SceneContext;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.BaseNodeMutator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;

public class SpacingProperty extends Property {


	@Getter
	private final double spacing;




	public SpacingProperty(double spacing) {
		super(SpacingProperty.class);
		this.spacing = spacing;
	}




	@Override
	protected boolean isPropertyEqual(final Property other) {
		return spacing == other.getAs(SpacingProperty.class).getSpacing();
	}




	@Override
	public String printValue() {
		return Double.toString(spacing);
	}




	public static class VBoxSpacingUpdatingBuilder implements PropFxNodeUpdatingBuilder<SpacingProperty, VBox> {


		@Override
		public void build(final SceneContext context, final SNode node, final SpacingProperty property, final VBox fxNode) {
			fxNode.setSpacing(property.getSpacing());
		}




		@Override
		public BaseNodeMutator.MutationResult update(final SceneContext context, final SpacingProperty property, final SNode node, final VBox fxNode) {
			fxNode.setSpacing(property.getSpacing());
			return BaseNodeMutator.MutationResult.MUTATED;
		}




		@Override
		public BaseNodeMutator.MutationResult remove(final SceneContext context, final SpacingProperty property, final SNode node, final VBox fxNode) {
			fxNode.setSpacing(0);
			return BaseNodeMutator.MutationResult.MUTATED;
		}

	}






	public static class HBoxSpacingUpdatingBuilder implements PropFxNodeUpdatingBuilder<SpacingProperty, HBox> {


		@Override
		public void build(final SceneContext context, final SNode node, final SpacingProperty property, final HBox fxNode) {
			fxNode.setSpacing(property.getSpacing());
		}




		@Override
		public BaseNodeMutator.MutationResult update(final SceneContext context, final SpacingProperty property, final SNode node, final HBox fxNode) {
			fxNode.setSpacing(property.getSpacing());
			return BaseNodeMutator.MutationResult.MUTATED;
		}




		@Override
		public BaseNodeMutator.MutationResult remove(final SceneContext context, final SpacingProperty property, final SNode node, final HBox fxNode) {
			fxNode.setSpacing(0);
			return BaseNodeMutator.MutationResult.MUTATED;
		}

	}


}
