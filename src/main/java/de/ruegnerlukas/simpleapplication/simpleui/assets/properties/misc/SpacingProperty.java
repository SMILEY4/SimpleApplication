package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;

public class SpacingProperty extends SuiProperty {


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
	protected boolean isPropertyEqual(final SuiProperty other) {
		return spacing == ((SpacingProperty) other).getSpacing();
	}




	public static class VBoxUpdatingBuilder implements PropFxNodeUpdatingBuilder<SpacingProperty, VBox> {


		@Override
		public void build(final SuiNode node,
						  final SpacingProperty property,
						  final VBox fxNode) {
			fxNode.setSpacing(property.getSpacing());
		}




		@Override
		public MutationResult update(final SpacingProperty property,
									 final SuiNode node,
									 final VBox fxNode) {
			fxNode.setSpacing(property.getSpacing());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final SpacingProperty property,
									 final SuiNode node,
									 final VBox fxNode) {
			fxNode.setSpacing(0);
			return MutationResult.MUTATED;
		}

	}






	public static class HBoxUpdatingBuilder implements PropFxNodeUpdatingBuilder<SpacingProperty, HBox> {


		@Override
		public void build(final SuiNode node,
						  final SpacingProperty property,
						  final HBox fxNode) {
			fxNode.setSpacing(property.getSpacing());
		}




		@Override
		public MutationResult update(final SpacingProperty property,
									 final SuiNode node,
									 final HBox fxNode) {
			fxNode.setSpacing(property.getSpacing());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final SpacingProperty property,
									 final SuiNode node,
									 final HBox fxNode) {
			fxNode.setSpacing(0);
			return MutationResult.MUTATED;
		}

	}


}
