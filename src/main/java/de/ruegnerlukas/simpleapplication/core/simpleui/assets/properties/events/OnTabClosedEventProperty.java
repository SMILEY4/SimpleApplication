package de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.events;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.jfxelements.ExtendedTabPane;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.events.TabActionEventData;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.events.SuiEmittingEventListener;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.events.SuiEventListener;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.builders.FactoryExtension;
import de.ruegnerlukas.simpleapplication.common.tags.Tags;
import javafx.scene.control.Tab;
import lombok.Getter;

import java.util.function.Consumer;

public class OnTabClosedEventProperty extends AbstractEventListenerProperty<TabActionEventData> {


	/**
	 * The listener for events with {@link TabActionEventData}.
	 */
	@Getter
	private final SuiEventListener<TabActionEventData> listener;


	/**
	 * The actual listener.
	 */
	@Getter
	private final Consumer<Tab> proxyListener;




	/**
	 * @param propertyId see {@link de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiProperty#getPropertyId()}.
	 * @param listener   the listener for events with {@link TabActionEventData}.
	 */
	public OnTabClosedEventProperty(final String propertyId, final SuiEventListener<TabActionEventData> listener) {
		super(OnTabClosedEventProperty.class, propertyId);
		Validations.INPUT.notNull(listener).exception("The listener may not be null");
		this.listener = listener;
		this.proxyListener = tab -> getListener().onEvent(
				TabActionEventData.builder()
						.title(tab.getText())
						.source(null)
						.build()
		);
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param propertyId see {@link de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiProperty#getPropertyId()}.
		 * @param listener   the listener for events with {@link TabActionEventData}.
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T eventClosedTab(final String propertyId, final SuiEventListener<TabActionEventData> listener) {
			getBuilderProperties().add(new OnTabClosedEventProperty(propertyId, listener));
			return (T) this;
		}

		/**
		 * @param tags the tags to attach to the emitted event
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T emitEventClosedTab(final Tags tags) {
			getBuilderProperties().add(new OnActionEventProperty(".", new SuiEmittingEventListener<>(tags)));
			return (T) this;
		}

	}






	public static class TabPaneUpdatingBuilder implements PropFxNodeUpdatingBuilder<OnTabClosedEventProperty, ExtendedTabPane> {


		@Override
		public void build(final SuiNode node, final OnTabClosedEventProperty property, final ExtendedTabPane fxNode) {
			fxNode.setOnTabClosed(property.getProxyListener());
		}




		@Override
		public MutationResult update(final OnTabClosedEventProperty property, final SuiNode node, final ExtendedTabPane fxNode) {
			fxNode.setOnTabClosed(property.getProxyListener());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final OnTabClosedEventProperty property, final SuiNode node, final ExtendedTabPane fxNode) {
			fxNode.setOnTabClosed(null);
			return MutationResult.MUTATED;
		}


	}


}
