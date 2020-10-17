package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.FactoryExtension;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import lombok.Getter;

import java.util.function.BiFunction;

public class FitToWidthProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<FitToWidthProperty, FitToWidthProperty, Boolean> COMPARATOR =
			(a, b) -> a.isFitToWidth() == b.isFitToWidth();


	/**
	 * Whether the element should fit the width of its parent element.
	 */
	@Getter
	private final boolean fitToWidth;




	/**
	 * @param fitToWidth whether the element should fit the width of its parent element.
	 */
	public FitToWidthProperty(final boolean fitToWidth) {
		super(FitToWidthProperty.class, COMPARATOR);
		this.fitToWidth = fitToWidth;
	}




	@SuppressWarnings ("unchecked")
	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		default T fitToWidth() {
			getFactoryInternalProperties().add(Properties.fitToWidth());
			return (T) this;
		}

		default T fitToWidth(final boolean fitToWidth) {
			getFactoryInternalProperties().add(Properties.fitToWidth(fitToWidth));
			return (T) this;
		}

	}






	public static class ScrollPaneUpdatingBuilder implements PropFxNodeUpdatingBuilder<FitToWidthProperty, ScrollPane> {


		@Override
		public void build(final SuiNode node,
						  final FitToWidthProperty property,
						  final ScrollPane fxNode) {
			fxNode.setFitToWidth(property.isFitToWidth());
		}




		@Override
		public MutationResult update(final FitToWidthProperty property,
									 final SuiNode node,
									 final ScrollPane fxNode) {
			fxNode.setFitToWidth(property.isFitToWidth());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final FitToWidthProperty property,
									 final SuiNode node,
									 final ScrollPane fxNode) {
			fxNode.setFitToWidth(false);
			return MutationResult.MUTATED;
		}

	}






	public static class VBoxUpdatingBuilder implements PropFxNodeUpdatingBuilder<FitToWidthProperty, VBox> {


		@Override
		public void build(final SuiNode node,
						  final FitToWidthProperty property,
						  final VBox fxNode) {
			fxNode.setFillWidth(property.isFitToWidth());
		}




		@Override
		public MutationResult update(final FitToWidthProperty property,
									 final SuiNode node,
									 final VBox fxNode) {
			fxNode.setFillWidth(property.isFitToWidth());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final FitToWidthProperty property,
									 final SuiNode node,
									 final VBox fxNode) {
			fxNode.setFillWidth(false);
			return MutationResult.MUTATED;
		}

	}

}



