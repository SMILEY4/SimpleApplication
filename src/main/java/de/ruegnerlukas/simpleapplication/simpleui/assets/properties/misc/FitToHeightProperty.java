package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.builders.FactoryExtension;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import lombok.Getter;

import java.util.function.BiFunction;

public class FitToHeightProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<FitToHeightProperty, FitToHeightProperty, Boolean> COMPARATOR =
			(a, b) -> a.isFitToHeight() == b.isFitToHeight();

	/**
	 * Whether the element should fit the height of its parent element.
	 */
	@Getter
	private final boolean fitToHeight;




	/**
	 * @param fitToHeight whether the element should fit the height of its parent element.
	 */
	public FitToHeightProperty(final boolean fitToHeight) {
		super(FitToHeightProperty.class, COMPARATOR);
		this.fitToHeight = fitToHeight;
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @return this builder for chaining
		 */
		default T fitToHeight() {
			return fitToHeight(true);
		}

		/**
		 * @param fitToHeight whether to fit the element(s) to the height
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T fitToHeight(final boolean fitToHeight) {
			getBuilderProperties().add(new FitToHeightProperty(fitToHeight));
			return (T) this;
		}

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
