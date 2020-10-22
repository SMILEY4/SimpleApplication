package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.assets.events.MouseDragEventData;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.events.SuiEmittingEventListener;
import de.ruegnerlukas.simpleapplication.simpleui.core.events.SuiEventListener;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.builders.FactoryExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.tags.Tags;
import javafx.scene.Node;
import lombok.Getter;

public class OnMouseDragEnteredEventProperty extends AbstractEventListenerProperty<MouseDragEventData> {


	/**
	 * The listener for events with {@link MouseDragEventData}.
	 */
	@Getter
	private final SuiEventListener<MouseDragEventData> listener;




	/**
	 * @param propertyId see {@link de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty#getPropertyId()}.
	 * @param listener   the listener for events with {@link MouseDragEventData}.
	 */
	public OnMouseDragEnteredEventProperty(final String propertyId, final SuiEventListener<MouseDragEventData> listener) {
		super(OnMouseDragEnteredEventProperty.class, propertyId);
		Validations.INPUT.notNull(listener).exception("The listener may not be null");
		this.listener = listener;
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param propertyId see {@link de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty#getPropertyId()}.
		 * @param listener   the listener for events with {@link MouseDragEventData}.
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T eventMouseDragEntered(final String propertyId, final SuiEventListener<MouseDragEventData> listener) {
			getBuilderProperties().add(new OnMouseDragEnteredEventProperty(propertyId, listener));
			return (T) this;
		}

		/**
		 * @param tags the tags to attach to the emitted event
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T emitEventMouseDragEntered(final Tags tags) {
			getBuilderProperties().add(new OnActionEventProperty(".", new SuiEmittingEventListener<>(tags)));
			return (T) this;
		}

	}






	public static class UpdatingBuilder implements PropFxNodeUpdatingBuilder<OnMouseDragEnteredEventProperty, Node> {


		@Override
		public void build(final SuiNode node,
						  final OnMouseDragEnteredEventProperty property,
						  final Node fxNode) {
			setListener(fxNode, property);
		}




		@Override
		public MutationResult update(final OnMouseDragEnteredEventProperty property,
									 final SuiNode node,
									 final Node fxNode) {
			setListener(fxNode, property);
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final OnMouseDragEnteredEventProperty property,
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
		private void setListener(final Node fxNode, final OnMouseDragEnteredEventProperty property) {
			fxNode.setOnMouseDragEntered(e -> property.getListener().onEvent(
					MouseDragEventData.builder()
							.x(e.getX())
							.y(e.getY())
							.button(e.getButton())
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
