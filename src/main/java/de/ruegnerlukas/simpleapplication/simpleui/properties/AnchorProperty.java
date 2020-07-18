package de.ruegnerlukas.simpleapplication.simpleui.properties;


import de.ruegnerlukas.simpleapplication.simpleui.SNode;
import de.ruegnerlukas.simpleapplication.simpleui.SceneContext;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.BaseNodeMutator;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;

import java.util.Objects;
import java.util.Optional;

@Getter
public class AnchorProperty extends Property {


	private final Number top;
	private final Number bottom;
	private final Number left;
	private final Number right;




	public AnchorProperty(final Number top, final Number bottom, final Number left, final Number right) {
		super(AnchorProperty.class);
		this.top = top;
		this.bottom = bottom;
		this.left = left;
		this.right = right;
	}




	@Override
	protected boolean isPropertyEqual(final Property other) {
		final AnchorProperty anchorOther = (AnchorProperty) other;
		return Objects.equals(getTop(), anchorOther.getTop())
				&& Objects.equals(getBottom(), anchorOther.getBottom())
				&& Objects.equals(getLeft(), anchorOther.getLeft())
				&& Objects.equals(getRight(), anchorOther.getRight());
	}




	@Override
	public String printValue() {
		return "t="+getTop() + " b="+getBottom() + " l="+getLeft() + " r="+getRight();
	}




	public static class AnchorUpdatingBuilder implements PropFxNodeUpdatingBuilder<AnchorProperty, Node> {


		@Override
		public void build(final SceneContext context, final SNode node, final AnchorProperty property, final Node fxNode) {
			AnchorPane.setTopAnchor(fxNode, Optional.ofNullable(property.getTop()).map(Number::doubleValue).orElse(null));
			AnchorPane.setBottomAnchor(fxNode, Optional.ofNullable(property.getBottom()).map(Number::doubleValue).orElse(null));
			AnchorPane.setLeftAnchor(fxNode, Optional.ofNullable(property.getLeft()).map(Number::doubleValue).orElse(null));
			AnchorPane.setRightAnchor(fxNode, Optional.ofNullable(property.getRight()).map(Number::doubleValue).orElse(null));
		}




		@Override
		public BaseNodeMutator.MutationResult update(final SceneContext context, final AnchorProperty property, final SNode node, final Node fxNode) {
			AnchorPane.setTopAnchor(fxNode, Optional.ofNullable(property.getTop()).map(Number::doubleValue).orElse(null));
			AnchorPane.setBottomAnchor(fxNode, Optional.ofNullable(property.getBottom()).map(Number::doubleValue).orElse(null));
			AnchorPane.setLeftAnchor(fxNode, Optional.ofNullable(property.getLeft()).map(Number::doubleValue).orElse(null));
			AnchorPane.setRightAnchor(fxNode, Optional.ofNullable(property.getRight()).map(Number::doubleValue).orElse(null));
			return BaseNodeMutator.MutationResult.MUTATED;
		}




		@Override
		public BaseNodeMutator.MutationResult remove(final SceneContext context, final AnchorProperty property, final SNode node, final Node fxNode) {
			AnchorPane.setTopAnchor(fxNode, null);
			AnchorPane.setBottomAnchor(fxNode, null);
			AnchorPane.setLeftAnchor(fxNode, null);
			AnchorPane.setRightAnchor(fxNode, null);
			return BaseNodeMutator.MutationResult.MUTATED;
		}

	}


}
