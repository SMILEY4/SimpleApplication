package de.ruegnerlukas.simpleapplication.simpleui.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.elements.basenode.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.elements.jfxelements.SearchableComboBox;
import de.ruegnerlukas.simpleapplication.simpleui.events.SUIEventListener;
import de.ruegnerlukas.simpleapplication.simpleui.events.ValueChangedEventData;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import lombok.Getter;

public class OnValueChangedEventProperty<T> extends AbstractEventListenerProperty<ValueChangedEventData<T>> {


	/**
	 * The listener for events with {@link ValueChangedEventData}.
	 */
	@Getter
	private final SUIEventListener<ValueChangedEventData<T>> listener;

	/**
	 * The proxy for the actual change listener.
	 */
	@Getter
	private final ChangeListenerProxy<T> changeListenerProxy;




	/**
	 * @param listener the listener for events with {@link ValueChangedEventData}.
	 */
	public OnValueChangedEventProperty(final SUIEventListener<ValueChangedEventData<T>> listener) {
		super(OnValueChangedEventProperty.class);
		this.listener = listener;
		this.changeListenerProxy = new ChangeListenerProxy<>((prev, next) -> listener.onEvent(
				new ValueChangedEventData<>(next, prev))
		);

	}




	public static class ChoiceBoxUpdatingBuilder<T> implements PropFxNodeUpdatingBuilder<OnValueChangedEventProperty<T>, ChoiceBox<T>> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers,
						  final SuiNode node,
						  final OnValueChangedEventProperty<T> property,
						  final ChoiceBox<T> fxNode) {
			property.getChangeListenerProxy().addTo(fxNode.getSelectionModel().selectedItemProperty());
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers,
									 final OnValueChangedEventProperty<T> property,
									 final SuiNode node,
									 final ChoiceBox<T> fxNode) {
			node.getPropertySafe(OnValueChangedEventProperty.class)
					.map(prop -> (OnValueChangedEventProperty<T>) prop)
					.map(OnValueChangedEventProperty::getChangeListenerProxy)
					.ifPresent(proxy -> proxy.removeFrom(fxNode.getSelectionModel().selectedItemProperty()));
			property.getChangeListenerProxy().addTo(fxNode.getSelectionModel().selectedItemProperty());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers,
									 final OnValueChangedEventProperty<T> property,
									 final SuiNode node,
									 final ChoiceBox<T> fxNode) {
			property.getChangeListenerProxy().removeFrom(fxNode.getSelectionModel().selectedItemProperty());
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
				property.getChangeListenerProxy().addTo(((SearchableComboBox<T>) fxNode).getSelectedValueProperty());
			} else {
				property.getChangeListenerProxy().addTo(fxNode.getSelectionModel().selectedItemProperty());
			}
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers,
									 final OnValueChangedEventProperty<T> property,
									 final SuiNode node,
									 final ComboBox<T> fxNode) {
			if (fxNode instanceof SearchableComboBox) {
				node.getPropertySafe(OnValueChangedEventProperty.class)
						.map(prop -> (OnValueChangedEventProperty<T>) prop)
						.map(OnValueChangedEventProperty::getChangeListenerProxy)
						.ifPresent(proxy -> proxy.removeFrom(((SearchableComboBox<T>) fxNode).getSelectedValueProperty()));
				property.getChangeListenerProxy().addTo(((SearchableComboBox<T>) fxNode).getSelectedValueProperty());
			} else {
				node.getPropertySafe(OnValueChangedEventProperty.class)
						.map(prop -> (OnValueChangedEventProperty<T>) prop)
						.map(OnValueChangedEventProperty::getChangeListenerProxy)
						.ifPresent(proxy -> proxy.removeFrom(fxNode.getSelectionModel().selectedItemProperty()));
				property.getChangeListenerProxy().addTo(fxNode.getSelectionModel().selectedItemProperty());
			}
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers,
									 final OnValueChangedEventProperty<T> property,
									 final SuiNode node,
									 final ComboBox<T> fxNode) {
			if (fxNode instanceof SearchableComboBox) {
				property.getChangeListenerProxy().removeFrom(((SearchableComboBox<T>) fxNode).getSelectedValueProperty());
			} else {
				property.getChangeListenerProxy().removeFrom(fxNode.getSelectionModel().selectedItemProperty());
			}
			return MutationResult.MUTATED;
		}

	}






	public static class SliderUpdatingBuilder implements PropFxNodeUpdatingBuilder<OnValueChangedEventProperty<Number>, Slider> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers,
						  final SuiNode node,
						  final OnValueChangedEventProperty<Number> property,
						  final Slider fxNode) {
			property.getChangeListenerProxy().addTo(fxNode.valueProperty());
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers,
									 final OnValueChangedEventProperty<Number> property,
									 final SuiNode node,
									 final Slider fxNode) {
			node.getPropertySafe(OnValueChangedEventProperty.class)
					.map(prop -> (OnValueChangedEventProperty<Number>) prop)
					.map(OnValueChangedEventProperty::getChangeListenerProxy)
					.ifPresent(proxy -> proxy.removeFrom(fxNode.valueProperty()));
			property.getChangeListenerProxy().addTo(fxNode.valueProperty());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers,
									 final OnValueChangedEventProperty<Number> property,
									 final SuiNode node,
									 final Slider fxNode) {
			property.getChangeListenerProxy().removeFrom(fxNode.valueProperty());
			return MutationResult.MUTATED;
		}

	}

}
