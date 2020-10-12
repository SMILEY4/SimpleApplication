package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiLabeledSlider;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.jfxelements.ExtendedChoiceBox;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.jfxelements.ExtendedComboBox;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.jfxelements.ExtendedSlider;
import de.ruegnerlukas.simpleapplication.simpleui.assets.events.ValueChangedEventData;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import javafx.scene.control.Spinner;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.function.BiConsumer;

@Slf4j
public class OnValueChangedEventProperty<T> extends AbstractEventListenerProperty<ValueChangedEventData<T>> {


	/**
	 * The listener for events with {@link ValueChangedEventData}.
	 */
	@Getter
	private final SuiEventListener<ValueChangedEventData<T>> listener;

	/**
	 * The proxy for the actual listener.
	 */
	@Getter
	private final BiConsumer<T, T> listenerProxy;

	/**
	 * The proxy for the actual listener. todo: replace with cleaner listener systems (see choicebox)
	 */
	@Getter
	private final ChangeListenerProxy<T> changeListenerProxy;




	/**
	 * @param propertyId see {@link de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty#getPropertyId()}.
	 * @param listener   the listener for events with {@link ValueChangedEventData}.
	 */
	public OnValueChangedEventProperty(final String propertyId, final SuiEventListener<ValueChangedEventData<T>> listener) {
		super(OnValueChangedEventProperty.class, propertyId);
		this.listener = listener;
		this.listenerProxy = (prev, next) -> listener.onEvent(new ValueChangedEventData<>(next, prev));
		this.changeListenerProxy = new ChangeListenerProxy<>((prev, next) -> listener.onEvent(
				new ValueChangedEventData<>(next, prev))
		);
	}




	public static class ChoiceBoxUpdatingBuilder<T>
			implements PropFxNodeUpdatingBuilder<OnValueChangedEventProperty<T>, ExtendedChoiceBox<T>> {


		@Override
		public void build(final SuiNode node, final OnValueChangedEventProperty<T> property, final ExtendedChoiceBox<T> fxNode) {
			fxNode.setSelectedItemListener(property.getListenerProxy());
		}




		@Override
		public MutationResult update(final OnValueChangedEventProperty<T> property, final SuiNode node, final ExtendedChoiceBox<T> fxNode) {
			fxNode.setSelectedItemListener(property.getListenerProxy());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final OnValueChangedEventProperty<T> property, final SuiNode node, final ExtendedChoiceBox<T> fxNode) {
			fxNode.setSelectedItemListener(null);
			return MutationResult.MUTATED;
		}

	}






	public static class ComboBoxUpdatingBuilder<T>
			implements PropFxNodeUpdatingBuilder<OnValueChangedEventProperty<T>, ExtendedComboBox<T>> {


		@Override
		public void build(final SuiNode node, final OnValueChangedEventProperty<T> property, final ExtendedComboBox<T> fxNode) {
			fxNode.setListener(property.getListenerProxy());
		}




		@Override
		public MutationResult update(final OnValueChangedEventProperty<T> property, final SuiNode node, final ExtendedComboBox<T> fxNode) {
			fxNode.setListener(property.getListenerProxy());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final OnValueChangedEventProperty<T> property, final SuiNode node, final ExtendedComboBox<T> fxNode) {
			fxNode.setListener(null);
			return MutationResult.MUTATED;
		}

	}






	public static class SliderUpdatingBuilder implements PropFxNodeUpdatingBuilder<OnValueChangedEventProperty<Number>, ExtendedSlider> {


		@Override
		public void build(final SuiNode node, final OnValueChangedEventProperty<Number> property, final ExtendedSlider fxNode) {
			fxNode.setListener(property.getListenerProxy());
		}




		@Override
		public MutationResult update(final OnValueChangedEventProperty<Number> property, final SuiNode node, final ExtendedSlider fxNode) {
			fxNode.setListener(property.getListenerProxy());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final OnValueChangedEventProperty<Number> property, final SuiNode node, final ExtendedSlider fxNode) {
			fxNode.setListener(null);
			return MutationResult.MUTATED;
		}

	}






	public static class LabeledSliderUpdatingBuilder implements PropFxNodeUpdatingBuilder<OnValueChangedEventProperty<Number>, Pane> {


		@Override
		public void build(final SuiNode node, final OnValueChangedEventProperty<Number> property, final Pane fxNode) {
			final ExtendedSlider slider = SuiLabeledSlider.getSlider(fxNode);
			if (slider != null) {
				slider.setListener(property.getListenerProxy());
			} else {
				log.warn("Slider not found. Can not set listener");
			}
		}




		@Override
		public MutationResult update(final OnValueChangedEventProperty<Number> property, final SuiNode node, final Pane fxNode) {
			final ExtendedSlider slider = SuiLabeledSlider.getSlider(fxNode);
			if (slider != null) {
				slider.setListener(property.getListenerProxy());
			} else {
				log.warn("Slider not found. Can not set listener");
			}
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final OnValueChangedEventProperty<Number> property, final SuiNode node, final Pane fxNode) {
			final ExtendedSlider slider = SuiLabeledSlider.getSlider(fxNode);
			if (slider != null) {
				slider.setListener(null);
			} else {
				log.warn("Slider not found. Can not set listener");
			}
			return MutationResult.MUTATED;
		}

	}






	public static class SpinnerUpdatingBuilder<T> implements PropFxNodeUpdatingBuilder<OnValueChangedEventProperty<T>, Spinner<T>> {


		@Override
		public void build(final SuiNode node,
						  final OnValueChangedEventProperty<T> property,
						  final Spinner<T> fxNode) {
			property.getChangeListenerProxy().addTo(fxNode.valueProperty());
		}




		@Override
		public MutationResult update(final OnValueChangedEventProperty<T> property,
									 final SuiNode node,
									 final Spinner<T> fxNode) {
			node.getPropertyStore().getSafe(OnValueChangedEventProperty.class)
					.map(prop -> (OnValueChangedEventProperty<T>) prop)
					.map(OnValueChangedEventProperty::getChangeListenerProxy)
					.ifPresent(proxy -> proxy.removeFrom(fxNode.valueProperty()));
			property.getChangeListenerProxy().addTo(fxNode.valueProperty());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final OnValueChangedEventProperty<T> property,
									 final SuiNode node,
									 final Spinner<T> fxNode) {
			property.getChangeListenerProxy().removeFrom(fxNode.valueProperty());
			return MutationResult.MUTATED;
		}

	}


}
