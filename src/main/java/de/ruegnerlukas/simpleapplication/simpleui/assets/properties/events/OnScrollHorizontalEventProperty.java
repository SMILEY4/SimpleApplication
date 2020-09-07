package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.core.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.assets.events.ScrollEventData;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import javafx.scene.control.ScrollPane;
import lombok.Getter;

public class OnScrollHorizontalEventProperty extends AbstractEventListenerProperty<ScrollEventData> {


	/**
	 * The listener for events with {@link ScrollEventData}.
	 */
	@Getter
	private final SUIEventListener<ScrollEventData> listener;

	/**
	 * The proxy for the actual change listener.
	 */
	@Getter
	private final ChangeListenerProxy<Number> changeListenerProxy;




	/**
	 * @param listener the listener for events with {@link ScrollEventData}.
	 */
	public OnScrollHorizontalEventProperty(final SUIEventListener<ScrollEventData> listener) {
		super(OnScrollHorizontalEventProperty.class);
		this.listener = listener;
		this.changeListenerProxy = new ChangeListenerProxy<>((prev, next) -> {
			if (prev != null && next != null) {
				listener.onEvent(
						ScrollEventData.builder()
								.yPos(0)
								.prevYPos(0)
								.dy(0)
								.xPos(next.doubleValue())
								.prevXPos(prev.doubleValue())
								.dx(next.doubleValue() - prev.doubleValue())
								.build()
				);
			}
		});
	}




	public static class ScrollPaneUpdatingBuilder implements PropFxNodeUpdatingBuilder<OnScrollHorizontalEventProperty, ScrollPane> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers,
						  final SuiNode node,
						  final OnScrollHorizontalEventProperty property,
						  final ScrollPane fxNode) {
			property.getChangeListenerProxy().addTo(fxNode.hvalueProperty());
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers,
									 final OnScrollHorizontalEventProperty property,
									 final SuiNode node,
									 final ScrollPane fxNode) {
			node.getPropertySafe(OnScrollHorizontalEventProperty.class)
					.map(OnScrollHorizontalEventProperty::getChangeListenerProxy)
					.ifPresent(proxy -> proxy.removeFrom(fxNode.hvalueProperty()));
			property.getChangeListenerProxy().addTo(fxNode.hvalueProperty());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers,
									 final OnScrollHorizontalEventProperty property,
									 final SuiNode node,
									 final ScrollPane fxNode) {
			property.getChangeListenerProxy().removeFrom(fxNode.hvalueProperty());
			return MutationResult.MUTATED;
		}

	}

}
