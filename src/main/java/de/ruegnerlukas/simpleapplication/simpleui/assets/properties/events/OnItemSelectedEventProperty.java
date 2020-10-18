package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.jfxelements.ExtendedListView;
import de.ruegnerlukas.simpleapplication.simpleui.assets.events.ItemSelectedEventData;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.events.SuiEventListener;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.builders.FactoryExtension;
import javafx.scene.control.ListView;
import lombok.Getter;

import java.util.List;
import java.util.function.Consumer;

public class OnItemSelectedEventProperty<T> extends AbstractEventListenerProperty<ItemSelectedEventData<T>> {


	/**
	 * The listener for events with {@link ItemSelectedEventData}.
	 */
	@Getter
	private final SuiEventListener<ItemSelectedEventData<T>> listener;


	/**
	 * The proxy for the actual listener.
	 */
	@Getter
	private final Consumer<List<T>> proxyListener;




	/**
	 * @param propertyId see {@link de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty#getPropertyId()}.
	 * @param listener   the listener for events with {@link ItemSelectedEventData<T>}.
	 */
	public OnItemSelectedEventProperty(final String propertyId, final SuiEventListener<ItemSelectedEventData<T>> listener) {
		super(OnItemSelectedEventProperty.class, propertyId);
		Validations.INPUT.notNull(listener).exception("The listener may not be null");
		this.listener = listener;
		this.proxyListener = selectedItems -> listener.onEvent(new ItemSelectedEventData<>(selectedItems));
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param propertyId   see {@link de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty#getPropertyId()}.
		 * @param expectedType the expected type of the item
		 * @param listener     the listener for events with {@link ItemSelectedEventData}.
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default <E> T eventItemSelected(final String propertyId,
										final Class<E> expectedType,
										final SuiEventListener<ItemSelectedEventData<E>> listener) {
			return eventItemSelected(propertyId, listener);
		}

		/**
		 * @param propertyId see {@link de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty#getPropertyId()}.
		 * @param listener   the listener for events with {@link ItemSelectedEventData}.
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default <E> T eventItemSelected(final String propertyId, final SuiEventListener<ItemSelectedEventData<E>> listener) {
			getBuilderProperties().add(new OnItemSelectedEventProperty<>(propertyId, listener));
			return (T) this;
		}


	}






	public static class ListViewUpdatingBuilder<T> implements PropFxNodeUpdatingBuilder<OnItemSelectedEventProperty<T>, ListView<T>> {


		@Override
		public void build(final SuiNode node, final OnItemSelectedEventProperty<T> property, final ListView<T> fxNode) {
			((ExtendedListView<T>) fxNode).setSelectionListener(property.getProxyListener());
		}




		@Override
		public MutationResult update(final OnItemSelectedEventProperty<T> property, final SuiNode node, final ListView<T> fxNode) {
			((ExtendedListView<T>) fxNode).setSelectionListener(property.getProxyListener());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final OnItemSelectedEventProperty<T> property, final SuiNode node, final ListView<T> fxNode) {
			((ExtendedListView<T>) fxNode).setSelectionListener(null);
			return MutationResult.MUTATED;
		}

	}


}
