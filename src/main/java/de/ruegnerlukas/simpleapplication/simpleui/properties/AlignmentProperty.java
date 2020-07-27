package de.ruegnerlukas.simpleapplication.simpleui.properties;


import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import javafx.geometry.Pos;
import javafx.scene.control.Labeled;
import javafx.scene.layout.VBox;
import lombok.Getter;

import static de.ruegnerlukas.simpleapplication.simpleui.mutation.BaseNodeMutator.MutationResult;

public class AlignmentProperty extends Property {


	/**
	 * The alignment.
	 */
	@Getter
	private final Pos alignment;




	/**
	 * @param alignment the alignment
	 */
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
		public void build(final MasterNodeHandlers nodeHandlers, final SUINode node, final AlignmentProperty property,
						  final VBox fxNode) {
			fxNode.setAlignment(property.getAlignment());
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers, final AlignmentProperty property,
									 final SUINode node, final VBox fxNode) {
			fxNode.setAlignment(property.getAlignment());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers, final AlignmentProperty property,
									 final SUINode node, final VBox fxNode) {
			fxNode.setAlignment(Pos.TOP_LEFT);
			return MutationResult.MUTATED;
		}

	}






	public static class LabeledAlignmentUpdatingBuilder implements PropFxNodeUpdatingBuilder<AlignmentProperty, Labeled> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers, final SUINode node, final AlignmentProperty property,
						  final Labeled fxNode) {
			fxNode.setAlignment(property.getAlignment());
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers, final AlignmentProperty property,
									 final SUINode node, final Labeled fxNode) {
			fxNode.setAlignment(property.getAlignment());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers, final AlignmentProperty property,
									 final SUINode node, final Labeled fxNode) {
			fxNode.setAlignment(Pos.TOP_LEFT);
			return MutationResult.MUTATED;
		}

	}

}



