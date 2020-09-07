package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.assets.events.CheckedEventData;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiBaseNode;
import javafx.scene.control.CheckBox;
import lombok.Getter;

public class OnCheckedEventProperty extends AbstractEventListenerProperty<CheckedEventData> {


	/**
	 * The listener for events with {@link CheckedEventData}.
	 */
	@Getter
	private final SUIEventListener<CheckedEventData> listener;

	/**
	 * The proxy for the actual change listener.
	 */
	@Getter
	private final ChangeListenerProxy<Boolean> changeListenerProxy;




	/**
	 * @param listener the listener for events with {@link CheckedEventData}.
	 */
	public OnCheckedEventProperty(final SUIEventListener<CheckedEventData> listener) {
		super(OnCheckedEventProperty.class);
		this.listener = listener;
		this.changeListenerProxy = new ChangeListenerProxy<>((prev, next) -> {
			if (next != null && next) {
				listener.onEvent(
						CheckedEventData.builder()
								.checked(true)
								.build()
				);
			}
		});
	}




	public static class CheckboxUpdatingBuilder implements PropFxNodeUpdatingBuilder<OnCheckedEventProperty, CheckBox> {


		@Override
		public void build(final SuiBaseNode node,
						  final OnCheckedEventProperty property,
						  final CheckBox fxNode) {
			property.getChangeListenerProxy().addTo(fxNode.focusedProperty());
		}




		@Override
		public MutationResult update(final OnCheckedEventProperty property,
									 final SuiBaseNode node,
									 final CheckBox fxNode) {
			node.getPropertyStore().getSafe(OnCheckedEventProperty.class)
					.map(OnCheckedEventProperty::getChangeListenerProxy)
					.ifPresent(proxy -> proxy.removeFrom(fxNode.focusedProperty()));
			property.getChangeListenerProxy().addTo(fxNode.focusedProperty());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final OnCheckedEventProperty property,
									 final SuiBaseNode node,
									 final CheckBox fxNode) {
			property.getChangeListenerProxy().removeFrom(fxNode.focusedProperty());
			return MutationResult.MUTATED;
		}

	}


}
