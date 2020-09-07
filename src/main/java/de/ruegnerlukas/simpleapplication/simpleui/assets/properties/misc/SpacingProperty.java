package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Property;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiBaseNode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;

public class SpacingProperty extends Property {


	/**
	 * The spacing value between the elements.
	 */
	@Getter
	private final double spacing;




	/**
	 * @param spacing the spacing value between the elements.
	 */
	public SpacingProperty(final double spacing) {
		super(SpacingProperty.class);
		this.spacing = spacing;
	}




	@Override
	protected boolean isPropertyEqual(final Property other) {
		return spacing == ((SpacingProperty) other).getSpacing();
	}




	@Override
	public String printValue() {
		return Double.toString(spacing);
	}




	public static class VBoxUpdatingBuilder implements PropFxNodeUpdatingBuilder<SpacingProperty, VBox> {


		@Override
		public void build(final SuiBaseNode node,
						  final SpacingProperty property,
						  final VBox fxNode) {
			fxNode.setSpacing(property.getSpacing());
		}




		@Override
		public MutationResult update(final SpacingProperty property,
									 final SuiBaseNode node,
									 final VBox fxNode) {
			fxNode.setSpacing(property.getSpacing());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final SpacingProperty property,
									 final SuiBaseNode node,
									 final VBox fxNode) {
			fxNode.setSpacing(0);
			return MutationResult.MUTATED;
		}

	}






	public static class HBoxUpdatingBuilder implements PropFxNodeUpdatingBuilder<SpacingProperty, HBox> {


		@Override
		public void build(final SuiBaseNode node,
						  final SpacingProperty property,
						  final HBox fxNode) {
			fxNode.setSpacing(property.getSpacing());
		}




		@Override
		public MutationResult update(final SpacingProperty property,
									 final SuiBaseNode node,
									 final HBox fxNode) {
			fxNode.setSpacing(property.getSpacing());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final SpacingProperty property,
									 final SuiBaseNode node,
									 final HBox fxNode) {
			fxNode.setSpacing(0);
			return MutationResult.MUTATED;
		}

	}


}
