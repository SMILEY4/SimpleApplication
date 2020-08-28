package de.ruegnerlukas.simpleapplication.simpleui.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.events.HoverEventData;
import de.ruegnerlukas.simpleapplication.simpleui.events.SUIEvent;
import de.ruegnerlukas.simpleapplication.simpleui.events.SUIEventListener;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;
import javafx.scene.Node;
import lombok.Getter;

public class OnHoverStartedEventProperty extends AbstractObservableListenerProperty<HoverEventData, Boolean> {


	/**
	 * The identifying string of the event.
	 */
	public static final String EVENT_ID = "hover.started";

	/**
	 * The listener for events with {@link HoverEventData}.
	 */
	@Getter
	private final SUIEventListener<HoverEventData> listener;




	/**
	 * @param listener the listener for events with {@link HoverEventData}.
	 */
	public OnHoverStartedEventProperty(final SUIEventListener<HoverEventData> listener) {
		super(OnHoverStartedEventProperty.class, (value, prev, next) -> {
			if (next) {
				listener.onEvent(new SUIEvent<>(
						EVENT_ID,
						HoverEventData.builder()
								.hover(true)
								.build()
				));
			}
		});
		this.listener = listener;
	}




	public static class UpdatingBuilder implements PropFxNodeUpdatingBuilder<OnHoverStartedEventProperty, Node> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers, final SUINode node, final OnHoverStartedEventProperty property,
						  final Node fxNode) {
			fxNode.hoverProperty().addListener(property.getChangeListener());
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers, final OnHoverStartedEventProperty property,
									 final SUINode node, final Node fxNode) {
			node.getPropertySafe(OnHoverStartedEventProperty.class).ifPresent(prop -> {
				fxNode.hoverProperty().removeListener(prop.getChangeListener());
			});
			fxNode.hoverProperty().addListener(property.getChangeListener());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers, final OnHoverStartedEventProperty property,
									 final SUINode node, final Node fxNode) {
			fxNode.hoverProperty().removeListener(property.getChangeListener());
			return MutationResult.MUTATED;
		}

	}

}
