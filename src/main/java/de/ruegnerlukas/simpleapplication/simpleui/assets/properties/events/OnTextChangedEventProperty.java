package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.assets.events.TextContentEventData;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.EventProperties;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.FactoryExtension;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lombok.Getter;

public class OnTextChangedEventProperty extends AbstractEventListenerProperty<TextContentEventData> {


	/**
	 * The listener for events with {@link TextContentEventData}.
	 */
	@Getter
	private final SuiEventListener<TextContentEventData> listener;

	/**
	 * The proxy for the actual change listener.
	 */
	@Getter
	private final ChangeListenerProxy<String> changeListenerProxy;




	/**
	 * @param propertyId see {@link de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty#getPropertyId()}.
	 * @param listener   the listener for events with {@link TextContentEventData}.
	 */
	public OnTextChangedEventProperty(final String propertyId, final SuiEventListener<TextContentEventData> listener) {
		super(OnTextChangedEventProperty.class, propertyId);
		this.listener = listener;
		this.changeListenerProxy = new ChangeListenerProxy<>((prev, next) -> listener.onEvent(
				TextContentEventData.builder()
						.text(next)
						.prevText(prev)
						.build()
		));
	}




	@SuppressWarnings ("unchecked")
	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		default T eventTextChanged(final SuiEventListener<TextContentEventData> listener) {
			getFactoryInternalProperties().add(EventProperties.eventTextChanged(listener));
			return (T) this;
		}
		default T eventTextChanged(final String propertyId, final SuiEventListener<TextContentEventData> listener) {
			getFactoryInternalProperties().add(EventProperties.eventTextChanged(propertyId, listener));
			return (T) this;
		}

	}






	public static class TextFieldUpdatingBuilder implements PropFxNodeUpdatingBuilder<OnTextChangedEventProperty, TextField> {


		@Override
		public void build(final SuiNode node,
						  final OnTextChangedEventProperty property,
						  final TextField fxNode) {
			property.getChangeListenerProxy().addTo(fxNode.textProperty());
		}




		@Override
		public MutationResult update(final OnTextChangedEventProperty property,
									 final SuiNode node,
									 final TextField fxNode) {
			node.getPropertyStore().getSafe(OnTextChangedEventProperty.class)
					.map(OnTextChangedEventProperty::getChangeListenerProxy)
					.ifPresent(proxy -> proxy.removeFrom(fxNode.textProperty()));
			property.getChangeListenerProxy().addTo(fxNode.textProperty());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final OnTextChangedEventProperty property,
									 final SuiNode node,
									 final TextField fxNode) {
			property.getChangeListenerProxy().removeFrom(fxNode.textProperty());
			return MutationResult.MUTATED;
		}

	}






	public static class TextAreaUpdatingBuilder implements PropFxNodeUpdatingBuilder<OnTextChangedEventProperty, TextArea> {


		@Override
		public void build(final SuiNode node,
						  final OnTextChangedEventProperty property,
						  final TextArea fxNode) {
			property.getChangeListenerProxy().addTo(fxNode.textProperty());
		}




		@Override
		public MutationResult update(final OnTextChangedEventProperty property,
									 final SuiNode node,
									 final TextArea fxNode) {
			node.getPropertyStore().getSafe(OnTextChangedEventProperty.class)
					.map(OnTextChangedEventProperty::getChangeListenerProxy)
					.ifPresent(proxy -> proxy.removeFrom(fxNode.textProperty()));
			property.getChangeListenerProxy().addTo(fxNode.textProperty());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final OnTextChangedEventProperty property,
									 final SuiNode node,
									 final TextArea fxNode) {
			property.getChangeListenerProxy().removeFrom(fxNode.textProperty());
			return MutationResult.MUTATED;
		}

	}

}
