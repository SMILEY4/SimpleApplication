package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.assets.events.TextContentEventData;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.builders.FactoryExtension;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lombok.Getter;

public class OnTextEnteredEventProperty extends AbstractEventListenerProperty<TextContentEventData> {


	/**
	 * The listener for events with {@link TextContentEventData}.
	 */
	@Getter
	private final SuiEventListener<TextContentEventData> listener;

	/**
	 * The event handler for listening to key events of text areas
	 * without reserving {@link javafx.scene.Node#setOnKeyReleased(EventHandler)}.
	 */
	@Getter
	private final EventHandler<KeyEvent> textAreaEventHandler;




	/**
	 * @param propertyId see {@link de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty#getPropertyId()}.
	 * @param listener   the listener for events with {@link TextContentEventData}.
	 */
	public OnTextEnteredEventProperty(final String propertyId, final SuiEventListener<TextContentEventData> listener) {
		super(OnTextEnteredEventProperty.class, propertyId);
		Validations.INPUT.notNull(listener).exception("The listener may not be null");
		this.listener = listener;
		this.textAreaEventHandler = e -> {
			if (e.getCode() == KeyCode.ENTER && e.isShortcutDown()) {
				TextArea area = (TextArea) e.getSource();
				listener.onEvent(
						TextContentEventData.builder()
								.text(area.getText())
								.prevText(area.getText())
								.build()
				);
			}
		};
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param propertyId see {@link de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty#getPropertyId()}.
		 * @param listener   the listener for events with {@link TextContentEventData}.
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T eventTextEntered(final String propertyId, final SuiEventListener<TextContentEventData> listener) {
			getBuilderProperties().add(new OnTextEnteredEventProperty(propertyId, listener));
			return (T) this;
		}

	}






	public static class TextFieldUpdatingBuilder implements PropFxNodeUpdatingBuilder<OnTextEnteredEventProperty, TextField> {


		@Override
		public void build(final SuiNode node,
						  final OnTextEnteredEventProperty property,
						  final TextField fxNode) {
			setListener(fxNode, property);
		}




		@Override
		public MutationResult update(final OnTextEnteredEventProperty property,
									 final SuiNode node,
									 final TextField fxNode) {
			setListener(fxNode, property);
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final OnTextEnteredEventProperty property,
									 final SuiNode node,
									 final TextField fxNode) {
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
			fxNode.setOnAction(e -> property.getListener().onEvent(
					TextContentEventData.builder()
							.text(fxNode.getText())
							.prevText(fxNode.getText())
							.build()
			));
		}

	}






	public static class TextAreaUpdatingBuilder implements PropFxNodeUpdatingBuilder<OnTextEnteredEventProperty, TextArea> {


		@Override
		public void build(final SuiNode node,
						  final OnTextEnteredEventProperty property,
						  final TextArea fxNode) {
			fxNode.addEventHandler(KeyEvent.KEY_RELEASED, property.getTextAreaEventHandler());
		}




		@Override
		public MutationResult update(final OnTextEnteredEventProperty property,
									 final SuiNode node,
									 final TextArea fxNode) {
			node.getPropertyStore().getSafe(OnTextEnteredEventProperty.class).ifPresent(prop -> {
				fxNode.removeEventHandler(KeyEvent.KEY_RELEASED, prop.getTextAreaEventHandler());
			});
			fxNode.addEventHandler(KeyEvent.KEY_RELEASED, property.getTextAreaEventHandler());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final OnTextEnteredEventProperty property,
									 final SuiNode node,
									 final TextArea fxNode) {
			fxNode.removeEventHandler(KeyEvent.KEY_RELEASED, property.getTextAreaEventHandler());
			return MutationResult.MUTATED;
		}


	}


}
