package de.ruegnerlukas.simpleapplication.simpleui.properties;


import de.ruegnerlukas.simpleapplication.simpleui.SNode;
import de.ruegnerlukas.simpleapplication.simpleui.SceneContext;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.BaseNodeMutator;
import javafx.scene.layout.Region;
import lombok.Getter;

@Getter
public class SizeProperty extends Property {


	private final Double minWidth;
	private final Double minHeight;

	private final Double preferredWidth;
	private final Double preferredHeight;

	private final Double maxWidth;
	private final Double maxHeight;




	public SizeProperty(final Double minWidth, final Double minHeight,
						final Double preferredWidth, final Double preferredHeight,
						final Double maxWidth, final Double maxHeight) {

		super(SizeProperty.class);
		this.minWidth = minWidth;
		this.minHeight = minHeight;
		this.preferredWidth = preferredWidth;
		this.preferredHeight = preferredHeight;
		this.maxWidth = maxWidth;
		this.maxHeight = maxHeight;
	}




	@Override
	protected boolean isPropertyEqual(final Property other) {
		final SizeProperty sizeOther = (SizeProperty) other;
		return isEqual(this.getMinWidth(), sizeOther.getMinWidth())
				&& isEqual(this.getMinHeight(), sizeOther.getMinHeight())
				&& isEqual(this.getPreferredWidth(), sizeOther.getPreferredWidth())
				&& isEqual(this.getPreferredHeight(), sizeOther.getPreferredHeight())
				&& isEqual(this.getMaxWidth(), sizeOther.getMaxWidth())
				&& isEqual(this.getMaxHeight(), sizeOther.getMaxHeight());
	}




	@Override
	public String printValue() {
		return "min=" + getMinWidth() + "x" + getMinHeight()
				+ " preferred=" + getPreferredWidth() + "x" + getPreferredHeight()
				+ " max=" + getMaxWidth() + "x" + getMaxHeight();
	}




	protected static boolean isEqual(Double a, Double b) {
		if (a == null && b == null) {
			return true;
		} else {
			if (a != null) {
				if (b == null) {
					return false;
				} else {
					return a.compareTo(b) == 0;
				}
			} else {
				return false;
			}
		}
	}




	public static class SizeUpdatingBuilder implements PropFxNodeUpdatingBuilder<SizeProperty, Region> {


		@Override
		public void build(final SceneContext context, final SNode node, final SizeProperty property, final Region fxNode) {
			setSize(node, property, fxNode);
		}




		@Override
		public BaseNodeMutator.MutationResult update(final SceneContext context, final SizeProperty property, final SNode node, final Region fxNode) {
			setSize(node, property, fxNode);
			return BaseNodeMutator.MutationResult.MUTATED;
		}




		private void setSize(final SNode node, final SizeProperty property, final Region fxNode) {
			if (!node.hasProperty(SizeMinProperty.class)) {
				fxNode.setMinSize(property.getMinWidth(), property.getMinHeight());
			}
			if (!node.hasProperty(SizePreferredProperty.class)) {
				fxNode.setPrefSize(property.getPreferredWidth(), property.getPreferredHeight());
			}
			if (!node.hasProperty(SizeMaxProperty.class)) {
				fxNode.setMaxSize(property.getMaxWidth(), property.getMaxHeight());
			}
		}




		@Override
		public BaseNodeMutator.MutationResult remove(final SceneContext context, final SizeProperty property, final SNode node, final Region fxNode) {
			if (!node.hasProperty(SizeMinProperty.class)) {
				fxNode.setMinSize(0, 0);
			}
			if (!node.hasProperty(SizePreferredProperty.class)) {
				fxNode.setPrefSize(property.getPreferredWidth(), property.getPreferredHeight());
				return BaseNodeMutator.MutationResult.REBUILD;
			}
			if (!node.hasProperty(SizeMaxProperty.class)) {
				fxNode.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			}
			return BaseNodeMutator.MutationResult.MUTATED;
		}

	}


}
