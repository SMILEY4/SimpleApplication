package de.ruegnerlukas.simpleapplication.simpleui.properties;


import de.ruegnerlukas.simpleapplication.simpleui.SNode;
import de.ruegnerlukas.simpleapplication.simpleui.SceneContext;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.BaseNodeMutator;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import lombok.Getter;

public class FitToWidthProperty extends Property {


	@Getter
	private final boolean fitToWidth;




	public FitToWidthProperty(boolean fitToWidth) {
		super(FitToWidthProperty.class);
		this.fitToWidth = fitToWidth;
	}




	@Override
	protected boolean isPropertyEqual(final Property other) {
		return fitToWidth == ((FitToWidthProperty) other).isFitToWidth();
	}




	@Override
	public String printValue() {
		return isFitToWidth() ? "fit to width" : "dont fit to width";
	}




	public static boolean fitToWidth(SNode node) {
		return node.getPropertySafe(FitToWidthProperty.class)
				.map(FitToWidthProperty::isFitToWidth)
				.orElse(false);
	}




	public static class ScrollPaneFitToWidthUpdatingBuilder implements PropFxNodeUpdatingBuilder<FitToWidthProperty, ScrollPane> {


		@Override
		public void build(final SceneContext context, final SNode node, final FitToWidthProperty property, final ScrollPane fxNode) {
			fxNode.setFitToWidth(property.isFitToWidth());
		}




		@Override
		public BaseNodeMutator.MutationResult update(final SceneContext context, final FitToWidthProperty property, final SNode node, final ScrollPane fxNode) {
			fxNode.setFitToWidth(property.isFitToWidth());
			return BaseNodeMutator.MutationResult.MUTATED;
		}




		@Override
		public BaseNodeMutator.MutationResult remove(final SceneContext context, final FitToWidthProperty property, final SNode node, final ScrollPane fxNode) {
			fxNode.setFitToWidth(false);
			return BaseNodeMutator.MutationResult.MUTATED;
		}

	}


	public static class VBoxFitToWidthUpdatingBuilder implements PropFxNodeUpdatingBuilder<FitToWidthProperty, VBox> {


		@Override
		public void build(final SceneContext context, final SNode node, final FitToWidthProperty property, final VBox fxNode) {
			fxNode.setFillWidth(property.isFitToWidth());
		}




		@Override
		public BaseNodeMutator.MutationResult update(final SceneContext context, final FitToWidthProperty property, final SNode node, final VBox fxNode) {
			fxNode.setFillWidth(property.isFitToWidth());
			return BaseNodeMutator.MutationResult.MUTATED;
		}




		@Override
		public BaseNodeMutator.MutationResult remove(final SceneContext context, final FitToWidthProperty property, final SNode node, final VBox fxNode) {
			fxNode.setFillWidth(false);
			return BaseNodeMutator.MutationResult.MUTATED;
		}

	}


}



