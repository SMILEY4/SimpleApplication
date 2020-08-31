package de.ruegnerlukas.simpleapplication.simpleui.properties;


import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;
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
		public void build(final MasterNodeHandlers nodeHandlers, final SuiNode node, final SpacingProperty property,
						  final VBox fxNode) {
			fxNode.setSpacing(property.getSpacing());
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers, final SpacingProperty property,
									 final SuiNode node, final VBox fxNode) {
			fxNode.setSpacing(property.getSpacing());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers, final SpacingProperty property,
									 final SuiNode node, final VBox fxNode) {
			fxNode.setSpacing(0);
			return MutationResult.MUTATED;
		}

	}






	public static class HBoxUpdatingBuilder implements PropFxNodeUpdatingBuilder<SpacingProperty, HBox> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers, final SuiNode node, final SpacingProperty property,
						  final HBox fxNode) {
			fxNode.setSpacing(property.getSpacing());
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers, final SpacingProperty property,
									 final SuiNode node, final HBox fxNode) {
			fxNode.setSpacing(property.getSpacing());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers, final SpacingProperty property,
									 final SuiNode node, final HBox fxNode) {
			fxNode.setSpacing(0);
			return MutationResult.MUTATED;
		}

	}


}
