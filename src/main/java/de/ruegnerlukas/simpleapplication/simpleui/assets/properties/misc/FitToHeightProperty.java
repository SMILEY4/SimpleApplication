package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import lombok.Getter;

public class FitToHeightProperty extends SuiProperty {


	/**
	 * Whether the element should fit the height of its parent element.
	 */
	@Getter
	private final boolean fitToHeight;




	/**
	 * @param fitToHeight whether the element should fit the height of its parent element.
	 */
	public FitToHeightProperty(final boolean fitToHeight) {
		super(FitToHeightProperty.class);
		this.fitToHeight = fitToHeight;
	}




	@Override
	protected boolean isPropertyEqual(final SuiProperty other) {
		return fitToHeight == ((FitToHeightProperty) other).isFitToHeight();
	}






	public static class ScrollPaneUpdatingBuilder implements PropFxNodeUpdatingBuilder<FitToHeightProperty, ScrollPane> {


		@Override
		public void build(final SuiNode node,
						  final FitToHeightProperty property,
						  final ScrollPane fxNode) {
			fxNode.setFitToHeight(property.isFitToHeight());
		}




		@Override
		public MutationResult update(final FitToHeightProperty property,
									 final SuiNode node,
									 final ScrollPane fxNode) {
			fxNode.setFitToHeight(property.isFitToHeight());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final FitToHeightProperty property,
									 final SuiNode node,
									 final ScrollPane fxNode) {
			fxNode.setFitToHeight(false);
			return MutationResult.MUTATED;
		}

	}






	public static class HBoxUpdatingBuilder implements PropFxNodeUpdatingBuilder<FitToHeightProperty, HBox> {


		@Override
		public void build(final SuiNode node,
						  final FitToHeightProperty property,
						  final HBox fxNode) {
			fxNode.setFillHeight(property.isFitToHeight());
		}




		@Override
		public MutationResult update(final FitToHeightProperty property,
									 final SuiNode node,
									 final HBox fxNode) {
			fxNode.setFillHeight(property.isFitToHeight());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final FitToHeightProperty property,
									 final SuiNode node,
									 final HBox fxNode) {
			fxNode.setFillHeight(false);
			return MutationResult.MUTATED;
		}

	}


}
