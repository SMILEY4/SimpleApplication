package de.ruegnerlukas.simpleapplication.simpleui.properties;


import de.ruegnerlukas.simpleapplication.common.utils.NumberUtils;
import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;
import javafx.scene.layout.Region;
import lombok.Getter;

@Getter
public class SizeMinProperty extends Property {


	/**
	 * The minimum width.
	 */
	private final Number width;


	/**
	 * The minimum height.
	 */
	private final Number height;




	/**
	 * @param width  the minimum width.
	 * @param height the minimum height.
	 */
	public SizeMinProperty(final Number width, final Number height) {
		super(SizeMinProperty.class);
		this.width = width;
		this.height = height;
	}




	@Override
	protected boolean isPropertyEqual(final Property other) {
		final SizeMinProperty sizeOther = (SizeMinProperty) other;
		return NumberUtils.isEqual(this.getWidth(), sizeOther.getWidth())
				&& NumberUtils.isEqual(this.getHeight(), sizeOther.getHeight());
	}




	@Override
	public String printValue() {
		return getWidth() + "x" + getHeight();
	}




	public static class RegionUpdatingBuilder implements PropFxNodeUpdatingBuilder<SizeMinProperty, Region> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers, final SuiNode node, final SizeMinProperty property,
						  final Region fxNode) {
			fxNode.setMinSize(property.getWidth().doubleValue(), property.getHeight().doubleValue());
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers, final SizeMinProperty property,
									 final SuiNode node, final Region fxNode) {
			fxNode.setMinSize(property.getWidth().doubleValue(), property.getHeight().doubleValue());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers, final SizeMinProperty property,
									 final SuiNode node, final Region fxNode) {
			if (node.hasProperty(SizeProperty.class)) {
				SizeProperty sizeProp = node.getProperty(SizeProperty.class);
				fxNode.setMinSize(sizeProp.getMinWidth().doubleValue(), sizeProp.getMinHeight().doubleValue());
			} else {
				fxNode.setMinSize(0, 0);
			}
			return MutationResult.MUTATED;
		}

	}


}
