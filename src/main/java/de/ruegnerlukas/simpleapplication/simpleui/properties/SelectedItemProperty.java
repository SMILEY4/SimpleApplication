package de.ruegnerlukas.simpleapplication.simpleui.properties;


import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;
import javafx.scene.control.ChoiceBox;
import lombok.Getter;

import java.util.Objects;

public class SelectedItemProperty<T> extends Property {


	/**
	 * The selected item.
	 */
	@Getter
	private final T selected;




	/**
	 * @param selected the selected item
	 */
	public SelectedItemProperty(final T selected) {
		super(SelectedItemProperty.class);
		this.selected = selected;
	}




	@Override
	public boolean isPropertyEqual(final Property other) {
		return getSelected().equals(((SelectedItemProperty) other).getSelected());
	}




	@Override
	public String printValue() {
		return getSelected() == null ? "null" : getSelected().toString();
	}




	public static class ChoiceBoxUpdatingBuilder<T> implements PropFxNodeUpdatingBuilder<SelectedItemProperty<T>, ChoiceBox<T>> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers,
						  final SuiNode node,
						  final SelectedItemProperty<T> property,
						  final ChoiceBox<T> fxNode) {
			if (!Objects.equals(fxNode.getSelectionModel().getSelectedItem(), property.getSelected())) {
				fxNode.getSelectionModel().select(property.getSelected());
			}
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers,
									 final SelectedItemProperty<T> property,
									 final SuiNode node,
									 final ChoiceBox<T> fxNode) {
			if (!Objects.equals(fxNode.getSelectionModel().getSelectedItem(), property.getSelected())) {
				fxNode.getSelectionModel().select(property.getSelected());
			}
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers,
									 final SelectedItemProperty<T> property,
									 final SuiNode node,
									 final ChoiceBox<T> fxNode) {
			return MutationResult.MUTATED;
		}

	}


}
