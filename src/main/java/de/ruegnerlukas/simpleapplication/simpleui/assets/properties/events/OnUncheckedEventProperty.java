package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.assets.events.CheckedEventData;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import javafx.scene.control.CheckBox;
import lombok.Getter;

public class OnUncheckedEventProperty extends AbstractEventListenerProperty<CheckedEventData> {


	/**
	 * The listener for events with {@link CheckedEventData}.
	 */
	@Getter
	private final SuiEventListener<CheckedEventData> listener;

	/**
	 * The proxy for the actual change listener.
	 */
	@Getter
	private final ChangeListenerProxy<Boolean> changeListenerProxy;




	/**
	 * @param propertyId see {@link de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty#getPropertyId()}.
	 * @param listener   the listener for events with {@link CheckedEventData}.
	 */
	public OnUncheckedEventProperty(final String propertyId, final SuiEventListener<CheckedEventData> listener) {
		super(OnUncheckedEventProperty.class, propertyId);
		this.listener = listener;
		this.changeListenerProxy = new ChangeListenerProxy<>((prev, next) -> {
			if (next != null && !next) {
				listener.onEvent(
						CheckedEventData.builder()
								.checked(false)
								.build()
				);
			}
		});
	}




	public static class CheckboxUpdatingBuilder implements PropFxNodeUpdatingBuilder<OnUncheckedEventProperty, CheckBox> {


		@Override
		public void build(final SuiNode node,
						  final OnUncheckedEventProperty property,
						  final CheckBox fxNode) {
			property.getChangeListenerProxy().addTo(fxNode.selectedProperty());
		}




		@Override
		public MutationResult update(final OnUncheckedEventProperty property,
									 final SuiNode node,
									 final CheckBox fxNode) {
			node.getPropertyStore().getSafe(OnUncheckedEventProperty.class)
					.map(OnUncheckedEventProperty::getChangeListenerProxy)
					.ifPresent(proxy -> proxy.removeFrom(fxNode.selectedProperty()));
			property.getChangeListenerProxy().addTo(fxNode.selectedProperty());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final OnUncheckedEventProperty property,
									 final SuiNode node,
									 final CheckBox fxNode) {
			property.getChangeListenerProxy().removeFrom(fxNode.selectedProperty());
			return MutationResult.MUTATED;
		}

	}


}
