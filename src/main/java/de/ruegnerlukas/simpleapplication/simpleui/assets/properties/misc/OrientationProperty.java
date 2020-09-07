package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Property;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.core.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import lombok.Getter;

import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;

public class OrientationProperty extends Property {


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
	protected boolean isPropertyEqual(final Property other) {
		return orientation == ((OrientationProperty) other).getOrientation();
	}




	@Override
	public String printValue() {
		return this.orientation.toString();
	}




	public static class SeparatorUpdatingBuilder implements PropFxNodeUpdatingBuilder<OrientationProperty, Separator> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers,
						  final SuiNode node,
						  final OrientationProperty property,
						  final Separator fxNode) {
			fxNode.setOrientation(property.getOrientation());
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers,
									 final OrientationProperty property,
									 final SuiNode node,
									 final Separator fxNode) {
			fxNode.setOrientation(property.getOrientation());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers,
									 final OrientationProperty property,
									 final SuiNode node,
									 final Separator fxNode) {
			fxNode.setOrientation(Orientation.HORIZONTAL);
			return MutationResult.MUTATED;
		}


	}


}


