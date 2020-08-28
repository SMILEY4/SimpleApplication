package de.ruegnerlukas.simpleapplication.simpleui.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.events.SUIEvent;
import de.ruegnerlukas.simpleapplication.simpleui.events.SUIEventListener;
import de.ruegnerlukas.simpleapplication.simpleui.events.SelectedIndexEventData;
import de.ruegnerlukas.simpleapplication.simpleui.events.SelectedItemEventData;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;
import javafx.scene.control.ChoiceBox;
import lombok.Getter;

import java.util.Optional;

public class OnSelectedIndexEventProperty extends AbstractEventListenerProperty<SelectedIndexEventData> {


	/**
	 * The listener for events with {@link SelectedItemEventData}.
	 */
	@Getter
	private final SUIEventListener<SelectedIndexEventData> listener;




	/**
	 * @param listener the listener for events with {@link SelectedIndexEventData}.
	 */
	public OnSelectedIndexEventProperty(final SUIEventListener<SelectedIndexEventData> listener) {
		super(OnSelectedIndexEventProperty.class);
		this.listener = listener;
	}




	public static class ChoiceBoxUpdatingBuilder implements PropFxNodeUpdatingBuilder<OnSelectedIndexEventProperty, ChoiceBox<?>> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers, final SUINode node, final OnSelectedIndexEventProperty property,
						  final ChoiceBox<?> fxNode) {
			setListener(property, fxNode);
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers, final OnSelectedIndexEventProperty property,
									 final SUINode node, final ChoiceBox<?> fxNode) {
			return MutationResult.REQUIRES_REBUILD;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers, final OnSelectedIndexEventProperty property,
									 final SUINode node, final ChoiceBox<?> fxNode) {
			return MutationResult.REQUIRES_REBUILD;
		}




		/**
		 * Sets the listener of the given property and the given choicebox.
		 *
		 * @param property  the property
		 * @param choiceBox the choiceBox
		 */
		private void setListener(final OnSelectedIndexEventProperty property, final ChoiceBox<?> choiceBox) {
			choiceBox.getSelectionModel().selectedIndexProperty().addListener((value, prev, next) -> {
				property.getListener().onEvent(new SUIEvent<>(
						"selection.index.choicebox",
						SelectedIndexEventData.builder()
								.index(Optional.ofNullable(next).map(Number::intValue).orElse(null))
								.index(Optional.ofNullable(prev).map(Number::intValue).orElse(null))
								.build()
				));
			});
		}

	}


}
