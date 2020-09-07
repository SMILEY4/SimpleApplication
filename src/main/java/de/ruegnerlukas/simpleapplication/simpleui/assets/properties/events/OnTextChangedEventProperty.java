package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.core.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.assets.events.TextContentEventData;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lombok.Getter;

public class OnTextChangedEventProperty extends AbstractEventListenerProperty<TextContentEventData> {



	/**
	 * The listener for events with {@link TextContentEventData}.
	 */
	@Getter
	private final SUIEventListener<TextContentEventData> listener;

	/**
	 * The proxy for the actual change listener.
	 */
	@Getter
	private final ChangeListenerProxy<String> changeListenerProxy;





	/**
	 * @param listener the listener for events with {@link TextContentEventData}.
	 */
	public OnTextChangedEventProperty(final SUIEventListener<TextContentEventData> listener) {
		super(OnTextChangedEventProperty.class);
		this.listener = listener;
		this.changeListenerProxy = new ChangeListenerProxy<>((prev, next) -> listener.onEvent(
				TextContentEventData.builder()
						.text(next)
						.prevText(prev)
						.build()
		));
	}




	public static class TextFieldUpdatingBuilder implements PropFxNodeUpdatingBuilder<OnTextChangedEventProperty, TextField> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers,
						  final SuiNode node,
						  final OnTextChangedEventProperty property,
						  final TextField fxNode) {
			property.getChangeListenerProxy().addTo(fxNode.textProperty());
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers,
									 final OnTextChangedEventProperty property,
									 final SuiNode node,
									 final TextField fxNode) {
			node.getPropertySafe(OnTextChangedEventProperty.class)
					.map(OnTextChangedEventProperty::getChangeListenerProxy)
					.ifPresent(proxy -> proxy.removeFrom(fxNode.textProperty()));
			property.getChangeListenerProxy().addTo(fxNode.textProperty());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers,
									 final OnTextChangedEventProperty property,
									 final SuiNode node,
									 final TextField fxNode) {
			property.getChangeListenerProxy().removeFrom(fxNode.textProperty());
			return MutationResult.MUTATED;
		}

	}






	public static class TextAreaUpdatingBuilder implements PropFxNodeUpdatingBuilder<OnTextChangedEventProperty, TextArea> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers,
						  final SuiNode node,
						  final OnTextChangedEventProperty property,
						  final TextArea fxNode) {
			property.getChangeListenerProxy().addTo(fxNode.textProperty());
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers,
									 final OnTextChangedEventProperty property,
									 final SuiNode node,
									 final TextArea fxNode) {
			node.getPropertySafe(OnTextChangedEventProperty.class)
					.map(OnTextChangedEventProperty::getChangeListenerProxy)
					.ifPresent(proxy -> proxy.removeFrom(fxNode.textProperty()));
			property.getChangeListenerProxy().addTo(fxNode.textProperty());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers,
									 final OnTextChangedEventProperty property,
									 final SuiNode node,
									 final TextArea fxNode) {
			property.getChangeListenerProxy().removeFrom(fxNode.textProperty());
			return MutationResult.MUTATED;
		}

	}

}