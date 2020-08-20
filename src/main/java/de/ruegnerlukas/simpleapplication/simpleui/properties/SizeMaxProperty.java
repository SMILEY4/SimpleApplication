package de.ruegnerlukas.simpleapplication.simpleui.properties;


import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import javafx.scene.layout.Region;
import lombok.Getter;

import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;

@Getter
public class SizeMaxProperty extends Property {


	/**
	 * The maximum width.
	 */
	private final Double width;


	/**
	 * The maximum height.
	 */
	private final Double height;




	/**
	 * @param width  the maximum width.
	 * @param height the maximum height.
	 */
	public SizeMaxProperty(final Double width, final Double height) {
		super(SizeMaxProperty.class);
		this.width = width;
		this.height = height;
	}




	@Override
	protected boolean isPropertyEqual(final Property other) {
		final SizeMaxProperty sizeOther = (SizeMaxProperty) other;
		return SizeProperty.isEqual(this.getWidth(), sizeOther.getWidth())
				&& SizeProperty.isEqual(this.getHeight(), sizeOther.getHeight());
	}




	@Override
	public String printValue() {
		return getWidth() + "x" + getHeight();
	}




	public static class SizeMaxUpdatingBuilder implements PropFxNodeUpdatingBuilder<SizeMaxProperty, Region> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers, final SUINode node, final SizeMaxProperty property,
						  final Region fxNode) {
			fxNode.setMaxSize(property.getWidth(), property.getHeight());
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers, final SizeMaxProperty property,
									 final SUINode node, final Region fxNode) {
			fxNode.setMaxSize(property.getWidth(), property.getHeight());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers, final SizeMaxProperty property,
									 final SUINode node, final Region fxNode) {
			if (node.hasProperty(SizeProperty.class)) {
				SizeProperty sizeProp = node.getProperty(SizeProperty.class);
				fxNode.setMaxSize(sizeProp.getMaxWidth(), sizeProp.getMaxHeight());
			} else {
				fxNode.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			}
			return MutationResult.MUTATED;
		}

	}


}
