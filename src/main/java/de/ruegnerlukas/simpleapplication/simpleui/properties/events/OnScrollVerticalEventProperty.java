package de.ruegnerlukas.simpleapplication.simpleui.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.events.SUIEventListener;
import de.ruegnerlukas.simpleapplication.simpleui.events.ScrollEventData;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;
import javafx.scene.control.ScrollPane;
import lombok.Getter;

public class OnScrollVerticalEventProperty extends AbstractObservableListenerProperty<ScrollEventData, Number> {


	/**
	 * The identifying string of the event.
	 */
	public static final String EVENT_ID = "scroll.vertical";

	/**
	 * The listener for events with {@link ScrollEventData}.
	 */
	@Getter
	private final SUIEventListener<ScrollEventData> listener;




	/**
	 * @param listener the listener for events with {@link ScrollEventData}.
	 */
	public OnScrollVerticalEventProperty(final SUIEventListener<ScrollEventData> listener) {
		super(OnScrollVerticalEventProperty.class, (value, prev, next) -> {
			if (prev != null && next != null) {
				listener.onEvent(
						ScrollEventData.builder()
								.yPos(next.doubleValue())
								.prevYPos(prev.doubleValue())
								.dy(next.doubleValue() - prev.doubleValue())
								.xPos(0)
								.prevXPos(0)
								.dx(0)
								.build()
				);
			}
		});
		this.listener = listener;
	}




	public static class ScrollPaneUpdatingBuilder implements PropFxNodeUpdatingBuilder<OnScrollVerticalEventProperty, ScrollPane> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers,
						  final SuiNode node,
						  final OnScrollVerticalEventProperty property,
						  final ScrollPane fxNode) {
			property.addChangeListenerTo(fxNode.vvalueProperty());
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers,
									 final OnScrollVerticalEventProperty property,
									 final SuiNode node,
									 final ScrollPane fxNode) {
			node.getPropertySafe(OnScrollVerticalEventProperty.class).ifPresent(prop -> {
				prop.removeChangeListenerFrom(fxNode.vvalueProperty());
			});
			property.addChangeListenerTo(fxNode.vvalueProperty());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers,
									 final OnScrollVerticalEventProperty property,
									 final SuiNode node,
									 final ScrollPane fxNode) {
			property.removeChangeListenerFrom(fxNode.vvalueProperty());
			return MutationResult.MUTATED;
		}

	}

}
