package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.assets.events.FocusEventData;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.events.SuiEmittingEventListener;
import de.ruegnerlukas.simpleapplication.simpleui.core.events.SuiEventListener;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.builders.FactoryExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.tags.Tags;
import javafx.scene.Node;
import lombok.Getter;

public class OnFocusReceivedEventProperty extends AbstractEventListenerProperty<FocusEventData> {


	/**
	 * The listener for events with {@link FocusEventData}.
	 */
	@Getter
	private final SuiEventListener<FocusEventData> listener;

	/**
	 * The proxy for the actual change listener.
	 */
	@Getter
	private final ChangeListenerProxy<Boolean> changeListenerProxy;




	/**
	 * @param propertyId see {@link de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty#getPropertyId()}.
	 * @param listener   the listener for events with {@link FocusEventData}.
	 */
	public OnFocusReceivedEventProperty(final String propertyId, final SuiEventListener<FocusEventData> listener) {
		super(OnFocusReceivedEventProperty.class, propertyId);
		Validations.INPUT.notNull(listener).exception("The listener may not be null");
		this.listener = listener;
		this.changeListenerProxy = new ChangeListenerProxy<>((prev, next) -> {
			if (next) {
				listener.onEvent(
						FocusEventData.builder()
								.focused(true)
								.build()
				);
			}
		});
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param propertyId see {@link de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty#getPropertyId()}.
		 * @param listener   the listener for events with {@link FocusEventData}.
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T eventFocusReceived(final String propertyId, final SuiEventListener<FocusEventData> listener) {
			getBuilderProperties().add(new OnFocusReceivedEventProperty(propertyId, listener));
			return (T) this;
		}

		/**
		 * @param tags the tags to attach to the emitted event
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T emitEventFocusReceived(final Tags tags) {
			getBuilderProperties().add(new OnFocusReceivedEventProperty(".", new SuiEmittingEventListener<>(tags)));
			return (T) this;
		}


	}






	public static class UpdatingBuilder implements PropFxNodeUpdatingBuilder<OnFocusReceivedEventProperty, Node> {


		@Override
		public void build(final SuiNode node,
						  final OnFocusReceivedEventProperty property,
						  final Node fxNode) {
			property.getChangeListenerProxy().addTo(fxNode.focusedProperty());
		}




		@Override
		public MutationResult update(final OnFocusReceivedEventProperty property,
									 final SuiNode node,
									 final Node fxNode) {
			node.getPropertyStore().getSafe(OnFocusReceivedEventProperty.class)
					.map(OnFocusReceivedEventProperty::getChangeListenerProxy)
					.ifPresent(proxy -> proxy.removeFrom(fxNode.focusedProperty()));
			property.getChangeListenerProxy().addTo(fxNode.focusedProperty());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final OnFocusReceivedEventProperty property,
									 final SuiNode node,
									 final Node fxNode) {
			property.getChangeListenerProxy().removeFrom(fxNode.focusedProperty());
			return MutationResult.MUTATED;
		}

	}

}
