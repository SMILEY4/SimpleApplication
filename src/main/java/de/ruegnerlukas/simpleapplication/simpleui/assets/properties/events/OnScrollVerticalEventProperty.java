package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.assets.events.ScrollEventData;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.EventProperties;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.FactoryExtension;
import javafx.scene.control.ScrollPane;
import lombok.Getter;

public class OnScrollVerticalEventProperty extends AbstractEventListenerProperty<ScrollEventData> {


	/**
	 * The listener for events with {@link ScrollEventData}.
	 */
	@Getter
	private final SuiEventListener<ScrollEventData> listener;

	/**
	 * The proxy for the actual change listener.
	 */
	@Getter
	private final ChangeListenerProxy<Number> changeListenerProxy;




	/**
	 * @param propertyId see {@link de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty#getPropertyId()}.
	 * @param listener   the listener for events with {@link ScrollEventData}.
	 */
	public OnScrollVerticalEventProperty(final String propertyId, final SuiEventListener<ScrollEventData> listener) {
		super(OnScrollVerticalEventProperty.class, propertyId);
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




	@SuppressWarnings ("unchecked")
	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		default T eventScrollVertical(final SuiEventListener<ScrollEventData> listener) {
			getFactoryInternalProperties().add(EventProperties.eventScrollVertical(listener));
			return (T) this;
		}

		default T eventScrollVertical(final String propertyId, final SuiEventListener<ScrollEventData> listener) {
			getFactoryInternalProperties().add(EventProperties.eventScrollVertical(propertyId, listener));
			return (T) this;
		}

	}






	public static class ScrollPaneUpdatingBuilder implements PropFxNodeUpdatingBuilder<OnScrollVerticalEventProperty, ScrollPane> {


		@Override
		public void build(final SuiNode node,
						  final OnScrollVerticalEventProperty property,
						  final ScrollPane fxNode) {
			property.getChangeListenerProxy().addTo(fxNode.vvalueProperty());
		}




		@Override
		public MutationResult update(final OnScrollVerticalEventProperty property,
									 final SuiNode node,
									 final ScrollPane fxNode) {
			node.getPropertyStore().getSafe(OnScrollVerticalEventProperty.class)
					.map(OnScrollVerticalEventProperty::getChangeListenerProxy)
					.ifPresent(proxy -> proxy.removeFrom(fxNode.vvalueProperty()));
			property.getChangeListenerProxy().addTo(fxNode.vvalueProperty());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final OnScrollVerticalEventProperty property,
									 final SuiNode node,
									 final ScrollPane fxNode) {
			property.getChangeListenerProxy().removeFrom(fxNode.vvalueProperty());
			return MutationResult.MUTATED;
		}

	}

}
