package de.ruegnerlukas.simpleapplication.simpleui.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.elements.basenode.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.events.HoverEventData;
import de.ruegnerlukas.simpleapplication.simpleui.events.SUIEventListener;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;
import javafx.scene.Node;
import lombok.Getter;

public class OnHoverChangedEventProperty extends AbstractEventListenerProperty<HoverEventData> {

	/**
	 * The listener for events with {@link HoverEventData}.
	 */
	@Getter
	private final SUIEventListener<HoverEventData> listener;

	/**
	 * The proxy for the actual change listener.
	 */
	@Getter
	private final ChangeListenerProxy<Boolean> changeListenerProxy;



	/**
	 * @param listener the listener for events with {@link HoverEventData}.
	 */
	public OnHoverChangedEventProperty(final SUIEventListener<HoverEventData> listener) {
		super(OnHoverChangedEventProperty.class);
		this.listener = listener;
		this.changeListenerProxy = new ChangeListenerProxy<>((prev, next) -> listener.onEvent(
				HoverEventData.builder()
						.hover(next)
						.build()
		));
	}




	public static class UpdatingBuilder implements PropFxNodeUpdatingBuilder<OnHoverChangedEventProperty, Node> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers,
						  final SuiNode node,
						  final OnHoverChangedEventProperty property,
						  final Node fxNode) {
			property.getChangeListenerProxy().addTo(fxNode.focusedProperty());
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers,
									 final OnHoverChangedEventProperty property,
									 final SuiNode node,
									 final Node fxNode) {
			node.getPropertySafe(OnHoverChangedEventProperty.class)
					.map(OnHoverChangedEventProperty::getChangeListenerProxy)
					.ifPresent(proxy -> proxy.removeFrom(fxNode.focusedProperty()));
			property.getChangeListenerProxy().addTo(fxNode.focusedProperty());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers,
									 final OnHoverChangedEventProperty property,
									 final SuiNode node,
									 final Node fxNode) {
			property.getChangeListenerProxy().removeFrom(fxNode.focusedProperty());
			return MutationResult.MUTATED;
		}

	}

}
