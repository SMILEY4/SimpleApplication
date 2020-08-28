package de.ruegnerlukas.simpleapplication.simpleui.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.events.SUIEvent;
import de.ruegnerlukas.simpleapplication.simpleui.events.SUIEventListener;
import de.ruegnerlukas.simpleapplication.simpleui.events.ScrollEventData;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;
import javafx.scene.control.ScrollPane;
import lombok.Getter;

public class OnScrollHorizontalEventProperty extends AbstractEventListenerProperty<ScrollEventData> {


	/**
	 * The listener for events with {@link ScrollEventData}.
	 */
	@Getter
	private final SUIEventListener<ScrollEventData> listener;




	/**
	 * @param listener the listener for events with {@link ScrollEventData}.
	 */
	public OnScrollHorizontalEventProperty(final SUIEventListener<ScrollEventData> listener) {
		super(OnScrollHorizontalEventProperty.class);
		this.listener = listener;
	}




	public static class ScrollPaneUpdatingBuilder implements PropFxNodeUpdatingBuilder<OnScrollHorizontalEventProperty, ScrollPane> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers, final SUINode node, final OnScrollHorizontalEventProperty property,
						  final ScrollPane fxNode) {
			fxNode.hvalueProperty().addListener((value, prev, next) -> {
				if (prev != null && next != null) {
					property.getListener().onEvent(new SUIEvent<>(
							"scroll.horizontal",
							ScrollEventData.builder()
									.yPos(0)
									.prevYPos(0)
									.dy(0)
									.xPos(next.doubleValue())
									.prevXPos(prev.doubleValue())
									.dx(next.doubleValue() - prev.doubleValue())
									.build()
					));
				}
			});
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers, final OnScrollHorizontalEventProperty property,
									 final SUINode node, final ScrollPane fxNode) {
			return MutationResult.REQUIRES_REBUILD;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers, final OnScrollHorizontalEventProperty property,
									 final SUINode node, final ScrollPane fxNode) {
			return MutationResult.REQUIRES_REBUILD;
		}

	}

}