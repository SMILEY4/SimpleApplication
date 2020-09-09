package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import lombok.Getter;

public class OrientationProperty extends SuiProperty {


	/**
	 * The orientation value
	 */
	@Getter
	private final Orientation orientation;




	/**
	 * @param orientation the orientation value
	 */
	public OrientationProperty(final Orientation orientation) {
		super(OrientationProperty.class);
		this.orientation = orientation;
	}




	@Override
	protected boolean isPropertyEqual(final SuiProperty other) {
		return orientation == ((OrientationProperty) other).getOrientation();
	}




	public static class SeparatorUpdatingBuilder implements PropFxNodeUpdatingBuilder<OrientationProperty, Separator> {


		@Override
		public void build(final SuiNode node,
						  final OrientationProperty property,
						  final Separator fxNode) {
			fxNode.setOrientation(property.getOrientation());
		}




		@Override
		public MutationResult update(final OrientationProperty property,
									 final SuiNode node,
									 final Separator fxNode) {
			fxNode.setOrientation(property.getOrientation());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final OrientationProperty property,
									 final SuiNode node,
									 final Separator fxNode) {
			fxNode.setOrientation(Orientation.HORIZONTAL);
			return MutationResult.MUTATED;
		}


	}


}



