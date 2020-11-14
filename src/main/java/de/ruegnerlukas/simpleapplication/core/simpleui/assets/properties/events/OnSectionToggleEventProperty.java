package de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.events;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.jfxelements.ExtendedAccordion;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.events.SectionEventData;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.events.SuiEmittingEventListener;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.events.SuiEventListener;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.builders.FactoryExtension;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.tags.Tags;
import lombok.Getter;

import java.util.function.BiConsumer;

public class OnSectionToggleEventProperty extends AbstractEventListenerProperty<SectionEventData> {


	/**
	 * The listener for events with {@link SectionEventData}.
	 */
	@Getter
	private final SuiEventListener<SectionEventData> listener;

	/**
	 * The actual listener listening to the sections.
	 */
	@Getter
	private final BiConsumer<String, Boolean> proxylistener;




	/**
	 * @param propertyId see {@link de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiProperty#getPropertyId()}.
	 * @param listener   the listener for events with {@link SectionEventData}.
	 */
	public OnSectionToggleEventProperty(final String propertyId, final SuiEventListener<SectionEventData> listener) {
		super(OnSectionToggleEventProperty.class, propertyId);
		Validations.INPUT.notNull(listener).exception("The listener may not be null");
		this.listener = listener;
		this.proxylistener = (title, expanded) -> listener.onEvent(
				SectionEventData.builder()
						.sectionTitle(title)
						.expanded(expanded)
						.build()
		);
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param propertyId see {@link de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiProperty#getPropertyId()}.
		 * @param listener   the listener for events with {@link SectionEventData}.
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T eventToggleSection(final String propertyId, final SuiEventListener<SectionEventData> listener) {
			getBuilderProperties().add(new OnSectionToggleEventProperty(propertyId, listener));
			return (T) this;
		}

		/**
		 * @param tags the tags to attach to the emitted event
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T emitEventToggleSection(final Tags tags) {
			getBuilderProperties().add(new OnActionEventProperty(".", new SuiEmittingEventListener<>(tags)));
			return (T) this;
		}
	}






	public static class AccordionUpdatingBuilder implements PropFxNodeUpdatingBuilder<OnSectionToggleEventProperty, ExtendedAccordion> {


		@Override
		public void build(final SuiNode node, final OnSectionToggleEventProperty property, final ExtendedAccordion fxNode) {
			fxNode.setExpandedSectionChangedListener(property.getProxylistener());
		}




		@Override
		public MutationResult update(final OnSectionToggleEventProperty property, final SuiNode node, final ExtendedAccordion fxNode) {
			fxNode.setExpandedSectionChangedListener(property.getProxylistener());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final OnSectionToggleEventProperty property, final SuiNode node, final ExtendedAccordion fxNode) {
			fxNode.setExpandedSectionChangedListener(null);
			return MutationResult.MUTATED;
		}

	}

}
