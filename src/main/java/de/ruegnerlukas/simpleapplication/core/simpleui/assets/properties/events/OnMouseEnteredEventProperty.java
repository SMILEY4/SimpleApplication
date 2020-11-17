package de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.events;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.events.MouseMoveEventData;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.events.SuiEmittingEventListener;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.events.SuiEventListener;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.builders.FactoryExtension;
import de.ruegnerlukas.simpleapplication.common.tags.Tags;
import javafx.scene.Node;
import lombok.Getter;

public class OnMouseEnteredEventProperty extends AbstractEventListenerProperty<MouseMoveEventData> {


	/**
	 * The listener for events with {@link MouseMoveEventData}.
	 */
	@Getter
	private final SuiEventListener<MouseMoveEventData> listener;




	/**
	 * @param propertyId see {@link de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiProperty#getPropertyId()}.
	 * @param listener   the listener for events with {@link MouseMoveEventData}.
	 */
	public OnMouseEnteredEventProperty(final String propertyId, final SuiEventListener<MouseMoveEventData> listener) {
		super(OnMouseEnteredEventProperty.class, propertyId);
		Validations.INPUT.notNull(listener).exception("The listener may not be null");
		this.listener = listener;
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param propertyId see {@link de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiProperty#getPropertyId()}.
		 * @param listener   the listener for events with {@link MouseMoveEventData}.
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T eventMouseEntered(final String propertyId, final SuiEventListener<MouseMoveEventData> listener) {
			getBuilderProperties().add(new OnMouseEnteredEventProperty(propertyId, listener));
			return (T) this;
		}

		/**
		 * @param tags the tags to attach to the emitted event
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T emitEventMouseEntered(final Tags tags) {
			getBuilderProperties().add(new OnActionEventProperty(".", new SuiEmittingEventListener<>(tags)));
			return (T) this;
		}

	}






	public static class UpdatingBuilder implements PropFxNodeUpdatingBuilder<OnMouseEnteredEventProperty, Node> {


		@Override
		public void build(final SuiNode node,
						  final OnMouseEnteredEventProperty property,
						  final Node fxNode) {
			setListener(fxNode, property);
		}




		@Override
		public MutationResult update(final OnMouseEnteredEventProperty property,
									 final SuiNode node,
									 final Node fxNode) {
			setListener(fxNode, property);
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final OnMouseEnteredEventProperty property,
									 final SuiNode node,
									 final Node fxNode) {
			fxNode.setOnMouseClicked(null);
			return MutationResult.MUTATED;
		}




		/**
		 * Attaches the listener to the given fx node.
		 *
		 * @param fxNode   the fx node to listen to
		 * @param property the property with the listener to add
		 */
		private void setListener(final Node fxNode, final OnMouseEnteredEventProperty property) {
			fxNode.setOnMouseEntered(e -> property.getListener().onEvent(
					MouseMoveEventData.builder()
							.x(e.getX())
							.y(e.getY())
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
