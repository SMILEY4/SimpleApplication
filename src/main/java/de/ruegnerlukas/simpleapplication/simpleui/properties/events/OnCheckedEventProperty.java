package de.ruegnerlukas.simpleapplication.simpleui.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.elements.basenode.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.events.CheckedEventData;
import de.ruegnerlukas.simpleapplication.simpleui.events.SUIEventListener;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;
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
		public void build(final MasterNodeHandlers nodeHandlers,
						  final SuiNode node,
						  final OnCheckedEventProperty property,
						  final CheckBox fxNode) {
			property.getChangeListenerProxy().addTo(fxNode.focusedProperty());
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers,
									 final OnCheckedEventProperty property,
									 final SuiNode node,
									 final CheckBox fxNode) {
			node.getPropertySafe(OnCheckedEventProperty.class)
					.map(OnCheckedEventProperty::getChangeListenerProxy)
					.ifPresent(proxy -> proxy.removeFrom(fxNode.focusedProperty()));
			property.getChangeListenerProxy().addTo(fxNode.focusedProperty());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers,
									 final OnCheckedEventProperty property,
									 final SuiNode node,
									 final CheckBox fxNode) {
			property.getChangeListenerProxy().removeFrom(fxNode.focusedProperty());
			return MutationResult.MUTATED;
		}

	}


}
