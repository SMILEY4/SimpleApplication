package de.ruegnerlukas.simpleapplication.simpleui.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.events.SUIEvent;
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
		super(OnSelectedIndexEventProperty.class, (value, prev, next) -> listener.onEvent(new SUIEvent<>(
				EVENT_ID,
				SelectedIndexEventData.builder()
						.index(Optional.ofNullable(next).map(Number::intValue).orElse(null))
						.prevIndex(Optional.ofNullable(prev).map(Number::intValue).orElse(null))
						.build()
		)));
		this.listener = listener;
	}




	public static class ChoiceBoxUpdatingBuilder implements PropFxNodeUpdatingBuilder<OnSelectedIndexEventProperty, ChoiceBox<?>> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers, final SUINode node, final OnSelectedIndexEventProperty property,
						  final ChoiceBox<?> fxNode) {
			fxNode.getSelectionModel().selectedIndexProperty().addListener(property.getChangeListener());
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers, final OnSelectedIndexEventProperty property,
									 final SUINode node, final ChoiceBox<?> fxNode) {
			node.getPropertySafe(OnSelectedIndexEventProperty.class).ifPresent(prop -> {
				fxNode.getSelectionModel().selectedIndexProperty().removeListener(prop.getChangeListener());
			});
			fxNode.getSelectionModel().selectedIndexProperty().addListener(property.getChangeListener());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers, final OnSelectedIndexEventProperty property,
									 final SUINode node, final ChoiceBox<?> fxNode) {
			fxNode.getSelectionModel().selectedIndexProperty().addListener(property.getChangeListener());
			return MutationResult.MUTATED;
		}

	}


}
