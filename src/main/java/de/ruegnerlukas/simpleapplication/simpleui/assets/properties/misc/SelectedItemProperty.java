package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import javafx.scene.control.ChoiceBox;
import lombok.Getter;

import java.util.Objects;

public class SelectedItemProperty<T> extends SuiProperty {


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
	public boolean isPropertyEqual(final SuiProperty other) {
		return getSelected().equals(((SelectedItemProperty) other).getSelected());
	}




	public static class ChoiceBoxUpdatingBuilder<T> implements PropFxNodeUpdatingBuilder<SelectedItemProperty<T>, ChoiceBox<T>> {


		@Override
		public void build(final SuiNode node,
						  final SelectedItemProperty<T> property,
						  final ChoiceBox<T> fxNode) {
			if (!Objects.equals(fxNode.getSelectionModel().getSelectedItem(), property.getSelected())) {
				fxNode.getSelectionModel().select(property.getSelected());
			}
		}




		@Override
		public MutationResult update(final SelectedItemProperty<T> property,
									 final SuiNode node,
									 final ChoiceBox<T> fxNode) {
			if (!Objects.equals(fxNode.getSelectionModel().getSelectedItem(), property.getSelected())) {
				fxNode.getSelectionModel().select(property.getSelected());
			}
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final SelectedItemProperty<T> property,
									 final SuiNode node,
									 final ChoiceBox<T> fxNode) {
			return MutationResult.MUTATED;
		}

	}


}
