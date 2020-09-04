package de.ruegnerlukas.simpleapplication.simpleui.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.events.HoverEventData;
import de.ruegnerlukas.simpleapplication.simpleui.events.SUIEventListener;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;
import javafx.scene.Node;
import lombok.Getter;

public class OnHoverChangedEventProperty extends AbstractObservableListenerProperty<HoverEventData, Boolean> {

	/**
	 * The identifying string of the event.
	 */
	public static final String EVENT_ID = "hover.changed";

	/**
	 * The listener for events with {@link HoverEventData}.
	 */
	@Getter
	private final SUIEventListener<HoverEventData> listener;




	/**
	 * @param listener the listener for events with {@link HoverEventData}.
	 */
	public OnHoverChangedEventProperty(final SUIEventListener<HoverEventData> listener) {
		super(OnHoverChangedEventProperty.class, (value, prev, next) -> {
			listener.onEvent(
					HoverEventData.builder()
							.hover(next)
							.build()
			);
		});
		this.listener = listener;
	}




	public static class UpdatingBuilder implements PropFxNodeUpdatingBuilder<OnHoverChangedEventProperty, Node> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers,
						  final SuiNode node,
						  final OnHoverChangedEventProperty property,
						  final Node fxNode) {
			property.addChangeListenerTo(fxNode.hoverProperty());
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers,
									 final OnHoverChangedEventProperty property,
									 final SuiNode node,
									 final Node fxNode) {
			node.getPropertySafe(OnHoverChangedEventProperty.class).ifPresent(prop -> {
				prop.removeChangeListenerFrom(fxNode.hoverProperty());
			});
			property.addChangeListenerTo(fxNode.hoverProperty());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers,
									 final OnHoverChangedEventProperty property,
									 final SuiNode node,
									 final Node fxNode) {
			property.removeChangeListenerFrom(fxNode.hoverProperty());
			return MutationResult.MUTATED;
		}

	}

}
