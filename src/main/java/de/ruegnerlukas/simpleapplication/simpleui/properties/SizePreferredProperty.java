package de.ruegnerlukas.simpleapplication.simpleui.properties;


import de.ruegnerlukas.simpleapplication.simpleui.SNode;
import de.ruegnerlukas.simpleapplication.simpleui.SceneContext;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.BaseNodeMutator;
import javafx.scene.layout.Region;
import lombok.Getter;

import java.util.Optional;

@Getter
public class SizePreferredProperty extends Property {


	private final Double width;
	private final Double height;




	public SizePreferredProperty(final Double width, final Double height) {
		super(SizePreferredProperty.class);
		this.width = width;
		this.height = height;
	}




	@Override
	protected boolean isPropertyEqual(final Property other) {
		final SizePreferredProperty sizeOther = (SizePreferredProperty) other;
		return SizeProperty.isEqual(this.getWidth(), sizeOther.getWidth()) && SizeProperty.isEqual(this.getHeight(), sizeOther.getHeight());
	}




	@Override
	public String printValue() {
		return getWidth() + "x" + getHeight();
	}




	public static Optional<Double> getPreferredWidth(SNode node) {
		Double width = node.getPropertySafe(SizePreferredProperty.class)
				.map(SizePreferredProperty::getWidth)
				.orElse(null);
		if (width == null) {
			width = node.getPropertySafe(SizeProperty.class)
					.map(SizeProperty::getPreferredWidth)
					.orElse(null);
		}
		return Optional.ofNullable(width);
	}




	public static Optional<Double> getPreferredHeight(SNode node) {
		Double height = node.getPropertySafe(SizePreferredProperty.class)
				.map(SizePreferredProperty::getHeight)
				.orElse(null);
		if (height == null) {
			height = node.getPropertySafe(SizeProperty.class)
					.map(SizeProperty::getPreferredHeight)
					.orElse(null);
		}
		return Optional.ofNullable(height);
	}




	public static class SizePreferredUpdatingBuilder implements PropFxNodeUpdatingBuilder<SizePreferredProperty, Region> {


		@Override
		public void build(final SceneContext context, final SNode node, final SizePreferredProperty property, final Region fxNode) {
			fxNode.setPrefSize(property.getWidth(), property.getHeight());
		}




		@Override
		public BaseNodeMutator.MutationResult update(final SceneContext context, final SizePreferredProperty property, final SNode node, final Region fxNode) {
			fxNode.setPrefSize(property.getWidth(), property.getHeight());
			return BaseNodeMutator.MutationResult.MUTATED;
		}




		@Override
		public BaseNodeMutator.MutationResult remove(final SceneContext context, final SizePreferredProperty property, final SNode node, final Region fxNode) {
			if (node.hasProperty(SizeProperty.class)) {
				SizeProperty sizeProp = node.getProperty(SizeProperty.class);
				fxNode.setPrefSize(sizeProp.getPreferredWidth(), sizeProp.getPreferredHeight());
				return BaseNodeMutator.MutationResult.MUTATED;
			} else {
				return BaseNodeMutator.MutationResult.REBUILD;
			}
		}

	}


}
