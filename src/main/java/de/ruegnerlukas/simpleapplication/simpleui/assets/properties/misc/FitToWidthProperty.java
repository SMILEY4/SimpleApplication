package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Property;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.core.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import lombok.Getter;

import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;

public class FitToWidthProperty extends Property {


	/**
	 * Whether the element should fit the width of its parent element.
	 */
	@Getter
	private final boolean fitToWidth;




	/**
	 * @param fitToWidth whether the element should fit the width of its parent element.
	 */
	public FitToWidthProperty(final boolean fitToWidth) {
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




	public static class ScrollPaneUpdatingBuilder implements PropFxNodeUpdatingBuilder<FitToWidthProperty, ScrollPane> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers,
						  final SuiNode node,
						  final FitToWidthProperty property,
						  final ScrollPane fxNode) {
			fxNode.setFitToWidth(property.isFitToWidth());
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers,
									 final FitToWidthProperty property,
									 final SuiNode node,
									 final ScrollPane fxNode) {
			fxNode.setFitToWidth(property.isFitToWidth());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers,
									 final FitToWidthProperty property,
									 final SuiNode node,
									 final ScrollPane fxNode) {
			fxNode.setFitToWidth(false);
			return MutationResult.MUTATED;
		}

	}






	public static class VBoxUpdatingBuilder implements PropFxNodeUpdatingBuilder<FitToWidthProperty, VBox> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers,
						  final SuiNode node,
						  final FitToWidthProperty property,
						  final VBox fxNode) {
			fxNode.setFillWidth(property.isFitToWidth());
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers,
									 final FitToWidthProperty property,
									 final SuiNode node,
									 final VBox fxNode) {
			fxNode.setFillWidth(property.isFitToWidth());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers,
									 final FitToWidthProperty property,
									 final SuiNode node,
									 final VBox fxNode) {
			fxNode.setFillWidth(false);
			return MutationResult.MUTATED;
		}

	}

}



