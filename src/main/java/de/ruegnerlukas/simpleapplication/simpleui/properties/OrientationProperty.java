package de.ruegnerlukas.simpleapplication.simpleui.properties;


import de.ruegnerlukas.simpleapplication.simpleui.SNode;
import de.ruegnerlukas.simpleapplication.simpleui.SceneContext;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.BaseNodeMutator;
import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import lombok.Getter;

public class OrientationProperty extends Property {


	@Getter
	private final Orientation orientation;




	public OrientationProperty(final Orientation orientation) {
		super(OrientationProperty.class);
		this.orientation = orientation;
	}




	@Override
	protected boolean isPropertyEqual(final Property other) {
		return orientation == ((OrientationProperty) other).getOrientation();
	}




	@Override
	public String printValue() {
		return this.orientation.toString();
	}




	public static class SeparatorOrientationUpdatingBuilder implements PropFxNodeUpdatingBuilder<OrientationProperty, Separator> {


		@Override
		public void build(final SceneContext context, final SNode node, final OrientationProperty property, final Separator fxNode) {
			fxNode.setOrientation(property.getOrientation());
		}




		@Override
		public BaseNodeMutator.MutationResult update(final SceneContext context, final OrientationProperty property, final SNode node, final Separator fxNode) {
			fxNode.setOrientation(property.getOrientation());
			return BaseNodeMutator.MutationResult.MUTATED;
		}




		@Override
		public BaseNodeMutator.MutationResult remove(final SceneContext context, final OrientationProperty property, final SNode node, final Separator fxNode) {
			fxNode.setOrientation(Orientation.HORIZONTAL);
			return BaseNodeMutator.MutationResult.MUTATED;
		}


	}


}



