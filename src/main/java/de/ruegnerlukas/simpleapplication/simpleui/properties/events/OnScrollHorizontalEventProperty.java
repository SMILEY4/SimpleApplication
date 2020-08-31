package de.ruegnerlukas.simpleapplication.simpleui.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.events.SuiEvent;
import de.ruegnerlukas.simpleapplication.simpleui.events.SUIEventListener;
import de.ruegnerlukas.simpleapplication.simpleui.events.ScrollEventData;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;
import javafx.scene.control.ScrollPane;
import lombok.Getter;

public class OnScrollHorizontalEventProperty extends AbstractObservableListenerProperty<ScrollEventData, Number> {


	/**
	 * The identifying string of the event.
	 */
	public static final String EVENT_ID = "scroll.horizontal";

	/**
	 * The listener for events with {@link ScrollEventData}.
	 */
	@Getter
	private final SUIEventListener<ScrollEventData> listener;




	/**
	 * @param listener the listener for events with {@link ScrollEventData}.
	 */
	public OnScrollHorizontalEventProperty(final SUIEventListener<ScrollEventData> listener) {
		super(OnScrollHorizontalEventProperty.class, (value, prev, next) -> {
			if (prev != null && next != null) {
				listener.onEvent(new SuiEvent<>(
						EVENT_ID,
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
		this.listener = listener;
	}




	public static class ScrollPaneUpdatingBuilder implements PropFxNodeUpdatingBuilder<OnScrollHorizontalEventProperty, ScrollPane> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers, final SuiNode node, final OnScrollHorizontalEventProperty property,
						  final ScrollPane fxNode) {
			fxNode.hvalueProperty().addListener(property.getChangeListener());
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers, final OnScrollHorizontalEventProperty property,
									 final SuiNode node, final ScrollPane fxNode) {
			node.getPropertySafe(OnScrollHorizontalEventProperty.class).ifPresent(prop -> {
				fxNode.hvalueProperty().removeListener(prop.getChangeListener());
			});
			fxNode.hvalueProperty().addListener(property.getChangeListener());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers, final OnScrollHorizontalEventProperty property,
									 final SuiNode node, final ScrollPane fxNode) {
			fxNode.hvalueProperty().removeListener(property.getChangeListener());
			return MutationResult.MUTATED;
		}

	}

}
