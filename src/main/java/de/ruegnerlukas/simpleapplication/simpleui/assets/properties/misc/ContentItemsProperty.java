package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.jfxelements.ExtendedChoiceBox;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.jfxelements.ExtendedComboBox;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.jfxelements.ExtendedListView;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.builders.FactoryExtension;
import lombok.Getter;

import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

public class ContentItemsProperty<T> extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<ContentItemsProperty, ContentItemsProperty, Boolean> COMPARATOR = (a, b) -> {
		if (Objects.equals(a.getSelectedChoice(), b.getSelectedChoice()) && a.getChoices().size() == b.getChoices().size()) {
			for (int i = 0; i < a.getChoices().size(); i++) {
				if (!Objects.equals(a.getChoices().get(i), b.getChoices().get(i))) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	};

	/**
	 * The list of choices
	 */
	@Getter
	private final List<T> choices;

	/**
	 * The choice to select from the list of choices (or null)
	 */
	@Getter
	private final T selectedChoice;




	/**
	 * @param choices the list of choices
	 */
	public ContentItemsProperty(final List<T> choices) {
		this(choices, null);
	}




	/**
	 * @param choices        the list of choices
	 * @param selectedChoice the choice to select from the list of choices (or null)
	 */
	public ContentItemsProperty(final List<T> choices, final T selectedChoice) {
		super(ContentItemsProperty.class, COMPARATOR);
		Validations.INPUT.notNull(choices).exception("The choice-list may not be null");
		this.choices = List.copyOf(choices);
		this.selectedChoice = selectedChoice;
	}




	public interface PropertyBuilderExtensionWithSelected<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param choices        the list of choices
		 * @param selectedChoice the choice to select from the list of choices (or null)
		 * @return the builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default <E> T contentItems(final List<E> choices, final E selectedChoice) {
			getBuilderProperties().add(new ContentItemsProperty<>(choices, selectedChoice));
			return (T) this;
		}

	}






	public interface PropertyBuilderExtensionNoSelected<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param choices the list of choices
		 * @return the builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default <E> T contentItems(final List<E> choices) {
			getBuilderProperties().add(new ContentItemsProperty<>(choices));
			return (T) this;
		}

	}






	public static class ChoiceBoxUpdatingBuilder<T> implements PropFxNodeUpdatingBuilder<ContentItemsProperty<T>, ExtendedChoiceBox<T>> {


		@Override
		public void build(final SuiNode node, final ContentItemsProperty<T> property, final ExtendedChoiceBox<T> fxNode) {
			fxNode.setItems(property.getChoices(), property.getSelectedChoice());
		}




		@Override
		public MutationResult update(final ContentItemsProperty<T> property, final SuiNode node, final ExtendedChoiceBox<T> fxNode) {
			fxNode.setItems(property.getChoices(), property.getSelectedChoice());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final ContentItemsProperty<T> property, final SuiNode node, final ExtendedChoiceBox<T> fxNode) {
			fxNode.clearItems();
			return MutationResult.MUTATED;
		}

	}






	public static class ComboBoxUpdatingBuilder<T> implements PropFxNodeUpdatingBuilder<ContentItemsProperty<T>, ExtendedComboBox<T>> {


		@Override
		public void build(final SuiNode node, final ContentItemsProperty<T> property, final ExtendedComboBox<T> fxNode) {
			fxNode.setItems(property.getChoices(), property.getSelectedChoice());
		}




		@Override
		public MutationResult update(final ContentItemsProperty<T> property, final SuiNode node, final ExtendedComboBox<T> fxNode) {
			fxNode.setItems(property.getChoices(), property.getSelectedChoice());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final ContentItemsProperty<T> property, final SuiNode node, final ExtendedComboBox<T> fxNode) {
			fxNode.clearItems();
			return MutationResult.MUTATED;
		}


	}






	public static class ListViewUpdatingBuilder<T> implements PropFxNodeUpdatingBuilder<ContentItemsProperty<T>, ExtendedListView<T>> {


		@Override
		public void build(final SuiNode node, final ContentItemsProperty<T> property, final ExtendedListView<T> fxNode) {
			fxNode.setItems(property.getChoices());
		}




		@Override
		public MutationResult update(final ContentItemsProperty<T> property, final SuiNode node, final ExtendedListView<T> fxNode) {
			fxNode.setItems(property.getChoices());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final ContentItemsProperty<T> property, final SuiNode node, final ExtendedListView<T> fxNode) {
			fxNode.clearItems();
			return MutationResult.MUTATED;
		}


	}


}
