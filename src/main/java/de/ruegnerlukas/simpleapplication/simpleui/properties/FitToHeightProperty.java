package de.ruegnerlukas.simpleapplication.simpleui.properties;


import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import lombok.Getter;

import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;

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




	public static class ScrollPaneFitToHeightUpdatingBuilder implements PropFxNodeUpdatingBuilder<FitToHeightProperty, ScrollPane> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers, final SUINode node, final FitToHeightProperty property,
						  final ScrollPane fxNode) {
			fxNode.setFitToHeight(property.isFitToHeight());
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers, final FitToHeightProperty property,
									 final SUINode node, final ScrollPane fxNode) {
			fxNode.setFitToHeight(property.isFitToHeight());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers, final FitToHeightProperty property,
									 final SUINode node, final ScrollPane fxNode) {
			fxNode.setFitToHeight(false);
			return MutationResult.MUTATED;
		}

	}



	public static class HBoxFitToHeightUpdatingBuilder implements PropFxNodeUpdatingBuilder<FitToHeightProperty, HBox> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers, final SUINode node, final FitToHeightProperty property,
						  final HBox fxNode) {
			fxNode.setFillHeight(property.isFitToHeight());
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers, final FitToHeightProperty property, final SUINode node,
									 final HBox fxNode) {
			fxNode.setFillHeight(property.isFitToHeight());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers, final FitToHeightProperty property, final SUINode node,
									 final HBox fxNode) {
			fxNode.setFillHeight(false);
			return MutationResult.MUTATED;
		}

	}



}



