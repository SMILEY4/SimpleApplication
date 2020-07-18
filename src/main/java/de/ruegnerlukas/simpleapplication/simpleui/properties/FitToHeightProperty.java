package de.ruegnerlukas.simpleapplication.simpleui.properties;


import de.ruegnerlukas.simpleapplication.simpleui.SNode;
import de.ruegnerlukas.simpleapplication.simpleui.SceneContext;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.BaseNodeMutator;
import javafx.scene.control.ScrollPane;
import lombok.Getter;

public class FitToHeightProperty extends Property {


	@Getter
	private final boolean fitToHeight;




	public FitToHeightProperty(boolean fitToHeight) {
		super(FitToHeightProperty.class);
		this.fitToHeight = fitToHeight;
	}




	@Override
	protected boolean isPropertyEqual(final Property other) {
		return fitToHeight == ((FitToHeightProperty) other).isFitToHeight();
	}




	@Override
	public String printValue() {
		return isFitToHeight() ? "fit to height" : "dont fit to height";
	}




	public static boolean fitToHeight(SNode node) {
		return node.getPropertySafe(FitToHeightProperty.class)
				.map(FitToHeightProperty::isFitToHeight)
				.orElse(false);
	}




	public static class FitToHeightUpdatingBuilder implements PropFxNodeUpdatingBuilder<FitToHeightProperty, ScrollPane> {


		@Override
		public void build(final SceneContext context, final SNode node, final FitToHeightProperty property, final ScrollPane fxNode) {
			fxNode.setFitToHeight(property.isFitToHeight());
		}




		@Override
		public BaseNodeMutator.MutationResult update(final SceneContext context, final FitToHeightProperty property, final SNode node, final ScrollPane fxNode) {
			fxNode.setFitToHeight(property.isFitToHeight());
			return BaseNodeMutator.MutationResult.MUTATED;
		}




		@Override
		public BaseNodeMutator.MutationResult remove(final SceneContext context, final FitToHeightProperty property, final SNode node, final ScrollPane fxNode) {
			fxNode.setFitToHeight(false);
			return BaseNodeMutator.MutationResult.MUTATED;
		}

	}


}



