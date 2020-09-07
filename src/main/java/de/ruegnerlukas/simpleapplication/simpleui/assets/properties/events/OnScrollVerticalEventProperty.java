package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.assets.events.ScrollEventData;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiBaseNode;
import javafx.scene.control.ScrollPane;
import lombok.Getter;

public class OnScrollVerticalEventProperty extends AbstractEventListenerProperty<ScrollEventData> {



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
	public OnScrollVerticalEventProperty(final SUIEventListener<ScrollEventData> listener) {
		super(OnScrollVerticalEventProperty.class);
		this.listener = listener;
		this.changeListenerProxy = new ChangeListenerProxy<>((prev, next) -> {
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
	}




	public static class ScrollPaneUpdatingBuilder implements PropFxNodeUpdatingBuilder<OnScrollVerticalEventProperty, ScrollPane> {


		@Override
		public void build(final SuiBaseNode node,
						  final OnScrollVerticalEventProperty property,
						  final ScrollPane fxNode) {
			property.getChangeListenerProxy().addTo(fxNode.vvalueProperty());
		}




		@Override
		public MutationResult update(final OnScrollVerticalEventProperty property,
									 final SuiBaseNode node,
									 final ScrollPane fxNode) {
			node.getPropertyStore().getSafe(OnScrollVerticalEventProperty.class)
					.map(OnScrollVerticalEventProperty::getChangeListenerProxy)
					.ifPresent(proxy -> proxy.removeFrom(fxNode.vvalueProperty()));
			property.getChangeListenerProxy().addTo(fxNode.vvalueProperty());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final OnScrollVerticalEventProperty property,
									 final SuiBaseNode node,
									 final ScrollPane fxNode) {
			property.getChangeListenerProxy().removeFrom(fxNode.vvalueProperty());
			return MutationResult.MUTATED;
		}

	}

}
