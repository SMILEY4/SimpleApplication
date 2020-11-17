package de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.events;

import de.ruegnerlukas.simpleapplication.common.tags.Tags;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.jfxelements.ExtendedCheckbox;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.jfxelements.ExtendedRadioButton;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.jfxelements.ExtendedToggleButton;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.events.CheckedEventData;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.events.SuiEmittingEventListener;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.events.SuiEventListener;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.builders.FactoryExtension;
import lombok.Getter;

import java.util.function.Consumer;

public class OnCheckedEventProperty extends AbstractEventListenerProperty<CheckedEventData> {


	/**
	 * The listener for events with {@link CheckedEventData}.
	 */
	@Getter
	private final SuiEventListener<CheckedEventData> listener;

	/**
	 * The proxy for the actual change listener.
	 */
	@Getter
	private final Consumer<Boolean> listenerProxy;




	/**
	 * @param propertyId see {@link de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiProperty#getPropertyId()}.
	 * @param listener   the listener for events with {@link CheckedEventData}.
	 */
	public OnCheckedEventProperty(final String propertyId, final SuiEventListener<CheckedEventData> listener) {
		super(OnCheckedEventProperty.class, propertyId);
		Validations.INPUT.notNull(listener).exception("The listener may not be null");
		this.listener = listener;
		this.listenerProxy = checked -> listener.onEvent(
				CheckedEventData.builder()
						.checked(checked)
						.build()
		);
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param propertyId see {@link de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiProperty#getPropertyId()}.
		 * @param listener   the listener for events with {@link CheckedEventData}.
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T eventChecked(final String propertyId, final SuiEventListener<CheckedEventData> listener) {
			getBuilderProperties().add(new OnCheckedEventProperty(propertyId, listener));
			return (T) this;
		}

		/**
		 * @param tags the tags to attach to the emitted event
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T emitEventChecked(final Tags tags) {
			getBuilderProperties().add(new OnCheckedEventProperty(".", new SuiEmittingEventListener<>(tags)));
			return (T) this;
		}

	}






	public static class CheckboxUpdatingBuilder implements PropFxNodeUpdatingBuilder<OnCheckedEventProperty, ExtendedCheckbox> {


		@Override
		public void build(final SuiNode node, final OnCheckedEventProperty property, final ExtendedCheckbox fxNode) {
			fxNode.setListener(property.getListenerProxy());
		}




		@Override
		public MutationResult update(final OnCheckedEventProperty property, final SuiNode node, final ExtendedCheckbox fxNode) {
			fxNode.setListener(property.getListenerProxy());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final OnCheckedEventProperty property, final SuiNode node, final ExtendedCheckbox fxNode) {
			fxNode.setListener(null);
			return MutationResult.MUTATED;
		}

	}






	public static class RadioButtonUpdatingBuilder implements PropFxNodeUpdatingBuilder<OnCheckedEventProperty, ExtendedRadioButton> {


		@Override
		public void build(final SuiNode node, final OnCheckedEventProperty property, final ExtendedRadioButton fxNode) {
			fxNode.setListener(property.getListenerProxy());
		}




		@Override
		public MutationResult update(final OnCheckedEventProperty property, final SuiNode node, final ExtendedRadioButton fxNode) {
			fxNode.setListener(property.getListenerProxy());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final OnCheckedEventProperty property, final SuiNode node, final ExtendedRadioButton fxNode) {
			fxNode.setListener(null);
			return MutationResult.MUTATED;
		}

	}






	public static class ToggleButtonUpdatingBuilder implements PropFxNodeUpdatingBuilder<OnCheckedEventProperty, ExtendedToggleButton> {


		@Override
		public void build(final SuiNode node, final OnCheckedEventProperty property, final ExtendedToggleButton fxNode) {
			fxNode.setListener(property.getListenerProxy());
		}




		@Override
		public MutationResult update(final OnCheckedEventProperty property, final SuiNode node, final ExtendedToggleButton fxNode) {
			fxNode.setListener(property.getListenerProxy());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final OnCheckedEventProperty property, final SuiNode node, final ExtendedToggleButton fxNode) {
			fxNode.setListener(null);
			return MutationResult.MUTATED;
		}

	}


}
