package de.ruegnerlukas.simpleapplication.simpleui.properties;


import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import javafx.scene.layout.Region;
import lombok.Getter;

import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;

@Getter
public class SizeMinProperty extends Property {


	/**
	 * The minimum width.
	 */
	private final Double width;


	/**
	 * The minimum height.
	 */
	private final Double height;




	/**
	 * @param width  the minimum width.
	 * @param height the minimum height.
	 */
	public SizeMinProperty(final Double width, final Double height) {
		super(SizeMinProperty.class);
		this.width = width;
		this.height = height;
	}




	@Override
	protected boolean isPropertyEqual(final Property other) {
		final SizeMinProperty sizeOther = (SizeMinProperty) other;
		return SizeProperty.isEqual(this.getWidth(), sizeOther.getWidth())
				&& SizeProperty.isEqual(this.getHeight(), sizeOther.getHeight());
	}




	@Override
	public String printValue() {
		return getWidth() + "x" + getHeight();
	}




	public static class SizeMinUpdatingBuilder implements PropFxNodeUpdatingBuilder<SizeMinProperty, Region> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers, final SuiNode node, final SizeMinProperty property,
						  final Region fxNode) {
			fxNode.setMinSize(property.getWidth(), property.getHeight());
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers, final SizeMinProperty property,
									 final SuiNode node, final Region fxNode) {
			fxNode.setMinSize(property.getWidth(), property.getHeight());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers, final SizeMinProperty property,
									 final SuiNode node, final Region fxNode) {
			if (node.hasProperty(SizeProperty.class)) {
				SizeProperty sizeProp = node.getProperty(SizeProperty.class);
				fxNode.setMinSize(sizeProp.getMinWidth(), sizeProp.getMinHeight());
			} else {
				fxNode.setMinSize(0, 0);
			}
			return MutationResult.MUTATED;
		}

	}


}
