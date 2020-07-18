package de.ruegnerlukas.simpleapplication.simpleui.properties;


import de.ruegnerlukas.simpleapplication.simpleui.SNode;
import de.ruegnerlukas.simpleapplication.simpleui.SceneContext;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.BaseNodeMutator;
import javafx.scene.layout.Region;
import lombok.Getter;

import java.util.Optional;

@Getter
public class SizeMinProperty extends Property {


	private final Double width;
	private final Double height;




	public SizeMinProperty(final Double width, final Double height) {
		super(SizeMinProperty.class);
		this.width = width;
		this.height = height;
	}




	@Override
	protected boolean isPropertyEqual(final Property other) {
		final SizeMinProperty sizeOther = (SizeMinProperty) other;
		return SizeProperty.isEqual(this.getWidth(), sizeOther.getWidth()) && SizeProperty.isEqual(this.getHeight(), sizeOther.getHeight());
	}




	@Override
	public String printValue() {
		return getWidth() + "x" + getHeight();
	}




	public static Optional<Double> getMinWidth(SNode node) {
		Double width = node.getPropertySafe(SizeMinProperty.class)
				.map(SizeMinProperty::getWidth)
				.orElse(null);
		if (width == null) {
			width = node.getPropertySafe(SizeProperty.class)
					.map(SizeProperty::getMinWidth)
					.orElse(null);
		}
		return Optional.ofNullable(width);
	}




	public static Optional<Double> getMinHeight(SNode node) {
		Double height = node.getPropertySafe(SizeMinProperty.class)
				.map(SizeMinProperty::getHeight)
				.orElse(null);
		if (height == null) {
			height = node.getPropertySafe(SizeProperty.class)
					.map(SizeProperty::getMinHeight)
					.orElse(null);
		}
		return Optional.ofNullable(height);
	}




	public static class SizeMinUpdatingBuilder implements PropFxNodeUpdatingBuilder<SizeMinProperty, Region> {


		@Override
		public void build(final SceneContext context, final SNode node, final SizeMinProperty property, final Region fxNode) {
			fxNode.setMinSize(property.getWidth(), property.getHeight());
		}




		@Override
		public BaseNodeMutator.MutationResult update(final SceneContext context, final SizeMinProperty property, final SNode node, final Region fxNode) {
			fxNode.setMinSize(property.getWidth(), property.getHeight());
			return BaseNodeMutator.MutationResult.MUTATED;
		}




		@Override
		public BaseNodeMutator.MutationResult remove(final SceneContext context, final SizeMinProperty property, final SNode node, final Region fxNode) {
			if (node.hasProperty(SizeProperty.class)) {
				SizeProperty sizeProp = node.getProperty(SizeProperty.class);
				fxNode.setMinSize(sizeProp.getMinWidth(), sizeProp.getMinHeight());
			} else {
				fxNode.setMinSize(0, 0);
			}
			return BaseNodeMutator.MutationResult.MUTATED;
		}

	}


}
