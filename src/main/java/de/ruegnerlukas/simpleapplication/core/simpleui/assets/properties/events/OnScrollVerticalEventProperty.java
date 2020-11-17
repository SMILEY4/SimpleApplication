package de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.events;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.events.ScrollEventData;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.events.SuiEmittingEventListener;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.events.SuiEventListener;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.builders.FactoryExtension;
import de.ruegnerlukas.simpleapplication.common.tags.Tags;
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
	 * @param propertyId see {@link de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiProperty#getPropertyId()}.
	 * @param listener   the listener for events with {@link ScrollEventData}.
	 */
	public OnScrollVerticalEventProperty(final String propertyId, final SuiEventListener<ScrollEventData> listener) {
		super(OnScrollVerticalEventProperty.class, propertyId);
		Validations.INPUT.notNull(listener).exception("The listener may not be null");
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




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param propertyId see {@link de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiProperty#getPropertyId()}.
		 * @param listener   the listener for events with {@link ScrollEventData}.
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T eventScrollVertical(final String propertyId, final SuiEventListener<ScrollEventData> listener) {
			getBuilderProperties().add(new OnScrollVerticalEventProperty(propertyId, listener));
			return (T) this;
		}

		/**
		 * @param tags the tags to attach to the emitted event
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T emitEventScrollVertical(final Tags tags) {
			getBuilderProperties().add(new OnActionEventProperty(".", new SuiEmittingEventListener<>(tags)));
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
