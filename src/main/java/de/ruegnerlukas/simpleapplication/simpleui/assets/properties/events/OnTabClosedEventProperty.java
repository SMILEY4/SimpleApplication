package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.jfxelements.ExtendedTabPane;
import de.ruegnerlukas.simpleapplication.simpleui.assets.events.TabActionEventData;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import lombok.Getter;

public class OnTabClosedEventProperty extends AbstractEventListenerProperty<TabActionEventData> {


	/**
	 * The listener for events with {@link TabActionEventData}.
	 */
	@Getter
	private final SuiEventListener<TabActionEventData> listener;




	/**
	 * @param propertyId see {@link de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty#getPropertyId()}.
	 * @param listener   the listener for events with {@link TabActionEventData}.
	 */
	public OnTabClosedEventProperty(final String propertyId, final SuiEventListener<TabActionEventData> listener) {
		super(OnTabClosedEventProperty.class, propertyId);
		this.listener = listener;
	}




	public static class TabPaneUpdatingBuilder implements PropFxNodeUpdatingBuilder<OnTabClosedEventProperty, ExtendedTabPane> {


		@Override
		public void build(final SuiNode node,
						  final OnTabClosedEventProperty property,
						  final ExtendedTabPane fxNode) {
			setListener(fxNode, property);
		}




		@Override
		public MutationResult update(final OnTabClosedEventProperty property,
									 final SuiNode node,
									 final ExtendedTabPane fxNode) {
			setListener(fxNode, property);
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final OnTabClosedEventProperty property,
									 final SuiNode node,
									 final ExtendedTabPane fxNode) {
			fxNode.setOnTabClosed(null);
			return MutationResult.MUTATED;
		}




		/**
		 * Attaches the listener to the given fx node.
		 *
		 * @param fxNode   the fx node to listen to
		 * @param property the property with the listener to add
		 */
		private void setListener(final ExtendedTabPane fxNode, final OnTabClosedEventProperty property) {
			fxNode.setOnTabClosed(tab -> property.getListener().onEvent(
					TabActionEventData.builder()
							.tab(tab)
							.source(null)
							.build()
			));
		}

	}


}
