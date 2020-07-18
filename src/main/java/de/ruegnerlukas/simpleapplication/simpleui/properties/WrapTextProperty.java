package de.ruegnerlukas.simpleapplication.simpleui.properties;

import de.ruegnerlukas.simpleapplication.simpleui.SNode;
import de.ruegnerlukas.simpleapplication.simpleui.SceneContext;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.BaseNodeMutator;
import javafx.scene.control.Labeled;
import lombok.Getter;

public class WrapTextProperty extends Property {


	@Getter
	private final boolean wrap;




	public WrapTextProperty(boolean wrap) {
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




	public static boolean isWrapEnabled(SNode node) {
		return node.getPropertySafe(WrapTextProperty.class)
				.map(WrapTextProperty::isWrap)
				.orElse(false);
	}




	public static class WrapTextUpdatingBuilder implements PropFxNodeUpdatingBuilder<WrapTextProperty, Labeled> {


		@Override
		public void build(final SceneContext context, final SNode node, final WrapTextProperty property, final Labeled fxNode) {
			fxNode.setWrapText(property.isWrap());
		}




		@Override
		public BaseNodeMutator.MutationResult update(final SceneContext context, final WrapTextProperty property, final SNode node, final Labeled fxNode) {
			fxNode.setWrapText(property.isWrap());
			return BaseNodeMutator.MutationResult.MUTATED;
		}




		@Override
		public BaseNodeMutator.MutationResult remove(final SceneContext context, final WrapTextProperty property, final SNode node, final Labeled fxNode) {
			fxNode.setWrapText(false);
			return BaseNodeMutator.MutationResult.MUTATED;
		}

	}


}
