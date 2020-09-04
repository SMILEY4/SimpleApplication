package de.ruegnerlukas.simpleapplication.simpleui.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.events.SUIEventListener;
import de.ruegnerlukas.simpleapplication.simpleui.events.SelectedIndexEventData;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;
import javafx.scene.control.ChoiceBox;
import lombok.Getter;

import java.util.Optional;

public class OnSelectedIndexEventProperty extends AbstractObservableListenerProperty<SelectedIndexEventData, Number> {


	/**
	 * The identifying string of the event.
	 */
	public static final String EVENT_ID = "selection.index.choicebox";

	/**
	 * The listener for events with {@link SelectedIndexEventData}.
	 */
	@Getter
	private SUIEventListener<SelectedIndexEventData> listener;




	/**
	 * @param listener the listener for events with {@link SelectedIndexEventData}.
	 */
	public OnSelectedIndexEventProperty(final SUIEventListener<SelectedIndexEventData> listener) {
		super(OnSelectedIndexEventProperty.class, (value, prev, next) -> listener.onEvent(
				SelectedIndexEventData.builder()
						.index(Optional.ofNullable(next).map(Number::intValue).orElse(null))
						.prevIndex(Optional.ofNullable(prev).map(Number::intValue).orElse(null))
						.build()
		));
		this.listener = listener;
	}




	public static class ChoiceBoxUpdatingBuilder implements PropFxNodeUpdatingBuilder<OnSelectedIndexEventProperty, ChoiceBox<?>> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers,
						  final SuiNode node,
						  final OnSelectedIndexEventProperty property,
						  final ChoiceBox<?> fxNode) {
			property.addChangeListenerTo(fxNode.getSelectionModel().selectedIndexProperty());
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers,
									 final OnSelectedIndexEventProperty property,
									 final SuiNode node,
									 final ChoiceBox<?> fxNode) {
			node.getPropertySafe(OnSelectedIndexEventProperty.class).ifPresent(prop -> {
				prop.removeChangeListenerFrom(fxNode.getSelectionModel().selectedIndexProperty());
			});
			property.addChangeListenerTo(fxNode.getSelectionModel().selectedIndexProperty());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers,
									 final OnSelectedIndexEventProperty property,
									 final SuiNode node,
									 final ChoiceBox<?> fxNode) {
			property.removeChangeListenerFrom(fxNode.getSelectionModel().selectedIndexProperty());
			return MutationResult.MUTATED;
		}

	}


}
