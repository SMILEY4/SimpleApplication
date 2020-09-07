package de.ruegnerlukas.simpleapplication.simpleui.properties;


import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.elements.basenode.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import lombok.Getter;

public class FitToHeightProperty extends Property {


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
	protected boolean isPropertyEqual(final Property other) {
		return fitToHeight == ((FitToHeightProperty) other).isFitToHeight();
	}




	@Override
	public String printValue() {
		return isFitToHeight() ? "fit to height" : "dont fit to height";
	}




	public static class ScrollPaneUpdatingBuilder implements PropFxNodeUpdatingBuilder<FitToHeightProperty, ScrollPane> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers,
						  final SuiNode node,
						  final FitToHeightProperty property,
						  final ScrollPane fxNode) {
			fxNode.setFitToHeight(property.isFitToHeight());
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers,
									 final FitToHeightProperty property,
									 final SuiNode node,
									 final ScrollPane fxNode) {
			fxNode.setFitToHeight(property.isFitToHeight());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers,
									 final FitToHeightProperty property,
									 final SuiNode node,
									 final ScrollPane fxNode) {
			fxNode.setFitToHeight(false);
			return MutationResult.MUTATED;
		}

	}






	public static class HBoxUpdatingBuilder implements PropFxNodeUpdatingBuilder<FitToHeightProperty, HBox> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers,
						  final SuiNode node,
						  final FitToHeightProperty property,
						  final HBox fxNode) {
			fxNode.setFillHeight(property.isFitToHeight());
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers,
									 final FitToHeightProperty property,
									 final SuiNode node,
									 final HBox fxNode) {
			fxNode.setFillHeight(property.isFitToHeight());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers,
									 final FitToHeightProperty property,
									 final SuiNode node,
									 final HBox fxNode) {
			fxNode.setFillHeight(false);
			return MutationResult.MUTATED;
		}

	}


}
