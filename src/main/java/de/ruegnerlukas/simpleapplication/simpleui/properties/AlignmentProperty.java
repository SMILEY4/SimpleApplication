package de.ruegnerlukas.simpleapplication.simpleui.properties;


import de.ruegnerlukas.simpleapplication.simpleui.SNode;
import de.ruegnerlukas.simpleapplication.simpleui.SceneContext;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.BaseNodeMutator;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import lombok.Getter;

public class AlignmentProperty extends Property {


	@Getter
	private final Pos alignment;





	public AlignmentProperty(final Pos alignment) {
		super(AlignmentProperty.class);
		this.alignment = alignment;
	}




	@Override
	protected boolean isPropertyEqual(final Property other) {
		return alignment == ((AlignmentProperty) other).getAlignment();
	}




	@Override
	public String printValue() {
		return this.alignment.toString();
	}




	public static class VBoxAlignmentUpdatingBuilder implements PropFxNodeUpdatingBuilder<AlignmentProperty, VBox> {


		@Override
		public void build(final SceneContext context, final SNode node, final AlignmentProperty property, final VBox fxNode) {
			fxNode.setAlignment(property.getAlignment());
		}




		@Override
		public BaseNodeMutator.MutationResult update(final SceneContext context, final AlignmentProperty property, final SNode node, final VBox fxNode) {
			fxNode.setAlignment(property.getAlignment());
			return BaseNodeMutator.MutationResult.MUTATED;
		}




		@Override
		public BaseNodeMutator.MutationResult remove(final SceneContext context, final AlignmentProperty property, final SNode node, final VBox fxNode) {
			fxNode.setAlignment(Pos.TOP_LEFT);
			return BaseNodeMutator.MutationResult.MUTATED;
		}

	}


}



