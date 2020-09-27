package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.assets.events.AccordionExpandedEventData;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import lombok.Getter;

public class OnAccordionExpandedEventProperty extends AbstractEventListenerProperty<AccordionExpandedEventData> {


	/**
	 * The listener for events with {@link AccordionExpandedEventData}.
	 */
	@Getter
	private final SuiEventListener<AccordionExpandedEventData> listener;

	/**
	 * The proxy for the actual change listener.
	 */
	@Getter
	private final ChangeListenerProxy<TitledPane> changeListenerProxy;




	/**
	 * @param propertyId see {@link de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty#getPropertyId()}.
	 * @param listener   the listener for events with {@link AccordionExpandedEventData}.
	 */
	public OnAccordionExpandedEventProperty(final String propertyId, final SuiEventListener<AccordionExpandedEventData> listener) {
		super(OnAccordionExpandedEventProperty.class, propertyId);
		this.listener = listener;
		this.changeListenerProxy = new ChangeListenerProxy<>(
				(prev, next) -> listener.onEvent(
						AccordionExpandedEventData.builder()
								.expandedTitle(next != null ? next.getText() : null)
								.prevExpandedTitle(prev != null ? prev.getText() : null)
								.build()
				));
	}




	public static class AccordionUpdatingBuilder implements PropFxNodeUpdatingBuilder<OnAccordionExpandedEventProperty, Accordion> {


		@Override
		public void build(final SuiNode node,
						  final OnAccordionExpandedEventProperty property,
						  final Accordion fxNode) {
			property.getChangeListenerProxy().addTo(fxNode.expandedPaneProperty());
		}




		@Override
		public MutationResult update(final OnAccordionExpandedEventProperty property,
									 final SuiNode node,
									 final Accordion fxNode) {
			node.getPropertyStore().getSafe(OnAccordionExpandedEventProperty.class)
					.map(OnAccordionExpandedEventProperty::getChangeListenerProxy)
					.ifPresent(proxy -> proxy.removeFrom(fxNode.expandedPaneProperty()));
			property.getChangeListenerProxy().addTo(fxNode.expandedPaneProperty());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final OnAccordionExpandedEventProperty property,
									 final SuiNode node,
									 final Accordion fxNode) {
			property.getChangeListenerProxy().removeFrom(fxNode.expandedPaneProperty());
			return MutationResult.MUTATED;
		}

	}

}
