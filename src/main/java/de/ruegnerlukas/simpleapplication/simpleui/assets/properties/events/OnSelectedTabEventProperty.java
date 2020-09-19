package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.assets.events.TabActionEventData;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import lombok.Getter;

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
	private final ChangeListenerProxy<Tab> changeListenerProxy;




	/**
	 * @param propertyId see {@link de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty#getPropertyId()}.
	 * @param listener   the listener for events with {@link TabActionEventData}.
	 */
	public OnSelectedTabEventProperty(final String propertyId, final SuiEventListener<TabActionEventData> listener) {
		super(OnSelectedTabEventProperty.class, propertyId);
		this.listener = listener;
		this.changeListenerProxy = new ChangeListenerProxy<>((prev, next) -> listener.onEvent(
				TabActionEventData.builder()
						.tab(next)
						.build()
		));
	}




	public static class TabPaneUpdatingBuilder implements PropFxNodeUpdatingBuilder<OnSelectedTabEventProperty, TabPane> {


		@Override
		public void build(final SuiNode node,
						  final OnSelectedTabEventProperty property,
						  final TabPane fxNode) {
			property.getChangeListenerProxy().addTo(fxNode.getSelectionModel().selectedItemProperty());
		}




		@Override
		public MutationResult update(final OnSelectedTabEventProperty property,
									 final SuiNode node,
									 final TabPane fxNode) {
			node.getPropertyStore().getSafe(OnSelectedTabEventProperty.class)
					.map(OnSelectedTabEventProperty::getChangeListenerProxy)
					.ifPresent(proxy -> proxy.removeFrom(fxNode.getSelectionModel().selectedItemProperty()));
			property.getChangeListenerProxy().addTo(fxNode.getSelectionModel().selectedItemProperty());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final OnSelectedTabEventProperty property,
									 final SuiNode node,
									 final TabPane fxNode) {
			property.getChangeListenerProxy().removeFrom(fxNode.getSelectionModel().selectedItemProperty());
			return MutationResult.MUTATED;
		}

	}





}
