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

public class OnHoverChangedEventProperty extends AbstractEventListenerProperty<HoverEventData> {


	/**
	 * The listener for events with {@link HoverEventData}.
	 */
	@Getter
	private final SUIEventListener<HoverEventData> listener;




	/**
	 * @param listener the listener for events with {@link HoverEventData}.
	 */
	public OnHoverChangedEventProperty(final SUIEventListener<HoverEventData> listener) {
		super(OnHoverChangedEventProperty.class);
		this.listener = listener;
	}




	public static class UpdatingBuilder implements PropFxNodeUpdatingBuilder<OnHoverChangedEventProperty, Node> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers, final SUINode node, final OnHoverChangedEventProperty property,
						  final Node fxNode) {
			fxNode.hoverProperty().addListener((value, prev, next) -> property.getListener().onEvent(new SUIEvent<>(
					"hover.changed",
					HoverEventData.builder()
							.hover(next)
							.build()
			)));
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers, final OnHoverChangedEventProperty property,
									 final SUINode node, final Node fxNode) {
			return MutationResult.REQUIRES_REBUILD;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers, final OnHoverChangedEventProperty property,
									 final SUINode node, final Node fxNode) {
			return MutationResult.REQUIRES_REBUILD;
		}

	}

}