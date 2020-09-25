package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import javafx.scene.image.ImageView;
import lombok.Getter;

import java.util.function.BiFunction;

public class PreserveRatioProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<PreserveRatioProperty, PreserveRatioProperty, Boolean> COMPARATOR =
			(a, b) -> a.isPreserveRatio() == b.isPreserveRatio();

	/**
	 * Whether the image should preserve the ratio.
	 */
	@Getter
	private final boolean preserveRatio;




	/**
	 * @param preserveRatio whether the image should preserve the ratio.
	 */
	public PreserveRatioProperty(final boolean preserveRatio) {
		super(PreserveRatioProperty.class, COMPARATOR);
		this.preserveRatio = preserveRatio;
	}




	public static class ImageViewUpdatingBuilder implements PropFxNodeUpdatingBuilder<PreserveRatioProperty, ImageView> {


		@Override
		public void build(final SuiNode node,
						  final PreserveRatioProperty property,
						  final ImageView fxNode) {
			fxNode.setPreserveRatio(property.preserveRatio);
		}




		@Override
		public MutationResult update(final PreserveRatioProperty property,
									 final SuiNode node,
									 final ImageView fxNode) {
			fxNode.setPreserveRatio(property.preserveRatio);
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final PreserveRatioProperty property,
									 final SuiNode node,
									 final ImageView fxNode) {
			fxNode.setPreserveRatio(false);
			return MutationResult.MUTATED;
		}

	}


}
