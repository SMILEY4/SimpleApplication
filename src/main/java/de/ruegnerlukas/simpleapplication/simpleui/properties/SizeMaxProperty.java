package de.ruegnerlukas.simpleapplication.simpleui.properties;


import de.ruegnerlukas.simpleapplication.simpleui.SNode;
import de.ruegnerlukas.simpleapplication.simpleui.SceneContext;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.BaseNodeMutator;
import javafx.scene.layout.Region;
import lombok.Getter;

import java.util.Optional;

@Getter
public class SizeMaxProperty extends Property {


	private final Double width;
	private final Double height;




	public SizeMaxProperty(final Double width, final Double height) {
		super(SizeMaxProperty.class);
		this.width = width;
		this.height = height;
	}




	@Override
	protected boolean isPropertyEqual(final Property other) {
		final SizeMaxProperty sizeOther = (SizeMaxProperty) other;
		return SizeProperty.isEqual(this.getWidth(), sizeOther.getWidth()) && SizeProperty.isEqual(this.getHeight(), sizeOther.getHeight());
	}




	@Override
	public String printValue() {
		return getWidth() + "x" + getHeight();
	}




	public static Optional<Double> getMaxWidth(SNode node) {
		Double width = node.getPropertySafe(SizeMaxProperty.class)
				.map(SizeMaxProperty::getWidth)
				.orElse(null);
		if (width == null) {
			width = node.getPropertySafe(SizeProperty.class)
					.map(SizeProperty::getMaxWidth)
					.orElse(null);
		}
		return Optional.ofNullable(width);
	}




	public static Optional<Double> getMaxHeight(SNode node) {
		Double height = node.getPropertySafe(SizeMaxProperty.class)
				.map(SizeMaxProperty::getHeight)
				.orElse(null);
		if (height == null) {
			height = node.getPropertySafe(SizeProperty.class)
					.map(SizeProperty::getMaxHeight)
					.orElse(null);
		}
		return Optional.ofNullable(height);
	}




	public static class SizeMaxUpdatingBuilder implements PropFxNodeUpdatingBuilder<SizeMaxProperty, Region> {


		@Override
		public void build(final SceneContext context, final SNode node, final SizeMaxProperty property, final Region fxNode) {
			fxNode.setMaxSize(property.getWidth(), property.getHeight());
		}




		@Override
		public BaseNodeMutator.MutationResult update(final SceneContext context, final SizeMaxProperty property, final SNode node, final Region fxNode) {
			fxNode.setMaxSize(property.getWidth(), property.getHeight());
			return BaseNodeMutator.MutationResult.MUTATED;
		}




		@Override
		public BaseNodeMutator.MutationResult remove(final SceneContext context, final SizeMaxProperty property, final SNode node, final Region fxNode) {
			if (node.hasProperty(SizeProperty.class)) {
				SizeProperty sizeProp = node.getProperty(SizeProperty.class);
				fxNode.setMaxSize(sizeProp.getMaxWidth(), sizeProp.getMaxHeight());
			} else {
				fxNode.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			}
			return BaseNodeMutator.MutationResult.MUTATED;
		}

	}


}
