package de.ruegnerlukas.simpleapplication.simpleui.properties;


import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import javafx.scene.layout.Region;
import lombok.Getter;

import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;

@Getter
public class SizePreferredProperty extends Property {


	/**
	 * The preferred width.
	 */
	private final Double width;


	/**
	 * The preferred height.
	 */
	private final Double height;




	/**
	 * @param width  the preferred width.
	 * @param height the preferred height.
	 */
	public SizePreferredProperty(final Double width, final Double height) {
		super(SizePreferredProperty.class);
		this.width = width;
		this.height = height;
	}




	@Override
	protected boolean isPropertyEqual(final Property other) {
		final SizePreferredProperty sizeOther = (SizePreferredProperty) other;
		return SizeProperty.isEqual(this.getWidth(), sizeOther.getWidth())
				&& SizeProperty.isEqual(this.getHeight(), sizeOther.getHeight());
	}




	@Override
	public String printValue() {
		return getWidth() + "x" + getHeight();
	}




	public static class SizePreferredUpdatingBuilder implements PropFxNodeUpdatingBuilder<SizePreferredProperty, Region> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers, final SUINode node, final SizePreferredProperty property,
						  final Region fxNode) {
			fxNode.setPrefSize(property.getWidth(), property.getHeight());
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers, final SizePreferredProperty property,
									 final SUINode node, final Region fxNode) {
			fxNode.setPrefSize(property.getWidth(), property.getHeight());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers, final SizePreferredProperty property,
									 final SUINode node, final Region fxNode) {
			if (node.hasProperty(SizeProperty.class)) {
				SizeProperty sizeProp = node.getProperty(SizeProperty.class);
				fxNode.setPrefSize(sizeProp.getPreferredWidth(), sizeProp.getPreferredHeight());
				return MutationResult.MUTATED;
			} else {
				return MutationResult.REQUIRES_REBUILD;
			}
		}

	}


}
