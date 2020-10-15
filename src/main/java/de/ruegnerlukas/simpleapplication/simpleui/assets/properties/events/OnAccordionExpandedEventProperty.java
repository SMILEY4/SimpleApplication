package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.jfxelements.ExtendedAccordion;
import de.ruegnerlukas.simpleapplication.simpleui.assets.events.SectionEventData;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import lombok.Getter;

import java.util.function.BiConsumer;

public class OnAccordionExpandedEventProperty extends AbstractEventListenerProperty<SectionEventData> {


	/**
	 * The listener for events with {@link SectionEventData}.
	 */
	@Getter
	private final SuiEventListener<SectionEventData> listener;

	/**
	 * The actual listener listening to the accordion.
	 */
	@Getter
	private final BiConsumer<String, Boolean> proxylistener;




	/**
	 * @param propertyId see {@link de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty#getPropertyId()}.
	 * @param listener   the listener for events with {@link SectionEventData}.
	 */
	public OnAccordionExpandedEventProperty(final String propertyId, final SuiEventListener<SectionEventData> listener) {
		super(OnAccordionExpandedEventProperty.class, propertyId);
		this.listener = listener;
		this.proxylistener = (title, expanded) -> listener.onEvent(
				SectionEventData.builder()
						.sectionTitle(title)
						.expanded(expanded)
						.build()
		);
	}




	public static class AccordionUpdatingBuilder implements PropFxNodeUpdatingBuilder<OnAccordionExpandedEventProperty, ExtendedAccordion> {


		@Override
		public void build(final SuiNode node, final OnAccordionExpandedEventProperty property, final ExtendedAccordion fxNode) {
			fxNode.setExpandedSectionChangedListener(property.getProxylistener());
		}




		@Override
		public MutationResult update(final OnAccordionExpandedEventProperty property, final SuiNode node, final ExtendedAccordion fxNode) {
			fxNode.setExpandedSectionChangedListener(property.getProxylistener());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final OnAccordionExpandedEventProperty property, final SuiNode node, final ExtendedAccordion fxNode) {
			fxNode.setExpandedSectionChangedListener(null);
			return MutationResult.MUTATED;
		}

	}

}
