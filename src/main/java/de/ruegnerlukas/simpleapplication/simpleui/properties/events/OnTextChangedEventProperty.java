package de.ruegnerlukas.simpleapplication.simpleui.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.events.FocusEventData;
import de.ruegnerlukas.simpleapplication.simpleui.events.SUIEventListener;
import de.ruegnerlukas.simpleapplication.simpleui.events.SuiEvent;
import de.ruegnerlukas.simpleapplication.simpleui.events.TextContentEventData;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;
import javafx.scene.control.TextField;
import lombok.Getter;

public class OnTextChangedEventProperty extends AbstractObservableListenerProperty<TextContentEventData, String> {


	/**
	 * The identifying string of the event.
	 */
	public static final String EVENT_ID = "text.changed";

	/**
	 * The listener for events with {@link FocusEventData}.
	 */
	@Getter
	private final SUIEventListener<TextContentEventData> listener;




	/**
	 * @param listener the listener for events with {@link TextContentEventData}.
	 */
	public OnTextChangedEventProperty(final SUIEventListener<TextContentEventData> listener) {
		super(OnTextChangedEventProperty.class, (value, prev, next) -> {
			listener.onEvent(new SuiEvent<>(
					EVENT_ID,
					TextContentEventData.builder()
							.text(next)
							.prevText(prev)
							.build()
			));
		});
		this.listener = listener;
	}




	public static class TextFieldUpdatingBuilder implements PropFxNodeUpdatingBuilder<OnTextChangedEventProperty, TextField> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers, final SuiNode node, final OnTextChangedEventProperty property,
						  final TextField fxNode) {
			fxNode.textProperty().addListener(property.getChangeListener());
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers, final OnTextChangedEventProperty property,
									 final SuiNode node, final TextField fxNode) {
			node.getPropertySafe(OnTextChangedEventProperty.class).ifPresent(prop -> {
				fxNode.textProperty().removeListener(prop.getChangeListener());
			});
			fxNode.textProperty().addListener(property.getChangeListener());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers, final OnTextChangedEventProperty property,
									 final SuiNode node, final TextField fxNode) {
			fxNode.textProperty().removeListener(property.getChangeListener());
			return MutationResult.MUTATED;
		}

	}

}
