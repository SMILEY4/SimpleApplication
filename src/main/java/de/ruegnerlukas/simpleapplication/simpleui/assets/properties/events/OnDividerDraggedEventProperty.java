package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events;

import de.ruegnerlukas.simpleapplication.common.utils.Pair;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.jfxelements.ExtendedSplitPane;
import de.ruegnerlukas.simpleapplication.simpleui.assets.events.DividerDraggedEventData;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import javafx.scene.control.SplitPane;
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
	 * @param propertyId see {@link de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty#getPropertyId()}.
	 * @param listener   the listener for events with {@link DividerDraggedEventData}.
	 */
	public OnDividerDraggedEventProperty(final String propertyId, final SuiEventListener<DividerDraggedEventData> listener) {
		super(OnDividerDraggedEventProperty.class, propertyId);
		this.listener = listener;
		this.changeListener = (index, positions) -> listener.onEvent(
				DividerDraggedEventData.builder()
						.dividerIndex(index)
						.prevPosition(positions.getLeft())
						.nextPosition(positions.getRight())
						.build()
		);
	}




	public static class SplitPaneUpdatingBuilder implements PropFxNodeUpdatingBuilder<OnDividerDraggedEventProperty, SplitPane> {


		@Override
		public void build(final SuiNode node,
						  final OnDividerDraggedEventProperty property,
						  final SplitPane fxNode) {
			((ExtendedSplitPane) fxNode).addDividerListener(property.getChangeListener());
		}




		@Override
		public MutationResult update(final OnDividerDraggedEventProperty property,
									 final SuiNode node,
									 final SplitPane fxNode) {
			node.getPropertyStore().getSafe(OnDividerDraggedEventProperty.class)
					.map(OnDividerDraggedEventProperty::getChangeListener)
					.ifPresent(((ExtendedSplitPane) fxNode)::removeDividerListener);
			((ExtendedSplitPane) fxNode).addDividerListener(property.getChangeListener());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final OnDividerDraggedEventProperty property,
									 final SuiNode node,
									 final SplitPane fxNode) {
			((ExtendedSplitPane) fxNode).removeDividerListener(property.getChangeListener());
			return MutationResult.MUTATED;
		}

	}

}
