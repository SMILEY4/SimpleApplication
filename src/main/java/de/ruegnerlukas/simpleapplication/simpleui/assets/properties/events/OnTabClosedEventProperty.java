package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.jfxelements.ExtendedTabPane;
import de.ruegnerlukas.simpleapplication.simpleui.assets.events.TabActionEventData;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.EventProperties;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.FactoryExtension;
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
	 * @param propertyId see {@link de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty#getPropertyId()}.
	 * @param listener   the listener for events with {@link TabActionEventData}.
	 */
	public OnTabClosedEventProperty(final String propertyId, final SuiEventListener<TabActionEventData> listener) {
		super(OnTabClosedEventProperty.class, propertyId);
		this.listener = listener;
		this.proxyListener = tab -> getListener().onEvent(
				TabActionEventData.builder()
						.title(tab.getText())
						.source(null)
						.build()
		);
	}




	@SuppressWarnings ("unchecked")
	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		default T eventTabClosed(final String propertyId, final SuiEventListener<TabActionEventData> listener) {
			getFactoryInternalProperties().add(EventProperties.eventClosedTab(propertyId, listener));
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
