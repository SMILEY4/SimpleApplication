package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.common.utils.NumberUtils;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import javafx.scene.layout.Region;
import lombok.Getter;

@Getter
public class SizeMaxProperty extends SuiProperty {


	/**
	 * The maximum width.
	 */
	private final Number width;


	/**
	 * The maximum height.
	 */
	private final Number height;




	/**
	 * @param width  the maximum width.
	 * @param height the maximum height.
	 */
	public SizeMaxProperty(final Number width, final Number height) {
		super(SizeMaxProperty.class);
		this.width = width;
		this.height = height;
	}




	@Override
	protected boolean isPropertyEqual(final SuiProperty other) {
		final SizeMaxProperty sizeOther = (SizeMaxProperty) other;
		return NumberUtils.isEqual(this.getWidth(), sizeOther.getWidth())
				&& NumberUtils.isEqual(this.getHeight(), sizeOther.getHeight());
	}




	@Override
	public String printValue() {
		return getWidth() + "x" + getHeight();
	}




	public static class RegionUpdatingBuilder implements PropFxNodeUpdatingBuilder<SizeMaxProperty, Region> {


		@Override
		public void build(final SuiNode node,
						  final SizeMaxProperty property,
						  final Region fxNode) {
			fxNode.setMaxSize(property.getWidth().doubleValue(), property.getHeight().doubleValue());
		}




		@Override
		public MutationResult update(final SizeMaxProperty property,
									 final SuiNode node,
									 final Region fxNode) {
			fxNode.setMaxSize(property.getWidth().doubleValue(), property.getHeight().doubleValue());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final SizeMaxProperty property,
									 final SuiNode node,
									 final Region fxNode) {
			if (node.getPropertyStore().has(SizeProperty.class)) {
				SizeProperty sizeProp = node.getPropertyStore().get(SizeProperty.class);
				fxNode.setMaxSize(sizeProp.getMaxWidth().doubleValue(), sizeProp.getMaxHeight().doubleValue());
			} else {
				fxNode.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			}
			return MutationResult.MUTATED;
		}

	}


}
