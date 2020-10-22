package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.assets.events.KeyEventData;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.events.SuiEmittingEventListener;
import de.ruegnerlukas.simpleapplication.simpleui.core.events.SuiEventListener;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.builders.FactoryExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.tags.Tags;
import javafx.scene.Node;
import lombok.Getter;

public class OnKeyReleasedEventProperty extends AbstractEventListenerProperty<KeyEventData> {


	/**
	 * The listener for events with {@link KeyEventData}.
	 */
	@Getter
	private final SuiEventListener<KeyEventData> listener;




	/**
	 * @param propertyId see {@link de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty#getPropertyId()}.
	 * @param listener   the listener for events with {@link KeyEventData}.
	 */
	public OnKeyReleasedEventProperty(final String propertyId, final SuiEventListener<KeyEventData> listener) {
		super(OnKeyReleasedEventProperty.class, propertyId);
		Validations.INPUT.notNull(listener).exception("The listener may not be null");
		this.listener = listener;
	}


	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param propertyId see {@link de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty#getPropertyId()}.
		 * @param listener   the listener for events with {@link KeyEventData}.
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T eventKeyReleased(final String propertyId, final SuiEventListener<KeyEventData> listener) {
			getBuilderProperties().add(new OnKeyReleasedEventProperty(propertyId, listener));
			return (T) this;
		}

		/**
		 * @param tags the tags to attach to the emitted event
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T emitEventKeyReleased(final Tags tags) {
			getBuilderProperties().add(new OnKeyReleasedEventProperty(".", new SuiEmittingEventListener<>(tags)));
			return (T) this;
		}

	}


	public static class UpdatingBuilder implements PropFxNodeUpdatingBuilder<OnKeyReleasedEventProperty, Node> {


		@Override
		public void build(final SuiNode node,
						  final OnKeyReleasedEventProperty property,
						  final Node fxNode) {
			setListener(fxNode, property);
		}




		@Override
		public MutationResult update(final OnKeyReleasedEventProperty property,
									 final SuiNode node,
									 final Node fxNode) {
			setListener(fxNode, property);
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final OnKeyReleasedEventProperty property,
									 final SuiNode node,
									 final Node fxNode) {
			fxNode.setOnKeyPressed(null);
			return MutationResult.MUTATED;
		}




		/**
		 * Attaches the listener to the given fx node.
		 *
		 * @param fxNode   the fx node to listen to
		 * @param property the property with the listener to add
		 */
		private void setListener(final Node fxNode, final OnKeyReleasedEventProperty property) {
			fxNode.setOnKeyReleased(e -> property.getListener().onEvent(
					KeyEventData.builder()
							.keyCode(e.getCode())
							.character(e.getCharacter())
							.altDown(e.isAltDown())
							.ctrlDown(e.isControlDown())
							.metaDown(e.isMetaDown())
							.shiftDown(e.isShiftDown())
							.shortcutDown(e.isShortcutDown())
							.source(e)
							.build()
			));
		}

	}


}
