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

public class OnHoverStoppedEventProperty extends AbstractEventListenerProperty<HoverEventData> {


	/**
	 * The listener for events with {@link HoverEventData}.
	 */
	@Getter
	private final SUIEventListener<HoverEventData> listener;




	/**
	 * @param listener the listener for events with {@link HoverEventData}.
	 */
	public OnHoverStoppedEventProperty(final SUIEventListener<HoverEventData> listener) {
		super(OnHoverStoppedEventProperty.class);
		this.listener = listener;
	}




	public static class UpdatingBuilder implements PropFxNodeUpdatingBuilder<OnHoverStoppedEventProperty, Node> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers, final SUINode node, final OnHoverStoppedEventProperty property,
						  final Node fxNode) {
			fxNode.hoverProperty().addListener((value, prev, next) -> {
				if (!next) {
					property.getListener().onEvent(new SUIEvent<>(
							"hover.stopped",
							HoverEventData.builder()
									.hover(false)
									.build()
					));
				}
			});
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers, final OnHoverStoppedEventProperty property,
									 final SUINode node, final Node fxNode) {
			return MutationResult.REQUIRES_REBUILD;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers, final OnHoverStoppedEventProperty property,
									 final SUINode node, final Node fxNode) {
			return MutationResult.REQUIRES_REBUILD;
		}

	}

}