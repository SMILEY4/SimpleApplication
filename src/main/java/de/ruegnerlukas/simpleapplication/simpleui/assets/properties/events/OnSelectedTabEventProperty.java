package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.jfxelements.ExtendedTabPane;
import de.ruegnerlukas.simpleapplication.simpleui.assets.events.TabActionEventData;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.events.SuiEventListener;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.builders.FactoryExtension;
import javafx.scene.control.Tab;
import lombok.Getter;

import java.util.function.BiConsumer;

public class OnSelectedTabEventProperty extends AbstractEventListenerProperty<TabActionEventData> {


	/**
	 * The listener for events with {@link TabActionEventData}.
	 */
	@Getter
	private final SuiEventListener<TabActionEventData> listener;

	/**
	 * The proxy for the actual change listener.
	 */
	@Getter
	private final BiConsumer<Tab, Tab> listenerProxy;




	/**
	 * @param propertyId see {@link de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty#getPropertyId()}.
	 * @param listener   the listener for events with {@link TabActionEventData}.
	 */
	public OnSelectedTabEventProperty(final String propertyId, final SuiEventListener<TabActionEventData> listener) {
		super(OnSelectedTabEventProperty.class, propertyId);
		Validations.INPUT.notNull(listener).exception("The listener may not be null");
		this.listener = listener;
		this.listenerProxy = (prev, next) -> listener.onEvent(
				TabActionEventData.builder()
						.title(next.getText())
						.build()
		);
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param propertyId see {@link de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty#getPropertyId()}.
		 * @param listener   the listener for events with {@link TabActionEventData}.
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T eventSelectedTab(final String propertyId, final SuiEventListener<TabActionEventData> listener) {
			getBuilderProperties().add(new OnSelectedTabEventProperty(propertyId, listener));
			return (T) this;
		}

	}






	public static class TabPaneUpdatingBuilder implements PropFxNodeUpdatingBuilder<OnSelectedTabEventProperty, ExtendedTabPane> {


		@Override
		public void build(final SuiNode node, final OnSelectedTabEventProperty property, final ExtendedTabPane fxNode) {
			fxNode.setOnTabSelected(property.getListenerProxy());
		}




		@Override
		public MutationResult update(final OnSelectedTabEventProperty property, final SuiNode node, final ExtendedTabPane fxNode) {
			fxNode.setOnTabSelected(property.getListenerProxy());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final OnSelectedTabEventProperty property, final SuiNode node, final ExtendedTabPane fxNode) {
			fxNode.setOnTabSelected(null);
			return MutationResult.MUTATED;
		}

	}


}
