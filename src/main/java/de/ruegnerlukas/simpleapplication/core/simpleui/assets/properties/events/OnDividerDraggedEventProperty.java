package de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.events;

import de.ruegnerlukas.simpleapplication.common.utils.Pair;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.jfxelements.ExtendedSplitPane;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.events.DividerDraggedEventData;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.events.SuiEmittingEventListener;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.events.SuiEventListener;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.builders.FactoryExtension;
import de.ruegnerlukas.simpleapplication.common.tags.Tags;
import lombok.Getter;

import java.util.function.BiConsumer;

public class OnDividerDraggedEventProperty extends AbstractEventListenerProperty<DividerDraggedEventData> {


	/**
	 * The listener for events with {@link DividerDraggedEventData}.
	 */
	@Getter
	private final SuiEventListener<DividerDraggedEventData> listener;

	/**
	 * The proxy for the actual change listener.
	 */
	@Getter
	private final BiConsumer<Integer, Pair<Number, Number>> changeListener;




	/**
	 * @param propertyId see {@link de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiProperty#getPropertyId()}.
	 * @param listener   the listener for events with {@link DividerDraggedEventData}.
	 */
	public OnDividerDraggedEventProperty(final String propertyId, final SuiEventListener<DividerDraggedEventData> listener) {
		super(OnDividerDraggedEventProperty.class, propertyId);
		Validations.INPUT.notNull(listener).exception("The listener may not be null");
		this.listener = listener;
		this.changeListener = (index, positions) -> listener.onEvent(
				DividerDraggedEventData.builder()
						.dividerIndex(index)
						.prevPosition(positions.getLeft())
						.nextPosition(positions.getRight())
						.build()
		);
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param propertyId see {@link de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiProperty#getPropertyId()}.
		 * @param listener   the listener for events with {@link DividerDraggedEventData}.
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T eventDividerDragged(final String propertyId, final SuiEventListener<DividerDraggedEventData> listener) {
			getBuilderProperties().add(new OnDividerDraggedEventProperty(propertyId, listener));
			return (T) this;
		}

		/**
		 * @param tags the tags to attach to the emitted event
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T emitEventDividerDragged(final Tags tags) {
			getBuilderProperties().add(new OnDividerDraggedEventProperty(".", new SuiEmittingEventListener<>(tags)));
			return (T) this;
		}


	}






	public static class SplitPaneUpdatingBuilder implements PropFxNodeUpdatingBuilder<OnDividerDraggedEventProperty, ExtendedSplitPane> {


		@Override
		public void build(final SuiNode node, final OnDividerDraggedEventProperty property, final ExtendedSplitPane fxNode) {
			fxNode.setDividerListener(property.getChangeListener());
		}




		@Override
		public MutationResult update(final OnDividerDraggedEventProperty property, final SuiNode node, final ExtendedSplitPane fxNode) {
			fxNode.setDividerListener(property.getChangeListener());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final OnDividerDraggedEventProperty property, final SuiNode node, final ExtendedSplitPane fxNode) {
			fxNode.setDividerListener(null);
			return MutationResult.MUTATED;
		}

	}

}
