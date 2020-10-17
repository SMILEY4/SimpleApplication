package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.jfxelements.ExtendedListView;
import de.ruegnerlukas.simpleapplication.simpleui.assets.events.ItemSelectedEventData;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.EventProperties;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.FactoryExtension;
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
		this.listener = listener;
		this.proxyListener = selectedItems -> listener.onEvent(new ItemSelectedEventData<>(selectedItems));
	}




	@SuppressWarnings ("unchecked")
	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		default <E> T eventItemSelected(final String propertyId, final SuiEventListener<ItemSelectedEventData<E>> listener) {
			getFactoryInternalProperties().add(EventProperties.eventItemsSelected(propertyId, listener));
			return (T) this;
		}

		default <E> T eventItemSelected(final String propertyId,
										final Class<E> expectedType,
										final SuiEventListener<ItemSelectedEventData<E>> listener) {
			getFactoryInternalProperties().add(EventProperties.eventItemsSelected(propertyId, expectedType, listener));
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
