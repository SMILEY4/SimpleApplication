package de.ruegnerlukas.simpleapplication.simpleui.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.elements.jfxelements.SearchableComboBox;
import de.ruegnerlukas.simpleapplication.simpleui.events.SUIEventListener;
import de.ruegnerlukas.simpleapplication.simpleui.events.ValueChangedEventData;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import lombok.Getter;

public class OnValueChangedEventProperty<T> extends AbstractObservableListenerProperty<ValueChangedEventData<T>, T> {


	/**
	 * The identifying string of the event.
	 */
	public static final String EVENT_ID = "selection.item";

	/**
	 * The listener for events with {@link ValueChangedEventData}.
	 */
	@Getter
	private final SUIEventListener<ValueChangedEventData<T>> listener;




	/**
	 * @param listener the listener for events with {@link ValueChangedEventData}.
	 */
	public OnValueChangedEventProperty(final SUIEventListener<ValueChangedEventData<T>> listener) {
		super(OnValueChangedEventProperty.class, (value, prev, next) -> {
			listener.onEvent(
					new ValueChangedEventData<>(next, prev)
			);
		});
		this.listener = listener;
	}




	public static class ChoiceBoxUpdatingBuilder<T> implements PropFxNodeUpdatingBuilder<OnValueChangedEventProperty<T>, ChoiceBox<T>> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers,
						  final SuiNode node,
						  final OnValueChangedEventProperty<T> property,
						  final ChoiceBox<T> fxNode) {
			property.addChangeListenerTo(fxNode.getSelectionModel().selectedItemProperty());
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers,
									 final OnValueChangedEventProperty<T> property,
									 final SuiNode node,
									 final ChoiceBox<T> fxNode) {
			node.getPropertySafe(OnValueChangedEventProperty.class).ifPresent(prop -> {
				prop.removeChangeListenerFrom(fxNode.getSelectionModel().selectedItemProperty());
			});
			property.addChangeListenerTo(fxNode.getSelectionModel().selectedItemProperty());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers,
									 final OnValueChangedEventProperty<T> property,
									 final SuiNode node,
									 final ChoiceBox<T> fxNode) {
			property.removeChangeListenerFrom(fxNode.getSelectionModel().selectedItemProperty());
			return MutationResult.MUTATED;
		}

	}






	public static class ComboBoxUpdatingBuilder<T> implements PropFxNodeUpdatingBuilder<OnValueChangedEventProperty<T>, ComboBox<T>> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers,
						  final SuiNode node,
						  final OnValueChangedEventProperty<T> property,
						  final ComboBox<T> fxNode) {
			if (fxNode instanceof SearchableComboBox) {
				property.addChangeListenerTo(((SearchableComboBox<T>) fxNode).getSelectedValueProperty());
			} else {
				property.addChangeListenerTo(fxNode.getSelectionModel().selectedItemProperty());
			}
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers,
									 final OnValueChangedEventProperty<T> property,
									 final SuiNode node,
									 final ComboBox<T> fxNode) {
			if (fxNode instanceof SearchableComboBox) {
				node.getPropertySafe(OnValueChangedEventProperty.class).ifPresent(prop -> {
					prop.removeChangeListenerFrom(((SearchableComboBox<T>) fxNode).getSelectedValueProperty());
				});
				property.addChangeListenerTo(((SearchableComboBox<T>) fxNode).getSelectedValueProperty());
			} else {
				node.getPropertySafe(OnValueChangedEventProperty.class).ifPresent(prop -> {
					prop.removeChangeListenerFrom(fxNode.getSelectionModel().selectedItemProperty());
				});
				property.addChangeListenerTo(fxNode.getSelectionModel().selectedItemProperty());
			}
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers,
									 final OnValueChangedEventProperty<T> property,
									 final SuiNode node,
									 final ComboBox<T> fxNode) {
			if (fxNode instanceof SearchableComboBox) {
				property.removeChangeListenerFrom(((SearchableComboBox<T>) fxNode).getSelectedValueProperty());
			} else {
				property.removeChangeListenerFrom(fxNode.getSelectionModel().selectedItemProperty());
			}
			return MutationResult.MUTATED;
		}

	}






	public static class SliderUpdatingBuilder implements PropFxNodeUpdatingBuilder<OnValueChangedEventProperty<Double>, Slider> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers,
						  final SuiNode node,
						  final OnValueChangedEventProperty<Double> property,
						  final Slider fxNode) {
			// TODO
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers,
									 final OnValueChangedEventProperty<Double> property,
									 final SuiNode node,
									 final Slider fxNode) {
			// TODO
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers,
									 final OnValueChangedEventProperty<Double> property,
									 final SuiNode node,
									 final Slider fxNode) {
			// TODO
			return MutationResult.MUTATED;
		}

	}

}
