package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.common.utils.NumberUtils;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import javafx.scene.layout.Region;
import lombok.Getter;

@Getter
public class SizePreferredProperty extends SuiProperty {


	/**
	 * The preferred width.
	 */
	private final Number width;


	/**
	 * The preferred height.
	 */
	private final Number height;




	/**
	 * @param width  the preferred width.
	 * @param height the preferred height.
	 */
	public SizePreferredProperty(final Number width, final Number height) {
		super(SizePreferredProperty.class);
		this.width = width;
		this.height = height;
	}




	@Override
	protected boolean isPropertyEqual(final SuiProperty other) {
		final SizePreferredProperty sizeOther = (SizePreferredProperty) other;
		return NumberUtils.isEqual(this.getWidth(), sizeOther.getWidth())
				&& NumberUtils.isEqual(this.getHeight(), sizeOther.getHeight());
	}




	public static class RegionUpdatingBuilder implements PropFxNodeUpdatingBuilder<SizePreferredProperty, Region> {


		@Override
		public void build(final SuiNode node,
						  final SizePreferredProperty property,
						  final Region fxNode) {
			fxNode.setPrefSize(property.getWidth().doubleValue(), property.getHeight().doubleValue());
		}




		@Override
		public MutationResult update(final SizePreferredProperty property,
									 final SuiNode node,
									 final Region fxNode) {
			fxNode.setPrefSize(property.getWidth().doubleValue(), property.getHeight().doubleValue());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final SizePreferredProperty property,
									 final SuiNode node,
									 final Region fxNode) {
			if (node.getPropertyStore().has(SizeProperty.class)) {
				SizeProperty sizeProp = node.getPropertyStore().get(SizeProperty.class);
				fxNode.setPrefSize(sizeProp.getPreferredWidth().doubleValue(), sizeProp.getPreferredHeight().doubleValue());
				return MutationResult.MUTATED;
			} else {
				return MutationResult.REQUIRES_REBUILD;
			}
		}

	}


}
