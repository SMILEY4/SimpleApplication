package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import javafx.geometry.Pos;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;

public class AlignmentProperty extends SuiProperty {


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
	protected boolean isPropertyEqual(final SuiProperty other) {
		return alignment == ((AlignmentProperty) other).getAlignment();
	}




	@Override
	public String printValue() {
		return this.alignment.toString();
	}




	public static class VBoxUpdatingBuilder implements PropFxNodeUpdatingBuilder<AlignmentProperty, VBox> {


		@Override
		public void build(final SuiNode node,
						  final AlignmentProperty property,
						  final VBox fxNode) {
			fxNode.setAlignment(property.getAlignment());
		}




		@Override
		public MutationResult update(final AlignmentProperty property,
									 final SuiNode node,
									 final VBox fxNode) {
			fxNode.setAlignment(property.getAlignment());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final AlignmentProperty property,
									 final SuiNode node,
									 final VBox fxNode) {
			fxNode.setAlignment(Pos.TOP_LEFT);
			return MutationResult.MUTATED;
		}

	}






	public static class HBoxUpdatingBuilder implements PropFxNodeUpdatingBuilder<AlignmentProperty, HBox> {


		@Override
		public void build(final SuiNode node,
						  final AlignmentProperty property,
						  final HBox fxNode) {
			fxNode.setAlignment(property.getAlignment());
		}




		@Override
		public MutationResult update(final AlignmentProperty property,
									 final SuiNode node,
									 final HBox fxNode) {
			fxNode.setAlignment(property.getAlignment());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final AlignmentProperty property,
									 final SuiNode node,
									 final HBox fxNode) {
			fxNode.setAlignment(Pos.TOP_LEFT);
			return MutationResult.MUTATED;
		}

	}






	public static class LabeledUpdatingBuilder implements PropFxNodeUpdatingBuilder<AlignmentProperty, Labeled> {


		@Override
		public void build(final SuiNode node,
						  final AlignmentProperty property,
						  final Labeled fxNode) {
			fxNode.setAlignment(property.getAlignment());
		}




		@Override
		public MutationResult update(final AlignmentProperty property,
									 final SuiNode node,
									 final Labeled fxNode) {
			fxNode.setAlignment(property.getAlignment());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final AlignmentProperty property,
									 final SuiNode node,
									 final Labeled fxNode) {
			fxNode.setAlignment(Pos.TOP_LEFT);
			return MutationResult.MUTATED;
		}

	}






	public static class TextFieldUpdatingBuilder implements PropFxNodeUpdatingBuilder<AlignmentProperty, TextField> {


		@Override
		public void build(final SuiNode node,
						  final AlignmentProperty property,
						  final TextField fxNode) {
			fxNode.setAlignment(property.getAlignment());
		}




		@Override
		public MutationResult update(final AlignmentProperty property,
									 final SuiNode node,
									 final TextField fxNode) {
			fxNode.setAlignment(property.getAlignment());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final AlignmentProperty property,
									 final SuiNode node,
									 final TextField fxNode) {
			fxNode.setAlignment(Pos.TOP_LEFT);
			return MutationResult.MUTATED;
		}

	}


}



