package de.ruegnerlukas.simpleapplication.simpleui.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.elements.basenode.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.events.CheckedEventData;
import de.ruegnerlukas.simpleapplication.simpleui.events.SUIEventListener;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;
import javafx.scene.control.CheckBox;
import lombok.Getter;

public class OnUncheckedEventProperty extends AbstractEventListenerProperty<CheckedEventData> {


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
	public OnUncheckedEventProperty(final SUIEventListener<CheckedEventData> listener) {
		super(OnUncheckedEventProperty.class);
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
		public void build(final MasterNodeHandlers nodeHandlers,
						  final SuiNode node,
						  final OnUncheckedEventProperty property,
						  final CheckBox fxNode) {
			property.getChangeListenerProxy().addTo(fxNode.selectedProperty());
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers,
									 final OnUncheckedEventProperty property,
									 final SuiNode node,
									 final CheckBox fxNode) {
			node.getPropertySafe(OnUncheckedEventProperty.class)
					.map(OnUncheckedEventProperty::getChangeListenerProxy)
					.ifPresent(proxy -> proxy.removeFrom(fxNode.selectedProperty()));
			property.getChangeListenerProxy().addTo(fxNode.selectedProperty());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers,
									 final OnUncheckedEventProperty property,
									 final SuiNode node,
									 final CheckBox fxNode) {
			property.getChangeListenerProxy().removeFrom(fxNode.selectedProperty());
			return MutationResult.MUTATED;
		}

	}


}
