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

public class OnScrollVerticalEventProperty extends AbstractEventListenerProperty<ScrollEventData> {


	/**
	 * The listener for events with {@link ScrollEventData}.
	 */
	@Getter
	private final SUIEventListener<ScrollEventData> listener;




	/**
	 * @param listener the listener for events with {@link ScrollEventData}.
	 */
	public OnScrollVerticalEventProperty(final SUIEventListener<ScrollEventData> listener) {
		super(OnScrollVerticalEventProperty.class);
		this.listener = listener;
	}




	public static class ScrollPaneUpdatingBuilder implements PropFxNodeUpdatingBuilder<OnScrollVerticalEventProperty, ScrollPane> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers, final SUINode node, final OnScrollVerticalEventProperty property,
						  final ScrollPane fxNode) {
			fxNode.vvalueProperty().addListener((value, prev, next) -> {
				if (prev != null && next != null) {
					property.getListener().onEvent(new SUIEvent<>(
							"scroll.vertical",
							ScrollEventData.builder()
									.yPos(next.doubleValue())
									.prevYPos(prev.doubleValue())
									.dy(next.doubleValue() - prev.doubleValue())
									.xPos(0)
									.prevXPos(0)
									.dx(0)
									.build()
					));
				}
			});
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers, final OnScrollVerticalEventProperty property,
									 final SUINode node, final ScrollPane fxNode) {
			return MutationResult.REQUIRES_REBUILD;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers, final OnScrollVerticalEventProperty property,
									 final SUINode node, final ScrollPane fxNode) {
			return MutationResult.REQUIRES_REBUILD;
		}

	}

}