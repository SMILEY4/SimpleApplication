package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.jfxelements.ExtendedCheckbox;
import de.ruegnerlukas.simpleapplication.simpleui.assets.events.CheckedEventData;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
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
	 * @param propertyId see {@link de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty#getPropertyId()}.
	 * @param listener   the listener for events with {@link CheckedEventData}.
	 */
	public OnCheckedEventProperty(final String propertyId, final SuiEventListener<CheckedEventData> listener) {
		super(OnCheckedEventProperty.class, propertyId);
		this.listener = listener;
		this.listenerProxy = checked -> listener.onEvent(
				CheckedEventData.builder()
						.checked(checked)
						.build()
		);
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


}
