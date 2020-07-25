package de.ruegnerlukas.simpleapplication.simpleui.properties;

import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import javafx.scene.control.Labeled;
import lombok.Getter;

import static de.ruegnerlukas.simpleapplication.simpleui.mutation.BaseNodeMutator.MutationResult;

public class WrapTextProperty extends Property {


	/**
	 * Whether the text content should wrap.
	 */
	@Getter
	private final boolean wrap;




	/**
	 * @param wrap whether the text content should wrap.
	 */
	public WrapTextProperty(final boolean wrap) {
		super(WrapTextProperty.class);
		this.wrap = wrap;
	}




	@Override
	protected boolean isPropertyEqual(final Property other) {
		return wrap == ((WrapTextProperty) other).isWrap();
	}




	@Override
	public String printValue() {
		return wrap ? "wrap" : "dontWrap";
	}




	public static class WrapTextUpdatingBuilder implements PropFxNodeUpdatingBuilder<WrapTextProperty, Labeled> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers, final SUINode node, final WrapTextProperty property,
						  final Labeled fxNode) {
			fxNode.setWrapText(property.isWrap());
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers, final WrapTextProperty property,
									 final SUINode node, final Labeled fxNode) {
			fxNode.setWrapText(property.isWrap());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers, final WrapTextProperty property,
									 final SUINode node, final Labeled fxNode) {
			fxNode.setWrapText(false);
			return MutationResult.MUTATED;
		}

	}


}