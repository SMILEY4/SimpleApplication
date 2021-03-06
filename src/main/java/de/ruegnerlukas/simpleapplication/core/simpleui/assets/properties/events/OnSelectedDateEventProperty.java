package de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.events;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.events.DateSelectedEventData;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.events.SuiEmittingEventListener;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.events.SuiEventListener;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.builders.FactoryExtension;
import de.ruegnerlukas.simpleapplication.common.tags.Tags;
import javafx.scene.control.DatePicker;
import lombok.Getter;

public class OnSelectedDateEventProperty extends AbstractEventListenerProperty<DateSelectedEventData> {


	/**
	 * The listener for events with {@link DateSelectedEventData}.
	 */
	@Getter
	private final SuiEventListener<DateSelectedEventData> listener;




	/**
	 * @param propertyId see {@link de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiProperty#getPropertyId()}.
	 * @param listener   the listener for events with {@link DateSelectedEventData}.
	 */
	public OnSelectedDateEventProperty(final String propertyId, final SuiEventListener<DateSelectedEventData> listener) {
		super(OnSelectedDateEventProperty.class, propertyId);
		Validations.INPUT.notNull(listener).exception("The listener may not be null");
		this.listener = listener;
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param propertyId see {@link de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiProperty#getPropertyId()}.
		 * @param listener   the listener for events with {@link DateSelectedEventData}.
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T eventSelectedDate(final String propertyId, final SuiEventListener<DateSelectedEventData> listener) {
			getBuilderProperties().add(new OnSelectedDateEventProperty(propertyId, listener));
			return (T) this;
		}

		/**
		 * @param tags the tags to attach to the emitted event
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T emitEventSelectedDate(final Tags tags) {
			getBuilderProperties().add(new OnSelectedDateEventProperty(".", new SuiEmittingEventListener<>(tags)));
			return (T) this;
		}

	}






	public static class DatePickerUpdatingBuilder implements PropFxNodeUpdatingBuilder<OnSelectedDateEventProperty, DatePicker> {


		@Override
		public void build(final SuiNode node,
						  final OnSelectedDateEventProperty property,
						  final DatePicker fxNode) {
			setListener(fxNode, property);
		}




		@Override
		public MutationResult update(final OnSelectedDateEventProperty property,
									 final SuiNode node,
									 final DatePicker fxNode) {
			setListener(fxNode, property);
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final OnSelectedDateEventProperty property,
									 final SuiNode node,
									 final DatePicker fxNode) {
			fxNode.setOnAction(null);
			return MutationResult.MUTATED;
		}




		/**
		 * Attaches the listener to the given fx node.
		 *
		 * @param fxNode   the fx node to listen to
		 * @param property the property with the listener to add
		 */
		private void setListener(final DatePicker fxNode, final OnSelectedDateEventProperty property) {
			fxNode.setOnAction(e -> property.getListener().onEvent(new DateSelectedEventData(fxNode.getValue(), e)));
		}

	}

}
