package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.assets.events.ScrollEventData;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.events.SuiEmittingEventListener;
import de.ruegnerlukas.simpleapplication.simpleui.core.events.SuiEventListener;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.builders.FactoryExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.tags.Tags;
import javafx.scene.control.ScrollPane;
import lombok.Getter;

public class OnScrollHorizontalEventProperty extends AbstractEventListenerProperty<ScrollEventData> {


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
	public OnScrollHorizontalEventProperty(final String propertyId, final SuiEventListener<ScrollEventData> listener) {
		super(OnScrollHorizontalEventProperty.class, propertyId);
		Validations.INPUT.notNull(listener).exception("The listener may not be null");
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




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param propertyId see {@link de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty#getPropertyId()}.
		 * @param listener   the listener for events with {@link ScrollEventData}.
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T eventScrollHorizontal(final String propertyId, final SuiEventListener<ScrollEventData> listener) {
			getBuilderProperties().add(new OnScrollHorizontalEventProperty(propertyId, listener));
			return (T) this;
		}

		/**
		 * @param tags the tags to attach to the emitted event
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T emitEventScrollHorizontal(final Tags tags) {
			getBuilderProperties().add(new OnActionEventProperty(".", new SuiEmittingEventListener<>(tags)));
			return (T) this;
		}


	}






	public static class ScrollPaneUpdatingBuilder implements PropFxNodeUpdatingBuilder<OnScrollHorizontalEventProperty, ScrollPane> {


		@Override
		public void build(final SuiNode node,
						  final OnScrollHorizontalEventProperty property,
						  final ScrollPane fxNode) {
			property.getChangeListenerProxy().addTo(fxNode.hvalueProperty());
		}




		@Override
		public MutationResult update(final OnScrollHorizontalEventProperty property,
									 final SuiNode node,
									 final ScrollPane fxNode) {
			node.getPropertyStore().getSafe(OnScrollHorizontalEventProperty.class)
					.map(OnScrollHorizontalEventProperty::getChangeListenerProxy)
					.ifPresent(proxy -> proxy.removeFrom(fxNode.hvalueProperty()));
			property.getChangeListenerProxy().addTo(fxNode.hvalueProperty());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final OnScrollHorizontalEventProperty property,
									 final SuiNode node,
									 final ScrollPane fxNode) {
			property.getChangeListenerProxy().removeFrom(fxNode.hvalueProperty());
			return MutationResult.MUTATED;
		}

	}

}
