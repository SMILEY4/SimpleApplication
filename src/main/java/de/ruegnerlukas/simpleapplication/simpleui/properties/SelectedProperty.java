package de.ruegnerlukas.simpleapplication.simpleui.properties;


import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import javafx.scene.control.CheckBox;
import lombok.Getter;

import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;

public class SelectedProperty extends Property {


	/**
	 * Whether the element is selected.
	 */
	@Getter
	private final boolean selected;




	/**
	 * @param selected whether the element is disabled
	 */
	public SelectedProperty(final boolean selected) {
		super(SelectedProperty.class);
		this.selected = selected;
	}




	@Override
	protected boolean isPropertyEqual(final Property other) {
		return selected == ((SelectedProperty) other).isSelected();
	}




	@Override
	public String printValue() {
		return isSelected() ? "selected" : "notSelected";
	}




	public static class SelectedPropertyUpdatingBuilder implements PropFxNodeUpdatingBuilder<SelectedProperty, CheckBox> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers, final SUINode node, final SelectedProperty property,
						  final CheckBox fxNode) {
			fxNode.setSelected(property.isSelected());
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers, final SelectedProperty property,
									 final SUINode node, final CheckBox fxNode) {
			fxNode.setSelected(property.isSelected());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers, final SelectedProperty property,
									 final SUINode node, final CheckBox fxNode) {
			fxNode.setSelected(false);
			return MutationResult.MUTATED;
		}

	}


}



