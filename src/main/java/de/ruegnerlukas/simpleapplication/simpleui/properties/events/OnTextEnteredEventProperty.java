package de.ruegnerlukas.simpleapplication.simpleui.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.events.SUIEventListener;
import de.ruegnerlukas.simpleapplication.simpleui.events.SuiEvent;
import de.ruegnerlukas.simpleapplication.simpleui.events.TextContentEventData;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;
import javafx.scene.control.TextField;
import lombok.Getter;

public class OnTextEnteredEventProperty extends AbstractEventListenerProperty<TextContentEventData> {


	/**
	 * The identifying string of the event.
	 */
	public static final String EVENT_ID = "text.entered";


	/**
	 * The listener for events with {@link TextContentEventData}.
	 */
	@Getter
	private final SUIEventListener<TextContentEventData> listener;




	/**
	 * @param listener the listener for events with {@link TextContentEventData}.
	 */
	public OnTextEnteredEventProperty(final SUIEventListener<TextContentEventData> listener) {
		super(OnTextEnteredEventProperty.class);
		this.listener = listener;
	}




	public static class TextFieldUpdatingBuilder implements PropFxNodeUpdatingBuilder<OnTextEnteredEventProperty, TextField> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers, final SuiNode node, final OnTextEnteredEventProperty property,
						  final TextField fxNode) {
			setListener(fxNode, property);
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers, final OnTextEnteredEventProperty property,
									 final SuiNode node, final TextField fxNode) {
			setListener(fxNode, property);
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers, final OnTextEnteredEventProperty property,
									 final SuiNode node, final TextField fxNode) {
			fxNode.setOnAction(null);
			return MutationResult.MUTATED;
		}




		/**
		 * Attaches the listener to the given textfield.
		 *
		 * @param fxNode   the textfield to listen to
		 * @param property the property with the listener to add
		 */
		private void setListener(final TextField fxNode, final OnTextEnteredEventProperty property) {
			fxNode.setOnAction(e -> property.getListener().onEvent(new SuiEvent<>(
					EVENT_ID,
					TextContentEventData.builder()
							.text(fxNode.getText())
							.prevText(fxNode.getText())
							.build()
			)));
		}

	}


}
